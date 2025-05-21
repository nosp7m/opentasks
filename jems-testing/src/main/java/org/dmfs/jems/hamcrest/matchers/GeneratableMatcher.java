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

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.generatable.Generatable;
import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} to test {@link Generatable}s for the correct start sequence. Note, due do the infinite nature of a {@link Generatable} it is not possible
 * to test all the results.
 *
 * @author Marten Gajda
 */
public final class GeneratableMatcher<T> extends TypeSafeDiagnosingMatcher<Generatable<T>>
{
    private final Iterable<Matcher<T>> mExpectedStartSequence;


    @SafeVarargs
    public static <T> Matcher<Generatable<T>> startsWith(T... elements)
    {
        return new GeneratableMatcher<>(new Mapped<>(CoreMatchers::is, new Seq<>(elements)));
    }


    @SafeVarargs
    public static <T> Matcher<Generatable<T>> startsWith(Matcher<T>... elements)
    {
        return new GeneratableMatcher<>(new Seq<>(elements));
    }


    public static <T> Matcher<Generatable<T>> startsWith(Iterable<Matcher<T>> elements)
    {
        return new GeneratableMatcher<>(elements);
    }


    public GeneratableMatcher(Iterable<Matcher<T>> expectedStartSequence)
    {
        mExpectedStartSequence = expectedStartSequence;
    }


    @Override
    protected boolean matchesSafely(Generatable<T> item, Description mismatchDescription)
    {
        int pos = 0;
        Generator<T> generator = item.generator();
        for (Matcher<T> matcher : mExpectedStartSequence)
        {
            T next = generator.next();
            if (!matcher.matches(next))
            {
                mismatchDescription.appendText(String.format("element at position %d ", pos));
                matcher.describeMismatch(next, mismatchDescription);
                return false;
            }
            pos += 1;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("Generatable with start sequence ");
        boolean first = true;
        for (Matcher<T> matcher : mExpectedStartSequence)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                description.appendText(", ");
            }
            matcher.describeTo(description);
        }
    }
}
