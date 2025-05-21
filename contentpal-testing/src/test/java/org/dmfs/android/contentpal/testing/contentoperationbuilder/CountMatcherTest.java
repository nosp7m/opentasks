/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.contentpal.testing.contentoperationbuilder;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CountMatcherTest
{
    @Test
    public void testMatches()
    {
        assertThat(withExpectedCount(5).matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(5)), is(true));
        assertThat(withExpectedCount(5).matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(4)), is(false));
        assertThat(withExpectedCount(0).matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(0)), is(true));
        assertThat(withExpectedCount(5).matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(0)), is(false));

        assertThat(withoutExpectedCount().matches(ContentProviderOperation.newUpdate(dummy(Uri.class))), is(true));
        assertThat(withoutExpectedCount().matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(0)), is(false));
        assertThat(withoutExpectedCount().matches(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(4)), is(false));
    }


    @Test
    public void testMatchesSafelyMismatch()
    {
        Description description = new StringDescription();
        withExpectedCount(5).matchesSafely(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(3), description);
        assertThat(description.toString(), is("expected 3 results"));
    }


    @Test
    public void testMatchesSafelyMismatch2()
    {
        Description description = new StringDescription();
        withExpectedCount(5).matchesSafely(ContentProviderOperation.newUpdate(dummy(Uri.class)), description);
        assertThat(description.toString(), is("expected no specific number of results"));
    }


    @Test
    public void testMatchesSafelyMismatch3()
    {
        Description description = new StringDescription();
        withoutExpectedCount().matchesSafely(ContentProviderOperation.newUpdate(dummy(Uri.class)).withExpectedCount(3), description);
        assertThat(description.toString(), is("expected 3 results"));
    }


    @Test
    public void testDescribeTo()
    {
        Description description = new StringDescription();
        withExpectedCount(5).describeTo(description);
        assertThat(description.toString(), is("expects 5 results"));
    }


    @Test
    public void testDescribeTo2()
    {
        Description description = new StringDescription();
        withoutExpectedCount().describeTo(description);
        assertThat(description.toString(), is("expects no specific number of results"));
    }
}