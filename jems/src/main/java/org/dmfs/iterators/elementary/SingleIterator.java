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

package org.dmfs.iterators.elementary;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.jems.single.Single;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} to iterate the sole value of a {@link Single}.
 *
 * @param <E>
 *         The type of the iterated value.
 *
 * @author Marten Gajda
 */
public final class SingleIterator<E> extends AbstractBaseIterator<E>
{
    private boolean mHasNext = true;
    private final Single<E> mValue;


    /**
     * Creates an {@link Iterator} which iterates the value of the given {@link Single}.
     *
     * @param value
     *         The sole value to iterate.
     */
    public SingleIterator(final Single<E> value)
    {
        mValue = value;
    }


    @Override
    public boolean hasNext()
    {
        return mHasNext;
    }


    @Override
    public E next()
    {
        if (!mHasNext)
        {
            throw new NoSuchElementException("No more elements to iterate.");
        }
        mHasNext = false;
        return mValue.value();
    }
}