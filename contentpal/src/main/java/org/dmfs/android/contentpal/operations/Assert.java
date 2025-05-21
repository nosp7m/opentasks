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
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} for an assert query which checks that the given row referred by {@link RowSnapshot} has the provided {@link RowData}.
 *
 * @author Gabor Keszthelyi
 */
public final class Assert<T> implements Operation<T>
{
    private final RowSnapshot<T> mRowSnapshot;
    private final RowData<T> mRowData;


    public Assert(@NonNull RowSnapshot<T> rowSnapshot, @NonNull RowData<T> rowData)
    {
        mRowSnapshot = rowSnapshot;
        mRowData = rowData;
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
                transactionContext.resolved(mRowSnapshot.reference()).assertOperationBuilder(transactionContext));
    }
}
