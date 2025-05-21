# xmlobjects

__A lightweight high level XML pull parser framework__

This library is meant to build an XML stream parser that allows memory efficient operation while still beeing convenient. With this library you pull the objects you're interested in one by one. You can process one object before you parse the next one, allowing you to recycle the returned object.

## Key benefits

* __On-the-fly processing__
Allows to process data right when it's parsed, e.g. after parsing a large binary BLOB you ca save it immedialtely to a file and free the memory before parsing the next one. There is no need to parse the enitre XML tree into memory before processing it.
Also allows to serialize data from arbitrary sources. There is no need to keep all the data in memory at the time you start the serializer as long as you can access each bit at the time it's being serialized.
* __Object recycling__
Allows to recycle intermediate objects. When parsing long XML streams with repeating sub-tree patterns you can pull each pattern one by one, process it and reuse the parsed objects for the next pattern, giving the garbage collector less work to be done. You also can parse into objects that already exist.
* __Support for arbitrary classes__
Allows to parse directly into objects of arbitrary (even foreign) classes, no need to create temporary POJOs or modify existing classes. As long as you're able to create an object and populate its fields you can parse XML into it.
Also allows to serialize arbitrary objects. All you need to do is to write a simple serializer that knows how to serialize objects of a specific class.

## Requirements

This code builds on top of an existing parser that implements the XmlPull interface, see http://www.xmlpull.org.

## Builders

This library uses the concept of builders to create objects from the XML elements. For each XML element there must be at least one builder that knows how to transform it into an object. To do so, each builder has eight methods to 

* get a new object for a given XML Element
* update an object with an attribute
* update an object with a text value
* update an object with a child element
* update an object with an anonymous child (that's stil under development and not used in the current version)
* finish the object
* serialize attributes
* serialize child elements and text

Each time a start tag is parsed, the `get` method is called on the respective builder, which returns an object for this element. It's perfectly valid to return `null` if the start tag itself doesn't carry enough information to make up a new object. After the call to `get` the respective methods of the builder are called for each attribute, text value and child element of the current XML element. Finally the `finish` method is called for each object to do some final processing.

Each of the above methods returns the object that is beeing built, which allows the builder to return a completely different object if necessary.

### Recycling of instances

The `get` method takes an additional parameter to pass an old instance that is not used anymore. If possible, a builder can just reset and reuse that object instead of creating a new one. However, each builder is free to create new instances as it likes.

## Examples

The code snippets below should give you an idea how this library works and how to use it. This example parses an XML file that looks like this:

```xml
		<library>
			<book>
				<author>Moby-Dick; or, The Whale</author>
				<title>Herman Melville</title>
				<published>1851</published>
			</book>
			<book>
				<author>Alice’s Adventures in Wonderland</author>
				<title>Lewis Carroll</title>
				<published>1865</published>
			</book>
		</library>
```

### Defining the Java model

First you define the model that stores your data.

```java
		public class Book
		{
			public String title;
			public String author;
			public int published;

			@Override
			public String toString()
			{
				return title + " by " + author + ", published in " + published ;
			}
		}

		public class Library
		{
			ArrayList<Book> books = new ArrayList<Book>();
		}
```

### Defining builders

There are a couple of predefined builders for simple types you can use, but for complex elements you'll have to create your own builder like so:


```java
		private static class BookBuilder extends AbstractObjectBuilder<Book>
		{

			@Override
			public Book get(ElementDescriptor<Book> descriptor, Book recycle, ParserContext context)
			{
				if (recycle != null)
				{
					// recycle the book
					recycle.author = null;
					recycle.title = null;
					recycle.released = -1;
					return recycle;
				}
				// return a new book
				return new Book();
			}


			@Override
			public <V> Book update(ElementDescriptor<Book> descriptor, Book object, ElementDescriptor<V> childType,
					V child, ParserContext context)
			{
				if (childType == TITLE)
				{
					object.title = (String) child;
				}
				else if (childType == AUTHOR)
				{
					object.author = (String) child;
				}
				else if (childType == PUBLISHED)
				{
					object.published = (Integer) child;
				}
				return object;
			}


			@Override
			public void writeChildren(ElementDescriptor<Book> descriptor, Book book, IXmlChildWriter childWriter,
					SerializerContext context) throws SerializerException, IOException
			{
				childWriter.writeChild(TITLE, book.title);
				childWriter.writeChild(AUTHOR book.author;
				childWriter.writeChild(PUBLISHED, book.published);
			}
		}
```


If we pull the books from the stream we don't store them in the library. However, if we pull the library instead the parser will put all the books in there.

```java
		private static class LibraryBuilder extends AbstractObjectBuilder<Library>
		{

			@Override
			public Book get(ElementDescriptor<Library> descriptor, Library recycle, ParserContext context)
			{
				if (recycle != null)
				{
					recycle.books.clear();
					return recycle;
				}
				// return a new library
				return new Library();
			}


			@Override
			public <V> Book update(ElementDescriptor<Library> descriptor, Library object, ElementDescriptor<V> childType,
					V child, ParserContext context)
			{
				if (childType == BOOK)
				{
					object.books.add((Book) child);
				}
				return object;
			}


			@Override
			public void writeChildren(ElementDescriptor<Library> descriptor, Library library, IXmlChildWriter childWriter,
					SerializerContext context) throws SerializerException, IOException
			{
				for (Book book:library.books)
				{
					childWriter.writeChild(BOOK, book);
				}
			}
		}
```

### Defining the XML model

Next, you define the XML model and assign builders.

```java
		// author is just a String
		private final static ElementDescriptor<String> AUTHOR =
			ElementDescriptor.register(QualifiedName.get("author"), StringObjectBuilder.INSTANCE);

		// title is just a String as well
		private final static ElementDescriptor<String> TITLE =
			ElementDescriptor.register(QualifiedName.get("title"), StringObjectBuilder.INSTANCE);

		// published is an integer
		private final static ElementDescriptor<Integer> PUBLISHED =
			ElementDescriptor.register(QualifiedName.get("published"), IntegerObjectBuilder.INSTANCE);

		// book is built by a BookBuilder
		private final static ElementDescriptor<Book> BOOK =
			ElementDescriptor.register(QualifiedName.get("book"), new BookBuilder());

		// use the builder above to build & serialize libraries
		private final static ElementDescriptor<Library> LIBRARY =
			ElementDescriptor.register(QualifiedName.get("library"), new LibraryBuilder());
```

### Pulling objects

Now we're set up to pull books from the XML file.

```java
		// get an XmlPullParser
		XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = pullParserFactory.newPullParser();
		parser.setInput(new StringReader(inputXml) /* pass the reader that reads the xml stream */);

		// get an XmlObjectPull instance for this parser
		XmlObjectPull objectPull = new XmlObjectPull(parser);

		// the path to pull from, i.e. only elements in <library>
		XmlPath libraryPath = new XmlPath(LIBRARY);

		Book book = null;
		while (objectPull.moveToNext(BOOK, libraryPath))
		{
			book = op.pull(BOOK /* pull elements that are Books */,
				book /* recycle the previous book */,
				libraryPath /* pull from this path */);

			// do something with book
		}
```


### Serializing objects

Each builder also knows how to serialize. Once everything is set up for pulling objects, serializing is just a treat. 

```java
		// initialize the serializer for writing to an OutoutStream
		XmlObjectSerializer os = new XmlObjectSerializer(outputstream, "UTF-8", null /* use default context */);

		// serialize a library
		os.serialize(LIBRARY, library);
		
		// done
		out.close();
```

### Reflection

This framework doesn't rely on Reflection. However, there is some proof-of-concept state code that can make it easier to populate objects from XML elemtents.
Using the Reflection builder for the class `Book` above the models would look like:

```java
		private static class Book
		{
			@Element(name = "title")
			public String title;

			@Element(name = "author")
			public String author;

			@Element(name = "published")
			public int published;


			@Override
			public String toString()
			{
				return title + " by " + author + ", published in " + published ;
			}
		}

		// book is built by Reflection 
		private final static ElementDescriptor<Book> BOOK =
			ElementDescriptor.register(QualifiedName.get("book"), new ReflectionObjectBuilder<Book>(Book.class));
```

This will take care of parsing and serializing books properly.


## TODO:

* improve code, finialize interfaces
* support anonymous elements, i.e. elements that don't have an XmlElementDescriptor
* publish test suite

## License

Copyright (c) Marten Gajda 2014


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

