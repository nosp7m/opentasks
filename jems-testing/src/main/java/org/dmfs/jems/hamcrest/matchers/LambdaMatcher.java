/*
 * Copyright 2019 dmfs GmbH
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

import org.dmfs.jems.function.Function;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;


/**
 * A version of a {@link FeatureMatcher} which takes a delegate {@link Function} instead of requiring to override it.
 *
 * @author Marten Gajda
 */
public final class LambdaMatcher<Testee, Feature> extends FeatureMatcher<Testee, Feature>
{
    private final Function<Testee, Feature> mFunction;


    public static <Testee, Feature> Matcher<Testee> having(String name, Function<Testee, Feature> function, Matcher<? super Feature> subMatcher)
    {
        return new LambdaMatcher<>(function, subMatcher, name, name);
    }


    public static <Testee, Feature> Matcher<Testee> having(Function<Testee, Feature> function, Matcher<? super Feature> subMatcher)
    {
        return new LambdaMatcher<>(function, subMatcher, "unnamed feature", "unnamed feature");
    }


    public LambdaMatcher(Function<Testee, Feature> function, Matcher<? super Feature> subMatcher, String featureDescription, String featureName)
    {
        super(subMatcher, featureDescription, featureName);
        mFunction = function;
    }


    @Override
    protected Feature featureValueOf(Testee actual)
    {
        return mFunction.value(actual);
    }
}
