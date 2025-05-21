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

package org.dmfs.jems.generatable.elementary;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.generatable.Generatable;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.GeneratableMatcher.startsWith;
import static org.junit.Assert.assertThat;


/**
 * Test {@link Sequence}.
 *
 * @author Marten Gajda
 */
public final class SequenceTest
{
    @Test
    public void test()
    {
        assertThat(new Sequence<>(1, a -> a + 1), startsWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertThat(new Sequence<>(1, a -> a), startsWith(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
        assertThat(new Sequence<>("a", a -> a + "a"), startsWith("a", "aa", "aaa", "aaaa"));

        Generatable<StringBuilder> testee = new Sequence<StringBuilder>(StringBuilder::new, v -> v.append("a"));
        // the Generatable should always start with an empty StringBuilder and the sequence should always look the same
        assertThat(testee, startsWith(new Mapped<>(Matchers::hasToString, new Seq<>("", "a", "aa", "aaa", "aaaa", "aaaaa"))));
        assertThat(testee, startsWith(new Mapped<>(Matchers::hasToString, new Seq<>("", "a", "aa", "aaa", "aaaa", "aaaaa"))));
    }
}