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

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TargetMatcherTest
{
    @Test
    public void testTargets()
    {
        Uri dummyUri = dummy(Uri.class);
        assertThat(targets(sameInstance(dummyUri)).matches(ContentProviderOperation.newInsert(dummyUri)), is(true));
        assertThat(targets(sameInstance(dummyUri)).matches(ContentProviderOperation.newInsert(dummy(Uri.class))), is(false));
    }


    @Test
    public void testTargets1()
    {
        Uri uri = Uri.parse("http://example.org/test");
        assertThat(targets(uri).matches(ContentProviderOperation.newInsert(uri)), is(true));
        assertThat(targets(uri).matches(ContentProviderOperation.newInsert(Uri.parse("http://example.com"))), is(false));
    }


    @Test
    public void testTargets2()
    {
        assertThat(targets("http://example.org/test").matches(ContentProviderOperation.newInsert(Uri.parse("http://example.org/test"))), is(true));
        assertThat(targets("http://example.org/test").matches(ContentProviderOperation.newInsert(Uri.parse("http://example.com"))), is(false));
    }


    @Test
    public void testMatchesSafelyMismatch()
    {
        Description description = new StringDescription();
        targets("http://example.com").matchesSafely(ContentProviderOperation.newInsert(Uri.parse("http://example.org/test")), description);
        assertThat(description.toString(), is("Operation target was <http://example.org/test>"));
    }


    @Test
    public void testDescribeTo()
    {
        Description description = new StringDescription();
        targets("http://example.com").describeTo(description);
        assertThat(description.toString(), is("Operation target is <http://example.com>"));
    }

}