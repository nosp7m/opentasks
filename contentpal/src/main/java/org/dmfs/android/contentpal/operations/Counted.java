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

package org.dmfs.android.contentpal.operations;

import android.content.ContentProviderOperation;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} which fails if the number of affected rows doesn't match a predicted number.
 * <p>
 * Note: Don't use this with {@link InsertOperation}s.
 *
 * @author Marten Gajda
 */
public final class Counted<T> implements Operation<T>
{
    private final Operation<T> mOperation;
    private final int mCount;


    /**
     * Predicts the number of affected rows of the given operation and fails if the actual number of affected rows is different.
     *
     * @param count
     *         The expected number of affected rows.
     * @param operation
     *         The decorated {@link Operation}.
     */
    public Counted(int count, @NonNull Operation<T> operation)
    {
        mOperation = operation;
        mCount = count;
    }


    @NonNull
    @Override
    public Optional<SoftRowReference<T>> reference()
    {
        return mOperation.reference();
    }


    @NonNull
    @Override
    public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext)
    {
        return mOperation.contentOperationBuilder(transactionContext).withExpectedCount(mCount);
    }
}
