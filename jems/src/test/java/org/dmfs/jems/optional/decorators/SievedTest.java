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
import org.dmfs.jems.predicate.elementary.Anything;
import org.dmfs.jems.predicate.elementary.Nothing;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher.absent;
import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Sieved}.
 *
 * @author Marten Gajda
 */
public class SievedTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new Sieved<>(new Anything<>(), new Present<>("test")), is(present("test")));
        assertThat(new Sieved<>(new Nothing<>(), new Present<>("test")), is(absent()));
        assertThat(new Sieved<>(new Anything<>(), new Absent<String>()), is(absent()));
        assertThat(new Sieved<>(new Nothing<>(), new Absent<String>()), is(absent()));
    }
}