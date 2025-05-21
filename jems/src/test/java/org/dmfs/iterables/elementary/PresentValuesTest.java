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
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link PresentValues}.
 *
 * @author Gabor Keszthelyi
 */
public class PresentValuesTest
{

    @Test
    public void test()
    {
        assertThat(
                new PresentValues<>(
                        new Seq<>(
                                new Present<>("1"),
                                absent(),
                                absent(),
                                new Present<>("2"),
                                new Present<>("3"),
                                absent())),
                iteratesTo("1", "2", "3"));
    }


    @Test
    public void testCtors()
    {
        assertThat(new PresentValues<>(), emptyIterable());
        assertThat(new PresentValues<>(absent()), emptyIterable());
        assertThat(new PresentValues<>(new Present<>("test")), iteratesTo("test"));
        assertThat(new PresentValues<>(new Present<>("1"), new Present<>("2")), iteratesTo("1", "2"));
        assertThat(new PresentValues<>(absent(), absent(), absent(), new Present<>("1"), new Present<>("2")), iteratesTo("1", "2"));
    }
}