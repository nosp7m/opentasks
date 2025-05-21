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

import java.util.NoSuchElementException;


/**
 * {@link Matcher} of {@link Optional} that matches when the tested value is absent.
 *
 * @author Marten Gajda
 */
public final class AbsentMatcher<T> extends TypeSafeDiagnosingMatcher<Optional<? extends T>>
{
    /**
     * Creates a matcher that matches when the tested value is absent.
     */
    public static <T> AbsentMatcher<T> absent()
    {
        return new AbsentMatcher<>();
    }


    @Override
    protected boolean matchesSafely(Optional<? extends T> item, Description mismatchDescription)
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
            return true;
        }
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("absent");
    }
}
