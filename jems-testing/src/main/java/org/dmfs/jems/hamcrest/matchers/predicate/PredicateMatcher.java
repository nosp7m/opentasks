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

package org.dmfs.jems.hamcrest.matchers.predicate;

import org.dmfs.jems.predicate.Predicate;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} for {@link Predicate}s.
 *
 * @author Marten Gajda
 */
public final class PredicateMatcher<T> extends TypeSafeDiagnosingMatcher<Predicate<T>>
{
    private final T mTestee;


    public static <T> Matcher<Predicate<T>> satisfiedBy(T testee)
    {
        return new PredicateMatcher<>(testee);
    }


    public PredicateMatcher(T mTestee)
    {
        this.mTestee = mTestee;
    }


    @Override
    protected boolean matchesSafely(Predicate<T> item, Description mismatchDescription)
    {
        if (!item.satisfiedBy(mTestee))
        {
            mismatchDescription.appendText(String.format("was not satisfied by %s", mTestee));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format("satisfied by %s", mTestee));
    }
}
