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
import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.operations.internal.RawAssert;
import org.dmfs.android.contentpal.operations.internal.RawDelete;
import org.dmfs.android.contentpal.operations.internal.RawInsert;
import org.dmfs.android.contentpal.operations.internal.RawUpdate;
import org.dmfs.android.contentpal.views.BaseView;
import org.dmfs.jems.optional.Optional;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;


/**
 * A basic implementation of a {@link Table}.
 *
 * @param <T>
 *         The contract of this table.
 *
 * @author Marten Gajda
 */
public final class BaseTable<T> implements Table<T>
{
    private final Uri mTableUri;


    public BaseTable(@NonNull Uri tableUri)
    {
        mTableUri = tableUri;
    }


    @NonNull
    @Override
    public InsertOperation<T> insertOperation(@NonNull final UriParams uriParams)
    {
        return new RawInsert<>(uriParams.withParam(mTableUri.buildUpon()).build());
    }


    @NonNull
    @Override
    public Operation<T> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return new Constrained<>(new RawUpdate<>(uriParams.withParam(mTableUri.buildUpon()).build()), predicate);
    }


    @NonNull
    @Override
    public Operation<T> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return new Constrained<>(new RawDelete<>(uriParams.withParam(mTableUri.buildUpon()).build()), predicate);
    }


    @NonNull
    @Override
    public Operation<T> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate)
    {
        return new Constrained<>(new RawAssert<>(uriParams.withParam(mTableUri.buildUpon()).build()), predicate);
    }


    @NonNull
    @Override
    public View<T> view(@NonNull ContentProviderClient client)
    {
        return new BaseView<>(client, mTableUri);
    }


    /**
     * A Decorators to add a selection to a given {@link Operation}.
     * <p>
     * Note, this class is not public, because it is considered to be harmful in most cases other than in this class. The reason is that it's not possible to
     * append a selection to a ContentProviderOperation.Builder, instead the previous selection is replaced.
     *
     * @param <T>
     */
    private final static class Constrained<T> implements Operation<T>
    {
        private final Operation<T> mDelegate;
        private final Predicate<? super T> mPredicate;


        public Constrained(@NonNull Operation<T> delegate, @NonNull Predicate<? super T> predicate)
        {
            mDelegate = delegate;
            mPredicate = predicate;
        }


        @NonNull
        @Override
        public Optional<SoftRowReference<T>> reference()
        {
            return mDelegate.reference();
        }


        @NonNull
        @Override
        public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
        {
            ContentProviderOperation.Builder builder = mDelegate.contentOperationBuilder(transactionContext);

            List<String> arguments = new LinkedList<>();
            for (Predicate.Argument arg : mPredicate.arguments(transactionContext))
            {
                arguments.add(arg.value());
                Optional<Integer> backReference = arg.backReference();
                if (backReference.isPresent())
                {
                    builder.withSelectionBackReference(arguments.size() - 1, backReference.value());
                }
            }

            return builder.withSelection(mPredicate.selection(transactionContext).toString(), arguments.toArray(new String[0]));
        }
    }
}
