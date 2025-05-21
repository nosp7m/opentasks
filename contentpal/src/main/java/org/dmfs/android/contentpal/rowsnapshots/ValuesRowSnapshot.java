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

import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.references.AbsoluteRowReference;

import androidx.annotation.NonNull;


/**
 * A {@link RowSnapshot} derived from a {@link RowDataSnapshot}.
 *
 * @author Marten Gajda
 */
public final class ValuesRowSnapshot<T> implements RowSnapshot<T>
{
    private final RowDataSnapshot<T> mRowDataSnapshot;
    private final SoftRowReference<T> mRowReference;


    public ValuesRowSnapshot(@NonNull Table<T> table, @NonNull RowDataSnapshot<T> rowDataSnapshot)
    {
        mRowDataSnapshot = rowDataSnapshot;
        mRowReference = new AbsoluteRowReference<>(table, mRowDataSnapshot);
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
        return mRowDataSnapshot;
    }
}
