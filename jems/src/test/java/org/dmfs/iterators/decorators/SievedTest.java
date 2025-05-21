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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.predicate.elementary.Anything;
import org.dmfs.jems.predicate.elementary.Equals;
import org.dmfs.jems.predicate.elementary.Nothing;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class SievedTest
{
    @Test
    public void test()
    {
        assertThat(() -> new Sieved<>(new Equals<>("x"), new EmptyIterator<>()), is(emptyIterator()));
        assertThat(() -> new Sieved<>(new Nothing<>(), new Seq<>("1", "2", "3", "4")), is(emptyIterator()));

        assertThat(() -> new Sieved<>(new Equals<>("x"), new Seq<>("x", "y", "x", "y")), is(iteratorOf("x", "x")));
        assertThat(() -> new Sieved<>(new Anything<>(), new Seq<>("x", "y", "x", "y")), is(iteratorOf("x", "y", "x", "y")));
    }
}