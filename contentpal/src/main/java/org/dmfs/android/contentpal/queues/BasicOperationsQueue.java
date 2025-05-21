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

package org.dmfs.android.contentpal.queues;

import android.content.ContentProviderClient;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsQueue;
import org.dmfs.android.contentpal.Transaction;
import org.dmfs.android.contentpal.transactions.BaseTransaction;

import androidx.annotation.NonNull;


/**
 * @author Marten Gajda
 */
public final class BasicOperationsQueue implements OperationsQueue
{
    private final static int DEFAULT_COMMIT_LIMIT = 750 * 1024;

    private final ContentProviderClient mClient;
    private final int mCommitLimit;
    private Transaction mTransaction = new BaseTransaction();


    public BasicOperationsQueue(@NonNull ContentProviderClient client)
    {
        this(client, DEFAULT_COMMIT_LIMIT);
    }


    public BasicOperationsQueue(@NonNull ContentProviderClient client, int commitLimit)
    {
        mClient = client;
        mCommitLimit = commitLimit;
    }


    public void enqueue(@NonNull Iterable<? extends Operation<?>> operationsBatch) throws RemoteException, OperationApplicationException
    {
        Transaction newTransaction = mTransaction.with(operationsBatch);

        if (newTransaction.size() > mCommitLimit)
        {
            // new transaction size would exceed the commit size, flush before adding the new batch
            flush();
            newTransaction = mTransaction.with(operationsBatch);
        }

        mTransaction = newTransaction;
    }


    @Override
    public void flush() throws RemoteException, OperationApplicationException
    {
        mTransaction = new BaseTransaction(mTransaction.commit(mClient));
    }
}
