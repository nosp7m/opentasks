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

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * A {@link RowReference} to a row identified by its {@link RowSnapshot}.
 *
 * @author Marten Gajda
 */
public final class RowSnapshotReference<T> implements RowReference<T>
{
    private final RowSnapshot<T> mRowSnapshot;


    public RowSnapshotReference(@NonNull RowSnapshot<T> rowSnapshot)
    {
        mRowSnapshot = rowSnapshot;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder putOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return transactionContext.resolved(mRowSnapshot.reference()).putOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder deleteOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return transactionContext.resolved(mRowSnapshot.reference()).deleteOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder assertOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return transactionContext.resolved(mRowSnapshot.reference()).assertOperationBuilder(transactionContext);
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder builderWithReferenceData(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder operationBuilder, @NonNull String foreignKeyColumn)
    {
        return transactionContext.resolved(mRowSnapshot.reference()).builderWithReferenceData(transactionContext, operationBuilder, foreignKeyColumn);
    }


    @NonNull
    @Override
    public Predicate<T> predicate(@NonNull TransactionContext transactionContext, @NonNull String keyColumn)
    {
        return transactionContext.resolved(mRowSnapshot.reference()).predicate(transactionContext, keyColumn);
    }
}
