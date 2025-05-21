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

package org.dmfs.optional;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The next value of an {@link Iterator}.
 *
 * @author Gabor Keszthelyi
 * @deprecated in favour of {@link org.dmfs.jems.optional.adapters.Next}
 */
@Deprecated
public final class Next<E> implements Optional<E>
{
    private final Iterator<E> mIterator;

    private Optional<E> mDelegate;


    public Next(Iterator<E> iterator)
    {
        mIterator = iterator;
    }


    @Override
    public boolean isPresent()
    {
        return cachedDelegate().isPresent();
    }


    @Override
    public E value(E defaultValue)
    {
        return cachedDelegate().value(defaultValue);
    }


    @Override
    public E value() throws NoSuchElementException
    {
        return cachedDelegate().value();
    }


    private Optional<E> cachedDelegate()
    {
        if (mDelegate == null)
        {
            mDelegate = mIterator.hasNext() ? new Present<E>(mIterator.next()) : Absent.<E>absent();
        }
        return mDelegate;
    }

}
