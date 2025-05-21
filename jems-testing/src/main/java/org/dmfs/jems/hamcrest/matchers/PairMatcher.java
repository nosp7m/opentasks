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

import org.dmfs.jems.pair.Pair;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.CoreMatchers.equalTo;


/**
 * {@link Matcher} for {@link Pair}.
 *
 * @author Gabor Keszthelyi
 */
public final class PairMatcher<L, R> extends TypeSafeDiagnosingMatcher<Pair<? extends L, ? extends R>>
{
    private final Matcher<? super L> mLeftValueMatcher;
    private final Matcher<? super R> mRightValueMatcher;


    public PairMatcher(Matcher<? super L> leftValueMatcher, Matcher<? super R> rightValueMatcher)
    {
        mLeftValueMatcher = leftValueMatcher;
        mRightValueMatcher = rightValueMatcher;
    }


    @Override
    protected boolean matchesSafely(Pair<? extends L, ? extends R> actualPair, Description mismatchDescription)
    {
        if (!mLeftValueMatcher.matches(actualPair.left()))
        {
            mismatchDescription.appendText("Left value doesn't match: ");
            mLeftValueMatcher.describeMismatch(actualPair.left(), mismatchDescription);
            return false;
        }
        else if (!mRightValueMatcher.matches(actualPair.right()))
        {
            mismatchDescription.appendText("Right value doesn't match: ");
            mRightValueMatcher.describeMismatch(actualPair.right(), mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("is Pair with left value: ");
        mLeftValueMatcher.describeTo(description);
        description.appendText(" , and right value: ");
        mRightValueMatcher.describeTo(description);
    }


    /**
     * Matcher that matches when the provided left and right value equals() to actual {@link Pair}s left and right value.
     */
    public static <L, R> Matcher<Pair<? extends L, ? extends R>> pair(L leftValue, R rightValue)
    {
        return new PairMatcher<>(equalTo(leftValue), equalTo(rightValue));
    }


    /**
     * Matcher that matches when the provided {@link Matcher}s for the left and right value match with the {@link Pair}s left and right value.
     */
    public static <L, R> Matcher<Pair<? extends L, ? extends R>> pair(Matcher<? super L> leftValueMatcher, Matcher<? super R> rightValueMatcher)
    {
        return new PairMatcher<>(leftValueMatcher, rightValueMatcher);
    }
}
