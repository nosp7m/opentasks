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
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.rowdata.EmptyRowData;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} to update a given {@link RowSnapshot}.
 *
 * @author Marten Gajda
 */
public final class Put<T> implements Operation<T>
{
    private final RowSnapshot<T> mRowSnapshot;
    private final RowData<T> mData;


    public Put(@NonNull RowSnapshot<T> rowSnapshot)
    {
        this(rowSnapshot, EmptyRowData.instance());
    }


    public Put(@NonNull RowSnapshot<T> rowSnapshot, @NonNull RowData<T> data)
    {
        mRowSnapshot = rowSnapshot;
        mData = data;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return new Present<>(mRowSnapshot.reference());
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mData.updatedBuilder(transactionContext, transactionContext.resolved(mRowSnapshot.reference()).putOperationBuilder(transactionContext));
    }
}
