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
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceCharAtMatcher.hasChars;
import static org.dmfs.jems.hamcrest.matchers.CharSequenceLengthMatcher.hasLength;
import static org.dmfs.jems.hamcrest.matchers.CharSequenceSubSequenceMatcher.hasSubSequences;
import static org.hamcrest.Matchers.hasToString;


/**
 * @author Marten Gajda
 */
public final class CharSequenceMatcher
{

    private CharSequenceMatcher()
    {
    }


    /**
     * Tests the contract of a {@link CharSequence} against the given String value.
     *
     * @param expected
     *         A {@link CharSequence} which represents the expected characters.
     *
     * @return A {@link Matcher} which tests {@link CharSequence}s.
     */
    public static Matcher<CharSequence> validCharSequence(CharSequence expected)
    {
        return new AllOf<>(
                new Seq<Matcher<? super CharSequence>>(
                        hasToString(expected.toString()),
                        hasLength(expected.length()),
                        hasChars(expected),
                        hasSubSequences(expected, 3)));
    }

}
