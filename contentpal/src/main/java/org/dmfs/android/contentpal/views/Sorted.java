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

package org.dmfs.android.contentpal.views;

import android.database.Cursor;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.adapters.SinglePresent;
import org.dmfs.jems.single.combined.Backed;

import androidx.annotation.NonNull;


/**
 * {@link View} decorator that provides a default sorting for {@link RowSet}s.
 *
 * @param <T>
 *         The contract of this view.
 *
 * @author Marten Gajda
 */
public final class Sorted<T> implements View<T>
{
    private final View<T> mDelegate;
    private final String mSorting;


    public Sorted(@NonNull String sorting, @NonNull View<T> delegate)
    {
        mDelegate = delegate;
        mSorting = sorting;
    }


    @NonNull
    @Override
    public Cursor rows(@NonNull UriParams uriParams, @NonNull Projection<? super T> projection, @NonNull Predicate<? super T> predicate, @NonNull Optional<String> sorting) throws RemoteException
    {
        return mDelegate.rows(uriParams, projection, predicate, new SinglePresent<>(new Backed<>(sorting, mSorting)));
    }


    @NonNull
    @Override
    public Table<T> table()
    {
        return mDelegate.table();
    }
}
