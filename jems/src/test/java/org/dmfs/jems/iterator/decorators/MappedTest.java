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

package org.dmfs.jems.iterator.decorators;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.jems.iterator.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Unit test for {@link Mapped}.
 *
 * @author Marten Gajda
 */
public final class MappedTest
{
    @Test
    public void test()
    {
        assertThat(() -> new Mapped<>(Integer::parseInt, new EmptyIterator<String>()), is(emptyIterator()));
        assertThat(() -> new Mapped<>(Integer::parseInt, new Seq<>("9")), is(iteratorOf(9)));
        assertThat(() -> new Mapped<>(Integer::parseInt, new Seq<>("1", "2", "3", "4", "3", "2", "9")), is(iteratorOf(1, 2, 3, 4, 3, 2, 9)));
    }
}