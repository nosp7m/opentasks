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
import org.dmfs.android.contentpal.rowdata.EmptyRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} for an assert query which checks that the provided {@link RowData} is present in the given {@link Table} filtered by the given {@link
 * Predicate}.
 *
 * @author Gabor Keszthelyi
 */
public final class BulkAssert<T> implements Operation<T>
{
    private final Table<T> mTable;
    private final RowData<T> mRowData;
    private final Predicate<? super T> mPredicate;


    public BulkAssert(@NonNull Table<T> table, @NonNull RowData<T> rowData, @NonNull Predicate<? super T> predicate)
    {
        mTable = table;
        mRowData = rowData;
        mPredicate = predicate;
    }


    public BulkAssert(@NonNull Table<T> table, @NonNull RowData<T> rowData)
    {
        this(table, rowData, new AnyOf<>());
    }


    public BulkAssert(@NonNull Table<T> table, @NonNull Predicate<? super T> predicate)
    {
        this(table, new EmptyRowData<>(), predicate);
    }


    public BulkAssert(@NonNull Table<T> table)
    {
        this(table, new EmptyRowData<>(), new AnyOf<>());
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return Absent.absent();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mRowData.updatedBuilder(transactionContext,
                mTable.assertOperation(EmptyUriParams.INSTANCE, mPredicate).contentOperationBuilder(transactionContext));
    }
}
