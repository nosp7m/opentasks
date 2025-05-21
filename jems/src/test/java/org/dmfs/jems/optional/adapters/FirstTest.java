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

package org.dmfs.jems.optional.adapters;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.iterators.Filter;
import org.dmfs.jems.predicate.Predicate;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link First}.
 *
 * @author Marten Gajda
 */
public class FirstTest
{
    @Test
    public void testFirst() throws Exception
    {
        assertThat(new First<>(EmptyIterable.<String>instance()), is(absent()));
        assertThat(new First<>(new Seq<>("test")), is(present("test")));
        assertThat(new First<>(new Seq<>("test", "test123")), is(present("test")));
    }


    @Test
    public void testFilteredFirst() throws Exception
    {
        Filter<String> mockFilter = failingMock(Filter.class);
        doReturn(false).when(mockFilter).iterate(anyString());
        doReturn(true).when(mockFilter).iterate("test123");

        assertThat(new First<>(EmptyIterable.<String>instance(), mockFilter), is(absent()));
        assertThat(new First<>(new Seq<>("test"), mockFilter), is(absent()));
        assertThat(new First<>(new Seq<>("test", "test123"), mockFilter), is(present("test123")));
    }


    @Test
    public void testSievedFirst() throws Exception
    {
        Predicate<String> mockPredicate = failingMock(Predicate.class);
        doReturn(false).when(mockPredicate).satisfiedBy(anyString());
        doReturn(true).when(mockPredicate).satisfiedBy("test123");

        assertThat(new First<>(EmptyIterable.<String>instance(), mockPredicate), is(absent()));
        assertThat(new First<>(new Seq<>("test"), mockPredicate), is(absent()));
        assertThat(new First<>(new Seq<>("test", "test123"), mockPredicate), is(present("test123")));
    }
}