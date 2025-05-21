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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.AbstractBaseIterator;
import org.dmfs.iterators.Filter;
import org.dmfs.iterators.FluentIterator;
import org.dmfs.iterators.Function;

import java.util.Iterator;


/**
 * A basic {@link FluentIterator}.
 *
 * @author Marten Gajda
 */
public final class Fluent<E> extends AbstractBaseIterator<E> implements FluentIterator<E>
{
    private final Iterator<E> mDelegate;


    /**
     * Provides a {@link FluentIterator} for the given {@link Iterator}.
     *
     * @param delegate
     *         The {@link Iterator} to decorate.
     */
    public Fluent(Iterator<E> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public FluentIterator<E> filtered(Filter<E> filter)
    {
        return new Fluent<>(new Filtered<>(mDelegate, filter));
    }


    @Override
    public <T> FluentIterator<T> mapped(Function<E, T> function)
    {
        return new Fluent<T>(new Mapped<>(mDelegate, function));
    }


    @Override
    public boolean hasNext()
    {
        return mDelegate.hasNext();
    }


    @Override
    public E next()
    {
        return mDelegate.next();
    }
}
