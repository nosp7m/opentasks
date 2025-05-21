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

import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.pair.Pair;
import org.dmfs.jems.pair.elementary.ValuePair;
import org.hamcrest.Matcher;

import androidx.annotation.NonNull;

import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.CoreMatchers.is;


/**
 * Matcher for parts of a {@link Uri}.
 * <p>
 * TODO: move to Bolts project
 *
 * @author Marten Gajda
 */
public final class UriMatcher
{
    @NonNull
    public static Matcher<Uri> hasParamSet(@NonNull Matcher<Iterable<? extends Pair<String, String>>> paramNamesMatcher)
    {
        return having(
                "parameters",
                uri -> new Mapped<>(name -> new ValuePair<>(name, uri.getQueryParameter(name)), uri.getQueryParameterNames()),
                paramNamesMatcher);
    }


    @NonNull
    public static Matcher<Uri> hasParam(@NonNull String name, @NonNull String value)
    {
        return hasParam(name, is(value));
    }


    @NonNull
    public static Matcher<Uri> hasParam(@NonNull String name, @NonNull Matcher<String> valueMatcher)
    {
        return having(String.format("parameter \"%s\"", name), uri -> uri.getQueryParameter(name), valueMatcher);
    }


    @NonNull
    public static Matcher<Uri> encodedPath(@NonNull String path)
    {
        return having("path", Uri::getEncodedPath, is(path));
    }


    @NonNull
    public static Matcher<Uri> hierarchical()
    {
        return having("hierarchical", Uri::isHierarchical, is(true));
    }


    @NonNull
    public static Matcher<Uri> absolute()
    {
        return having("absolute", Uri::isAbsolute, is(true));
    }
}
