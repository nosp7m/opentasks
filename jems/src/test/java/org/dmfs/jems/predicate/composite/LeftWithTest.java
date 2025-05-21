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

package org.dmfs.jems.predicate.composite;

import org.dmfs.jems.pair.elementary.ValuePair;
import org.dmfs.jems.predicate.elementary.SameAs;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.predicate.PredicateMatcher.satisfiedBy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


/**
 * Test {@link LeftWith}.
 *
 * @author Marten Gajda
 */
public class LeftWithTest
{
    @Test
    public void testSatisfiedBy()
    {
        Object leftDummy = dummy(Object.class);
        assertThat(new LeftWith<>(new SameAs<>(leftDummy)), is(satisfiedBy(new ValuePair<>(leftDummy, dummy(Object.class)))));
        assertThat(new LeftWith<>(new SameAs<>(leftDummy)), is(not(satisfiedBy(new ValuePair<>(dummy(Object.class), dummy(Object.class))))));
    }

}