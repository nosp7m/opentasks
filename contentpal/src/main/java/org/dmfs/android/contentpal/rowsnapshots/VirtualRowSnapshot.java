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

package org.dmfs.android.contentpal.rowsnapshots;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.Insert;
import org.dmfs.android.contentpal.references.VirtualRowReference;
import org.dmfs.android.contentpal.rowdatasnapshots.EmptyRowDataSnapshot;

import androidx.annotation.NonNull;


/**
 * The {@link RowSnapshot} of a row which has not been inserted yet. As such this {@link RowSnapshot} always returns an empty {@link RowDataSnapshot}.
 *
 * @author Marten Gajda
 */
public final class VirtualRowSnapshot<T> implements RowSnapshot<T>
{
    private final SoftRowReference<T> mRowReference;


    public VirtualRowSnapshot(@NonNull Table<T> table)
    {
        this(new Insert<>(table));
    }


    public VirtualRowSnapshot(@NonNull InsertOperation<T> insertOperation)
    {
        mRowReference = new VirtualRowReference<>(insertOperation);
    }


    @NonNull
    @Override
    public SoftRowReference<T> reference()
    {
        return mRowReference;
    }


    @NonNull
    @Override
    public RowDataSnapshot<T> values()
    {
        return EmptyRowDataSnapshot.INSTANCE;
    }

}