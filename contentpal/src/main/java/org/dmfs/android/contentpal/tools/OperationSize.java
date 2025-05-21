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

package org.dmfs.android.contentpal.tools;

import android.content.ContentProviderOperation;
import android.os.Parcel;

import androidx.annotation.NonNull;


/**
 * The size of a {@link ContentProviderOperation}.
 *
 * @author Marten Gajda
 */
public final class OperationSize extends Number
{
    private final ContentProviderOperation mOperation;


    public OperationSize(@NonNull ContentProviderOperation operation)
    {
        mOperation = operation;
    }


    @Override
    public double doubleValue()
    {
        return size();
    }


    @Override
    public float floatValue()
    {
        return size();
    }


    @Override
    public int intValue()
    {
        return size();
    }


    @Override
    public long longValue()
    {
        return size();
    }


    private int size()
    {
        // get the size of the operation by parcelling it and retrieving the parcel size
        Parcel parcel = Parcel.obtain();
        mOperation.writeToParcel(parcel, 0);
        int size = parcel.dataSize();
        parcel.recycle();
        return size;
    }

}
