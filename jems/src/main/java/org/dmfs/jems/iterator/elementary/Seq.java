/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.iterator.elementary;

import org.dmfs.iterators.AbstractBaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} of a sequence of values.
 *
 * @param <E>
 *         The type of the iterated values.
 *
 * @author Marten Gajda
 */
public final class Seq<E> extends AbstractBaseIterator<E>
{
    private final E[] mValues;
    private final int mCount;
    private int mNext;


    /**
     * Creates an {@link Iterator} of the given elements.
     */
    @SafeVarargs
    public Seq(E... values)
    {
        this(values.length, values);
    }


    /**
     * Creates an {@link Iterator} of the first {@code count} elements of the given array.
     */
    public Seq(int count, E[] values)
    {
        if (count < 0)
        {
            throw new ArrayIndexOutOfBoundsException(String.format("Count must not be less than 0, was %d", count));
        }
        if (count > values.length)
        {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("Count must not be higher than the number of values (%d), was %d", values.length, count));
        }
        mCount = count;
        mValues = values;
    }


    @Override
    public boolean hasNext()
    {
        return mNext < mCount;
    }


    @Override
    public E next()
    {
        int next = mNext;
        if (next >= mCount)
        {
            throw new NoSuchElementException("No more elements to iterate");
        }
        mNext = next + 1;
        return mValues[next];
    }
}