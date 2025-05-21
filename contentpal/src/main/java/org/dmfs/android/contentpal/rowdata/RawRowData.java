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
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * {@link RowData}s that simply adds the provided nullable value with the given key.
 * <p>
 * Note that {@link ContentProviderOperation} only supports the following types:
 * <p>
 * <Code>String, Byte, Short, Integer, Long, Float, Double, Boolean, byte[]</Code>
 * <p>
 * (check in {@link ContentProviderOperation.Builder#withValue(String, Object)}
 *
 * @author Gabor Keszthelyi
 */
public final class RawRowData<T> implements RowData<T>
{
    private final String mKey;
    private final Object mValue;


    public RawRowData(@NonNull String key, @Nullable Object value)
    {
        mKey = key;
        mValue = value;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder updatedBuilder(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder builder)
    {
        return builder.withValue(mKey, mValue);
    }
}
