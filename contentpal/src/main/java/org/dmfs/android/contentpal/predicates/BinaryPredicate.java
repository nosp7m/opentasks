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

package org.dmfs.android.contentpal.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;

import java.util.Iterator;

import androidx.annotation.NonNull;


/**
 * A {@link Predicate} which connects a number of given predicates with the given binary operator. Like this:
 * <pre>{@code
 * (predicate_1) operator (predicate_2) operator (predicate_3) … operator (predicate_n)
 * }</pre>
 * <p>
 * If no predicates are given this always evaluates to "1".
 *
 * @author Marten Gajda
 */
public final class BinaryPredicate<Contract> implements Predicate<Contract>
{
    private final Iterable<Predicate<? super Contract>> mPredicates;
    private final String mOperator;


    @SafeVarargs
    public BinaryPredicate(@NonNull String operator, @NonNull Predicate<? super Contract>... predicates)
    {
        this(operator, new Seq<>(predicates));
    }


    public BinaryPredicate(@NonNull String operator, @NonNull Iterable<Predicate<? super Contract>> predicates)
    {
        mOperator = operator;
        mPredicates = predicates;
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        Iterator<Predicate<? super Contract>> iterator = mPredicates.iterator();
        if (!iterator.hasNext())
        {
            return "1";
        }
        StringBuilder result = new StringBuilder(256);
        result.append("( ");
        result.append(iterator.next().selection(transactionContext));
        while (iterator.hasNext())
        {
            result.append(" ) ");
            result.append(mOperator);
            result.append(" ( ");
            result.append(iterator.next().selection(transactionContext));
        }
        result.append(" )");
        return result;

    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull final TransactionContext transactionContext)
    {
        return new Joined<>(
                new Mapped<>(
                        predicate -> predicate.arguments(transactionContext),
                        mPredicates));
    }
}
