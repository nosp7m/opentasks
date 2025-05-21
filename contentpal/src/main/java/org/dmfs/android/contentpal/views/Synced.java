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

import android.accounts.Account;
import android.database.Cursor;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tools.uriparams.AccountScopedParams;
import org.dmfs.android.contentpal.tools.uriparams.SyncParams;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * A {@link View} filtered by account to be used by sync adapters. This only works for views which support the {@code account_name}, {@code account_type} and
 * {@code caller_is_syncadapter} query parameters.
 *
 * @param <T>
 *         The contract of this view.
 *
 * @author Marten Gajda
 */
public final class Synced<T> implements View<T>
{
    private final Account mAccount;
    private final View<T> mDelegate;


    public Synced(@NonNull Account account, @NonNull View<T> delegate)
    {
        mAccount = account;
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public Cursor rows(@NonNull UriParams uriParams, @NonNull Projection<? super T> projection, @NonNull Predicate<? super T> predicate, @NonNull Optional<String> sorting) throws RemoteException
    {
        return mDelegate.rows(new AccountScopedParams(mAccount, new SyncParams(uriParams)), projection, predicate, sorting);
    }


    @NonNull
    @Override
    public Table<T> table()
    {
        return new org.dmfs.android.contentpal.tables.Synced<>(mAccount, mDelegate.table());
    }
}
