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

package org.dmfs.jems.iterable.generators;

import java.util.Iterator;


/**
 * An {@link Iterable} of sequential integers.
 * <p>
 * Note, this {@link Iterable} stops iterating when the iterated value would leave the integer range.
 *
 * @author Marten Gajda
 */
public final class IntSequenceGenerator implements Iterable<Integer>
{
    private final int mStart;
    private final int mStep;


    /**
     * Creates a sequence of {@link Integer} starting at {@code 0} with a step size of {@code 1}.
     */
    public IntSequenceGenerator()
    {
        this(0, 1);
    }


    /**
     * Creates a sequence of {@link Integer} starting at the give value with a step size of {@code 1}.
     */
    public IntSequenceGenerator(int start)
    {
        this(start, 1);
    }


    /**
     * Creates a sequence of {@link Integer} starting at the give value with the given step size.
     */
    public IntSequenceGenerator(int start, int step)
    {
        mStart = start;
        mStep = step;
    }


    @Override
    public Iterator<Integer> iterator()
    {
        return new org.dmfs.jems.iterator.generators.IntSequenceGenerator(mStart, mStep);
    }
}
