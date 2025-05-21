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

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.function.BiFunction;
import org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DiffTest
{
    @Test
    public void test() throws Exception
    {
        // empty Iterables result in an empty Diff
        assertThat(new Diff<>(EmptyIterable.<String>instance(), EmptyIterable.<String>instance(), new TestFunction()),
            emptyIterable());

        // one empty side, means this side is always absent in the result
        assertThat(new Diff<>(new Seq<>("a"), EmptyIterable.<String>instance(), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(absent()))));
        assertThat(new Diff<>(new Seq<>("a", "b"), EmptyIterable.<String>instance(), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(absent())),
                pair(is(present("b")), is(absent()))));
        assertThat(new Diff<>(new Seq<>("a", "b", "c"), EmptyIterable.<String>instance(), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(absent())),
                pair(is(present("b")), is(absent())),
                pair(is(present("c")), is(absent()))));

        assertThat(new Diff<>(EmptyIterable.<String>instance(), new Seq<>("a"), new TestFunction()),
            iteratesTo(
                pair(is(absent()), is(present("a")))));
        assertThat(new Diff<>(EmptyIterable.<String>instance(), new Seq<>("a", "b"), new TestFunction()),
            iteratesTo(
                pair(is(absent()), is(present("a"))),
                pair(is(absent()), is(present("b")))));
        assertThat(new Diff<>(EmptyIterable.<String>instance(), new Seq<>("a", "b", "c"), new TestFunction()),
            iteratesTo(
                pair(is(absent()), is(present("a"))),
                pair(is(absent()), is(present("b"))),
                pair(is(absent()), is(present("c")))));

        // equals Iterables result in pairs with both sides present
        assertThat(new Diff<>(new Seq<>("a"), new Seq<>("a"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a")))));
        assertThat(new Diff<>(new Seq<>("a", "b"), new Seq<>("a", "b"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a"))),
                pair(is(present("b")), is(present("b")))));
        assertThat(new Diff<>(new Seq<>("a", "b", "c"), new Seq<>("a", "b", "c"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a"))),
                pair(is(present("b")), is(present("b"))),
                pair(is(present("c")), is(present("c")))));

        // various different Iterables
        assertThat(new Diff<>(new Seq<>("a"), new Seq<>("b"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(AbsentMatcher.<String>absent())),
                pair(is(absent()), is(present("b")))));
        assertThat(new Diff<>(new Seq<>("a"), new Seq<>("a", "b"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a"))),
                pair(is(absent()), is(present("b")))));
        assertThat(new Diff<>(new Seq<>("a", "b"), new Seq<>("b"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(absent())),
                pair(is(present("b")), is(present("b")))));

        assertThat(new Diff<>(new Seq<>("a", "c"), new Seq<>("a", "b", "c"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a"))),
                pair(is(absent()), is(present("b"))),
                pair(is(present("c")), is(present("c")))));

        assertThat(new Diff<>(new Seq<>("a", "b", "c"), new Seq<>("a", "c"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(present("a"))),
                pair(is(present("b")), is(absent())),
                pair(is(present("c")), is(present("c")))));

        assertThat(new Diff<>(new Seq<>("b"), new Seq<>("a", "b", "c"), new TestFunction()),
            iteratesTo(
                pair(is(absent()), is(present("a"))),
                pair(is(present("b")), is(present("b"))),
                pair(is(absent()), is(present("c")))));

        assertThat(new Diff<>(new Seq<>("a", "b", "c"), new Seq<>("b"), new TestFunction()),
            iteratesTo(
                pair(is(present("a")), is(absent())),
                pair(is(present("b")), is(present("b"))),
                pair(is(present("c")), is(absent()))));
    }


    private final static class TestFunction implements BiFunction<String, String, Integer>
    {

        @Override
        public Integer value(String s, String s2)
        {
            return s.compareTo(s2);
        }
    }
}