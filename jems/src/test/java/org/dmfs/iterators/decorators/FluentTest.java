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
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * @author marten
 */
public class FluentTest
{
    @Test
    public void filtered_empty_iterator()
    {
        assertThat(() -> new Fluent<>(new EmptyIterator<>()).filtered((e) -> true), is(emptyIterator()));
    }


    @Test
    public void filtered_none_pass()
    {
        assertThat(() -> new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered((e) -> false), is(emptyIterator()));
    }


    @Test
    public void filtered_some_pass()
    {
        assertThat(() -> new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered((e) -> e.length() != 2),
                is(iteratorOf("1", "2", "3", "100", "200", "300")));
    }


    @Test
    public void filtered_all_pass() throws Exception
    {
        assertThat(() -> new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered((e) -> true),
                is(iteratorOf("1", "2", "3", "10", "20", "30", "100", "200", "300")));
    }


    @Test
    public void mapped() throws Exception
    {
        assertThat(() -> new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).mapped(Integer::parseInt),
                is(iteratorOf(1, 2, 3, 10, 20, 30, 100, 200, 300)));
    }

}