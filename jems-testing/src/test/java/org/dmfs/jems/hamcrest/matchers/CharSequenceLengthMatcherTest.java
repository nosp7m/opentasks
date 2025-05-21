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

import static org.dmfs.jems.hamcrest.matchers.CharSequenceLengthMatcher.hasLength;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author marten
 */
public class CharSequenceLengthMatcherTest
{
    @Test
    public void testMatchesSafely() throws Exception
    {
        Description describeTo = new StringDescription();
        assertTrue(hasLength(0).matchesSafely("", describeTo));
        assertTrue(hasLength(1).matchesSafely("1", describeTo));
        assertTrue(hasLength(3).matchesSafely("123", describeTo));
    }


    @Test
    public void testMismatches() throws Exception
    {
        Description describeTo = new StringDescription();

        assertFalse(hasLength(2).matchesSafely("1234", describeTo));

        assertThat(describeTo.toString(), is("had length 4"));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        Description describeTo = new StringDescription();
        hasLength(3).describeTo(describeTo);
        assertThat(describeTo.toString(), is("has length 3"));
    }

}