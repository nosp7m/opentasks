/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.optional.adapters;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The next value of an {@link Iterator}.
 *
 * @author Gabor Keszthelyi
 */
public final class Next<E> implements Optional<E>
{
    private final Iterator<? extends E> mIterator;

    private Optional<E> mDelegate;


    public Next(Iterator<? extends E> iterator)
    {
        mIterator = iterator;
    }


    @Override
    public boolean isPresent()
    {
        return cachedDelegate().isPresent();
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
            mDelegate = mIterator.hasNext() ? new Present<>(mIterator.next()) : Absent.absent();
        }
        return mDelegate;
    }

}
