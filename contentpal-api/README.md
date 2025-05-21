[![Build Status](https://travis-ci.org/dmfs/ContentPal.svg?branch=master)](https://travis-ci.org/dmfs/ContentPal)
[![codecov](https://codecov.io/gh/dmfs/ContentPal/branch/master/graph/badge.svg)](https://codecov.io/gh/dmfs/ContentPal)

# ContentPal

A friend to help with Android ContentProvider operations.

## Rationale

Android uses the concept of "ContentProviders" to share data among apps quite successfully. While the concept behind these ContentProviders is quite powerful it
can be a real hassle to work with. Reading and writing data can be a a challenging task. The resulting code is easy to get wrong and often quite verbose and
hard to read. Using the efficient batch operations is non trivial as well, especially when working with "related" rows.

This library aims to add an abstraction layer to ContentProviders to handle operations in an object oriented manner. With ContentPal, ContentProvider operations
are written in a declarative way before being enqueued for aggregated execution.   
The resulting code is easier to write and read.

Note that this library is not meant to be an ORM and uses database terminology all over the place.

## Note well

The interfaces of this library are not considered 100% stable at this time. Design and names are still subject to change.

## Goals

This library has been created with specific goals in mind.

* reduce boilerplate when working with content providers
* provide a certain level of type safety, i.e. reduce the risk of writing data to the wrong table
* declarative way to describe operations
* utilize IPC limit as good as possible in order to make big transactions efficient
* automatically resolve references when inserting related rows in a single transaction

## ContactsPal

ContactsPal provides ContentProvider specific classes including some of the most important `Table` and `RowData` implementations like `RawContacts`
and `PhoneData`.

## CalendarPal

CalendarPal provides CalendarProvider specific classes including some of the most important `Table` and `RowData` implementations like `Events`
and `ReminderData`.

## Example

### Insert a Contact

#### Without ContactsPal

The following example is taken from
Android's [ContactsProvider Documentation](https://developer.android.com/guide/topics/providers/contacts-provider.html#Transactions). It shows the basic steps
to create a new contact with display name, phone and email.

```java
ArrayList<ContentProviderOperation> ops =
    new ArrayList<ContentProviderOperation>();

ContentProviderOperation.Builder op =
    ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, mSelectedAccount.getType())
    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, mSelectedAccount.getName());
ops.add(op.build());

op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
    .withValue(ContactsContract.Data.MIMETYPE,
        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
ops.add(op.build());

op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
    .withValue(ContactsContract.Data.MIMETYPE,
        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, phoneType);
ops.add(op.build());

op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
    .withValue(ContactsContract.Data.MIMETYPE,
        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, emailType);
op.withYieldAllowed(true);
ops.add(op.build());

getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
```

#### With ContactsPal

The following code snippets show the same operation with ContentPal/ContactsPal:

##### Step 1 - setup

Before you can perform any operations you need to create a few required objects like the tables to work with. Usually this has to be done only once.

```java
ContentProviderClient client =
  getContentResolver().acquireContentProviderClient(ContactsContract.AUTHORITY_URI);
OperationsQueue operationsQueue = new BasicOperationsQueue(client);
```

##### Step 2 - execute

Create an `OperationsBatch` which contains the operations to insert the RawContact and the data rows and enqueue them for execution.

```java
operationsQueue.enqueue(
  new Yieldable( // optional, not required when inserting only one contact
    new InsertRawContactBatch(
      account,
      // list the data to be inserted
      new DisplayNameData(name),
      new Typed(phoneType, new PhoneData(phone)),
      new Typed(emailType, new EmailData(email))))));
```

At this point you can enqueue more operations. All operations will be executed automatically when the transaction size grows too large or when `flush()` is
called.

References between the inserted rows will be resolved automatically.

Also note that all relevant types are generic having the contract they implement as the generic type. That makes it much harder to get confused. E.g. you can
not accidentally try to insert an email address into RawContacts.

##### Step 3 - shut down

Once everything is done, just make sure all pending operations get committed and close the `ContentProviderClient`

```java
operationsQueue.flush();
client.release();
```

### Reading Contacts

To read contacts you build a view onto the `Table` to read and iterate over a `RowSet` that contains the rows you need.

```java
// a sync-adapter view onto RawContacts, scoped to a specific account
View<ContactsContract.RawContacts> rawContacts =
  new Synced<>(new AccountScoped<>(new RawContacts(client), account));
    
// iterate over all "dirty" contacts 
for (RowSnapshot<ContactsContract.RawContacts> rowSnapshot:new Dirty<>(rawContacts))
{
  // work with the row snapshot
   
  // for instance, set the dirty flag to 0 like so
  operationsQueue.enqueue(
    new SingletonOperationsBatch(
      new new Put<>(rowSnapshot, new CleanData())));
}
```

The same can also be achieved in a declarative way like this:

```java
operationsQueue.enqueue(
  new MappedRowSetBatch<>(
    // map all dirty rows of a specific account
    new Dirty<>(new Synced<>(new AccountScoped<>(new RawContacts(client), account))),
    new Function<ContactsContract.RawContacts, Operation<>>()
    {
      public Operation<> apply(RowSnapshot<ContactsContract.RawContacts> rowSnapwhot)
      {
        // work with the row and return an Operation, can be a NoOp or this:
        return new Put<>(rowSnapshot, new CleanData());
      }
    }
  )
);
```

## License

Copyright dmfs GmbH 2017, licensed under Apache2.
