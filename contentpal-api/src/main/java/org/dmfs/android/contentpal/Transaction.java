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

package org.dmfs.android.contentpal;

import android.content.ContentProviderClient;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import androidx.annotation.NonNull;


/**
 * Represents a content provider transaction. On calling {@link #commit(ContentProviderClient)} it will execute a set of {@link Operation}s on the given {@link
 * ContentProviderClient} using the given {@link TransactionContext}.
 * <p>
 * Note, although you could {@link #commit(ContentProviderClient)} a transaction multiple times, the operation might fail on subsequent commits, due to database
 * constraints.
 *
 * @author Marten Gajda
 */
public interface Transaction
{
    /**
     * Executes the {@link Operation}s in this transactions.
     * <p>
     * Note, calling this again will try to execute exactly the same transaction once more.
     *
     * @param client
     *         A {@link ContentProviderClient} to execute the transaction on.
     *
     * @return A new {@link TransactionContext} that should be used for any subsequent transaction that might have rows referring to the ones created in this
     * transaction.
     *
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    @NonNull
    TransactionContext commit(@NonNull ContentProviderClient client) throws RemoteException, OperationApplicationException;

    /**
     * Appends the given {@link Operation}s to this {@link Transaction}. The Result is a new {@link Transaction} that contains all operations of this {@link
     * Transaction} and the operations in the given {@link Operation}s. This means you should commit only one of both (usually the new one) {@link
     * Transaction}s.
     *
     * @param batch
     *         An {@link Iterable} of {@link Operation}s to include into this transaction.
     *
     * @return A new {@link Transaction} that also includes the given {@link Operation}s.
     */
    @NonNull
    Transaction with(@NonNull Iterable<? extends Operation<?>> batch);

    /**
     * Returns the total size of all {@link Operation}s in this {@link Transaction}, which is considered a close estimate of the total transaction size.
     *
     * @return The estimated size of the transaction.
     */
    int size();
}
