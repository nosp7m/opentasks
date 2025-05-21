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
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link PresentValues}.
 *
 * @author Gabor Keszthelyi
 */
public final class PresentValuesTest
{

    private static final Absent<String> ABSENT = Absent.absent();


    @Test
    public void test()
    {
        Iterable<Optional<String>> optionals = new Seq<>(
                new Present<>("1"),
                ABSENT,
                ABSENT,
                new Present<>("2"),
                new Present<>("3"),
                ABSENT
        );

        PresentValues<String> result = new PresentValues<>(optionals);

        assertThat(result, contains("1", "2", "3"));
    }


    @Test
    public void testCtors()
    {
        assertThat(new PresentValues<>(), emptyIterable());
        assertThat(new PresentValues<>(Absent.absent()), emptyIterable());
        assertThat(new PresentValues<>(new Present<>("test")), contains("test"));
        assertThat(new PresentValues<>(new Present<>("1"), new Present<>("2")), contains("1", "2"));
        assertThat(new PresentValues<>(Absent.<String>absent(), Absent.<String>absent(), Absent.<String>absent(), new Present<>("1"), new Present<>("2")),
                contains("1", "2"));
    }
}