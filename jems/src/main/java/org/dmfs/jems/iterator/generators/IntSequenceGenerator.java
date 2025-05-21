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

package org.dmfs.jems.iterator.generators;

import org.dmfs.iterators.AbstractBaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} which iterates {@link Integer}s from a given start in given steps.
 * <p>
 * Note, this {@link Iterator} stops iterating when the iterated value would leave the integer range.
 *
 * @author Marten Gajda
 */
public final class IntSequenceGenerator extends AbstractBaseIterator<Integer>
{
    private long mNext;
    private final int mStep;


    public IntSequenceGenerator()
    {
        this(0, 1);
    }


    public IntSequenceGenerator(int next)
    {
        this(next, 1);
    }


    public IntSequenceGenerator(int next, int step)
    {
        mNext = next;
        mStep = step;
    }


    @Override
    public boolean hasNext()
    {
        return mNext >= Integer.MIN_VALUE && mNext <= Integer.MAX_VALUE;
    }


    @Override
    public Integer next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("Generator overflow. Next value is not an Integer anymore.");
        }
        int result = (int) mNext;
        mNext += mStep;
        return result;
    }
}
