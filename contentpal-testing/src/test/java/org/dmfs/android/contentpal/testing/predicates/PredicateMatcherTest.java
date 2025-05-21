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

package org.dmfs.android.contentpal.testing.predicates;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.backReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.emptyArguments;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Unit test for {@link PredicateMatcher}.
 *
 * @author Gabor Keszthelyi
 */
public final class PredicateMatcherTest
{

    @Test
    public void testSelection()
    {
        // Without TC matching
        assertTrue(selection("a").matches(
                when(mock(Predicate.class).selection(any(TransactionContext.class))).thenReturn("a").getMock()));
        // Without TC not matching
        assertFalse(selection("b").matches(
                when(mock(Predicate.class).selection(any(TransactionContext.class))).thenReturn("a").getMock()));

        // With TC matching
        TransactionContext dummyTc = dummy(TransactionContext.class);
        assertTrue(selection(dummyTc, "a").matches(
                when(mock(Predicate.class).selection(dummyTc)).thenReturn("a").getMock()));
        // With different TC
        TransactionContext dummyTc2 = dummy(TransactionContext.class);
        assertFalse(selection(dummyTc2, "a").matches(
                when(mock(Predicate.class).selection(dummyTc)).thenReturn("a").getMock()));
    }


    @Test
    public void testArgumentValue()
    {
        // Single matching
        Predicate.Argument argumentA = when(mock(Predicate.Argument.class).value()).thenReturn("a").getMock();
        assertTrue(argumentValues("a").matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(argumentA)).getMock()));
        // Single non-matching
        assertFalse(argumentValues("b").matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(argumentA)).getMock()));

        // Multiple
        Predicate.Argument aB = when(mock(Predicate.Argument.class).value()).thenReturn("b").getMock();
        Predicate.Argument aC = when(mock(Predicate.Argument.class).value()).thenReturn("c").getMock();
        Predicate.Argument aD = when(mock(Predicate.Argument.class).value()).thenReturn("d").getMock();
        assertTrue(argumentValues("b", "c", "d").matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(aB, aC, aD)).getMock()));

        // Single with TC
        TransactionContext dummyTc = dummy(TransactionContext.class);
        Predicate.Argument argumentE = when(mock(Predicate.Argument.class).value()).thenReturn("e").getMock();
        assertTrue(argumentValues(dummyTc, "e").matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(new Seq<>(argumentE)).getMock()));
    }


    @Test
    public void testBackReferences()
    {
        // Single matching
        Predicate.Argument argumentA = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        assertTrue(backReferences(is(present(1))).matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(argumentA)).getMock()));
        // Single non-matching
        assertFalse(backReferences(is(present(2))).matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(argumentA)).getMock()));

        // Multiple
        Predicate.Argument aB = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        Predicate.Argument aC = when(mock(Predicate.Argument.class).backReference()).thenReturn(absent()).getMock();
        Predicate.Argument aD = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(3)).getMock();
        assertTrue(backReferences(is(present(1)), is(AbsentMatcher.absent()), is(present(3))).matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(aB, aC, aD)).getMock()));

        // Single with TC
        TransactionContext dummyTc = dummy(TransactionContext.class);
        Predicate.Argument argumentE = when(mock(Predicate.Argument.class).backReference()).thenReturn(new Present<>(1)).getMock();
        assertTrue(backReferences(dummyTc, is(present(1))).matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(new Seq<>(argumentE)).getMock()));
    }


    @Test
    public void testEmptyArguments()
    {
        assertTrue(emptyArguments().matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(EmptyIterable.instance()).getMock()));

        assertFalse(emptyArguments().matches(
                when(mock(Predicate.class).arguments(any(TransactionContext.class))).thenReturn(new Seq<>(dummy(Predicate.Argument.class)))
                        .getMock()));

        TransactionContext dummyTc = dummy(TransactionContext.class);
        assertTrue(emptyArguments(dummyTc).matches(
                when(mock(Predicate.class).arguments(dummyTc)).thenReturn(EmptyIterable.instance()).getMock()));

    }


    @Test
    public void testCombined()
    {
        Predicate<Object> predicateMock = failingMock(Predicate.class);
        Predicate.Argument argumentMock = failingMock(Predicate.Argument.class);

        doReturn("sel").when(predicateMock).selection(any(TransactionContext.class));
        doReturn("arg").when(argumentMock).value();
        doReturn(new Seq<>(argumentMock)).when(predicateMock).arguments(any(TransactionContext.class));

        assertTrue(predicateWith(selection("sel"), argumentValues("arg")).matches(predicateMock));
    }

}