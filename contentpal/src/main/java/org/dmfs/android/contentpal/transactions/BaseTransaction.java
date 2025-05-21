/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.android.contentpal.transactions;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.SparseArray;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Transaction;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.references.BackReference;
import org.dmfs.android.contentpal.references.RowUriReference;
import org.dmfs.android.contentpal.tools.OperationSize;
import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;
import org.dmfs.android.contentpal.transactions.contexts.Quick;
import org.dmfs.jems.optional.Optional;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;


/**
 * @author Marten Gajda
 */
public final class BaseTransaction implements Transaction
{
    private final ArrayList<ContentProviderOperation> mOperations;
    private final TransactionContext mContext;
    private final TransactionContext mTransactionContext;
    private final SparseArray<RowReference<?>> mTransactionReferences;
    private final int mSize;


    public BaseTransaction()
    {
        this(EmptyTransactionContext.INSTANCE);
    }


    public BaseTransaction(@NonNull TransactionContext context)
    {
        this(new ArrayList<>(0), context, context, new SparseArray<>(64), 0);
    }


    private BaseTransaction(@NonNull ArrayList<ContentProviderOperation> operations, @NonNull TransactionContext context, @NonNull TransactionContext transactionContext, @NonNull SparseArray<RowReference<?>> transactionReferences, int size)
    {
        mOperations = operations;
        mContext = context;
        mTransactionContext = transactionContext;
        mTransactionReferences = transactionReferences;
        mSize = size;
    }


    @NonNull
    @Override
    public TransactionContext commit(@NonNull ContentProviderClient client) throws RemoteException, OperationApplicationException
    {
        final ContentProviderResult[] resultsArray = client.applyBatch(mOperations);

        return mTransactionReferences.size() == 0
                ? mContext
                : new ResultTransactionContext(mTransactionReferences, resultsArray, mContext);
    }


    @NonNull
    @Override
    public Transaction with(@NonNull Iterable<? extends Operation<?>> batch)
    {
        ArrayList<ContentProviderOperation> newBatch = new ArrayList<>(mOperations.size() + 100);
        newBatch.addAll(mOperations);

        final SparseArray<RowReference<?>> batchReferences = mTransactionReferences.clone();
        // a TransactionContext that's valid during this transaction only.
        TransactionContext tempTransactionContext = mTransactionContext;

        int size = mSize;
        for (Operation<?> operation : batch)
        {
            ContentProviderOperation op = operation.contentOperationBuilder(new Quick(tempTransactionContext)).build();

            // update the transaction context if this operation has a virtual reference which can not be resolved
            Optional<? extends SoftRowReference<?>> optionalReference = operation.reference();
            if (optionalReference.isPresent())
            {
                SoftRowReference<?> rowReference = optionalReference.value();
                if (rowReference.isVirtual() && tempTransactionContext.resolved(rowReference) == rowReference)
                {
                    batchReferences.put(newBatch.size(), optionalReference.value());
                    tempTransactionContext = new Updated(tempTransactionContext, optionalReference.value(), op.getUri(), newBatch.size());
                }
            }

            newBatch.add(op);
            size += new OperationSize(op).intValue();
        }

        return new BaseTransaction(newBatch, mContext, tempTransactionContext, batchReferences, size);
    }


    @Override
    public int size()
    {
        return mSize;
    }


    private static class Updated implements TransactionContext
    {
        private final TransactionContext mTransactionContext;
        private final WeakReference<RowReference<?>> mOriginalReference;
        private final Uri mUri;
        private final int mBackReference;


        private Updated(@NonNull TransactionContext transactionContext, RowReference<?> originalReference, Uri uri, int backReference)
        {
            mTransactionContext = transactionContext;
            mOriginalReference = new WeakReference<>(originalReference);
            mUri = uri;
            mBackReference = backReference;
        }


        @NonNull
        @Override
        public <T> RowReference<T> resolved(@NonNull SoftRowReference<T> reference)
        {
            return reference == mOriginalReference.get()
                    ? new BackReference<>(mUri, mBackReference)
                    : mTransactionContext.resolved(reference);
        }

    }


    private static class ResultTransactionContext implements TransactionContext
    {
        private final SparseArray<RowReference<?>> mBatchReferences;
        private final ContentProviderResult[] mResultsArray;
        private final TransactionContext mTransactionContext;


        private ResultTransactionContext(SparseArray<RowReference<?>> batchreferences, ContentProviderResult[] resultsArray, TransactionContext transactionContext)
        {
            mBatchReferences = batchreferences;
            mResultsArray = resultsArray;
            mTransactionContext = transactionContext;
        }


        @NonNull
        @Override
        public <T> RowReference<T> resolved(@NonNull SoftRowReference<T> reference)
        {
            int idx = mBatchReferences.indexOfValue(reference);
            return idx >= 0 ?
                    new RowUriReference<>(mResultsArray[mBatchReferences.keyAt(idx)].uri) :
                    mTransactionContext.resolved(reference);
        }
    }
}
