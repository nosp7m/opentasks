/*
 * Copyright 2020 dmfs GmbH
 *
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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.function.Function;

import java.util.Iterator;


/**
 * An {@link Iterator} which drops duplicates of already iterated elements.
 * <p>
 * Examples
 *
 * <pre>
 * Distinct([1, 2, 2, 1, 2, 3, 3]) -&gt;
 * [1, 2, 3]
 *
 * Distinct(String::length, ["abc", "xyz", "42", "21"]) -&gt;
 * ["abc", "42"]
 * </pre>
 */
public final class Distinct<T> extends DelegatingIterable<T>
{
    public <V> Distinct(Iterable<T> delegate)
    {
        super(() -> new org.dmfs.jems.iterator.decorators.Distinct<>(delegate.iterator()));
    }


    public <V> Distinct(Function<? super T, ? extends V> criterionFunction, Iterable<T> delegate)
    {
        super(() -> new org.dmfs.jems.iterator.decorators.Distinct<>(criterionFunction, delegate.iterator()));
    }
}
