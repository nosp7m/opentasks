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

package org.dmfs.jems.optional.adapters;

import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.AbsentMatcher.isAbsent;
import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Test for {@link Collapsed}.
 *
 * @author Gabor Keszthelyi
 */
public final class CollapsedTest
{

    @Test
    public void test()
    {
        assertThat(new Collapsed<>(new Absent<>()), isAbsent());
        assertThat(new Collapsed<>(new Present<>(new Absent<>())), isAbsent());
        assertThat(new Collapsed<>(new Present<>(new Present<>("a"))), isPresent("a"));
    }

}