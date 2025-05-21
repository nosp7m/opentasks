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

import org.dmfs.jems.single.elementary.ValueSingle;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Unit test for {@link SingleMatcher}.
 *
 * @author Gabor Keszthelyi
 */
public class SingleMatcherTest
{

    @Test
    public void testMatches()
    {
        assertFalse(new SingleMatcher<>(new IsEqual<>("a")).matches(new ValueSingle<>("b")));
        assertFalse(SingleMatcher.hasValue(new IsEqual<>("a")).matches(new ValueSingle<>("b")));
        assertFalse(SingleMatcher.hasValue("a").matches(new ValueSingle<>("b")));

        assertTrue(new SingleMatcher<>(new IsEqual<>("a")).matches(new ValueSingle<>("a")));
        assertTrue(SingleMatcher.hasValue(new IsEqual<>("a")).matches(new ValueSingle<>("a")));
        assertTrue(SingleMatcher.hasValue("a").matches(new ValueSingle<>("a")));
    }


    @Test
    public void testDescribeTo()
    {
        Description mismatchMsg = new StringDescription();
        new SingleMatcher<>(new IsEqual<>("a")).describeTo(mismatchMsg);
        assertThat(mismatchMsg, hasToString("Single with value() \"a\""));
    }

}