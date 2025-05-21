/*
 * Copyright 2019 dmfs GmbH
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
import org.dmfs.jems.generatable.elementary.Sequence;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;


/**
 * Unit test for {@link Truncated}.
 *
 * @author Marten Gajda
 */
public class TruncatedTest
{
    @Test
    public void testIterables()
    {
        assertThat(new Truncated<>(0, EmptyIterable.instance()), is(emptyIterable()));
        assertThat(new Truncated<>(-1, EmptyIterable.instance()), is(emptyIterable()));
        assertThat(new Truncated<>(1, EmptyIterable.instance()), is(emptyIterable()));
        assertThat(new Truncated<>(0, new Seq<>(3, 2, 1)), is(emptyIterable()));
        assertThat(new Truncated<>(-1, new Seq<>(3, 2, 1)), is(emptyIterable()));

        assertThat(new Truncated<>(1, new Seq<>(3, 2, 1)), iteratesTo(3));
        assertThat(new Truncated<>(2, new Seq<>(3, 2, 1)), iteratesTo(3, 2));
        assertThat(new Truncated<>(3, new Seq<>(3, 2, 1)), iteratesTo(3, 2, 1));
        assertThat(new Truncated<>(4, new Seq<>(3, 2, 1)), iteratesTo(3, 2, 1));
        assertThat(new Truncated<>(5, new Seq<>(3, 2, 1)), iteratesTo(3, 2, 1));
    }


    @Test
    public void testGeneratable()
    {
        assertThat(new Truncated<>(0, new Sequence<>(5, i -> i + 1)), is(emptyIterable()));
        assertThat(new Truncated<>(1, new Sequence<>(5, i -> i + 1)), iteratesTo(5));
        assertThat(new Truncated<>(5, new Sequence<>(5, i -> i + 1)), iteratesTo(5, 6, 7, 8, 9));
    }
}