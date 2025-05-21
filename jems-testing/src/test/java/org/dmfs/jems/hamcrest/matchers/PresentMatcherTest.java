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

package org.dmfs.jems.hamcrest.matchers;

import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Test for {@link PresentMatcher}.
 *
 * @author Marten Gajda
 */
public class PresentMatcherTest
{
    @Test
    public void testMatchesSafely() throws Exception
    {
        assertThat(new PresentMatcher<>().matchesSafely(Absent.absent(), new Description.NullDescription()), CoreMatchers.is(false));
        assertThat(new PresentMatcher<String>().matchesSafely(new Present<>("test"), new Description.NullDescription()), CoreMatchers.is(true));
        assertThat(new PresentMatcher<>("test").matchesSafely(new Present<>("test"), new Description.NullDescription()), CoreMatchers.is(true));
        assertThat(new PresentMatcher<>("test").matchesSafely(new Present<>("tost"), new Description.NullDescription()), CoreMatchers.is(false));

        // test that not returning the present value in value(T) will fail.
        Optional<String> mockOptional = failingMock(Optional.class);
        doReturn(true).when(mockOptional).isPresent();
        doReturn("test").when(mockOptional).value();
        doReturn("wrong").when(mockOptional).value((String) ArgumentMatchers.any());
        assertThat(new PresentMatcher<>("test").matchesSafely(mockOptional, new Description.NullDescription()), CoreMatchers.is(false));
    }
}