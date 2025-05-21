/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.mockito;

import org.junit.Test;

import java.util.List;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.called;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.calledInOrder;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.notCalled;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link MockInteractionMatcher}.
 */
public class MockInteractionMatcherTest
{
    @Test
    public void testOrdered()
    {
        List<String> mockListCorrect = mock(List.class);
        mockListCorrect.isEmpty();
        mockListCorrect.size();
        List<String> mockListWrongOrder = mock(List.class);
        mockListWrongOrder.size();
        mockListWrongOrder.isEmpty();
        List<String> mockListTooMany = mock(List.class);
        mockListTooMany.isEmpty();
        mockListTooMany.size();
        mockListTooMany.isEmpty();
        List<String> mockListTooFew1 = mock(List.class);
        mockListTooFew1.size();
        List<String> mockListTooFew2 = mock(List.class);
        mockListTooFew2.isEmpty();
        List<String> mockListNone = mock(List.class);

        assertThat(calledInOrder(List::isEmpty, List::size),
                allOf(
                        matches(mockListCorrect),
                        mismatches(mockListWrongOrder),
                        mismatches(mockListTooMany),
                        mismatches(mockListTooFew1),
                        mismatches(mockListTooFew2),
                        mismatches(mockListNone)));

        assertThat(calledInOrder(Object::toString),
                describesAs("mock is called in order"));
    }


    @Test
    public void testUnOrdered()
    {
        List<String> mockListCorrect1 = mock(List.class);
        mockListCorrect1.isEmpty();
        mockListCorrect1.size();
        List<String> mockListCorrect2 = mock(List.class);
        mockListCorrect2.size();
        mockListCorrect2.isEmpty();
        List<String> mockListTooMany = mock(List.class);
        mockListTooMany.isEmpty();
        mockListTooMany.size();
        mockListTooMany.isEmpty();
        List<String> mockListTooFew1 = mock(List.class);
        mockListTooFew1.size();
        List<String> mockListTooFew2 = mock(List.class);
        mockListTooFew2.isEmpty();
        List<String> mockListNone = mock(List.class);

        assertThat(called(List::isEmpty, List::size),
                allOf(
                        matches(mockListCorrect1),
                        matches(mockListCorrect2),
                        mismatches(mockListTooMany),
                        mismatches(mockListTooFew1),
                        mismatches(mockListTooFew2),
                        mismatches(mockListNone)));

        assertThat(called(Object::toString),
                describesAs("mock is called in any order"));
    }


    @Test
    public void testNoInteractions()
    {
        List<String> mockList1 = mock(List.class);
        mockList1.isEmpty();
        List<String> mockList2 = mock(List.class);
        mockList2.isEmpty();
        mockList2.size();
        List<String> mockListNone = mock(List.class);

        assertThat(notCalled(),
                allOf(
                        mismatches(mockList1),
                        mismatches(mockList2),
                        matches(mockListNone)));

        assertThat(notCalled(),
                describesAs("mock is not called"));
    }

}