/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.set;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.set.Set;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} which checks if a {@link Set} contains the given elements.
 *
 * @author Marten Gajda
 */
public final class SetContainsMatcher<T> extends TypeSafeDiagnosingMatcher<Set<? super T>>
{

    private final Iterable<T> mExpectedElements;


    public SetContainsMatcher(Iterable<T> expectedElements)
    {
        mExpectedElements = expectedElements;
    }


    @SafeVarargs
    public static <T> Matcher<Set<? super T>> contains(T... expected)
    {
        return contains(new Seq<>(expected));
    }


    public static <T> Matcher<Set<? super T>> contains(Iterable<T> expected)
    {
        return new SetContainsMatcher<>(expected);
    }


    @Override
    protected boolean matchesSafely(Set<? super T> item, Description mismatchDescription)
    {
        for (T expected : mExpectedElements)
        {
            if (!item.contains(expected))
            {
                mismatchDescription.appendText(String.format("did not contain %s", expected));
                return false;
            }
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("contains ");
        boolean first = true;
        for (T expected : mExpectedElements)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                description.appendText(", ");
            }
            description.appendText(expected.toString());
        }
    }
}
