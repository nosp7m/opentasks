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

package org.dmfs.jems.hamcrest.matchers.function;

import org.dmfs.jems.function.FragileFunction;
import org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.Matchers.is;


/**
 * A {@link Matcher} to test {@link FragileFunction}s.
 */
public final class FragileFunctionMatcher<Argument, Result, E extends Throwable> extends TypeSafeDiagnosingMatcher<FragileFunction<? super Argument, ? extends Result, E>>
{
    private final Argument mArgument;
    private final Matcher<? super Result> mResultMatcher;


    /**
     * Returns a {@link Matcher} which verifies that the tested {@link FragileFunction} throws the given throwable to the given argument.
     */
    public static <Argument, Result, E extends Throwable> Matcher<FragileFunction<? super Argument, ? extends Result, E>> throwing(
        Argument argument,
        Matcher<E> throwableMatcher)
    {
        return having("value Fragile", testee -> () -> testee.value(argument), is(BrokenFragileMatcher.throwing(throwableMatcher)));
    }


    /**
     * Returns a {@link Matcher} which verifies that the tested {@link FragileFunction} associates the given result to the given argument.
     */
    public static <Argument, Result, E extends Throwable> Matcher<FragileFunction<? super Argument, ? extends Result, E>> associates(Argument argument,
                                                                                                                                     Result result)
    {
        return new FragileFunctionMatcher<>(argument, is(result));
    }


    /**
     * Returns a {@link Matcher} which verifies that the tested {@link FragileFunction} associates a result satisfying the given {@link Matcher} to the given
     * argument.
     */
    public static <Argument, Result, E extends Throwable> Matcher<FragileFunction<? super Argument, ? extends Result, E>> associates(Argument argument,
                                                                                                                                     Matcher<? super Result> resultMatcher)
    {
        return new FragileFunctionMatcher<>(argument, resultMatcher);
    }


    public FragileFunctionMatcher(Argument mArgument, Matcher<? super Result> mResultMatcher)
    {
        this.mArgument = mArgument;
        this.mResultMatcher = mResultMatcher;
    }


    @Override
    protected boolean matchesSafely(FragileFunction<? super Argument, ? extends Result, E> item, Description mismatchDescription)
    {
        Result result = null;
        try
        {
            result = item.value(mArgument);
        }
        catch (Throwable throwable)
        {
            mismatchDescription.appendText(String.format("threw %s for argument %s", throwable, mArgument));
            return false;
        }
        if (!mResultMatcher.matches(result))
        {
            mismatchDescription.appendText(String.format("result for argument %s ", mArgument));
            mResultMatcher.describeMismatch(result, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description
            .appendText(String.format("result for argument %s ", mArgument))
            .appendDescriptionOf(mResultMatcher);
    }

}
