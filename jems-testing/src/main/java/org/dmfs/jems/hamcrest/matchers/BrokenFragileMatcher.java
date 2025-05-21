/*
 * Copyright 2018 dmfs GmbH
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

import org.dmfs.jems.fragile.Fragile;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.CoreMatchers.instanceOf;


/**
 * A {@link Matcher} for {@link Fragile}s which are expected to be broken.
 *
 * @author Marten Gajda
 */
public final class BrokenFragileMatcher<E extends Throwable> extends TypeSafeDiagnosingMatcher<Fragile<?, E>>
{
    private final Matcher<? super E> mExceptionMatcher;


    public static <E extends Throwable> Matcher<Fragile<?, E>> throwing(Matcher<E> exceptionMatcher)
    {
        return new BrokenFragileMatcher<>(exceptionMatcher);
    }


    public static <E extends Throwable> Matcher<Fragile<?, E>> throwing(Class<E> exceptionClass)
    {
        return new BrokenFragileMatcher<>(instanceOf(exceptionClass));
    }


    /**
     * @deprecated in favour of {@link #throwing(Class)} (for the better name).
     */
    @Deprecated
    public static <E extends Throwable> Matcher<Fragile<?, E>> isBroken(Class<E> exceptionClass)
    {
        return new BrokenFragileMatcher<>(instanceOf(exceptionClass));
    }


    public BrokenFragileMatcher(Matcher<? super E> exceptionMatcher)
    {
        mExceptionMatcher = exceptionMatcher;
    }


    @Override
    protected boolean matchesSafely(Fragile<?, E> item, Description mismatchDescription)
    {
        try
        {
            item.value();
            mismatchDescription.appendText("Fragile was not broken");
            return false;
        }
        catch (Throwable throwable)
        {
            if (!mExceptionMatcher.matches(throwable))
            {
                mismatchDescription.appendText("broken Fragile threw ");
                mExceptionMatcher.describeMismatch(throwable, mismatchDescription);
                return false;
            }
            return true;
        }
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("broken Fragile throwing ");
        description.appendDescriptionOf(mExceptionMatcher);
    }
}
