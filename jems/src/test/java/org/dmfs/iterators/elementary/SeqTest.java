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

package org.dmfs.iterators.elementary;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.emptyIterator;
import static org.dmfs.jems.hamcrest.matchers.iterator.IteratorMatcher.iteratorOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Seq}.
 *
 * @author Marten Gajda
 */
public class SeqTest
{

    @Test
    public void test()
    {
        assertThat(Seq::new, is(emptyIterator()));
        assertThat(() -> new Seq<>("1"), is(iteratorOf("1")));
        assertThat(() -> new Seq<>("a", "1"), is(iteratorOf("a", "1")));
        assertThat(() -> new Seq<>("a", "1", null, "3"), is(iteratorOf("a", "1", null, "3")));
    }
}