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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.AnyOf;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * An {@link Operation} to update all rows of a given {@link Table} which match a specific {@link Predicate} with the given {@link RowData}.
 *
 * @author Marten Gajda
 */
public final class BulkUpdate<T> implements Operation<T>
{
    private final Table<T> mTable;
    private final RowData<T> mData;
    private final Predicate<? super T> mPredicate;


    public BulkUpdate(@NonNull Table<T> table, @NonNull RowData<T> data)
    {
        this(table, data, new AnyOf<>());
    }


    public BulkUpdate(@NonNull Table<T> table, @NonNull RowData<T> data, @NonNull Predicate<? super T> predicate)
    {
        mTable = table;
        mData = data;
        mPredicate = predicate;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mData.updatedBuilder(transactionContext,
                mTable.updateOperation(EmptyUriParams.INSTANCE, mPredicate).contentOperationBuilder(transactionContext));
    }
}
