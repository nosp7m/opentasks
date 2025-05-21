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

import org.dmfs.iterators.decorators.Serialized;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} that serializes the results of other {@link Iterator}s which are iterated by an {@link Iterator}
 * themselves.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link Serialized}, to be removed in version 2.0.
 */
@Deprecated
public final class SerialIteratorIterator<E> extends AbstractBaseIterator<E>
{
    private final Iterator<Iterator<E>> mIterators;

    private Iterator<E> mCurrentIterator;


    /**
     * Constructor of a {@link SerialIteratorIterator}.
     *
     * @param iteratorIterator
     *         An {@link Iterator} that iterates other iterators of type &lt;E&gt;.
     */
    public SerialIteratorIterator(final Iterator<Iterator<E>> iteratorIterator)
    {
        mIterators = iteratorIterator;
        mCurrentIterator = EmptyIterator.instance();
    }


    @Override
    public boolean hasNext()
    {
        while (!mCurrentIterator.hasNext() && mIterators.hasNext())
        {
            // advance to the next iterator that contains elements to iterate
            mCurrentIterator = mIterators.next();
        }

        return mCurrentIterator.hasNext();
    }


    @Override
    public E next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("No more elements to iterate");
        }

        return mCurrentIterator.next();
    }
}