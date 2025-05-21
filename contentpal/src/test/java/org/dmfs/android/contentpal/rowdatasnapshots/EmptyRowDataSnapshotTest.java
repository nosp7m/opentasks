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

package org.dmfs.android.contentpal.rowdatasnapshots;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class EmptyRowDataSnapshotTest
{
    @Test
    public void testCharData()
    {
        assertThat(new EmptyRowDataSnapshot<>().data("key", x -> x), is(absent()));
    }


    @Test
    public void testByteData()
    {
        assertThat(new EmptyRowDataSnapshot<>().byteData("key"), is(absent()));
    }


    @Test
    public void testIterator()
    {
        assertThat(new EmptyRowDataSnapshot<>(), emptyIterable());
    }

}