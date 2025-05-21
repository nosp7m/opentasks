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

import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.jems.stack.Stack;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.StackMatcher.emptyStack;
import static org.dmfs.jems.hamcrest.matchers.StackMatcher.stacked;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class StackMatcherTest
{
    @Test
    public void testValidEmpty()
    {
    }


    @Test
    public void testMatch()
    {
        Stack<Object> emptyMockStack = failingMock(Stack.class);
        doReturn(absent()).when(emptyMockStack).top();

        Object dummyElement = new Object();
        Stack<Object> mockStack = failingMock(Stack.class);
        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(emptyMockStack).when(mockStackTop).bottom();
        doReturn(new Present<>(mockStackTop)).when(mockStack).top();

        Object dummyElement2 = new Object();
        Stack<Object> mockStackOuter = failingMock(Stack.class);
        Stack.StackTop<Object> mockStackTop2 = failingMock(Stack.StackTop.class);
        doReturn(dummyElement2).when(mockStackTop2).element();
        doReturn(mockStack).when(mockStackTop2).bottom();
        doReturn(new Present<>(mockStackTop2)).when(mockStackOuter).top();

        assertThat(emptyStack().matches(emptyMockStack), is(true));
        assertThat(stacked(sameInstance(dummyElement)).matches(mockStack), is(true));
        assertThat(stacked(sameInstance(dummyElement2), sameInstance(dummyElement)).matches(mockStackOuter), is(true));
    }


    @Test
    public void testMismatch()
    {
        Stack<Object> emptyMockStack = failingMock(Stack.class);
        doReturn(absent()).when(emptyMockStack).top();

        Object dummyElement = new Object();
        Stack<Object> mockStack = failingMock(Stack.class);
        Stack.StackTop<Object> mockStackTop = failingMock(Stack.StackTop.class);
        doReturn(dummyElement).when(mockStackTop).element();
        doReturn(emptyMockStack).when(mockStackTop).bottom();
        doReturn(new Present<>(mockStackTop)).when(mockStack).top();

        Object dummyElement2 = new Object();
        Stack<Object> mockStackOuter = failingMock(Stack.class);
        Stack.StackTop<Object> mockStackTop2 = failingMock(Stack.StackTop.class);
        doReturn(dummyElement2).when(mockStackTop2).element();
        doReturn(mockStack).when(mockStackTop2).bottom();
        doReturn(new Present<>(mockStackTop2)).when(mockStackOuter).top();

        assertThat(emptyStack().matches(mockStack), is(false));
        assertThat(stacked(sameInstance(dummyElement2)).matches(mockStack), is(false));
        assertThat(stacked(sameInstance(dummyElement2), sameInstance(dummyElement)).matches(mockStack), is(false));

        assertThat(emptyStack().matches(mockStackOuter), is(false));
        assertThat(stacked(sameInstance(dummyElement)).matches(mockStackOuter), is(false));
        assertThat(stacked(sameInstance(dummyElement2)).matches(mockStackOuter), is(false));
        assertThat(stacked(sameInstance(dummyElement2), sameInstance(dummyElement), sameInstance(new Object())).matches(mockStackOuter), is(false));
    }


    @Test
    public void test_describeTo()
    {
        Description description = new StringDescription();
        Matcher<String> elementMockMatcher = is("xyz");
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doNothing().when(bottomMockMatcher).describeTo(description);
        doReturn("matcher").when(bottomMockMatcher).toString();

        stacked(elementMockMatcher, bottomMockMatcher).describeTo(description);
        MatcherAssert.assertThat(description.toString(), is("Elements in Stack iterable containing [is <is \"xyz\">, is <matcher>]"));
    }


    @Test
    public void test_emptyDescribeTo()
    {
        Description description = new StringDescription();
        Matcher<String> elementMockMatcher = is("xyz");
        Matcher<Stack<Object>> bottomMockMatcher = failingMock(Matcher.class);
        doNothing().when(bottomMockMatcher).describeTo(description);
        doReturn("matcher").when(bottomMockMatcher).toString();

        emptyStack().describeTo(description);
        MatcherAssert.assertThat(description.toString(), is("StackTop absent"));
    }
}