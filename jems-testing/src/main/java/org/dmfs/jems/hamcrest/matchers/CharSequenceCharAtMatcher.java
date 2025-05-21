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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Locale;


/**
 * A {@link Matcher} which checks that {@code charAt} of a {@link CharSequence} returns expected values.
 *
 * @author Marten Gajda
 */
public final class CharSequenceCharAtMatcher extends TypeSafeDiagnosingMatcher<CharSequence>
{
    private final CharSequence mExpectedValue;


    /**
     * Returns a {@link Matcher} to check the {@code charAt} method of a {@link CharSequence} by comparing it to a String.
     *
     * @param expectedValues
     *         A {@link String} which contains the expected characters.
     *
     * @return A {@link CharSequenceCharAtMatcher}.
     */
    public static CharSequenceCharAtMatcher hasChars(CharSequence expectedValues)
    {
        return new CharSequenceCharAtMatcher(expectedValues);
    }


    public CharSequenceCharAtMatcher(CharSequence expectedValue)
    {
        mExpectedValue = expectedValue;
    }


    @Override
    protected boolean matchesSafely(CharSequence item, Description mismatchDescription)
    {
        for (int i = -mExpectedValue.length() - 1; i < mExpectedValue.length() * 2 + 1; ++i)
        {
            if (i < 0 || i >= mExpectedValue.length())
            {
                try
                {
                    item.charAt(i);
                    mismatchDescription.appendText(String.format(Locale.ENGLISH, "Did not throw when accessing index %d", i));
                    return false;
                }
                catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e)
                {
                    // pass
                }
            }
            else if (item.charAt(i) != mExpectedValue.charAt(i))
            {
                mismatchDescription.appendText(String.format(Locale.ENGLISH, "char at %d was '%c'", i, item.charAt(i)));
                return false;
            }
        }

        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("chars match \"").appendText(mExpectedValue.toString()).appendText("\"");
    }
}
