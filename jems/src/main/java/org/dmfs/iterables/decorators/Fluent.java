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

package org.dmfs.iterables.decorators;

import org.dmfs.iterables.FluentIterable;
import org.dmfs.iterators.Filter;
import org.dmfs.iterators.Function;

import java.util.Iterator;


/**
 * A basic {@link FluentIterable}.
 *
 * @author Gabor Keszthelyi
 */
public final class Fluent<E> implements FluentIterable<E>
{
    private final Iterable<E> mDelegate;


    /**
     * Provides a {@link FluentIterable} for the given {@link Iterator}.
     *
     * @param delegate
     *         The {@link Iterable} to decorate.
     */
    public Fluent(Iterable<E> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public FluentIterable<E> filtered(Filter<E> filter)
    {
        return new Fluent<>(new Filtered<>(mDelegate, filter));
    }


    @Override
    public <T> FluentIterable<T> mapped(Function<E, T> function)
    {
        return new Fluent<>(new Mapped<>(mDelegate, function));
    }


    @Override
    public Iterator<E> iterator()
    {
        return mDelegate.iterator();
    }
}
