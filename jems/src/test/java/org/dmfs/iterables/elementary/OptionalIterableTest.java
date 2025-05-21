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

package org.dmfs.iterables.elementary;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.optional.elementary.Present;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link OptionalIterable}.
 *
 * @author Marten Gajda
 */
public class OptionalIterableTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new OptionalIterable<>(absent()), emptyIterable());
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>())), Matchers.emptyIterable());
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>("1"))), iteratesTo("1"));
        assertThat(new OptionalIterable<>(new Present<>(new Seq<>("1", "2"))), iteratesTo("1", "2"));
    }
}