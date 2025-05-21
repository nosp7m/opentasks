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
import org.dmfs.iterables.EmptyIterable;

import androidx.annotation.NonNull;


/**
 * @author Marten Gajda
 */
public final class NotNull<Contract> implements Predicate<Contract>
{
    private final String mColumnName;


    public NotNull(@NonNull String columnName)
    {
        mColumnName = columnName;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return new StringBuilder(mColumnName).append(" is not null");
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return EmptyIterable.instance();
    }
}
