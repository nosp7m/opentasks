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

import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.causedBy;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.throwable;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.withMessage;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link ThrowableMatcher}.
 *
 * @author Marten Gajda
 */
public class ThrowableMatcherTest
{
    @Test
    public void testThrowable()
    {
        assertThat(throwable(anything()),
                allOf(
                        matches(new RuntimeException()),
                        matches(new IllegalStateException())));

        assertThat(throwable(anything(), anything()),
                allOf(
                        matches(new RuntimeException()),
                        matches(new IllegalStateException())));

        assertThat(throwable(anything(), not(anything())),
                allOf(
                        mismatches(new RuntimeException()),
                        mismatches(new IllegalStateException())));

        assertThat(throwable(not(anything()), not(anything())),
                allOf(
                        mismatches(new RuntimeException()),
                        mismatches(new IllegalStateException())));

        assertThat(throwable(RuntimeException.class),
                allOf(
                        matches(new RuntimeException()),
                        matches(new IllegalStateException())));
        assertThat(throwable(RuntimeException.class, anything()), matches(new RuntimeException()));
        assertThat(throwable(RuntimeException.class, anything(), anything()), matches(new RuntimeException()));
        assertThat(throwable(RuntimeException.class, new Seq<>(anything(), anything())), matches(new RuntimeException()));
        assertThat(throwable(RuntimeException.class, new Seq<>(anything(), anything())), matches(new RuntimeException()));

        assertThat(throwable(not(anything())),
                mismatches(new IllegalStateException(), "not ANYTHING was <java.lang.IllegalStateException>"));
        assertThat(throwable(IllegalStateException.class), mismatches(new Exception(), "<java.lang.Exception> is a java.lang.Exception"));
        assertThat(throwable(IllegalStateException.class, anything()),
                mismatches(new Exception(), "an instance of java.lang.IllegalStateException <java.lang.Exception> is a java.lang.Exception"));
        assertThat(throwable(IllegalStateException.class, not(anything())),
                mismatches(new IllegalStateException(), "(not ANYTHING) not ANYTHING was <java.lang.IllegalStateException>"));
        assertThat(throwable(IllegalStateException.class, new Seq<>(not(anything()))),
                mismatches(new IllegalStateException(), "(not ANYTHING) not ANYTHING was <java.lang.IllegalStateException>"));

        assertThat(throwable(not(anything())),
                mismatches(new IllegalStateException(), "not ANYTHING was <java.lang.IllegalStateException>"));
        assertThat(throwable(IllegalStateException.class), mismatches(new Exception(), "<java.lang.Exception> is a java.lang.Exception"));
        assertThat(throwable(IllegalStateException.class, anything()),
                mismatches(new Exception(), "an instance of java.lang.IllegalStateException <java.lang.Exception> is a java.lang.Exception"));
        assertThat(throwable(IllegalStateException.class, not(anything())),
                mismatches(new IllegalStateException(), "(not ANYTHING) not ANYTHING was <java.lang.IllegalStateException>"));
        assertThat(throwable(IllegalStateException.class, new Seq<>(not(anything()))),
                mismatches(new IllegalStateException(), "(not ANYTHING) not ANYTHING was <java.lang.IllegalStateException>"));

    }


    @Test
    public void testWithMessage()
    {
        assertThat(withMessage("xy"), matches(new Exception("xy")));
        assertThat(withMessage("xy"), mismatches(new Exception("ab"), "message was \"ab\""));
        assertThat(withMessage("xy"), describesAs("message is \"xy\""));

        assertThat(withMessage(is("xy")), matches(new Exception("xy")));
        assertThat(withMessage(is("xy")), mismatches(new Exception("ab"), "message was \"ab\""));
        assertThat(withMessage(is("xy")), describesAs("message is \"xy\""));
    }


    @Test
    public void testCausedBy()
    {
        assertThat(causedBy(throwable(Exception.class)),
                matches(new Exception("xy", new Exception())));

        assertThat(causedBy(throwable(Exception.class, withMessage("ab"))),
                mismatches(new Exception("xy", new Exception("12")), "caused by (message is \"ab\") message is \"ab\" message was \"12\""));

        assertThat(causedBy(throwable(Exception.class)),
                describesAs("caused by an instance of java.lang.Exception"));
    }
}