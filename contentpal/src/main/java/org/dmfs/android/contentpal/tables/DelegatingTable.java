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

package org.dmfs.android.contentpal.tables;

import android.content.ContentProviderClient;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;

import androidx.annotation.NonNull;


/**
 * An abstract {@link Table} which delegates all calls to another {@link Table}.
 *
 * @param <T>
 *         The contract of this table.
 *
 * @author Marten Gajda
 */
public abstract class DelegatingTable<T> implements Table<T>
{
    private final Table<T> mDelegate;


    public DelegatingTable(@NonNull Table<T> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public final InsertOperation<T> insertOperation(@NonNull UriParams uriParams)
    {
        return mDelegate.insertOperation(uriParams);
    }


    @NonNull
    @Override
    public final Operation<T> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.updateOperation(uriParams, predicate);
    }


    @NonNull
    @Override
    public final Operation<T> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.deleteOperation(uriParams, predicate);
    }


    @NonNull
    @Override
    public final Operation<T> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.assertOperation(uriParams, predicate);
    }


    @NonNull
    @Override
    public final View<T> view(@NonNull ContentProviderClient client)
    {
        return mDelegate.view(client);
    }
}
