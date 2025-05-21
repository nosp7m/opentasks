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

package org.dmfs.iterators.composite;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher;
import org.dmfs.jems.pair.Pair;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Unit test for {@link PairZipped}.
 *
 * @author Marten Gajda
 */
public final class PairZippedTest
{

    @Test
    public void test()
    {
        assertThat(() -> new PairZipped<>(new EmptyIterator<>(), new EmptyIterator<>()),
                is(IteratorMatcher.<Pair<String, String>>emptyIterator()));
        assertThat(() -> new PairZipped<>(new Seq<>("1", "2", "3"), new EmptyIterator<>()),
                is(IteratorMatcher.<Pair<String, String>>emptyIterator()));
        assertThat(() -> new PairZipped<>(new EmptyIterator<>(), new Seq<>("a", "b", "c")),
                is(IteratorMatcher.<Pair<String, String>>emptyIterator()));

        assertThat(() -> new PairZipped<>(new Seq<>("1"), new Seq<>("a", "b", "c")),
                is(iteratorOf(pair("1", "a"))));
        assertThat(() -> new PairZipped<>(new Seq<>("1", "2", "3"), new Seq<>("a")),
                is(iteratorOf(pair("1", "a"))));
        assertThat(() -> new PairZipped<>(new Seq<>("1", "2", "3"), new Seq<>("a", "b", "c")),
                is(iteratorOf(pair("1", "a"), pair("2", "b"), pair("3", "c"))));
    }
}