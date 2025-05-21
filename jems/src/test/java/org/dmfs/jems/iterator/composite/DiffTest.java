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

package org.dmfs.jems.iterator.composite;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.pair.Pair;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Diff}.
 *
 * @author Marten Gajda
 */
public class DiffTest
{
    @Test
    public void test()
    {
        assertThat(() -> new Diff<>(new EmptyIterator<>(), new EmptyIterator<>(), (l, r) -> 0),
                is(emptyIterator()));

        assertThat(() -> new Diff<>(new Seq<>("a", "a", "b", "c"), new EmptyIterator<>(), String::compareTo),
                is(iteratorOf(
                        pair(is(present("a")), is(absent())),
                        pair(is(present("a")), is(absent())),
                        pair(is(present("b")), is(absent())),
                        pair(is(present("c")), is(absent())))));

        assertThat(() -> new Diff<>(new EmptyIterator<>(), new Seq<>("a", "a", "b", "c"), String::compareTo),
                is(iteratorOf(
                        pair(is(absent()), is(present("a"))),
                        pair(is(absent()), is(present("a"))),
                        pair(is(absent()), is(present("b"))),
                        pair(is(absent()), is(present("c")))
                )));

        assertThat(() -> new Diff<>(new Seq<>("1", "2", "3", "4"), new Seq<>("a", "a", "b", "c"), String::compareTo),
                is(iteratorOf(
                        pair(is(present("1")), is(AbsentMatcher.<String>absent())),
                        pair(is(present("2")), is(absent())),
                        pair(is(present("3")), is(absent())),
                        pair(is(present("4")), is(absent())),
                        pair(is(absent()), is(present("a"))),
                        pair(is(absent()), is(present("a"))),
                        pair(is(absent()), is(present("b"))),
                        pair(is(absent()), is(present("c")))
                )));

        assertThat(() -> new Diff<>(new Seq<>("a", "b", "b", "c"), new Seq<>("a", "a", "b", "c"), String::compareTo),
                is(iteratorOf(
                        pair(is(present("a")), is(present("a"))),
                        pair(is(absent()), is(present("a"))),
                        pair(is(present("b")), is(present("b"))),
                        pair(is(present("b")), is(absent())),
                        pair(is(present("c")), is(present("c")))
                )));

        assertThat(() -> new Diff<>(new Seq<>("b", "b", "c"), new Seq<>("a", "a", "b"), String::compareTo),
                is(iteratorOf(
                        pair(is(absent()), is(present("a"))),
                        pair(is(absent()), is(present("a"))),
                        pair(is(present("b")), is(present("b"))),
                        pair(is(present("b")), is(absent())),
                        pair(is(present("c")), is(absent()))
                )));
    }
}