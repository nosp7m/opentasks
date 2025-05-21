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

package org.dmfs.iterables;

import org.dmfs.iterators.decorators.Filtered;

import java.util.Iterator;


/**
 * {@link Iterable} decorator that removes any duplicates from another {@link Iterable}, keeping only the first occurrence of each iterated value.
 *
 * @param <T>
 *     The type of the iterated elements.
 *
 * @author Marten Gajda
 * @deprecated In favour of {@link org.dmfs.jems.iterable.decorators.Distinct}.
 */
@Deprecated
public final class Distinct<T> implements Iterable<T>
{

    private final Iterable<T> mDelegate;


    /**
     * Creates an {@link Iterable} which iterates each value of the given {@link Iterable} exactly once.
     *
     * @param delegate
     *     Another {@link Iterable}.
     */
    public Distinct(Iterable<T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Iterator<T> iterator()
    {
        return new Filtered<>(mDelegate.iterator(), new org.dmfs.iterators.filters.Distinct<T>());
    }

}
