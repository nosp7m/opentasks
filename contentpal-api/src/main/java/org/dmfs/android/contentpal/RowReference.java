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

import android.content.ContentProviderOperation;

import androidx.annotation.NonNull;


/**
 * A reference to a {@link RowSnapshot}.
 * <p>
 * Note, depending on the state of the row, some methods may not be supported for a particular {@link RowReference}.
 *
 * @param <T>
 *         The contract of the {@link Table} of the {@link RowSnapshot} this reference refers to.
 *
 * @author Marten Gajda
 */
public interface RowReference<T>
{
    /**
     * Creates an {@link ContentProviderOperation.Builder} that will insert or update the referred row.
     *
     * @param transactionContext
     *         A {@link TransactionContext} of the {@link Transaction} this is being executed in.
     *
     * @return A {@link ContentProviderOperation.Builder}.
     */
    @NonNull
    ContentProviderOperation.Builder putOperationBuilder(@NonNull TransactionContext transactionContext);

    /**
     * Creates an {@link ContentProviderOperation.Builder} that will delete the referred row.
     *
     * @param transactionContext
     *         A {@link TransactionContext} of the {@link Transaction} this is being executed in.
     *
     * @return A {@link ContentProviderOperation.Builder}.
     *
     * @throws IllegalStateException
     *         if the {@link RowReference} doesn't refer to an existing row.
     */
    @NonNull
    ContentProviderOperation.Builder deleteOperationBuilder(@NonNull TransactionContext transactionContext);

    /**
     * Creates an {@link ContentProviderOperation.Builder} that will assert on the referred row.
     *
     * @param transactionContext
     *         A {@link TransactionContext} of the {@link Transaction} this is being executed in.
     *
     * @return A {@link ContentProviderOperation.Builder}.
     *
     * @throws IllegalStateException
     *         if the {@link RowReference} doesn't refer to an existing row.
     */
    @NonNull
    ContentProviderOperation.Builder assertOperationBuilder(@NonNull TransactionContext transactionContext);

    /**
     * Select the referred item in the given {@link ContentProviderOperation.Builder}.
     *
     * @param transactionContext
     *         A {@link TransactionContext} of the {@link Transaction} this is being executed in.
     * @param operationBuilder
     *         The  {@link ContentProviderOperation.Builder}.
     * @param foreignKeyColumn
     */
    @NonNull
    ContentProviderOperation.Builder builderWithReferenceData(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder operationBuilder, @NonNull String foreignKeyColumn);

    /**
     * Returns a {@link Predicate} which matches the row on the given key column.
     *
     * @param transactionContext
     * @param keyColumn
     *
     * @return A {@link Predicate}
     */
    @NonNull
    Predicate<T> predicate(@NonNull TransactionContext transactionContext, @NonNull String keyColumn);
}
