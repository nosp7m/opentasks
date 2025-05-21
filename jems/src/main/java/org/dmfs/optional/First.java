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

import org.dmfs.iterables.decorators.Filtered;
import org.dmfs.iterables.decorators.Sieved;
import org.dmfs.iterators.Filter;
import org.dmfs.jems.predicate.Predicate;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The first value of an {@link Iterable}.
 *
 * @author Marten Gajda
 * @deprecated in favour of {@link org.dmfs.jems.optional.adapters.First}.
 */
@Deprecated
public final class First<T> implements Optional<T>
{
    private final Iterable<T> mIterable;
    private Optional<T> mDelegate;


    /**
     * Creates an {@link Optional} of the first value of the given {@link Iterable} which matches the given {@link Predicate}.
     *
     * @param iterable
     *         The {@link Iterable}
     * @param predicate
     *         The {@link Predicate}
     */
    public First(Iterable<T> iterable, Predicate<T> predicate)
    {
        this(new Sieved<T>(predicate, iterable));
    }


    /**
     * Creates an {@link Optional} of the first value of the given {@link Iterable} which matches the given {@link Filter}.
     *
     * @param iterable
     *         The {@link Iterable}
     * @param filter
     *         The {@link Filter}
     */
    public First(Iterable<T> iterable, Filter<T> filter)
    {
        this(new Filtered<T>(iterable, filter));
    }


    /**
     * Creates the {@link Optional} first value of the given {@link Iterable}.
     *
     * @param iterable
     *         A {@link Iterable}.
     */
    public First(Iterable<T> iterable)
    {
        mIterable = iterable;
    }


    @Override
    public boolean isPresent()
    {
        return delegate().isPresent();
    }


    @Override
    public T value(T defaultValue)
    {
        return delegate().value(defaultValue);
    }


    @Override
    public T value() throws NoSuchElementException
    {
        return delegate().value();
    }


    private Optional<T> delegate()
    {
        if (mDelegate == null)
        {
            Iterator<T> iterator = mIterable.iterator();
            mDelegate = iterator.hasNext() ? new Present<>(iterator.next()) : Absent.<T>absent();
        }
        return mDelegate;
    }
}
