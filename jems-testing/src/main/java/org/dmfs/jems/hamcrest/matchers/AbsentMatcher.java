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

import org.dmfs.jems.mockito.doubles.DummySingle;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;
import org.dmfs.optional.Optional;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.NoSuchElementException;


/**
 * {@link Matcher} or {@link Optional} that matches when the tested value is absent.
 *
 * @author Marten Gajda
 * @author Gabor Keszthelyi
 * @deprecated in favour of {@link org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher}.
 */
@Deprecated
public final class AbsentMatcher<T> extends TypeSafeDiagnosingMatcher<Optional<T>>
{
    private final Single<T> mFallbackDummySingle;


    private AbsentMatcher(Single<T> fallbackDummySingle)
    {
        mFallbackDummySingle = fallbackDummySingle;
    }


    @Override
    protected boolean matchesSafely(Optional<T> item, Description mismatchDescription)
    {
        if (item.isPresent())
        {
            mismatchDescription.appendText("present");
            return false;
        }

        try
        {
            item.value();
            mismatchDescription.appendText("value() did not throw NoSuchElementException");
            return false;
        }
        catch (NoSuchElementException e)
        {
            // pass
        }

        try
        {
            T fallbackDummy = mFallbackDummySingle.value();
            if (item.value(fallbackDummy) != fallbackDummy)
            {
                mismatchDescription.appendText("value(default) did not return the default value");
                return false;
            }
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException("ClassCastException in AbsentMatcher, need to use #absent(Class<T> c) or #absent(T t) method", e);
        }

        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("absent");
    }


    /**
     * Creates a matcher that matches when the tested value is absent,
     * <p>
     * works with any kind of `Optional` class.
     *
     * @param fallbackDummy
     *         used for testing the {@link Optional#value(Object)} method
     * @param <T>
     *         the type parameter of the tested {@link Optional}
     */
    public static <T> AbsentMatcher<T> isAbsent(T fallbackDummy)
    {
        return new AbsentMatcher<>(new ValueSingle<T>(fallbackDummy));
    }


    /**
     * Creates a matcher that matches when the tested value is absent,
     * <p>
     * works with `Optional` classes whose parameter type is a general type, or an interface, or a non-final class (so Mockito can create a dummy instance)
     * (throws with clear message otherwise).
     *
     * @param <T>
     *         the type parameter of the tested {@link Optional}
     */
    public static <T> AbsentMatcher<T> isAbsent(final Class<T> clazz)
    {
        return new AbsentMatcher<>(new DummySingle<T>(clazz, "in AbsentMatcher, need to use #absent() or #absent(T t) method"));
    }


    /**
     * Creates a matcher that matches when the tested value is absent,
     * <p>
     * works with `Optional` classes that have generic type parameter (throws with clear message otherwise).
     */
    public static <T> AbsentMatcher<T> isAbsent()
    {
        //noinspection unchecked
        return (AbsentMatcher<T>) new AbsentMatcher<>(new ValueSingle<>(new Object()));
    }
}
