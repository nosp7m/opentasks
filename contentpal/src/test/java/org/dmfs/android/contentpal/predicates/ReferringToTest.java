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

import android.net.Uri;

import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.references.BackReference;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.backReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class ReferringToTest
{
    @Test
    public void testSelection()
    {
        RowSnapshot<Object> mockRowSnapshot = failingMock(RowSnapshot.class);
        SoftRowReference<Object> dummyRowReference = dummy(SoftRowReference.class);
        TransactionContext mockTc = failingMock(TransactionContext.class);

        // let the RowSnapshot return the mock RowReference
        doReturn(dummyRowReference).when(mockRowSnapshot).reference();

        // let the TransactionContext resolve the RowReference to a BackReference with index 10
        doReturn(new BackReference<>(dummy(Uri.class), 10)).when(mockTc).resolved(dummyRowReference);

        assertThat(new ReferringTo<>("x", mockRowSnapshot),
                predicateWith(
                        selection(mockTc, "x = ?"),
                        argumentValues(mockTc, "-1"),
                        backReferences(mockTc, is(present(10)))
                ));
    }

}