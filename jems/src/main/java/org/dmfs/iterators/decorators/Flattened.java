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
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} that serializes the values of multiple {@link Iterable}s. This means, it iterates the elements of each {@link Iterable} before moving on
 * to the next {@link Iterable}.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 * @see Serialized
 */
public final class Flattened<E> extends AbstractBaseIterator<E>
{
    private final Iterator<? extends Iterable<? extends E>> mIterables;

    private Iterator<? extends E> mCurrentIterator;


    /**
     * Constructor of an {@link Iterator} that serializes the elements of {@link Iterable}s.
     *
     * @param iterables
     *         An array of {@link Iterable}s.
     */
    @SafeVarargs
    public Flattened(final Iterable<? extends E>... iterables)
    {
        this(new Seq<>(iterables));
    }


    /**
     * Constructor of a {@link Flattened} {@link Iterator}.
     *
     * @param iterableIterator
     *         An {@link Iterator} that iterates {@link Iterable}s of type &lt;E;gt;.
     */
    public Flattened(final Iterator<? extends Iterable<? extends E>> iterableIterator)
    {
        mIterables = iterableIterator;
        mCurrentIterator = EmptyIterator.instance();
    }


    @Override
    public boolean hasNext()
    {
        while (!mCurrentIterator.hasNext() && mIterables.hasNext())
        {
            // advance to the next iterator that contains elements to iterate
            mCurrentIterator = mIterables.next().iterator();
        }

        return mCurrentIterator.hasNext();
    }


    @Override
    public E next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No more elements to iterate.");
        }

        return mCurrentIterator.next();
    }
}