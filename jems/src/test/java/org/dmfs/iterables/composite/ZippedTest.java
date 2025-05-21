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

package org.dmfs.iterables.composite;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.function.BiFunction;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;


/**
 * @author Marten Gajda
 */
public class ZippedTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new Zipped<>(EmptyIterable.<String>instance(), EmptyIterable.<String>instance(), new TestFunction()), emptyIterable());
        assertThat(new Zipped<>(new Seq<>("1", "2", "3"), EmptyIterable.<String>instance(), new TestFunction()), emptyIterable());
        assertThat(new Zipped<>(EmptyIterable.<String>instance(), new Seq<>("a", "b", "c"), new TestFunction()), emptyIterable());

        assertThat(new Zipped<>(new Seq<>("1"), new Seq<>("a", "b", "c"), new TestFunction()), contains("1a"));
        assertThat(new Zipped<>(new Seq<>("1", "2", "3"), new Seq<>("a"), new TestFunction()), contains("1a"));
        assertThat(new Zipped<>(new Seq<>("1", "2", "3"), new Seq<>("a", "b", "c"), new TestFunction()), contains("1a", "2b", "3c"));
    }


    private final static class TestFunction implements BiFunction<String, String, String>
    {
        @Override
        public String value(String s, String s2)
        {
            return s + s2;
        }
    }
}