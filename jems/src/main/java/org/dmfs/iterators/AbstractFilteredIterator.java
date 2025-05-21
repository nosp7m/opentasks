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
import java.util.NoSuchElementException;


/**
 * An abstract {@link Iterator} that iterates the elements of another {@link Iterator}, if a {@link Filter} permits it.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 * @deprecated To be removed in version 2.0
 */
@Deprecated
public abstract class AbstractFilteredIterator<E> extends AbstractBaseIterator<E>
{

    /**
     * Interface of a filter for filtered Iterators.
     *
     * @param <E>
     *         The type of the filtered elements.
     *
     * @deprecated in favor of {@link Filter}
     */
    @Deprecated
    // kept for backwards compatibility
    public interface IteratorFilter<E> extends Filter<E>
    {
    }


    private final Iterator<E> mIterator;
    private final Filter<E> mFilter;

    private E mNext;
    private boolean mHasNext;


    /**
     * Creates a filtered {@link Iterator} that iterates the elements of the given {@link Iterator} if the given {@link Filter} permits it.
     *
     * @param iterator
     *         The {@link Iterator} to be filtered.
     * @param filter
     *         The {@link Filter}.
     */
    public AbstractFilteredIterator(final Iterator<E> iterator, final Filter<E> filter)
    {
        mIterator = iterator;
        mFilter = filter;
        moveToNext();
    }


    /**
     * Creates a filtered {@link Iterator} that iterates the elements of the given {@link Iterator} if the given {@link IteratorFilter} permits it.
     *
     * @param iterator
     *         The {@link Iterator} to be filtered.
     * @param iteratorFilter
     *         The {@link IteratorFilter}.
     *
     * @deprecated in favor of {@link AbstractFilteredIterator#AbstractFilteredIterator(Iterator, Filter)}.
     */
    @Deprecated
    public AbstractFilteredIterator(final Iterator<E> iterator, final IteratorFilter<E> iteratorFilter)
    {
        mIterator = iterator;
        // adapt the old interface to the new one
        mFilter = new Filter<E>()
        {
            @Override
            public boolean iterate(E argument)
            {
                return iteratorFilter.iterate(argument);
            }
        };
        moveToNext();
    }


    @Override
    public final boolean hasNext()
    {
        return mHasNext;
    }


    @Override
    public final E next()
    {
        if (!mHasNext)
        {
            throw new NoSuchElementException("No more elements to iterate");
        }

        E result = mNext;
        moveToNext();
        return result;
    }


    private void moveToNext()
    {
        while (mIterator.hasNext())
        {
            E next = mIterator.next();
            if (mFilter.iterate(next))
            {
                mNext = next;
                mHasNext = true;
                return;
            }
        }

        mHasNext = false;
    }
}
