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

import org.dmfs.iterators.SingletonIterator;

import java.util.Iterator;


/**
 * {@link Iterable} with a single element.
 *
 * @author Gabor Keszthelyi
 */
public final class SingletonIterable<E> implements Iterable<E>
{
    private final E mElement;


    public SingletonIterable(E element)
    {
        mElement = element;
    }


    @Override
    public Iterator<E> iterator()
    {
        return new SingletonIterator<>(mElement);
    }
}
