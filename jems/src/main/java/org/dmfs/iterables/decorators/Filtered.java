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

import org.dmfs.iterators.Filter;

import java.util.Iterator;


/**
 * {@link Iterable} decorator that applies {@link org.dmfs.iterators.decorators.Filtered} on the {@link Iterator} returned.
 *
 * @author Gabor Keszthelyi
 */
public final class Filtered<E> implements Iterable<E>
{
    private final Filter<E> mFilter;
    private Iterable<E> mDelegate;


    public Filtered(Iterable<E> delegate, Filter<E> filter)
    {
        mDelegate = delegate;
        mFilter = filter;
    }


    @Override
    public Iterator<E> iterator()
    {
        return new org.dmfs.iterators.decorators.Filtered<>(mDelegate.iterator(), mFilter);
    }
}
