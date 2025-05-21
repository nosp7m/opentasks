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

import org.dmfs.jems.function.Function;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.is;


/**
 * A {@link Matcher} to test {@link Function}s.
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link FragileFunctionMatcher}
 */
@Deprecated
public final class FunctionMatcher<Argument, Result> extends TypeSafeDiagnosingMatcher<Function<? super Argument, ? extends Result>>
{
    private final Argument mArgument;
    private final Matcher<? super Result> mResultMatcher;


    /**
     * Returns a {@link Matcher} which verifies that the tested {@link Function} associates the given result to the given argument.
     *
     * @deprecated in favor of {@link FragileFunctionMatcher#associates(Object, Object)}
     */
    @Deprecated
    public static <Argument, Result> Matcher<Function<? super Argument, ? extends Result>> associates(Argument argument, Result result)
    {
        return new FunctionMatcher<>(argument, is(result));
    }


    /**
     * Returns a {@link Matcher} which verifies that the tested {@link Function} associates a result satisfying the given {@link Matcher} to the given
     * argument.
     *
     * @deprecated in favor of {@link FragileFunctionMatcher#associates(Object, Matcher)}.
     */
    @Deprecated
    public static <Argument, Result> Matcher<Function<? super Argument, ? extends Result>> associates(Argument argument, Matcher<? super Result> resultMatcher)
    {
        return new FunctionMatcher<>(argument, resultMatcher);
    }


    public FunctionMatcher(Argument mArgument, Matcher<? super Result> mResultMatcher)
    {
        this.mArgument = mArgument;
        this.mResultMatcher = mResultMatcher;
    }


    @Override
    protected boolean matchesSafely(Function<? super Argument, ? extends Result> item, Description mismatchDescription)
    {
        Result result = item.value(mArgument);
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
