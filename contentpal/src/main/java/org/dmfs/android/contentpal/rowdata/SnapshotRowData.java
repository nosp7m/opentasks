/*
 * Copyright 2019 dmfs GmbH
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

import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * {@link RowData} based on the values of a {@link RowDataSnapshot} and the columns of a given {@link Projection}. In particular it adds the {@link
 * RowDataSnapshot} value of each column in the given {@link Projection}.
 * <p>
 * Note, if the {@link RowDataSnapshot} is lacking a value which is present in the given {@link Projection} this will put a {@code null} value.
 *
 * @author Marten Gajda
 */
public final class SnapshotRowData<T> implements RowData<T>
{
    private final RowDataSnapshot<T> mData;
    private final Projection<? super T> mProjection;


    public SnapshotRowData(@NonNull Projection<? super T> projection, @NonNull RowDataSnapshot<T> data)
    {
        mData = data;
        mProjection = projection;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        for (String column : mProjection.toArray())
        {
            Optional<String> stringData = mData.data(column, s -> s);
            if (stringData.isPresent())
            {
                builder.withValue(column, stringData.value());
            }
            else
            {
                Optional<byte[]> binaryData = mData.byteData(column);
                builder.withValue(column, binaryData.isPresent() ? binaryData.value() : null);
            }
        }

        return builder;
    }
}
