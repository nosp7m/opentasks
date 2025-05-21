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

package org.dmfs.optional.decorators;

import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.dmfs.jems.predicate.elementary.Anything;
import org.dmfs.jems.predicate.elementary.Nothing;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class SievedTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new Sieved<>(new Anything<String>(), new Present<>("test")), isPresent("test"));
        assertThat(new Sieved<>(new Nothing<String>(), new Present<>("test")), AbsentMatcher.<String>isAbsent());
        assertThat(new Sieved<>(new Anything<String>(), new Absent<String>()), AbsentMatcher.<String>isAbsent());
        assertThat(new Sieved<>(new Nothing<String>(), new Absent<String>()), AbsentMatcher.<String>isAbsent());
    }
}