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

package org.dmfs.android.contentpal.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * Abstract convenience base class for {@link Predicate}s which only need to compose a {@link Predicate} to delegate to in the constructor.
 *
 * @author Gabor Keszthelyi
 */
public abstract class DelegatingPredicate<Contract> implements Predicate<Contract>
{
    private final Predicate<Contract> mDelegate;


    public DelegatingPredicate(@NonNull Predicate<Contract> predicate)
    {
        mDelegate = predicate;
    }


    @NonNull
    @Override
    public final CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return mDelegate.selection(transactionContext);
    }


    @NonNull
    @Override
    public final Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return mDelegate.arguments(transactionContext);
    }
}
