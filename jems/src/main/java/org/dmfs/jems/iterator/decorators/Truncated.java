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

package org.dmfs.jems.iterator.decorators;

import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.iterator.adapters.Infinite;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} which returns the first few elements of a delegate {@link Iterator} or {@link Generator}.
 *
 * @author Marten Gajda
 */
public final class Truncated<T> implements Iterator<T>
{
    private int mRemaining;
    private final Iterator<T> mDelegate;


    public Truncated(int remaining, Generator<T> delegate)
    {
        this(remaining, new Infinite<>(delegate));
    }


    public Truncated(int remaining, Iterator<T> delegate)
    {
        mRemaining = remaining;
        mDelegate = delegate;
    }


    @Override
    public boolean hasNext()
    {
        return mRemaining > 0 && mDelegate.hasNext();
    }


    @Override
    public T next()
    {
        if (mRemaining <= 0)
        {
            throw new NoSuchElementException("Truncation limit already reached");
        }
        mRemaining--;
        return mDelegate.next();
    }
}
