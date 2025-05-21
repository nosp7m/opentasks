/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.optional.elementary;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Present}.
 *
 * @author Marten Gajda
 */
public class PresentTest
{

    @Test(expected = IllegalArgumentException.class)
    public void testNull()
    {
        new Present<>(null);
    }


    @Test
    public void test()
    {
        assertThat(new Present<>("123"), is(present("123")));
    }
}