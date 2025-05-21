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

package org.dmfs.jems.optional.decorators;

import org.dmfs.jems.optional.elementary.Absent;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Restrained}.
 *
 * @author Marten Gajda
 */
public final class RestrainedTest
{
    @Test
    public void test()
    {
        assertThat(new Restrained<>(() -> true, new Absent<>()), is(absent()));
        assertThat(new Restrained<>(() -> false, new Absent<>()), is(absent()));
        assertThat(new Restrained<>(() -> true, new Present<>("abc")), is(present("abc")));
        assertThat(new Restrained<>(() -> false, new Present<>("abc")), is(absent()));
    }
}