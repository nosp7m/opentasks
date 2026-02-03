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
 * The data to be used in a content provider operation.
 *
 * @param <T>
 *     The contract of the table this {@link RowData} belongs to.
 *
 * @author Marten Gajda
 */
public interface RowData<T>
{

    /**
     * Returns an updated {@link ContentProviderOperation.Builder} which also contains this {@link RowData}.
     * <p>
     * Note that {@link ContentProviderOperation.Builder}s are mutable an can not be cloned, so you'll receive the same instance you passed to this method. Make
     * sure you don't rely on the old state of the builder and don't use it in concurrent threads.
     *
     * @param transactionContext
     *     The {@link TransactionContext} of the {@link Transaction} this is being executed in.
     * @param builder
     *     A {@link ContentProviderOperation.Builder}.
     *
     * @return The same {@link ContentProviderOperation.Builder}.
     */
    @NonNull
    ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder);
}
