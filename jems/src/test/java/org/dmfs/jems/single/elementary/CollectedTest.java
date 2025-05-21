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

package org.dmfs.jems.single.elementary;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import java.util.ArrayList;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.SingleMatcher.hasValue;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Collected}.
 *
 * @author Marten Gajda
 */
public class CollectedTest
{
    @Test
    public void test()
    {
        assertThat(
                new Collected<>(ArrayList::new, EmptyIterable.<String>instance()),
                hasValue(emptyIterable()));

        assertThat(
                new Collected<>(ArrayList::new, new Seq<>("a")),
                hasValue(iteratesTo("a")));

        assertThat(
                new Collected<>(ArrayList::new, new Seq<>("a", "b")),
                hasValue(iteratesTo("a", "b")));

        assertThat(
                new Collected<>(ArrayList::new, new Seq<>("a", "b", "c")),
                hasValue(iteratesTo("a", "b", "c")));

        assertThat(
                new Collected<>(ArrayList::new, new Seq<>("a", "a", "a")),
                hasValue(iteratesTo("a", "a", "a")));
    }
}