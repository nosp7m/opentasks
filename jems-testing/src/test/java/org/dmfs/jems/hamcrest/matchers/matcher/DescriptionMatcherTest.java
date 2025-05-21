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
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;


/**
 * Unit test for {@link DescriptionMatcher}.
 *
 * @author Marten Gajda
 */
public class DescriptionMatcherTest
{

    @Test
    public void testMatches() throws Exception
    {
        Matcher<String> mockMatcher = failingMock(Matcher.class);
        doAnswer(invocation -> {
            Description description = invocation.getArgument(0);
            description.appendText("description");
            return null;
        }).when(mockMatcher).describeTo(any(Description.class));
        assertThat(new DescriptionMatcher<>(is("description")), matches(mockMatcher));
        assertThat(describesAs(is("description")), matches(mockMatcher));
    }


    @Test
    public void testMismatches() throws Exception
    {
        Matcher<String> mockMatcher = failingMock(Matcher.class);
        doAnswer(invocation -> {
            Description description = invocation.getArgument(0);
            description.appendText("text");
            return null;
        }).when(mockMatcher).describeTo(any(Description.class));
        assertThat(new DescriptionMatcher<>(is("description")), mismatches(mockMatcher, "description was \"text\""));
        assertThat(describesAs(is("description")), mismatches(mockMatcher, "description was \"text\""));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        Description description = new StringDescription();
        new DescriptionMatcher<>(is("text")).describeTo(description);

        assertThat(description.toString(), is("description is \"text\""));
    }
}