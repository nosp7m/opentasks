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

package org.dmfs.jems.hamcrest.matchers.throwable;

import org.hamcrest.Matcher;

import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * A collection of {@link Matcher}s for {@link Throwable}s.
 * <p>
 * TODO: consider implementing a custom Matcher instead of using instanceOf to get better error messages
 *
 * @author Marten Gajda
 */
public final class ThrowableMatcher
{
    @SafeVarargs
    public static Matcher<Throwable> throwable(Matcher<? super Throwable>... features)
    {
        return allOf(features);
    }


    public static Matcher<Throwable> throwable(Class<? extends Throwable> exceptionClass)
    {
        return instanceOf(exceptionClass);
    }


    public static Matcher<Throwable> throwable(Class<? extends Throwable> exceptionClass, Iterable<Matcher<? super Throwable>> features)
    {
        return allOf(instanceOf(exceptionClass), allOf(features));
    }


    @SafeVarargs
    public static Matcher<Throwable> throwable(Class<? extends Throwable> exceptionClass, Matcher<? super Throwable>... features)
    {
        return allOf(instanceOf(exceptionClass), allOf(features));
    }


    public static Matcher<Throwable> withMessage(Matcher<String> messageMatcher)
    {
        return having("message", Throwable::getMessage, messageMatcher);
    }


    public static Matcher<Throwable> withMessage(String message)
    {
        return having("message", Throwable::getMessage, is(message));
    }


    public static Matcher<Throwable> causedBy(Matcher<? super Throwable> reasonMatcher)
    {
        return having("caused by", Throwable::getCause, reasonMatcher);
    }
}
