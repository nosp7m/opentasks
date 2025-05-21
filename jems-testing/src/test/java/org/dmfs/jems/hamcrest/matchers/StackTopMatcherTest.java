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

import org.dmfs.jems.stack.Stack;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.StackTopMatcher.stackTop;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


/**
 * @author Marten Gajda
 */
public class StackTopMatcherTest
{
    @Test
    public void testValid()
    {
        Object dummyElement = new Object();
        Stack<Object> dummyStack = failingMock(Stack.class);

        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(dummyStack).when(mockStackTop).bottom();

        Matcher<Object> elementMockMatcher = failingMock(Matcher.class);
        doReturn(true).when(elementMockMatcher).matches(dummyElement);
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doReturn(true).when(bottomMockMatcher).matches(dummyStack);

        assertThat(stackTop(elementMockMatcher, bottomMockMatcher).matches(mockStackTop), is(true));
    }


    @Test
    public void testElementInvalid()
    {
        Object dummyElement = new Object();
        Stack<Object> dummyStack = failingMock(Stack.class);

        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(dummyStack).when(mockStackTop).bottom();

        Matcher<Object> elementMockMatcher = failingMock(Matcher.class);
        doReturn(false).when(elementMockMatcher).matches(dummyElement);
        doNothing().when(elementMockMatcher).describeMismatch(same(dummyElement), any(Description.class));
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doReturn(true).when(bottomMockMatcher).matches(dummyStack);

        assertThat(stackTop(elementMockMatcher, bottomMockMatcher).matches(mockStackTop), is(false));
    }


    @Test
    public void testStackInvalid()
    {
        Object dummyElement = new Object();
        Stack<Object> dummyStack = failingMock(Stack.class);

        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(dummyStack).when(mockStackTop).bottom();

        Matcher<Object> elementMockMatcher = failingMock(Matcher.class);
        doReturn(true).when(elementMockMatcher).matches(dummyElement);
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doReturn(false).when(bottomMockMatcher).matches(dummyStack);
        doNothing().when(bottomMockMatcher).describeMismatch(same(dummyStack), any(Description.class));

        assertThat(stackTop(elementMockMatcher, bottomMockMatcher).matches(mockStackTop), is(false));
    }


    @Test
    public void testCompletelyInvalid()
    {
        Object dummyElement = new Object();
        Stack<Object> dummyStack = failingMock(Stack.class);

        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(dummyStack).when(mockStackTop).bottom();

        Matcher<Object> elementMockMatcher = failingMock(Matcher.class);
        doReturn(false).when(elementMockMatcher).matches(dummyElement);
        doNothing().when(elementMockMatcher).describeMismatch(same(dummyElement), any(Description.class));
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doReturn(false).when(bottomMockMatcher).matches(dummyStack);
        doNothing().when(bottomMockMatcher).describeMismatch(same(dummyStack), any(Description.class));

        assertThat(stackTop(elementMockMatcher, bottomMockMatcher).matches(mockStackTop), is(false));
    }


    @Test
    public void test_describeTo()
    {
        Description description = new StringDescription();
        Matcher<Object> elementMockMatcher = failingMock(Matcher.class);
        doNothing().when(elementMockMatcher).describeTo(description);
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doNothing().when(bottomMockMatcher).describeTo(description);

        stackTop(elementMockMatcher, bottomMockMatcher).describeTo(description);
        MatcherAssert.assertThat(description.toString(), Matchers.is("A StackTop with element value  and bottom "));
        verify(elementMockMatcher).describeTo(description);
        verify(bottomMockMatcher).describeTo(description);
    }

}