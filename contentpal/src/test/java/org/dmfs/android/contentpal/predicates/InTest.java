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

import android.provider.BaseColumns;
import android.provider.CalendarContract;

import org.dmfs.android.contentpal.RowSet;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.rowdatasnapshots.MapRowDataSnapshot;
import org.dmfs.android.contentpal.tools.FakeClosable;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyIterator;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.absentBackReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.emptyArguments;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class InTest
{
    @Test
    public void test()
    {
        assertThat(new In<>("x"), predicateWith(
                selection("x in (  ) "),
                emptyArguments()));

        assertThat(new In<>("x", "a"), predicateWith(
                selection("x in ( ? ) "),
                argumentValues("a"),
                absentBackReferences(1)));

        assertThat(new In<>("x", "a", 1), predicateWith(
                selection("x in ( ?, ? ) "),
                argumentValues("a", "1"),
                absentBackReferences(2)));

        assertThat(new In<>("x", "a", 1, 1.2), predicateWith(
                selection("x in ( ?, ?, ? ) "),
                argumentValues("a", "1", "1.2"),
                absentBackReferences(3)));
    }


    @Test
    public void testIterable()
    {
        assertThat(new In<>("x", EmptyIterable.instance()), predicateWith(
                selection("x in (  ) "),
                emptyArguments()));

        assertThat(new In<>("x", new Seq<>("a")), predicateWith(
                selection("x in ( ? ) "),
                argumentValues("a"),
                absentBackReferences(1)));

        assertThat(new In<>("x", new Seq<>("a", 1)), predicateWith(
                selection("x in ( ?, ? ) "),
                argumentValues("a", "1"),
                absentBackReferences(2)));

        assertThat(new In<>("x", new Seq<>("a", 1, 1.2)), predicateWith(
                selection("x in ( ?, ?, ? ) "),
                argumentValues("a", "1", "1.2"),
                absentBackReferences(3)));
    }


    @Test
    public void testRowSet()
    {
        RowSet<CalendarContract.Calendars> mockRowSet = failingMock(RowSet.class);

        doReturn(new FakeClosable<>(emptyIterator())).when(mockRowSet).iterator();

        assertThat(new In<>("x", mockRowSet), predicateWith(
                selection("x in (  ) "),
                emptyArguments()));

        RowSnapshot<CalendarContract.Calendars> rowSnapshot1 = failingMock(RowSnapshot.class);
        Map<String, String> valueMap1 = new HashMap<>();
        valueMap1.put(BaseColumns._ID, "1");
        doReturn(new MapRowDataSnapshot<>(valueMap1, new HashMap<>())).when(rowSnapshot1).values();
        doAnswer(invocation -> new FakeClosable<>(new org.dmfs.jems.iterator.elementary.Seq<>(rowSnapshot1))).when(mockRowSet).iterator();

        assertThat(new In<>("x", mockRowSet), predicateWith(
                selection("x in ( ? ) "),
                argumentValues("1"),
                absentBackReferences(1)));

        RowSnapshot<CalendarContract.Calendars> rowSnapshot2 = failingMock(RowSnapshot.class);
        Map<String, String> valueMap2 = new HashMap<>();
        valueMap2.put(BaseColumns._ID, "2");
        doReturn(new MapRowDataSnapshot<>(valueMap2, new HashMap<>())).when(rowSnapshot2).values();
        doAnswer(invocation -> new FakeClosable<>(new org.dmfs.jems.iterator.elementary.Seq<>(rowSnapshot1, rowSnapshot2))).when(mockRowSet).iterator();

        assertThat(new In<>("x", mockRowSet), predicateWith(
                selection("x in ( ?, ? ) "),
                argumentValues("1", "2"),
                absentBackReferences(2)));

        RowSnapshot<CalendarContract.Calendars> rowSnapshot3 = failingMock(RowSnapshot.class);
        Map<String, String> valueMap3 = new HashMap<>();
        valueMap3.put(BaseColumns._ID, "3");
        doReturn(new MapRowDataSnapshot<>(valueMap3, new HashMap<>())).when(rowSnapshot3).values();
        doAnswer(invocation -> new FakeClosable<>(new org.dmfs.jems.iterator.elementary.Seq<>(rowSnapshot1, rowSnapshot2, rowSnapshot3))).when(mockRowSet)
                .iterator();

        assertThat(new In<>("x", mockRowSet), predicateWith(
                selection("x in ( ?, ?, ? ) "),
                argumentValues("1", "2", "3"),
                absentBackReferences(3)));
    }
}