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
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} which matches {@link Stack.StackTop}s.
 *
 * @author Marten Gajda
 */
public final class StackTopMatcher<E> extends TypeSafeDiagnosingMatcher<Stack.StackTop<E>>
{
    private final Matcher<E> mElementMatcher;
    private final Matcher<Stack<E>> mBottomMatcher;


    public static <E> Matcher<Stack.StackTop<E>> stackTop(Matcher<E> matcher, Matcher<Stack<E>> bottomMatcher)
    {
        return new StackTopMatcher<>(matcher, bottomMatcher);
    }


    public StackTopMatcher(Matcher<E> matcher, Matcher<Stack<E>> bottomMatcher)
    {
        mElementMatcher = matcher;
        mBottomMatcher = bottomMatcher;
    }


    @Override
    protected boolean matchesSafely(Stack.StackTop<E> item, Description mismatchDescription)
    {
        if (!mElementMatcher.matches(item.element()))
        {
            mismatchDescription.appendText("element ");
            mElementMatcher.describeMismatch(item.element(), mismatchDescription);
            return false;
        }
        if (!mBottomMatcher.matches(item.bottom()))
        {
            mismatchDescription.appendText("bottom stack ");
            mBottomMatcher.describeMismatch(item.bottom(), mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("A StackTop with element value ");
        mElementMatcher.describeTo(description);
        description.appendText(" and bottom ");
        mBottomMatcher.describeTo(description);
    }
}
