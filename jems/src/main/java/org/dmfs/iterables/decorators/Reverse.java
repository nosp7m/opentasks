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

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * {@link Iterable} decorator that iterates the values of another {@link Iterable} in reverse order.
 *
 * @param <T>
 *         The type of the iterated elements.
 *
 * @author Marten Gajda
 */
public final class Reverse<T> implements Iterable<T>
{

    private final Iterable<T> mDelegate;


    /**
     * Creates an {@link Iterable} that iterates the given {@link Iterable} in reverse.
     *
     * @param delegate
     *         Another {@link Iterable}.
     */
    public Reverse(Iterable<T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Iterator<T> iterator()
    {
        Deque<T> list = new LinkedList<>();
        for (T element : mDelegate)
        {
            list.addFirst(element);
        }
        return list.iterator();
    }

}
