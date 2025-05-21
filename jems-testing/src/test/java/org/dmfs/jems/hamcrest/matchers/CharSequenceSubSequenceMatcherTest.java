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

import static org.dmfs.jems.hamcrest.matchers.CharSequenceSubSequenceMatcher.hasSubSequences;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author marten
 */
public class CharSequenceSubSequenceMatcherTest
{
    @Test
    public void testMatchesSafely() throws Exception
    {
        // to be ignored
        Description describeTo = new StringDescription();
        assertTrue(hasSubSequences("", 10).matchesSafely("", describeTo));
        assertTrue(hasSubSequences("1", 10).matchesSafely("1", describeTo));
        assertTrue(hasSubSequences("123", 10).matchesSafely("123", describeTo));
    }


    @Test
    public void testMismatches1() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasSubSequences("123", 10).matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {

            public CharSequence subSequence(int i, int i1)
            {
                return i1 > 3 ? "1234" : super.subSequence(i, i1);
            }

        }, describeTo));

        assertThat(describeTo.toString(), is("subSequence(-4, 4) did not throw"));
    }


    @Test
    public void testMismatches2() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasSubSequences("123", 10).matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {

            public CharSequence subSequence(int i, int i1)
            {
                return i >= 0 && i1 <= 3 && i <= i1 ? "12" : super.subSequence(i, i1);
            }

        }, describeTo));

        assertThat(describeTo.toString(), is("subSequence(0, 0) with toString() \"\" toString() was \"12\""));
    }


    @Test
    public void testMismatches3() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasSubSequences("123", 10).matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {

            public CharSequence subSequence(int i, int i1)
            {
                return i >= 0 && i1 <= 3 && i1 < i ? "12" : super.subSequence(i, i1);
            }

        }, describeTo));

        assertThat(describeTo.toString(), is("subSequence(0, -4) did not throw"));
    }


    @Test
    public void testMismatches4() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasSubSequences("123", 10).matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {

            public CharSequence subSequence(int i, int i1)
            {
                return new CharSequenceMatcherTest.TestSequence("123".subSequence(i, i1))
                {
                    @Override
                    public char charAt(int i)
                    {
                        return i == 1 ? 'x' : super.charAt(i);
                    }
                };
            }

        }, describeTo));

        assertThat(describeTo.toString(), is("subSequence(0, 1) chars match \"1\" Did not throw when accessing index 1"));
    }


    @Test
    public void testMismatches5() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasSubSequences("123", 10).matchesSafely(new CharSequenceMatcherTest.TestSequence("123")
        {

            public CharSequence subSequence(final int i, final int i1)
            {
                return new CharSequenceMatcherTest.TestSequence("123".subSequence(i, i1))
                {
                    @Override
                    public char charAt(int index)
                    {
                        return index == 0 && i == 0 && i1 == 1 ? 'x' : super.charAt(index);
                    }
                };
            }

        }, describeTo));

        assertThat(describeTo.toString(), is("subSequence(0, 1) chars match \"1\" char at 0 was 'x'"));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        Description describeTo = new StringDescription();
        hasSubSequences("123", 10).describeTo(describeTo);
        assertThat(describeTo.toString(), is("sub-sequences match \"123\""));
    }

}