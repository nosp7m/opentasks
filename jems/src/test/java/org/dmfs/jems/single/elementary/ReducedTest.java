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

package org.dmfs.jems.single.elementary;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.iterables.SingletonIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.function.BiFunction;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.SingleMatcher.hasValue;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class ReducedTest
{
    @Test
    public void testEmptyIterable()
    {
        Object dummy = new Object();
        assertThat(
                new Reduced<Object, Object>(dummy, failingMock(BiFunction.class), EmptyIterable.instance()),
                hasValue(sameInstance(dummy)));
    }


    @Test
    public void testSingletonIterable()
    {
        assertThat(
                new Reduced<>("0", (s, s2) -> s + s2, new SingletonIterable<>("1")),
                hasValue("01"));
    }


    @Test
    public void testSeqIterable()
    {
        assertThat(
                new Reduced<>("0", (s, s2) -> s + s2, new Seq<>("1", "2", "3", "4")),
                hasValue("01234"));
    }


    @Test
    public void testGeneratorEmptyIterable()
    {
        Object dummy = new Object();
        assertThat(
                new Reduced<Object, Object>(() -> dummy, failingMock(BiFunction.class), EmptyIterable.instance()),
                hasValue(sameInstance(dummy)));
    }


    @Test
    public void testGeneratorSingletonIterable()
    {
        assertThat(
                new Reduced<String, String>(() -> "0", (s, s2) -> s + s2, new SingletonIterable<>("1")),
                hasValue("01"));
    }


    @Test
    public void testGeneratorSeqIterable()
    {
        assertThat(
                new Reduced<String, String>(() -> "0", (s, s2) -> s + s2, new Seq<>("1", "2", "3", "4")),
                hasValue("01234"));
    }

}