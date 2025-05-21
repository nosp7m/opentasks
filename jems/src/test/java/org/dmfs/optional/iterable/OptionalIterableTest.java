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

package org.dmfs.optional.iterable;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Present;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * Test {@link OptionalIterable}.
 *
 * @author Marten Gajda
 */
public class OptionalIterableTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new OptionalIterable<>(Absent.<List<String>>absent()), emptyIterable());
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>())), Matchers.emptyIterable());
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>("1"))), contains("1"));
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>("1", "2"))), contains("1", "2"));
    }

}