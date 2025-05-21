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

import org.dmfs.iterators.filters.Distinct;

import java.util.Iterator;


/**
 * An {@link Iterator} that returns the elements of another Iterator exactly once, not matter how often they are iterated by the decorated {@link Iterator}.
 * Only the first occurrence of each element will be iterated.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link Distinct}, to be removed in version 2.0.
 */
@Deprecated
public final class DistinctIterator<E> extends AbstractFilteredIterator<E>
{

    /**
     * Return the values of the given {@link Iterator} exactly once.
     *
     * @param iterator
     */
    public DistinctIterator(final Iterator<E> iterator)
    {
        super(iterator, new Distinct<>());
    }
}
