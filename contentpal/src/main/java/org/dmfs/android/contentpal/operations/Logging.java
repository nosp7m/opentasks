/*
 * Copyright 2019 dmfs GmbH
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
import org.dmfs.jems.procedure.Procedure;

import androidx.annotation.NonNull;


/**
 * An {@link Operation} which logs the data of the {@link ContentProviderOperation} when generated.
 *
 * @author Marten Gajda
 */
public final class Logging<T> implements Operation<T>
{
    private final Procedure<String> mLogProcedure;
    private final Operation<T> mDelegate;


    public Logging(@NonNull Procedure<String> logProcedure, @NonNull Operation<T> delegate)
    {
        mLogProcedure = logProcedure;
        mDelegate = delegate;
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
        ContentProviderOperation.Builder result = mDelegate.contentOperationBuilder(transactionContext);
        mLogProcedure.process(result.build().toString());
        return result;
    }
}
