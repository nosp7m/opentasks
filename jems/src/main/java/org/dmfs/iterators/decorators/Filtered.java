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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.iterators.Filter;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} that iterates the elements of another {@link Iterator} for which a {@link Filter} evaluates to
 * {@code true}.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 */
public final class Filtered<E> extends AbstractBaseIterator<E>
{
    private final Iterator<E> mDelagate;
    private final Filter<E> mFilter;

    private E mNext;
    private boolean mHasNext;


    /**
     * Creates a filtered {@link Iterator} that iterates the elements of the given {@link Iterator} if the given {@link
     * Filter} evaluates to {@code true} for these elements.
     *
     * @param delegate
     *         The {@link Iterator} to be filtered.
     * @param filter
     *         The {@link Filter}.
     */
    public Filtered(final Iterator<E> delegate, final Filter<E> filter)
    {
        mDelagate = delegate;
        mFilter = filter;
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
        while (mDelagate.hasNext())
        {
            E next = mDelagate.next();
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
