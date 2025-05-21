/*
 * Copyright 2019 dmfs GmbH
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
 * Test {@link IdIn}
 *
 * @author Marten Gajda
 */
public class IdInTest
{

    @Test
    public void testLong()
    {
        assertThat(new IdIn<>(123), predicateWith(
                selection("_id = ?"),
                argumentValues("123"),
                absentBackReferences(1)
        ));
    }


    @Test
    public void testLongVarArg()
    {
        assertThat(new IdIn<>(1L, 2L, 3L), predicateWith(
                selection("_id in ( ?, ?, ? ) "),
                argumentValues("1", "2", "3"),
                absentBackReferences(3)
        ));
    }


    @Test
    public void testEmptyLongVarArg()
    {
        assertThat(new IdIn<>(new Long[0]), predicateWith(
                selection("_id in (  ) "),
                emptyArguments()
        ));
    }


    @Test
    public void testString()
    {
        assertThat(new IdIn<>("123"), predicateWith(
                selection("_id = ?"),
                argumentValues("123"),
                absentBackReferences(1)
        ));
    }


    @Test
    public void testStringVarArg()
    {
        assertThat(new IdIn<>("1", "2", "3"), predicateWith(
                selection("_id in ( ?, ?, ? ) "),
                argumentValues("1", "2", "3"),
                absentBackReferences(3)
        ));
    }


    @Test
    public void testEmptyStringVarArg()
    {
        assertThat(new IdIn<>(new String[0]), predicateWith(
                selection("_id in (  ) "),
                emptyArguments()
        ));
    }


    @Test
    public void testLongIterable()
    {
        assertThat(new IdIn<>(new Seq<>(1L, 2L, 3L)), predicateWith(
                selection("_id in ( ?, ?, ? ) "),
                argumentValues("1", "2", "3"),
                absentBackReferences(3)
        ));
    }


    @Test
    public void testEmptyLongIterable()
    {
        assertThat(new IdIn<>(EmptyIterable.instance()), predicateWith(
                selection("_id in (  ) "),
                emptyArguments()
        ));
    }

}