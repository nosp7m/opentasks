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
 * A {@link Predicate} which evaluates to {@code true}, if and only if at least one of the given {@link Predicate}s evaluates to {@code true}. This corresponds
 * to the Boolean "or" operation.
 *
 * @author Marten Gajda
 */
public final class AnyOf<Contract> extends DelegatingPredicate<Contract>
{
    @SafeVarargs
    public AnyOf(@NonNull Predicate<? super Contract>... predicates)
    {
        super(new BinaryPredicate<>("or", predicates));
    }


    public AnyOf(@NonNull Iterable<Predicate<? super Contract>> predicates)
    {
        super(new BinaryPredicate<>("or", predicates));
    }
}
