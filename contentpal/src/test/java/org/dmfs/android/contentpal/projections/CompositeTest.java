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

package org.dmfs.android.contentpal.projections;

import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.testing.table.Contract;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projects;
import static org.dmfs.android.contentpal.testing.projection.ProjectionMatcher.projectsEmpty;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class CompositeTest
{
    @Test
    public void testEmpty()
    {
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<>())), projectsEmpty());
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<>(), new EmptyProjection())), projectsEmpty());
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new EmptyProjection<>(), new EmptyProjection(), new EmptyProjection())),
                projectsEmpty());
    }


    @Test
    public void testNonEmpty()
    {
        // test vararg ctor
        assertThat(new Composite<>(new MultiProjection<Contract>("abc")), projects("abc"));
        assertThat(new Composite<>(new MultiProjection<Contract>("abc", "xyz", "qrs")), projects("abc", "xyz", "qrs"));
        assertThat(new Composite<>(new MultiProjection<Contract>("abc"), new MultiProjection<Contract>("xyz")), projects("abc", "xyz"));
        assertThat(new Composite<>(new MultiProjection<Contract>("abc", "xyz", "qrs")), projects("abc", "xyz", "qrs"));
        assertThat(new Composite<>(new MultiProjection<Contract>("abc", "xyz", "qrs"), new MultiProjection<Contract>("123", "456", "789")),
                projects("abc", "xyz", "qrs", "123", "456", "789"));

        // test iterable ctor
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<>("abc"))), projects("abc"));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<>("abc", "xyz", "qrs"))), projects("abc", "xyz", "qrs"));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<>("abc"), new MultiProjection<>("xyz"))),
                projects("abc", "xyz"));
        assertThat(new Composite<>(new Seq<Projection<Contract>>(new MultiProjection<>("abc", "xyz", "qrs"))), projects("abc", "xyz", "qrs"));
        assertThat(new Composite<>(
                        new Seq<Projection<Contract>>(new MultiProjection<>("abc", "xyz", "qrs"), new MultiProjection<>("123", "456", "789"))),
                projects("abc", "xyz", "qrs", "123", "456", "789"));
    }


    @Test
    public void testDuplicateColumns()
    {
        assertThat(new Composite<>(
                        new Seq<Projection<Contract>>(
                                new MultiProjection<>("abc", "xyz", "qrs", "123", "456", "789"),
                                new MultiProjection<>("abc", "xyz", "qrs", "123", "456", "789"))),
                projects("abc", "xyz", "qrs", "123", "456", "789", "abc", "xyz", "qrs", "123", "456", "789"));
    }

}