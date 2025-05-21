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

package org.dmfs.android.contentpal.rowdata;

import org.dmfs.android.contentpal.RowDataSnapshot;
import org.dmfs.android.contentpal.projections.EmptyProjection;
import org.dmfs.android.contentpal.projections.MultiProjection;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withValuesOnly;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.WithValues.withoutValues;
import static org.dmfs.android.contentpal.testing.contentvalues.Containing.containing;
import static org.dmfs.android.contentpal.testing.contentvalues.NullValue.withNullValue;
import static org.dmfs.android.contentpal.testing.rowdata.RowDataMatcher.builds;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;


/**
 * Test {@link SnapshotRowData}.
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SnapshotRowDataTest
{
    @Test
    public void testPartial()
    {
        RowDataSnapshot<Object> snapshot = failingMock(RowDataSnapshot.class);

        doReturn(new Present<>("1")).when(snapshot).data(eq("x"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("x");

        doReturn(new Absent<>()).when(snapshot).data(eq("y"), any(Function.class));
        doReturn(new Present<>(new byte[123])).when(snapshot).byteData("y");

        doReturn(new Absent<>()).when(snapshot).data(eq("z"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("z");

        assertThat(new SnapshotRowData<>(new MultiProjection<>("y"), snapshot),
                builds(
                        withValuesOnly(
                                containing("y", new byte[123]))));
    }


    @Test
    public void testRegular()
    {
        RowDataSnapshot<Object> snapshot = failingMock(RowDataSnapshot.class);

        doReturn(new Present<>("1")).when(snapshot).data(eq("x"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("x");

        doReturn(new Absent<>()).when(snapshot).data(eq("y"), any(Function.class));
        doReturn(new Present<>(new byte[123])).when(snapshot).byteData("y");

        doReturn(new Absent<>()).when(snapshot).data(eq("z"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("z");

        assertThat(new SnapshotRowData<>(new MultiProjection<>("x", "y", "z"), snapshot),
                builds(
                        withValuesOnly(
                                containing("x", "1"),
                                containing("y", new byte[123]),
                                withNullValue("z"))));
    }


    @Test
    public void testEmptyProjection()
    {
        RowDataSnapshot<Object> snapshot = failingMock(RowDataSnapshot.class);

        doReturn(new Present<>("1")).when(snapshot).data(eq("x"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("x");

        doReturn(new Absent<>()).when(snapshot).data(eq("y"), any(Function.class));
        doReturn(new Present<>(new byte[123])).when(snapshot).byteData("y");

        doReturn(new Absent<>()).when(snapshot).data(eq("z"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("z");

        assertThat(new SnapshotRowData<>(new EmptyProjection<>(), snapshot),
                builds(
                        withoutValues()));
    }


    @Test
    public void testAbsentValues()
    {
        RowDataSnapshot<Object> snapshot = failingMock(RowDataSnapshot.class);

        doReturn(new Absent<>()).when(snapshot).data(eq("x"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("x");

        doReturn(new Absent<>()).when(snapshot).data(eq("y"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("y");

        doReturn(new Absent<>()).when(snapshot).data(eq("z"), any(Function.class));
        doReturn(new Absent<>()).when(snapshot).byteData("z");

        assertThat(new SnapshotRowData<>(new MultiProjection<>("x", "y", "z"), snapshot),
                builds(
                        withValuesOnly(
                                withNullValue("x"),
                                withNullValue("y"),
                                withNullValue("z"))));
    }
}