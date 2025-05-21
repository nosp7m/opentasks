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
import org.hamcrest.StringDescription;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceCharAtMatcher.hasChars;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class CharSequenceCharAtMatcherTest
{
    @Test
    public void testMatchesSafely() throws Exception
    {
        Description describeTo = new StringDescription();
        assertTrue(hasChars("").matchesSafely("", describeTo));
        assertTrue(hasChars("1").matchesSafely("1", describeTo));
        assertTrue(hasChars("123").matchesSafely("123", describeTo));
    }


    @Test
    public void testMatchesInvalid1() throws Exception
    {
        Description describeTo = new StringDescription();

        // test charAt does not equal
        assertFalse(hasChars("123").matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i != 1 ? super.charAt(i) : 'x';
            }
        }, describeTo));

        assertThat(describeTo.toString(), is("char at 1 was 'x'"));
    }


    @Test
    public void testMatchesInvalid2() throws Exception
    {
        Description describeTo = new StringDescription();
        // test charAt does not throw
        assertFalse(hasChars("123").matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i < 3 ? super.charAt(i) : '1';
            }
        }, describeTo));

        assertThat(describeTo.toString(), is("Did not throw when accessing index 3"));
    }


    @Test
    public void testMatchesInvalid3() throws Exception
    {
        Description describeTo = new StringDescription();
        // test charAt does not throw
        assertFalse(hasChars("").matchesSafely(new CharSequenceMatcherTest.TestSequence("")
        {
            @Override
            public char charAt(int i)
            {
                return i >= 0 ? '1' : super.charAt(i);
            }
        }, describeTo));

        assertThat(describeTo.toString(), is("Did not throw when accessing index 0"));
    }


    @Test
    public void testMatchesInvalid4() throws Exception
    {
        Description describeTo = new StringDescription();
        // test charAt does not throw
        assertFalse(hasChars("123").matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i >= 0 ? super.charAt(i) : '1';
            }
        }, describeTo));

        assertThat(describeTo.toString(), is("Did not throw when accessing index -4"));
    }


    @Test
    public void testDescribeTo()
    {
        Description describeTo = new StringDescription();
        hasChars("123").describeTo(describeTo);
        assertThat(describeTo.toString(), is("chars match \"123\""));
    }
}