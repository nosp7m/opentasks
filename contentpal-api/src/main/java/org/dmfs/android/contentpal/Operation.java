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

import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * A single operation on a content provider table.
 *
 * @param <T>
 *         The contract of the table this {@link Operation} belongs to.
 *
 * @author Marten Gajda
 */
public interface Operation<T>
{

    /**
     * Returns an {@link Optional} reference to the row that is inserted or modified by this operation. The value is {@link Optional} because bulk and delete
     * operations usually don't result in a single row that can be referred to.
     *
     * @return An {@link Optional} {@link SoftRowReference}.
     */
    @NonNull
    Optional<SoftRowReference<T>> reference();

    /**
     * Creates a {@link ContentProviderOperation.Builder} for this {@link Operation}.
     *
     * @param transactionContext
     *         The current {@link TransactionContext}.
     *
     * @return A {@link ContentProviderOperation.Builder} that builds the {@link ContentProviderOperation} on {@link ContentProviderOperation.Builder#build()}.
     *
     * @throws UnsupportedOperationException
     *         If this is a NoOp.
     */
    @NonNull
    ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException;
}
