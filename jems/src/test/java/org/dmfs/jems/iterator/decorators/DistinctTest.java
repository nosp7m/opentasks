/*
 * Copyright 2020 dmfs GmbH
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
import org.dmfs.jems.iterator.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Distinct}.
 */
public class DistinctTest
{
    @Test
    public void test()
    {
        assertThat(() -> new Distinct<>(new EmptyIterator<>()), is(emptyIterator()));
        assertThat(() -> new Distinct<>(new Seq<>("1")), iteratorOf("1"));
        assertThat(() -> new Distinct<>(new Seq<>("1", "2", "3")), iteratorOf("1", "2", "3"));

        assertThat(() -> new Distinct<>(String::length, new EmptyIterator<>()), is(emptyIterator()));
        assertThat(() -> new Distinct<>(String::length, new Seq<>("1")), iteratorOf("1"));
        assertThat(() -> new Distinct<>(String::length, new Seq<>("1", "2", "3")), iteratorOf("1"));
        assertThat(() -> new Distinct<>(String::length, new Seq<>("1", "11", "2", "22", "3", "33")), iteratorOf("1", "11"));
        assertThat(() -> new Distinct<>(String::length, new Seq<>("33", "1", "11", "2", "22", "3")), iteratorOf("33", "1"));
    }
}