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

import android.content.OperationApplicationException;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsQueue;

import androidx.annotation.NonNull;


/**
 * Simple decorator for {@link OperationsQueue}s which are to be shared among multiple threads.
 *
 * @author Marten Gajda
 */
public final class ThreadSafe implements OperationsQueue
{
    private final OperationsQueue mDelegate;


    public ThreadSafe(@NonNull OperationsQueue delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public void enqueue(@NonNull Iterable<? extends Operation<?>> operationsBatch) throws RemoteException, OperationApplicationException
    {
        synchronized (mDelegate)
        {
            mDelegate.enqueue(operationsBatch);
        }
    }


    @Override
    public void flush() throws RemoteException, OperationApplicationException
    {
        synchronized (mDelegate)
        {
            mDelegate.flush();
        }
    }
}
