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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.composite.Zipped;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.function.elementary.PairingFunction;
import org.dmfs.jems.iterable.generators.IntSequenceGenerator;
import org.dmfs.jems.pair.Pair;


/**
 * An {@link Iterable} decorator which pairs every value of another iterator with it's sequential ordinal number.
 * <p>
 * Example:
 * <pre><code>
 *     Iterable&lt;String&gt; strings = new Seq&lt;&gt;("a", "b", "c");
 *     for (Pair&lt;Integer, String&gt; item:new Numbered&lt;&gt;(strings))
 *     {
 *        System.out.println(String.format("%d: %s", item.left(), item.right());
 *     }
 * </code></pre>
 * results in
 * <pre>
 * 0: a
 * 1: b
 * 2: c
 * </pre>
 *
 * @author Marten Gajda
 */
public final class Numbered<T> extends DelegatingIterable<Pair<Integer, T>>
{
    public Numbered(Iterable<T> delegate)
    {
        this(delegate, 0, 1);
    }


    public Numbered(Iterable<T> delegate, int start)
    {
        this(delegate, start, 1);
    }


    public Numbered(Iterable<T> delegate, int start, int step)
    {
        super(new Zipped<>(new IntSequenceGenerator(start, step), delegate, new PairingFunction<Integer, T>()));
    }
}
