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

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.elementary.Seq;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class ExpandedTest
{

    @Test
    public void test() throws Exception
    {
        Function<Object, Iterable<Object>> dummyFunction = dummy(Function.class);
        assertThat(new Expanded<>(dummyFunction, EmptyIterable.instance()), Matchers.emptyIterable());
        assertThat(new Expanded<>(
                        s -> new Seq<>(s + "1", s + "2", s + "3"),
                        new Seq<>("a", "b", "c")
                ),
                contains("a1", "a2", "a3", "b1", "b2", "b3", "c1", "c2", "c3"));
    }
}