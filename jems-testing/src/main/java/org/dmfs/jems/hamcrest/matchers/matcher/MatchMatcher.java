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

package org.dmfs.jems.hamcrest.matchers.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} to check if another Matcher matches a specific value.
 *
 * @author Marten Gajda
 */
public final class MatchMatcher<V, T extends Matcher<? super V>> extends TypeSafeDiagnosingMatcher<T>
{
    private final V mMatchingValue;


    public MatchMatcher(V matchingValue)
    {
        mMatchingValue = matchingValue;
    }


    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription)
    {
        if (!item.matches(mMatchingValue))
        {
            mismatchDescription.appendText(String.format("didn't match \"%s\"", mMatchingValue.toString()));
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText(String.format("matches \"%s\"", mMatchingValue.toString()));
    }
}
