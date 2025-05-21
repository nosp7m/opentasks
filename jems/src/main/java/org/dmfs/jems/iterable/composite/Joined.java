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

package org.dmfs.jems.iterable.composite;

import org.dmfs.jems.iterable.adapters.PresentValues;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;

import java.util.Iterator;


/**
 * An {@link Iterable} which joins other {@link Iterable}s by iterating the values of them one after another.
 * <p>
 * Note: other frameworks call this operation, "flatten". While this is technically appropriate, it often doesn't really express the intent very well. For
 * instance, if you want to iterate the elements of two lists one after another, this code is probably easier to grasp:
 * <pre><code>
 * Iterable&lt;SomeType&gt; result = new Joined&lt;&gt;(list1, list2);
 * </code></pre>
 * than this one
 * <pre><code>
 * Iterable&lt;SomeType&gt; result = new Flattened&lt;&gt;(list1, list2);
 * </code></pre>
 *
 * @param <T>
 *     The type of the iterated elements.
 *
 * @author Marten Gajda
 */
public final class Joined<T> implements Iterable<T>
{
    private final Iterable<? extends Iterable<? extends T>> mIterables;


    @SafeVarargs
    public Joined(Optional<? extends Iterable<? extends T>>... iterables)
    {
        this(new PresentValues<>(iterables));
    }


    @SafeVarargs
    public Joined(Iterable<? extends T>... iterables)
    {
        this(new Seq<>(iterables));
    }


    public Joined(Iterable<? extends Iterable<? extends T>> iterables)
    {
        mIterables = iterables;
    }


    @Override
    public Iterator<T> iterator()
    {
        return new org.dmfs.iterators.decorators.Flattened<>(mIterables.iterator());
    }
}
