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

package org.dmfs.jems.iterator.decorators;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.generatable.elementary.Sequence;
import org.dmfs.jems.iterator.elementary.Seq;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Truncated}.
 *
 * @author Marten Gajda
 */
public class TruncatedTest
{
    @Test
    public void testIterator()
    {
        assertThat(() -> new Truncated<>(0, EmptyIterator.instance()), is(emptyIterator()));
        assertThat(() -> new Truncated<>(0, new Seq<>(1)), is(emptyIterator()));
        assertThat(() -> new Truncated<>(0, new Seq<>(1, 2, 3)), is(emptyIterator()));
        assertThat(() -> new Truncated<>(-1, new Seq<>(1, 2, 3)), is(emptyIterator()));

        assertThat(() -> new Truncated<>(1, EmptyIterator.instance()), is(emptyIterator()));

        assertThat(() -> new Truncated<>(1, new Seq<>(1, 2, 3)), is(iteratorOf(1)));
        assertThat(() -> new Truncated<>(2, new Seq<>(1, 2, 3)), is(iteratorOf(1, 2)));
        assertThat(() -> new Truncated<>(3, new Seq<>(1, 2, 3)), is(iteratorOf(1, 2, 3)));
        assertThat(() -> new Truncated<>(4, new Seq<>(1, 2, 3)), is(iteratorOf(1, 2, 3)));
        assertThat(() -> new Truncated<>(5, new Seq<>(1, 2, 3)), is(iteratorOf(1, 2, 3)));
    }


    @Test
    public void testGenerator()
    {
        MatcherAssert.assertThat(new org.dmfs.jems.iterable.decorators.Truncated<>(0, new Sequence<>(5, i -> i + 1)), is(emptyIterable()));
        MatcherAssert.assertThat(new org.dmfs.jems.iterable.decorators.Truncated<>(1, new Sequence<>(5, i -> i + 1)), iteratesTo(5));
        MatcherAssert.assertThat(new org.dmfs.jems.iterable.decorators.Truncated<>(5, new Sequence<>(5, i -> i + 1)), iteratesTo(5, 6, 7, 8, 9));
    }
}