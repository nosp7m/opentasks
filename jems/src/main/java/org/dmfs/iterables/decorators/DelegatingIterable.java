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

import java.util.Iterator;


/**
 * An abstract {@link Iterable} which delegates all method calls to another given {@link Iterable}.
 * <p>
 * This class is abstract and is meant to be a convenient way of composing {@link Iterable}s despite the lack of native support for the decoration pattern in
 * Java.
 *
 * @author Marten Gajda
 */
public abstract class DelegatingIterable<E> implements Iterable<E>
{
    private Iterable<E> mDelegate;


    public DelegatingIterable(Iterable<E> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public final Iterator<E> iterator()
    {
        return mDelegate.iterator();
    }
}
