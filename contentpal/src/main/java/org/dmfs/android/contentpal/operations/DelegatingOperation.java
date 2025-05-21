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

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * Abstract {@link Operation} which delegates all calls to another operation.
 *
 * @author Marten Gajda
 */
public abstract class DelegatingOperation<T> implements Operation<T>
{
    private final Operation<T> mDelegate;


    public DelegatingOperation(@NonNull Operation<T> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public final Optional<SoftRowReference<T>> reference()
    {
        return mDelegate.reference();
    }


    @NonNull
    @Override
    public final ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
    {
        return mDelegate.contentOperationBuilder(transactionContext);
    }
}
