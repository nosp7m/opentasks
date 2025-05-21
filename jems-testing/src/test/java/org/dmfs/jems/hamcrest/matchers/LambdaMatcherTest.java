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

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.describesAs;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link LambdaMatcher}.
 *
 * @author Marten Gajda
 */
public class LambdaMatcherTest
{
    @Test
    public void test()
    {
        assertThat(having(Object::toString, is("test")), matches("test"));
        assertThat(having(Object::toString, is("test")), mismatches("testing", "unnamed feature was \"testing\""));
        assertThat(having(Object::toString, is("test")), describesAs("unnamed feature is \"test\""));

        assertThat(having("toString", Object::toString, is("test")), matches("test"));
        assertThat(having("toString", Object::toString, is("test")), mismatches("testing", "toString was \"testing\""));
        assertThat(having("toString", Object::toString, is("test")), describesAs("toString is \"test\""));

    }
}