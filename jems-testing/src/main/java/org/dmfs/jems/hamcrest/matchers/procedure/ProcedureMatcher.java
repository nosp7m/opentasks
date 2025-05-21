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

import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.procedure.Procedure;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} to test simple {@link Procedure}s.
 * <pre><code>
 * // simple case &gt; no mocked Ctor arguments
 * assertThat(new Testee("1"), processes(()-&gt;arg, equalTo(...)));
 * </code></pre>
 */
public final class ProcedureMatcher<Argument> extends TypeSafeDiagnosingMatcher<Procedure<? super Argument>>
{
    private final Generator<? extends Argument> mArgumentGenerator;
    private final Matcher<? super Argument> mDelegate;


    public static <Argument> Matcher<Procedure<? super Argument>> processes(Generator<? extends Argument> argumentGenerator, Matcher<? super Argument> delegate)
    {
        return new ProcedureMatcher<>(argumentGenerator, delegate);
    }


    public ProcedureMatcher(Generator<? extends Argument> argumentGenerator, Matcher<? super Argument> delegate)
    {
        mArgumentGenerator = argumentGenerator;
        mDelegate = delegate;
    }


    @Override
    protected boolean matchesSafely(Procedure<? super Argument> item, Description mismatchDescription)
    {
        Argument argument = mArgumentGenerator.next();
        item.process(argument);
        if (!mDelegate.matches(argument))
        {
            mismatchDescription.appendText("processed argument ");
            mDelegate.describeMismatch(argument, mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("processes ")
                .appendValue(mArgumentGenerator.next())
                .appendText(" to ")
                .appendDescriptionOf(mDelegate);
    }
}
