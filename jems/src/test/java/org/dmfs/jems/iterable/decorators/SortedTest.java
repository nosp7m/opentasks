/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.iterable.decorators;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import java.util.Comparator;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Sorted}.
 *
 * @author Marten Gajda
 */
public class SortedTest
{
    @Test
    public void test()
    {
        assertThat(new Sorted<>(Comparator.naturalOrder(), new EmptyIterable<String>()), is(emptyIterable()));
        assertThat(new Sorted<>(Comparator.naturalOrder(), new Seq<>(7, 4, 1, 3, 6, 9)), iteratesTo(1, 3, 4, 6, 7, 9));
        assertThat(new Sorted<>(Comparator.reverseOrder(), new Seq<>(7, 4, 1, 3, 6, 9)), iteratesTo(9, 7, 6, 4, 3, 1));
    }
}