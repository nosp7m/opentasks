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

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * A simple {@link InsertOperation} for a given {@link Table}.
 *
 * @author Marten Gajda
 */
public final class Insert<T> implements InsertOperation<T>
{
    private final Table<T> mTable;
    private final RowData<T> mData;


    /**
     * Creates an {@link InsertOperation} on the given {@link Table}.
     *
     * @param table
     *         The {@link Table} of the row to insert.
     */
    public Insert(@NonNull Table<T> table)
    {
        this(table, EmptyRowData.instance());
    }


    /**
     * Creates an {@link InsertOperation} on the given {@link Table}.
     *
     * @param table
     *         The {@link Table} of the row to insert.
     */
    public Insert(@NonNull Table<T> table, @NonNull RowData<T> data)
    {
        mTable = table;
        mData = data;
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
        return mData.updatedBuilder(transactionContext, mTable.insertOperation(EmptyUriParams.INSTANCE).contentOperationBuilder(transactionContext));
    }
}
