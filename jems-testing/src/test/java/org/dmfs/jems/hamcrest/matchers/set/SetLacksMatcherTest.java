/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.set;

import org.dmfs.jems.set.Set;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.hamcrest.matchers.set.SetLacksMatcher.lacks;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public final class SetLacksMatcherTest
{
    @Test
    public void test()
    {
        Set<String> mock = failingMock(Set.class);
        doReturn(false).when(mock).contains(any());
        doReturn(true).when(mock).contains("a");
        doReturn(true).when(mock).contains("b");
        assertThat(lacks("x"), matches(mock));
        assertThat(lacks("y"), matches(mock));
        assertThat(lacks("x", "y"), matches(mock));
        assertThat(lacks("x", "y", "a"), mismatches(mock, "did contain a"));
        assertThat(lacks("a", "b"), mismatches(mock, "did contain a"));
        assertThat(lacks("a", "b", "c"), describesAs("contains none of a, b, c"));
    }
}