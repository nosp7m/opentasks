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
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithYieldAllowed.withYieldNotAllowed;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.absentBackReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RowUriReferenceTest
{
    @Test
    public void testPutOperationBuilder()
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).putOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/123"),
                        updateOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testDeleteOperationBuilder()
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).deleteOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/123"),
                        deleteOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testAssertOperationBuilder()
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).assertOperationBuilder(
                dummy(TransactionContext.class)),
                allOf(
                        targets("content://authority/path/123"),
                        assertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withoutValues()));
    }


    @Test
    public void testBuilderWithReferenceData()
    {
        Uri dummyUri = dummy(Uri.class);
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).builderWithReferenceData(
                dummy(TransactionContext.class), ContentProviderOperation.newInsert(dummyUri), "column"),
                allOf(
                        targets(sameInstance(dummyUri)),
                        insertOperation(),
                        withoutExpectedCount(),
                        withYieldNotAllowed(),
                        withValuesOnly(
                                containing("column", 123L))));
    }


    @Test
    public void testPredicate()
    {
        TransactionContext dummyTc = dummy(TransactionContext.class);

        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).predicate(dummyTc, "column"),
                predicateWith(
                        selection(dummyTc, "column = ?"),
                        argumentValues(dummyTc, "123"),
                        absentBackReferences(dummyTc, 1)
                ));
    }


    @Test
    public void testIsVirtual()
    {
        assertThat(new RowUriReference<>(Uri.parse("content://authority/path/123")).isVirtual(),
                is(false));
    }

}