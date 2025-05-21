/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.iterator.adapters;

import org.dmfs.jems.generator.Generator;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * Test {@link Infinite}.
 *
 * @author Marten Gajda
 */
public final class InfiniteTest
{

    @Test
    public void test()
    {
        // note we can not really test "infinite" results. Given 100% test coverage, it should be safe to assume a success if it returns exactly what the generator returns.

        AtomicReference<Object> reference = new AtomicReference<>(new Object());

        // a dummy Generator which returns the atomic reference
        Generator<Object> dummyGenerator = reference::get;

        Iterator<Object> infinityIterator = new Infinite<>(dummyGenerator);

        for (int i = 0; i < 100; ++i)
        {
            assertThat(infinityIterator.hasNext(), is(true));
            assertThat(infinityIterator.next(), sameInstance(reference.get()));

            // prepare a new result
            reference.set(new Object());
        }
    }
}