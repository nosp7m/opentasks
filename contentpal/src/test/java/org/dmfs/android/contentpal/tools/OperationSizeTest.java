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

package org.dmfs.android.contentpal.tools;

import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.operations.BulkUpdate;
import org.dmfs.android.contentpal.rowdata.CharSequenceRowData;
import org.dmfs.android.contentpal.rowdata.Composite;
import org.dmfs.android.contentpal.tables.BaseTable;
import org.dmfs.android.contentpal.transactions.contexts.EmptyTransactionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class OperationSizeTest
{
    @Test
    public void testConsitency()
    {
        // test that all Number methods return the same value
        int expected = new OperationSize(
                new BulkUpdate<>(
                        new BaseTable<>(Uri.parse("content://authority")),
                        new CharSequenceRowData<>("key", "012345678901234567890123456789"))
                        .contentOperationBuilder(new EmptyTransactionContext())
                        .build())
                .intValue();
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.parse("content://authority")),
                                new CharSequenceRowData<>("key", "012345678901234567890123456789"))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .floatValue(),
                is((float) expected));
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.parse("content://authority")),
                                new CharSequenceRowData<>("key", "012345678901234567890123456789"))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .longValue(),
                is((long) expected));
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.parse("content://authority")),
                                new CharSequenceRowData<>("key", "012345678901234567890123456789"))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .doubleValue(),
                is((double) expected));
    }


    /**
     * This test ensures a "plausible" size of an operation.
     * <p>
     * Unfortunately, there is no reliable way of calculating the exact size of data sent when executing a {@link ContentProviderOperation}. The exact value
     * depends on implementation details which may vary on different platforms or platform versions.
     * <p>
     * Even under the assumption that the data is ultimately parcelled when doing the actual IPC, there are still unknown variables, like the exact number and
     * size of fields being parcelled and the actual parcel format.
     * <p>
     * To account for these unknown parameters we test if the size is within certain bounds which are expected to grow in a way that's somehow proportional to
     * the data we put into the operation. So under the assumption there is minimum size for an operation which only updates an empty string, we expect that the
     * size grows when we add more fields.
     * <p>
     * This test is by far not perfect any may have to be adjusted when exectued on new platforms.
     */
    @Test
    public void testPlausibility()
    {
        // minimal operation:
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.EMPTY), new CharSequenceRowData<>("", ""))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .intValue(),
                is(both(greaterThan(70)).and(lessThan(120))));

        //  minimal operation + 40 characters of data
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.EMPTY), new CharSequenceRowData<>("a1234567890123456789", "01234567890123456789"))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .intValue(),
                is(both(greaterThan(70 + 40)).and(lessThan(120 + 40 + 8))));

        //  minimal operation + 80 characters of data
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.EMPTY),
                                new Composite<>(
                                        new CharSequenceRowData<>("a1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("b1234567890123456789", "01234567890123456789")))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .intValue(),
                is(both(greaterThan(70 + 80)).and(lessThan(120 + 80 + 16))));
        //  minimal operation + 160 characters of data
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.EMPTY),
                                new Composite<>(
                                        new CharSequenceRowData<>("a1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("b1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("c1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("d1234567890123456789", "01234567890123456789")))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .intValue(),
                is(both(greaterThan(70 + 160)).and(lessThan(120 + 160 + 32))));
        //  minimal operation + 320 characters of data
        assertThat(
                new OperationSize(
                        new BulkUpdate<>(
                                new BaseTable<>(Uri.EMPTY),
                                new Composite<>(
                                        new CharSequenceRowData<>("a1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("b1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("c1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("d1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("e1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("f1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("g1234567890123456789", "01234567890123456789"),
                                        new CharSequenceRowData<>("h1234567890123456789", "01234567890123456789")))
                                .contentOperationBuilder(new EmptyTransactionContext())
                                .build())
                        .intValue(),
                is(both(greaterThan(70 + 320)).and(lessThan(120 + 320 + 64))));
    }

}