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
import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
public class JoinedProjectionTest
{

    @Test
    public void test()
    {
        String[] dummyArray = new String[0];
        Iterator<String> dummyIterator = failingMock(Iterator.class);
        Projection<Contract> mockProjection = failingMock(Projection.class);
        doReturn(dummyArray).when(mockProjection).toArray();

        assertThat(new Joined<>(mockProjection).toArray(), sameInstance(dummyArray));
    }
}