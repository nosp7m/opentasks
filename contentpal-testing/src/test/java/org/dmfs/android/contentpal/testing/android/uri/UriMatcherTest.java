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

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.absolute;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.encodedPath;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.hasParam;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.hasParamSet;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.hierarchical;
import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * TODO: move to Bolts
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UriMatcherTest
{
    @Test
    public void test()
    {
        assertThat(absolute(),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com")),
                        matches(Uri.parse("mailto:mail@example.com")),
                        mismatches(Uri.parse("non/absolute"), "absolute was <false>"),
                        describesAs("absolute is <true>")));

        assertThat(hierarchical(),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com")),
                        matches(Uri.parse("non/absolute")),
                        mismatches(Uri.parse("mailto:mail@example.com"), "hierarchical was <false>"),
                        describesAs("hierarchical is <true>")));

        assertThat(encodedPath("/abc/def%20123"),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com/abc/def%20123")),
                        matches(Uri.parse("/abc/def%20123")),
                        mismatches(Uri.parse("https://example.com/abc/def%21123"), "path was \"/abc/def%21123\""),
                        describesAs("path is \"/abc/def%20123\"")));

        assertThat(hasParam("param", "value"),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com/?param=value")),
                        mismatches(Uri.parse("https://example.com/"), "parameter \"param\" was null"),
                        mismatches(Uri.parse("https://example.com/?param=value1"), "parameter \"param\" was \"value1\""),
                        describesAs("parameter \"param\" is \"value\"")));

        assertThat(hasParam("param", is("value")),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com/?param=value")),
                        mismatches(Uri.parse("https://example.com/"), "parameter \"param\" was null"),
                        mismatches(Uri.parse("https://example.com/?param=value1"), "parameter \"param\" was \"value1\""),
                        describesAs("parameter \"param\" is \"value\"")));

        assertThat(hasParamSet(is(emptyIterable())),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com/")),
                        mismatches(Uri.parse("https://example.com/?param"), startsWith("parameters ")),
                        mismatches(Uri.parse("https://example.com/?param=value1"), startsWith("parameters ")),
                        describesAs("parameters is an empty iterable")));

        assertThat(hasParamSet(containsInAnyOrder(pair("a", "1"), pair("b", "2"))),
                Matchers.<org.hamcrest.Matcher<Uri>>allOf(matches(Uri.parse("https://example.com/?a=1&b=2")),
                        matches(Uri.parse("https://example.com/?b=2&a=1")),
                        mismatches(Uri.parse("https://example.com/?a=1"), startsWith("parameters ")),
                        mismatches(Uri.parse("https://example.com/?a=1&b=2&c=3"), startsWith("parameters ")),
                        mismatches(Uri.parse("https://example.com/?a=1&b=3"), startsWith("parameters ")),
                        describesAs(
                                "parameters iterable over [is Pair with left value: \"a\" , and right value: \"1\", is Pair with left value: \"b\" , and right value: \"2\"] in any order")));

    }

}