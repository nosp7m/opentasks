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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * The {@link SoftRowReference} of an {@link InsertOperation}.
 * <p>
 * Note that by design {@link RowReference#putOperationBuilder(TransactionContext)} returns a {@link ContentProviderOperation.Builder} to insert a new row on
 * every call.
 *
 * @author Marten Gajda
 */
public final class VirtualRowReference<T> implements SoftRowReference<T>
{
    private final InsertOperation<T> mInsertOperation;


    public VirtualRowReference(@NonNull InsertOperation<T> insertOperation)
    {
        mInsertOperation = insertOperation;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder putOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mInsertOperation.contentOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder deleteOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        throw new UnsupportedOperationException("Can't delete a virtual row.");
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder assertOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        throw new UnsupportedOperationException("Can't assert on a virtual row.");
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder builderWithReferenceData(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder operationBuilder, @NonNull String foreignKeyColumn)
    {
        throw new UnsupportedOperationException("Can't reference a virtual row.");
    }


    @NonNull
    @Override
    public Predicate<T> predicate(@NonNull TransactionContext transactionContext, @NonNull String keyColumn)
    {
        throw new UnsupportedOperationException("Can't create a predicate which matches a virtual row.");
    }


    @Override
    public boolean isVirtual()
    {
        return true;
    }
}
