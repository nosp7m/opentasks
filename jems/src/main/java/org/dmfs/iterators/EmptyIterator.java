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

package org.dmfs.iterators;

import java.util.Collections;
import java.util.NoSuchElementException;


/**
 * An Iterator that is always empty.
 * <p>
 * Note, there is an empty iterator at {@link Collections#emptyIterator()}, but it's not available on older Android versions.
 *
 * @param <E>
 *         The type of the iterated elements (if there were any).
 *
 * @author Marten Gajda
 */
public final class EmptyIterator<E> extends AbstractBaseIterator<E>
{
    private final static EmptyIterator<?> INSTANCE = new EmptyIterator<>();


    @SuppressWarnings("unchecked")
    public static <T> EmptyIterator<T> instance()
    {
        return (EmptyIterator<T>) INSTANCE;
    }


    @Override
    public boolean hasNext()
    {
        return false;
    }


    @Override
    public E next()
    {
        throw new NoSuchElementException("No more elements to iterate.");
    }
}
