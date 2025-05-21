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

package org.dmfs.jems.iterator.elementary;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher.throwing;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.throwable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Test of {@link Seq}.
 *
 * @author Marten Gajda
 */
public class SeqTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(Seq::new, emptyIterator());
        assertThat(() -> new Seq<>("1"), iteratorOf("1"));
        assertThat(() -> new Seq<>("1", "2", "3"), iteratorOf("1", "2", "3"));

        assertThat(() -> new Seq<>(0, new String[] {}), emptyIterator());
        assertThat(() -> new Seq<>(0, new String[] { "1", "2", "3" }), emptyIterator());
        assertThat(() -> new Seq<>(1, new String[] { "1" }), iteratorOf("1"));
        assertThat(() -> new Seq<>(1, new String[] { "1", "2", "3" }), iteratorOf("1"));
        assertThat(() -> new Seq<>(3, new String[] { "1", "2", "3" }), iteratorOf("1", "2", "3"));

        assertThat(() -> new Seq<>(1, new String[] {}), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
        assertThat(() -> new Seq<>(4, new String[] { "1", "2", "3" }), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
        assertThat(() -> new Seq<>(4, new String[] { "1" }), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
        assertThat(() -> new Seq<>(-1, new String[] {}), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
        assertThat(() -> new Seq<>(-1, new String[] { "1", "2", "3" }), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
        assertThat(() -> new Seq<>(-2, new String[] { "1" }), is(throwing(throwable(ArrayIndexOutOfBoundsException.class))));
    }

}