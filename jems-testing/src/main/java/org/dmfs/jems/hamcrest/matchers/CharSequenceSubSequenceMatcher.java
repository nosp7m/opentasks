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

import org.dmfs.jems.iterable.elementary.Seq;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.AllOf;

import java.util.Locale;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceCharAtMatcher.hasChars;
import static org.dmfs.jems.hamcrest.matchers.CharSequenceLengthMatcher.hasLength;
import static org.hamcrest.Matchers.hasToString;


/**
 * A {@link Matcher} to check that all sub-sequences of a {@link CharSequence} are valid {@link CharSequence}s themselves.
 *
 * @author Marten Gajda
 */
public final class CharSequenceSubSequenceMatcher extends TypeSafeDiagnosingMatcher<CharSequence>
{
    private final CharSequence mExpectedValue;
    private final int mSubSequenceTestDepth;


    /**
     * Test that sub-sequences of a {@link CharSequence} are valid {@link CharSequence}s as well.
     *
     * @param expectedValue
     *         The value to compare.
     * @param subSequenceTestDepth
     *         The recursion depth.
     *
     * @return A {@link CharSequenceSubSequenceMatcher}
     */
    public static CharSequenceSubSequenceMatcher hasSubSequences(CharSequence expectedValue, int subSequenceTestDepth)
    {
        return new CharSequenceSubSequenceMatcher(expectedValue, subSequenceTestDepth);
    }


    public CharSequenceSubSequenceMatcher(CharSequence expectedValue, int subSequenceTestDepth)
    {
        mExpectedValue = expectedValue;
        mSubSequenceTestDepth = subSequenceTestDepth;
    }


    @Override
    protected boolean matchesSafely(CharSequence item, Description mismatchDescription)
    {
        if (mSubSequenceTestDepth == 0)
        {
            // don't test any deeper to avoid infinite loops
            return true;
        }
        int max = mExpectedValue.length();

        // note, this loop can be quite intense for longer charSequences, consider reducing the tested range
        for (int i = -max - 1; i <= max * 2 + 1; ++i)
        {
            for (int j = -max - 1; j <= max * 2 + 1; ++j)
            {
                if (i < 0 || j > max || j < i)
                {
                    // test illegal indexes
                    try
                    {
                        item.subSequence(i, j);
                        mismatchDescription.appendText(String.format(Locale.ENGLISH, "subSequence(%d, %d) did not throw", i, j));
                        return false;
                    }
                    catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e)
                    {
                        // pass
                    }
                }
                else
                {
                    // test valid indexes
                    String expected = mExpectedValue.subSequence(i, j).toString();
                    Matcher<CharSequence> subSequenceMatcher = new AllOf<>(
                            new Seq<Matcher<? super CharSequence>>(
                                    hasToString(expected),
                                    hasLength(expected.length()),
                                    hasChars(expected),
                                    hasSubSequences(expected, mSubSequenceTestDepth - 1)));
                    if (!subSequenceMatcher.matches(item.subSequence(i, j)))
                    {
                        mismatchDescription.appendText(String.format(Locale.ENGLISH, "subSequence(%d, %d) ", i, j));
                        subSequenceMatcher.describeMismatch(item.subSequence(i, j), mismatchDescription);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    @Override
    public void describeTo(Description description)
    {
        description.appendText("sub-sequences match \"").appendText(mExpectedValue.toString()).appendText("\"");
    }
}
