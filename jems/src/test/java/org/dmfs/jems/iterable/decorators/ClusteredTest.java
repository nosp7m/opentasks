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
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Clustered}.
 *
 * @author Marten Gajda
 */
public class ClusteredTest
{
    @Test
    public void test()
    {
        assertThat(new Clustered<String>(Comparator.naturalOrder(), EmptyIterable.instance()), emptyIterable());
        assertThat(new Clustered<>(Comparator.naturalOrder(), new Seq<>(1)), iteratesTo(iteratesTo(1)));
        assertThat(new Clustered<>(Comparator.naturalOrder(), new Seq<>(1, 1)), iteratesTo(iteratesTo(1, 1)));
        assertThat(new Clustered<>(Comparator.naturalOrder(), new Seq<>(1, 2, 3)), iteratesTo(iteratesTo(1), iteratesTo(2), iteratesTo(3)));
        assertThat(new Clustered<>(Comparator.naturalOrder(), new Seq<>(1, 1, 2, 2, 3, 3)), iteratesTo(iteratesTo(1, 1), iteratesTo(2, 2), iteratesTo(3, 3)));
        assertThat(new Clustered<>(Comparator.naturalOrder(), new Seq<>(1, 1, 2, 2, 1, 1)), iteratesTo(iteratesTo(1, 1), iteratesTo(2, 2), iteratesTo(1, 1)));
    }
}