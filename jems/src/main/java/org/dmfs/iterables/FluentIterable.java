/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.iterables;

import org.dmfs.iterators.Filter;
import org.dmfs.iterators.Function;


/**
 * Interface of an {@link Iterable} that can be decorated with a fluent interface.
 *
 * @author Gabor Keszthelyi
 */
public interface FluentIterable<E> extends Iterable<E>
{

    /**
     * Returns a {@link FluentIterable} that filters the elements of this {@link FluentIterable} with the given
     * filter.
     */
    FluentIterable<E> filtered(Filter<E> filter);

    /**
     * Returns a {@link FluentIterable} that's maps the elements of this {@link FluentIterable} using the given {@link
     * Function}.
     */
    <T> FluentIterable<T> mapped(Function<E, T> function);
}
