/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.predicates;

import org.dmfs.android.contentpal.testing.predicates.Mocked;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.absentBackReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.emptyArguments;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class AllOfTest
{

    @Test
    public void test()
    {
        assertThat(new AllOf<>(), predicateWith(
                selection("1"),
                emptyArguments()));

        assertThat(new AllOf<>(new Mocked<>("x", "a")), predicateWith(
                selection("( x )"),
                argumentValues("a"),
                absentBackReferences(1)));

        assertThat(new AllOf<>(new Mocked<>("x", "a"), new Mocked<>("y", "1")), predicateWith(
                selection("( x ) and ( y )"),
                argumentValues("a", "1"),
                absentBackReferences(2)));

        assertThat(new AllOf<>(new Mocked<>("x", "a"), new Mocked<>("z", "w", "z"), new Mocked<>("y", "1")), predicateWith(
                selection("( x ) and ( z ) and ( y )"),
                argumentValues("a", "w", "z", "1"),
                absentBackReferences(4)));
    }


    @Test
    public void testIterable()
    {
        assertThat(new AllOf<>(EmptyIterable.instance()), predicateWith(
                selection("1"),
                emptyArguments()));

        assertThat(new AllOf<>(new Seq<>(new Mocked<>("x", "a"))), predicateWith(
                selection("( x )"),
                argumentValues("a"),
                absentBackReferences(1)));

        assertThat(new AllOf<>(new Seq<>(new Mocked<>("x", "a"), new Mocked<>("y", "1"))), predicateWith(
                selection("( x ) and ( y )"),
                argumentValues("a", "1"),
                absentBackReferences(2)));

        assertThat(new AllOf<>(new Seq<>(new Mocked<>("x", "a"), new Mocked<>("z", "w", "z"), new Mocked<>("y", "1"))), predicateWith(
                selection("( x ) and ( z ) and ( y )"),
                argumentValues("a", "w", "z", "1"),
                absentBackReferences(4)));
    }
}