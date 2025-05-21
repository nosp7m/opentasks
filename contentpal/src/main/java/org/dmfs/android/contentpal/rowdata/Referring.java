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

package org.dmfs.android.contentpal.rowdata;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * {@link RowData} decorator that adds a foreign id referring to the provided row from another table.
 *
 * @author Gabor Keszthelyi
 */
public final class Referring<T> implements RowData<T>
{
    private final String mForeignIdColumnName;
    private final RowSnapshot<?> mReferredRow;
    private final RowData<T> mDelegate;


    public Referring(@NonNull String foreignIdColumnName, @NonNull RowSnapshot<?> referredRow, @NonNull RowData<T> delegate)
    {
        mForeignIdColumnName = foreignIdColumnName;
        mReferredRow = referredRow;
        mDelegate = delegate;
    }


    public Referring(@NonNull String foreignIdColumnName, @NonNull RowSnapshot<?> referredRow)
    {
        this(foreignIdColumnName, referredRow, EmptyRowData.instance());
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return mDelegate.updatedBuilder(transactionContext, transactionContext.resolved(mReferredRow.reference())
                .builderWithReferenceData(transactionContext, builder, mForeignIdColumnName));
    }
}
