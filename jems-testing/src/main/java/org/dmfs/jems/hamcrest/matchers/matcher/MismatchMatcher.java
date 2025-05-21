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
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * A {@link Matcher} to check if another {@link Matcher} mismatches a specific value with a specific description.
 *
 * @author Marten Gajda
 */
public final class MismatchMatcher<V, T extends Matcher<? super V>> extends TypeSafeDiagnosingMatcher<T>
{
    private final V mMismatchItem;
    private final Matcher<? super String> mDescriptionMatcher;


    public MismatchMatcher(V mismatchItem, Matcher<? super String> descriptionMatcher)
    {
        mMismatchItem = mismatchItem;
        mDescriptionMatcher = descriptionMatcher;
    }


    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription)
    {
        if (item.matches(mMismatchItem))
        {
            mismatchDescription.appendText(String.format("did match \"%s\"", mMismatchItem.toString()));
            return false;
        }
        Description description = new StringDescription();
        item.describeMismatch(mMismatchItem, description);
        if (!mDescriptionMatcher.matches(description.toString()))
        {
            mismatchDescription.appendText("description ");
            mDescriptionMatcher.describeMismatch(description.toString(), mismatchDescription);
            return false;
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("mismatches with description ");
        mDescriptionMatcher.describeTo(description);
    }
}
