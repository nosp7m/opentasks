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

package org.dmfs.jems.hamcrest.matchers.predicate;

import org.dmfs.jems.predicate.Predicate;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.predicate.PredicateMatcher.satisfiedBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link PredicateMatcher}.
 *
 * @author Marten Gajda
 */
public class PredicateMatcherTest
{

    @Test
    public void test()
    {
        Predicate<String> mockPredicate = mock(Predicate.class);
        doReturn(true).when(mockPredicate).satisfiedBy("testee");
        doReturn(false).when(mockPredicate).satisfiedBy("nontestee");

        assertThat(satisfiedBy("testee").matches(mockPredicate), is(true));
        assertThat(satisfiedBy("nontestee").matches(mockPredicate), is(false));
    }


    @Test
    public void testDescribeMismatch()
    {
        Predicate<String> mockPredicate = mock(Predicate.class);
        doReturn(false).when(mockPredicate).satisfiedBy(anyString());

        Description description = new StringDescription();
        satisfiedBy("testee").describeMismatch(mockPredicate, description);
        assertThat(description.toString(), is("was not satisfied by testee"));
    }


    @Test
    public void testDescribeTo()
    {
        Description description = new StringDescription();
        satisfiedBy("testee").describeTo(description);
        assertThat(description.toString(), is("satisfied by testee"));
    }
}