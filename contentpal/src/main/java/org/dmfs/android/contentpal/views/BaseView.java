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

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.RemoteException;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tables.BaseTable;
import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.single.combined.Backed;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * A basic implementation of a {@link View}.
 *
 * @param <T>
 *         The contract of this view.
 *
 * @author Marten Gajda
 */
public final class BaseView<T> implements View<T>
{
    private final ContentProviderClient mClient;
    private final Uri mTableUri;


    public BaseView(@NonNull ContentProviderClient client, @NonNull Uri tableUri)
    {
        mClient = client;
        mTableUri = tableUri;
    }


    @NonNull
    @Override
    public Cursor rows(@NonNull UriParams uriParams, @NonNull Projection<? super T> projection, @NonNull final Predicate<? super T> predicate, @NonNull final Optional<String> sorting) throws RemoteException
    {
        List<String> args = new LinkedList<>();
        for (Predicate.Argument arg : predicate.arguments(EmptyTransactionContext.INSTANCE))
        {
            args.add(arg.value());
        }
        String[] projectionArray = projection.toArray();
        Cursor cursor = mClient.query(uriParams.withParam(mTableUri.buildUpon()).build(),
                projectionArray,
                predicate.selection(EmptyTransactionContext.INSTANCE).toString(),
                args.toArray(new String[0]),
                new Backed<>(sorting, (String) null).value());
        return cursor == null ? new MatrixCursor(projectionArray) : cursor;
    }


    @NonNull
    @Override
    public Table<T> table()
    {
        return new BaseTable<>(mTableUri);
    }
}
