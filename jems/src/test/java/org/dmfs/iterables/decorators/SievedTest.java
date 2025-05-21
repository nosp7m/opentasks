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

package org.dmfs.iterables.decorators;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.predicate.elementary.Anything;
import org.dmfs.jems.predicate.elementary.Equals;
import org.dmfs.jems.predicate.elementary.Nothing;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class SievedTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new Sieved<>(new Anything<>(), EmptyIterable.instance()), emptyIterable());
        assertThat(new Sieved<>(new Anything<String>(), new Seq<>("1")), iteratesTo("1"));
        assertThat(new Sieved<>(new Anything<String>(), new Seq<>("1", "2")), iteratesTo("1", "2"));
        assertThat(new Sieved<>(new Anything<String>(), new Seq<>("1", "2", "3")), iteratesTo("1", "2", "3"));

        assertThat(new Sieved<>(new Equals<>("2"), new Seq<>("1")), Matchers.<String>emptyIterable());
        assertThat(new Sieved<>(new Equals<>("2"), new Seq<>("1", "2")), iteratesTo("2"));
        assertThat(new Sieved<>(new Equals<>("2"), new Seq<>("1", "2", "3")), iteratesTo("2"));
        assertThat(new Sieved<>(new Equals<>("2"), new Seq<>("2", "2", "2")), iteratesTo("2", "2", "2"));
        assertThat(new Sieved<>(new Equals<>("2"), new Seq<>("1", "2", "3", "2")), iteratesTo("2", "2"));

        assertThat(new Sieved<>(new Nothing<String>(), new Seq<>("1")), emptyIterable());
        assertThat(new Sieved<>(new Nothing<String>(), new Seq<>("1", "2")), emptyIterable());
        assertThat(new Sieved<>(new Nothing<String>(), new Seq<>("1", "2", "3")), emptyIterable());
    }

}