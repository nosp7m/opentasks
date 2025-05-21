/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.contentpal.references;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.TransactionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.assertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.deleteOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithExpectedCount.withoutExpectedCount;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.backReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * TODO: test actual back reference selection
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BackReferenceTest
{
    @Test
    public void testPutOperationBuilder()
    {
        assertThat(new BackReference<>(Uri.parse("content://authority/path/"), 123).putOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/"),
                        updateOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testDeleteOperationBuilder()
    {
        assertThat(new BackReference<>(Uri.parse("content://authority/path/"), 123).deleteOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/"),
                        deleteOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testAssertOperationBuilder()
    {
        assertThat(new BackReference<>(Uri.parse("content://authority/path/"), 123).assertOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/"),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testBuilderWithReferenceData()
    {
        Uri dummyUri = dummy(Uri.class);
        assertThat(new BackReference<>(Uri.parse("content://authority/path/"), 123).builderWithReferenceData(
                dummy(TransactionContext.class), ContentProviderOperation.newInsert(dummyUri), "column"),
                allOf(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testPredicate()
    {
        TransactionContext mockTc = failingMock(TransactionContext.class);

        assertThat(new BackReference<>(Uri.parse("content://authority/path/"), 123).predicate(mockTc, "testcol"),
                predicateWith(
                        selection(mockTc, "testcol = ?"),
                        argumentValues(mockTc, "-1"),
                        backReferences(mockTc, is(present(123)))
                ));
    }

}