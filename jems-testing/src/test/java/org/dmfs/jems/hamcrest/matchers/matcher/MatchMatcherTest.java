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

package org.dmfs.jems.hamcrest.matchers.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link MatchMatcher}.
 *
 * @author Marten Gajda
 */
public class MatchMatcherTest
{

    @Test
    public void testMatches() throws Exception
    {
        Object dummy = new Object();
        Matcher mockMatcher = failingMock(Matcher.class);
        doReturn(true).when(mockMatcher).matches(dummy);

        assertThat(new MatchMatcher<>(dummy).matches(mockMatcher), is(true));
        assertThat(matches(dummy).matches(mockMatcher), is(true));
    }


    @Test
    public void testMismatches() throws Exception
    {
        Object dummy = new Object();
        Matcher mockMatcher = failingMock(Matcher.class);
        doReturn(false).when(mockMatcher).matches(dummy);

        assertThat(new MatchMatcher<>(dummy).matches(mockMatcher), is(false));
        assertThat(matches(dummy).matches(mockMatcher), is(false));
    }


    @Test
    public void testMismatchDescription() throws Exception
    {
        String dummy = "dummy";
        Matcher mockMatcher = failingMock(Matcher.class);
        doReturn(false).when(mockMatcher).matches(dummy);

        Description description = new StringDescription();
        new MatchMatcher<>(dummy).describeMismatch(mockMatcher, description);

        assertThat(description.toString(), is("didn't match \"dummy\""));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        assertThat(new MatchMatcher<>("test"), describesAs("matches \"test\""));
    }
}