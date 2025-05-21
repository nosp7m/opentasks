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

import androidx.annotation.NonNull;


/**
 * A {@link Predicate} which evaluates to {@code true}, if and only if all of the given {@link Predicate}s evaluate to {@code false}. This corresponds to the
 * Boolean "nor" operation.
 *
 * @author Marten Gajda
 */
public final class NoneOf<Contract> extends DelegatingPredicate<Contract>
{
    @SafeVarargs
    public NoneOf(@NonNull Predicate<? super Contract>... predicates)
    {
        super(new Not<>(new AnyOf<>(predicates)));
    }


    public NoneOf(@NonNull Iterable<Predicate<? super Contract>> predicates)
    {
        super(new Not<>(new AnyOf<>(predicates)));
    }

}
