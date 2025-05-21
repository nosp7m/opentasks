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

package org.dmfs.iterators;

import java.util.Iterator;


/**
 * Interface of an {@link Iterator} that can be decorated with a fluent interface.
 *
 * @author Marten Gajda
 */
public interface FluentIterator<E> extends Iterator<E>
{
    /**
     * Returns a {@link FluentIterator} that filters the elements of this {@link FluentIterator} with the given
     * filter.
     *
     * @param filter
     *         The {@link Filter} to filter the {@link Iterator} with.
     *
     * @return The filtered {@link FluentIterator}.
     */
    FluentIterator<E> filtered(Filter<E> filter);

    /**
     * Returns a {@link FluentIterator} that's maps the elements of this {@link FluentIterator} using the given {@link
     * Function}.
     *
     * @param function
     *         The {@link Function} to map the original {@link Iterator}.
     * @param <T>
     *         The type of the mapped elements.
     *
     * @return The mapped {@link FluentIterator}.
     */
    <T> FluentIterator<T> mapped(Function<E, T> function);
}
