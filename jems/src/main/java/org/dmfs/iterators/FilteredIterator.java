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

import org.dmfs.iterators.decorators.Filtered;

import java.util.Iterator;


/**
 * An {@link Iterator} that iterates the elements of another {@link Iterator}, if an {@link IteratorFilter} permits it.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link Filtered}, to be removed in version 2.0.
 */
@Deprecated
public final class FilteredIterator<E> extends AbstractFilteredIterator<E>
{

    /**
     * Creates a filtered {@link Iterator} that iterates the elements of the given {@link Iterator} if the given {@link
     * IteratorFilter} permits it.
     *
     * @param iterator
     *         The {@link Iterator} to be filtered.
     * @param filter
     *         The {@link IteratorFilter}.
     */
    public FilteredIterator(final Iterator<E> iterator, final IteratorFilter<E> filter)
    {
        super(iterator, filter);
    }
}
