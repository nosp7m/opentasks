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

package org.dmfs.android.contentpal.rowdata;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.rowdata.RowDataMatcher.builds;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CompositeTest
{
    @Test
    public void testEmpty()
    {
        assertThat(new Composite<Object>(new Optional[0]), builds(withoutValues()));
        assertThat(new Composite<Object>(new RowData[0]), builds(withoutValues()));
        assertThat(new Composite<>(new EmptyIterable<>()), builds(withoutValues()));

        assertThat(new Composite<>(Absent.absent()), builds(withoutValues()));
        assertThat(new Composite<>(Absent.absent(), Absent.absent()), builds(withoutValues()));
        assertThat(new Composite<>(Absent.absent(), Absent.absent(), Absent.absent()),
                builds(withoutValues()));
    }


    @Test
    public void testOptional()
    {
        assertThat(new Composite<>(
                        new Present<>(
                                new CharSequenceRowData<>("key", "value"))),
                builds(
                        withValuesOnly(
                                containing("key", "value"))));
        assertThat(new Composite<>(
                        new Present<>(
                                new CharSequenceRowData<>("key", "value")),
                        new Present<>(
                                new CharSequenceRowData<>("key2", "value2"))),
                builds(
                        withValuesOnly(
                                containing("key", "value"),
                                containing("key2", "value2"))));

        // add a few absent values
        assertThat(new Composite<>(
                        Absent.absent(),
                        new Present<>(
                                new CharSequenceRowData<>("key", "value")),
                        Absent.absent()
                ),
                builds(
                        withValuesOnly(
                                containing("key", "value"))));

        assertThat(new Composite<>(
                        Absent.absent(),
                        new Present<>(
                                new CharSequenceRowData<>("key", "value")),
                        Absent.absent(),
                        new Present<>(
                                new CharSequenceRowData<>("key2", "value2")),
                        Absent.absent()),
                builds(
                        withValuesOnly(
                                containing("key", "value"),
                                containing("key2", "value2"))));
    }


    @Test
    public void testRowData()
    {
        assertThat(new Composite<>(
                        new CharSequenceRowData<>("key", "value")),
                builds(
                        withValuesOnly(
                                containing("key", "value"))));
        assertThat(new Composite<>(
                        new CharSequenceRowData<>("key", "value"),
                        new CharSequenceRowData<>("key2", "value2")),
                builds(
                        withValuesOnly(
                                containing("key", "value"),
                                containing("key2", "value2"))));
    }


    @Test
    public void testIterable()
    {
        assertThat(new Composite<>(
                        new Seq<RowData<Object>>(
                                new CharSequenceRowData<>("key", "value"))),
                builds(
                        withValuesOnly(
                                containing("key", "value"))));
        assertThat(new Composite<>(
                        new Seq<RowData<Object>>(
                                new CharSequenceRowData<>("key", "value"),
                                new CharSequenceRowData<>("key2", "value2"))),
                builds(
                        withValuesOnly(
                                containing("key", "value"),
                                containing("key2", "value2"))));
    }

}