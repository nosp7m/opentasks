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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class NumberedTest
{
    @Test
    public void testEmpty()
    {
        assertThat(new Numbered<>(EmptyIterable.instance()), emptyIterable());
        assertThat(new Numbered<>(EmptyIterable.instance(), 1), emptyIterable());
        assertThat(new Numbered<>(EmptyIterable.instance(), 1, 1), emptyIterable());
    }


    @Test
    public void testBase0Step1()
    {
        assertThat(new Numbered<>(new Seq<>("a")), iteratesTo(pair(is(0), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c")), iteratesTo(pair(is(0), is("a")), pair(is(1), is("b")), pair(is(2), is("c"))));

        assertThat(new Numbered<>(new Seq<>("a"), 0), iteratesTo(pair(is(0), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 0), iteratesTo(pair(is(0), is("a")), pair(is(1), is("b")), pair(is(2), is("c"))));

        assertThat(new Numbered<>(new Seq<>("a"), 0, 1), iteratesTo(pair(is(0), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 0, 1), iteratesTo(pair(is(0), is("a")), pair(is(1), is("b")), pair(is(2), is("c"))));
    }


    @Test
    public void testBase1Step1()
    {
        assertThat(new Numbered<>(new Seq<>("a"), 1), iteratesTo(pair(is(1), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 1), iteratesTo(pair(is(1), is("a")), pair(is(2), is("b")), pair(is(3), is("c"))));

        assertThat(new Numbered<>(new Seq<>("a"), 1, 1), iteratesTo(pair(is(1), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 1, 1), iteratesTo(pair(is(1), is("a")), pair(is(2), is("b")), pair(is(3), is("c"))));
    }


    @Test
    public void testBase0Step10()
    {
        assertThat(new Numbered<>(new Seq<>("a"), 0, 10), iteratesTo(pair(is(0), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 0, 10), iteratesTo(pair(is(0), is("a")), pair(is(10), is("b")), pair(is(20), is("c"))));
    }


    @Test
    public void testBase10Step10()
    {
        assertThat(new Numbered<>(new Seq<>("a"), 10, 10), iteratesTo(pair(is(10), is("a"))));
        assertThat(new Numbered<>(new Seq<>("a", "b", "c"), 10, 10), iteratesTo(pair(is(10), is("a")), pair(is(20), is("b")), pair(is(30), is("c"))));
    }

}