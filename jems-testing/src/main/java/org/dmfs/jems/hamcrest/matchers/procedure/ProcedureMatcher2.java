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

package org.dmfs.jems.hamcrest.matchers.procedure;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.procedure.Procedure;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * Usage like
 * <pre><code>
 *     // Procedure with single mocked ctor argument
 *     assertThat(param -&gt; new Testee(param, "x"), processes(()-&gt; param, arg, equalTo(...)));
 * </code></pre>
 */
public final class ProcedureMatcher2<Param, Argument>
        extends TypeSafeDiagnosingMatcher<Function<? super Param, ? extends Procedure<? super Argument>>>
{
    private final Generator<Param> mParamGenerator;
    private final Argument mArgument;
    private final Matcher<? super Param> mDelegate;


    public static <Param, Argument> Matcher<Function<? super Param, ? extends Procedure<? super Argument>>> processes(
            Generator<Param> paramGenerator,
            Argument argument,
            Matcher<? super Param> delegate)
    {
        return new ProcedureMatcher2<>(paramGenerator, argument, delegate);
    }


    public ProcedureMatcher2(Generator<Param> paramGenerator, Argument argument, Matcher<? super Param> delegate)
    {
        mParamGenerator = paramGenerator;
        mArgument = argument;
        mDelegate = delegate;
    }


    @Override
    protected boolean matchesSafely(Function<? super Param, ? extends Procedure<? super Argument>> item, Description mismatchDescription)
    {
        Param param = mParamGenerator.next();
        item.value(param).process(mArgument);
        if (!mDelegate.matches(param))
        {
            mismatchDescription.appendText("processed parameter ");
            mDelegate.describeMismatch(param, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("processes ")
                .appendValue(mParamGenerator.next())
                .appendText(" with ")
                .appendValue(mArgument)
                .appendText(" to ")
                .appendDescriptionOf(mDelegate);
    }
}
