/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.testing.projection;

import org.dmfs.android.contentpal.Projection;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projectsEmpty;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class ProjectionMatcherTest
{
    @Test
    public void testMatchesSafelyEmpty()
    {
        Projection emptyMockProjection = failingMock(Projection.class);
        doReturn(new String[0]).when(emptyMockProjection).toArray();

        assertThat(projectsEmpty().matches(emptyMockProjection), is(true));
        assertThat(projects("123").matches(emptyMockProjection), is(false));
    }


    @Test
    public void testMatchesSafelyNonEmpty()
    {
        Projection mockProjection = failingMock(Projection.class);
        doAnswer(invocation -> new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        assertThat(projects("1", "2", "3").matches(mockProjection), is(true));
    }


    @Test
    public void testMismatchesSafely1()
    {
        Projection mockProjection = failingMock(Projection.class);
        doAnswer(invocation -> new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        assertThat(projectsEmpty().matches(mockProjection), is(false));
        assertThat(projects("1").matches(mockProjection), is(false));
        assertThat(projects("1", "2").matches(mockProjection), is(false));
        assertThat(projects("1", "2", "4").matches(mockProjection), is(false));
        assertThat(projects("1", "2", "3", "4").matches(mockProjection), is(false));
    }


    @Test
    public void testNonImmutable()
    {
        Projection mockProjection = failingMock(Projection.class);
        // return the same array on every call, so modifications are "permanent"
        doReturn(new String[] { "1", "2", "3" }).when(mockProjection).toArray();
        assertThat(projects("1", "2", "3").matches(mockProjection), is(false));
    }


    @Test
    public void testDescribeTo()
    {
        Description description = new StringDescription();
        projects("123", "456").describeTo(description);
        assertThat(description.toString(), is("iterable containing [\"123\", \"456\"]"));
    }


    @Test
    public void testDescribeToEmpty()
    {
        Description description = new StringDescription();
        projectsEmpty().describeTo(description);
        assertThat(description.toString(), is("an empty iterable"));
    }

}