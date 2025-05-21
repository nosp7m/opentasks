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

package org.dmfs.android.contentpal.testing.android.uri;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.android.uri.UriBuilderMatcher.builds;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.hasParam;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.junit.Assert.assertThat;


/**
 * TODO: move to Bolts
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UriBuilderMatcherTest
{
    @Test
    public void test()
    {
        assertThat(builds(hasParam("p", "1")), matches(new Uri.Builder().scheme("https").authority("example.com").appendQueryParameter("p", "1")));
        assertThat(builds(hasParam("p", "1")),
                mismatches(new Uri.Builder().scheme("https").authority("example.com").appendQueryParameter("p", "2"), "built Uri parameter \"p\" was \"2\""));
        assertThat(builds(hasParam("p", "1")), describesAs("builds a Uri with parameter \"p\" is \"1\""));
    }

}