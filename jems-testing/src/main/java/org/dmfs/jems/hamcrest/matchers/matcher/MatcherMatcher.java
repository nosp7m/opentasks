/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.hamcrest.matchers.matcher;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.any;


/**
 * Collection of static matcher factories to test {@link Matcher}s.
 *
 * @author Marten Gajda
 */
public final class MatcherMatcher
{
    public static <V> Matcher<Matcher<? super V>> matches(V value)
    {
        return new MatchMatcher<>(value);
    }


    public static <V> Matcher<Matcher<V>> describesAs(String description)
    {
        return describesAs(is(description));
    }


    public static <V> Matcher<Matcher<V>> describesAs(Matcher<? super String> descriptionMatcher)
    {
        return new DescriptionMatcher<>(descriptionMatcher);
    }


    public static <V> Matcher<Matcher<? super V>> mismatches(V value)
    {
        return new MismatchMatcher<>(value, any(String.class));
    }


    public static <V> Matcher<Matcher<? super V>> mismatches(V value, String mismatchDescription)
    {
        return new MismatchMatcher<>(value, is(mismatchDescription));
    }


    public static <V> Matcher<Matcher<V>> mismatches(V value, Matcher<? super String> mismatchDescriptionMatcher)
    {
        return new MismatchMatcher<>(value, mismatchDescriptionMatcher);
    }

}
