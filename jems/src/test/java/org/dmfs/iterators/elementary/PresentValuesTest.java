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

package org.dmfs.iterators.elementary;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Unit test for {@link PresentValues}.
 *
 * @author Marten Gajda
 */
public final class PresentValuesTest
{

    private static final Absent<String> ABSENT = absent();


    @Test
    public void test()
    {
        assertThat(() -> new PresentValues<>(new EmptyIterator<>()), is(emptyIterator()));
        assertThat(() -> new PresentValues<>(new Seq<>(absent())), is(emptyIterator()));
        assertThat(() -> new PresentValues<>(new Seq<>(absent(), absent(), absent())), is(emptyIterator()));

        assertThat(() -> new PresentValues<>(new Seq<>(new Present<>("hello"))), is(iteratorOf("hello")));
        assertThat(() -> new PresentValues<>(
                        new Seq<>(new Present<>("hello"), absent(), absent(), new Present<>("foo"), new Present<>("bar"))),
                is(iteratorOf("hello", "foo", "bar")));
        assertThat(() -> new PresentValues<>(
                        new Seq<>(absent(), absent(), new Present<>("hello"), absent(), new Present<>("foo"), new Present<>("bar"), absent())),
                is(iteratorOf("hello", "foo", "bar")));

        // run same test with vararg ctor
        assertThat(PresentValues::new, is(emptyIterator()));
        assertThat(() -> new PresentValues<>(absent()), is(emptyIterator()));
        assertThat(() -> new PresentValues<>(absent(), absent(), absent()), is(emptyIterator()));

        assertThat(() -> new PresentValues<>(new Present<>("hello")), is(iteratorOf("hello")));
        assertThat(() -> new PresentValues<>(new Present<>("hello"), absent(), absent(), new Present<>("foo"), new Present<>("bar")),
                is(iteratorOf("hello", "foo", "bar")));
        assertThat(() -> new PresentValues<>(absent(), absent(), new Present<>("hello"), absent(), new Present<>("foo"), new Present<>("bar"), absent()),
                is(iteratorOf("hello", "foo", "bar")));
    }
}