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

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.predicate.Predicate;
import org.dmfs.jems.predicate.elementary.Equals;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.predicate.PredicateMatcher.satisfiedBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


/**
 * Test {@link AllOf}.
 *
 * @author Marten Gajda
 */
public class AllOfTest
{
    @Test
    public void testSatisfiedBy()
    {
        // trivial predicate
        assertThat(new AllOf<>(), is(satisfiedBy(new Object())));
        assertThat(new AllOf<>(EmptyIterable.<Predicate<Object>>instance()), is(satisfiedBy(new Object())));

        // test matching predicates
        assertThat(new AllOf<>(new Equals<>("test"), new Equals<>("test"), new Equals<>("test")), is(satisfiedBy("test")));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"))), is(satisfiedBy("test")));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("test"))), is(satisfiedBy("test")));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("test"), new Equals<>("test"))), is(satisfiedBy("test")));

        // mismatched predicates

        // one delegate
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"))), is(not(satisfiedBy("test"))));

        // two delegates
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("test"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));

        // three delegates
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("test"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("fail"), new Equals<>("test"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("test"), new Equals<>("test"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("test"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("fail"), new Equals<>("test"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("test"), new Equals<>("fail"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));
        assertThat(new AllOf<>(new Seq<>(new Equals<>("fail"), new Equals<>("fail"), new Equals<>("fail"))), is(not(satisfiedBy("test"))));
    }

}