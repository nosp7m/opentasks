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

package org.dmfs.android.contentpal.testing.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;

import androidx.annotation.NonNull;


/**
 * A mocked {@link Predicate} which returns exactly what has been passed
 *
 * @author Marten Gajda
 */
public final class Mocked<Contract> implements Predicate<Contract>
{

    private final CharSequence mSelection;
    private final Iterable<String> mArguments;


    public Mocked(@NonNull CharSequence selection, @NonNull String... arguments)
    {
        mSelection = selection;
        mArguments = new Seq<>(arguments);
    }


    @NonNull
    @Override
    public CharSequence selection(@NonNull TransactionContext transactionContext)
    {
        return mSelection;
    }


    @NonNull
    @Override
    public Iterable<Argument> arguments(@NonNull TransactionContext transactionContext)
    {
        return new Mapped<>(ValueArgument::new, mArguments);
    }


    /**
     * A simple constant argument with no back reference.
     * <p>
     * Note: This class is 'copied' here from 'contentpal' module (that one is not available here on the classpath)
     */
    static final class ValueArgument implements Argument
    {
        private final Object mValue;


        public ValueArgument(@NonNull Object value)
        {

            mValue = value;
        }


        @NonNull
        @Override
        public String value()
        {
            return mValue.toString();
        }


        @NonNull
        @Override
        public Optional<Integer> backReference()
        {
            return Absent.absent();
        }
    }
}
