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

import org.dmfs.jems.single.Single;
import org.hamcrest.CoreMatchers;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;


/**
 * {@link Matcher} for {@link Single}.
 *
 * @author Gabor Keszthelyi
 */
public final class SingleMatcher<T> extends FeatureMatcher<Single<? extends T>, T>
{
    public SingleMatcher(Matcher<? super T> valueMatcher)
    {
        super(valueMatcher, "Single with value()", "Single with value()");
    }


    @Override
    protected T featureValueOf(Single<? extends T> actual)
    {
        return actual.value();
    }


    public static <T> Matcher<Single<? extends T>> hasValue(Matcher<T> valueMatcher)
    {
        return new SingleMatcher<>(valueMatcher);
    }


    public static <T> Matcher<Single<? extends T>> hasValue(T expectedValue)
    {
        return new SingleMatcher<>(CoreMatchers.equalTo(expectedValue));
    }
}
