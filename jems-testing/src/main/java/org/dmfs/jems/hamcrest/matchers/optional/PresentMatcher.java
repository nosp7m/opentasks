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

package org.dmfs.jems.hamcrest.matchers.optional;

import org.dmfs.jems.optional.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

import static org.hamcrest.Matchers.equalTo;


/**
 * A {@link Matcher} to match the presence and value of an {@link Optional}.
 *
 * @author Marten Gajda
 */
public final class PresentMatcher<T> extends TypeSafeDiagnosingMatcher<Optional<? extends T>>
{
    private final Matcher<? super T> mDelegate;


    public PresentMatcher()
    {
        this(new IsAnything<T>());
    }


    public PresentMatcher(T expected)
    {
        this(new IsEqual<>(expected));
    }


    public PresentMatcher(Matcher<? super T> delegate)
    {
        mDelegate = delegate;
    }


    public static <T> PresentMatcher<T> present()
    {
        return new PresentMatcher<>();
    }


    public static <T> PresentMatcher<T> present(T expectedValue)
    {
        return new PresentMatcher<>(equalTo(expectedValue));
    }


    public static <T> PresentMatcher<T> present(Matcher<? super T> delegate)
    {
        return new PresentMatcher<>(delegate);
    }


    @Override
    protected boolean matchesSafely(Optional<? extends T> item, Description mismatchDescription)
    {
        if (!item.isPresent())
        {
            mismatchDescription.appendText("not present");
            return false;
        }

        if (!mDelegate.matches(item.value()))
        {
            mismatchDescription.appendText("present, but value ");
            mDelegate.describeMismatch(item.value(), mismatchDescription);
            return false;
        }

        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("present with value ");
        mDelegate.describeTo(description);
    }
}
