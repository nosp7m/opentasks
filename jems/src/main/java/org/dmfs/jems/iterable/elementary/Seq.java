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

package org.dmfs.jems.iterable.elementary;

import java.util.Iterator;


/**
 * {@link Iterable} sequence of values.
 *
 * @param <T>
 *         The type of the iterated elements.
 *
 * @author Marten Gajda
 */
public final class Seq<T> implements Iterable<T>
{
    private final T[] mValues;
    private final int mCount;


    /**
     * Creates an {@link Iterable} of the given elements.
     */
    @SafeVarargs
    public Seq(T... values)
    {
        this(values.length, values);
    }


    /**
     * Creates an {@link Iterable} of the first {@code count} elements of the given array.
     */
    public Seq(int count, T[] values)
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
    public Iterator<T> iterator()
    {
        return new org.dmfs.jems.iterator.elementary.Seq<>(mCount, mValues);
    }
}
