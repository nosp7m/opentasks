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

package org.dmfs.android.contentpal;

import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * A predicate.
 *
 * @param <Contract>
 *         The contract of the table this predicate belongs to.
 *
 * @author Marten Gajda
 */
public interface Predicate<Contract>
{
    /**
     * A selection argument.
     */
    interface Argument
    {
        /**
         * Returns the string representation of the argument.
         *
         * @return
         */
        @NonNull
        String value();

        /**
         * Returns an optional back reference which will replace the argument value.
         *
         * @return
         */
        @NonNull
        Optional<Integer> backReference();
    }

    /**
     * Returns an SQL selection CharSequence which selects elements matching this predicate.
     *
     * @param transactionContext
     *         The {@link TransactionContext} of the Transaction this is executed in.
     *
     * @return
     */
    @NonNull
    CharSequence selection(@NonNull TransactionContext transactionContext);

    /**
     * An {@link Iterable} of all arguments of this predicate.
     *
     * @param transactionContext
     *         The {@link TransactionContext} of the Transaction this is executed in.
     */
    @NonNull
    Iterable<Argument> arguments(@NonNull TransactionContext transactionContext);
}
