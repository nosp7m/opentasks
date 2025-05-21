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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.provider.BaseColumns;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.Transaction;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.predicates.arguments.BackReferenceArgument;
import org.dmfs.iterables.SingletonIterable;

import androidx.annotation.NonNull;


/**
 * A {@link RowReference} to a {@link RowSnapshot} in a {@link ContentProviderOperation} batch. This implicitly refers to a specific {@link Transaction}.
 * <p>
 * Note, there should be no reason to instantiate this class directly. It's only for internal purposes.
 *
 * @author Marten Gajda
 */
public final class BackReference<T> implements RowReference<T>
{
    private final static String DEFAULT_SELECTION = "%s = ?";
    private final static String[] DEFAULT_SELECTION_ARGS = { "-1" };

    private final Uri mUri;
    private final int mBackReference;


    public BackReference(@NonNull Uri uri, int backReference)
    {
        mUri = uri;
        mBackReference = backReference;
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder putOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return withSelection(ContentProviderOperation.newUpdate(mUri));
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder deleteOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return withSelection(ContentProviderOperation.newDelete(mUri));
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder assertOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return withSelection(ContentProviderOperation.newAssertQuery(mUri));
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder builderWithReferenceData(@NonNull TransactionContext transactionContext, @NonNull ContentProviderOperation.Builder operationBuilder, @NonNull String foreignKeyColumn)
    {
        return operationBuilder.withValueBackReference(foreignKeyColumn, mBackReference);
    }


    @NonNull
    @Override
    public Predicate<T> predicate(@NonNull TransactionContext transactionContext, @NonNull final String keyColumn)
    {
        return new BackReferenceSelectionPredicate<>(keyColumn, mBackReference);
    }


    @NonNull
    private ContentProviderOperation.Builder withSelection(@NonNull ContentProviderOperation.Builder builder)
    {
        builder.withSelection(String.format(DEFAULT_SELECTION, BaseColumns._ID), DEFAULT_SELECTION_ARGS)
                .withSelectionBackReference(0, mBackReference);
        return builder;
    }


    private static class BackReferenceSelectionPredicate<Contract> implements Predicate<Contract>
    {
        private final String mKeyColumn;
        private final int mBackReference;


        public BackReferenceSelectionPredicate(@NonNull String keyColumn, int backReference)
        {
            mKeyColumn = keyColumn;
            mBackReference = backReference;
        }


        @NonNull
        @Override
        public CharSequence selection(@NonNull TransactionContext transactionContext)
        {
            return String.format("%s = ?", mKeyColumn);
        }


        @NonNull
        @Override
        public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
        {
            return new SingletonIterable<>(new BackReferenceArgument(mBackReference));
        }
    }
}
