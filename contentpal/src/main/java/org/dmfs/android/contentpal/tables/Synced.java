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

import android.accounts.Account;
import android.content.ContentProviderClient;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tools.uriparams.AccountScopedParams;
import org.dmfs.android.contentpal.tools.uriparams.SyncParams;

import androidx.annotation.NonNull;


/**
 * A {@link Table} filtered by account to be used by sync adapters. This only works for tables that support the {@code account_name}, {@code account_type} and
 * {@code caller_is_syncadapter} query parameters.
 *
 * @param <T>
 *         The contract of this table.
 *
 * @author Marten Gajda
 */
public final class Synced<T> implements Table<T>
{
    private final Account mAccount;
    private final Table<T> mDelegate;


    public Synced(@NonNull Account account, @NonNull Table<T> delegate)
    {
        mAccount = account;
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public InsertOperation<T> insertOperation(@NonNull UriParams uriParams)
    {
        return mDelegate.insertOperation(new AccountScopedParams(mAccount, new SyncParams(uriParams)));
    }


    @NonNull
    @Override
    public Operation<T> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.updateOperation(new AccountScopedParams(mAccount, new SyncParams(uriParams)), predicate);
    }


    @NonNull
    @Override
    public Operation<T> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.deleteOperation(new AccountScopedParams(mAccount, new SyncParams(uriParams)), predicate);
    }


    @NonNull
    @Override
    public Operation<T> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return mDelegate.assertOperation(new AccountScopedParams(mAccount, new SyncParams(uriParams)), predicate);
    }


    @NonNull
    @Override
    public View<T> view(@NonNull ContentProviderClient client)
    {
        return new org.dmfs.android.contentpal.views.Synced<>(mAccount, mDelegate.view(client));
    }

}
