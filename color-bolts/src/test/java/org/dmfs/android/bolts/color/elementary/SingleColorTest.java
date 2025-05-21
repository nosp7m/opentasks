/*
 * Copyright 2017 dmfs GmbH
 *
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

package org.dmfs.android.bolts.color.elementary;

import org.dmfs.android.bolts.color.Color;
import org.dmfs.jems.single.Single;
import org.junit.Test;

import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * Unit test for {@link SingleColor}
 *
 * @author Gabor Keszthelyi
 */
public final class SingleColorTest
{

    @Test
    public void test()
    {
        Single<Color> mockSingle = failingMock(Single.class);
        Color mockColor = failingMock(Color.class);
        doReturn(mockColor).when(mockSingle).value();
        doReturn(345).when(mockColor).argb();

        assertThat(new SingleColor(mockSingle).argb(), is(345));
    }
}