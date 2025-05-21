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

package org.dmfs.optional.adapters;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.AbsentMatcher.isAbsent;
import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link FirstPresent}.
 *
 * @author Gabor Keszthelyi
 */
public final class FirstPresentTest
{

    private static final Absent<String> ABSENT = Absent.absent();


    @Test
    public void testVariousCases()
    {
        assertThat(new FirstPresent<>(new Seq<Optional<String>>()), AbsentMatcher.<String>isAbsent());

        assertThat(new FirstPresent<>(new Seq<Optional<String>>(ABSENT)), AbsentMatcher.<String>isAbsent());
        assertThat(new FirstPresent<>(new Seq<Optional<String>>(ABSENT, ABSENT)), AbsentMatcher.<String>isAbsent());

        assertThat(new FirstPresent<>(new Seq<Optional<String>>(new Present<>("1"))), isPresent("1"));
        assertThat(new FirstPresent<>(new Seq<Optional<String>>(ABSENT, new Present<>("1"))), isPresent("1"));
        assertThat(new FirstPresent<>(new Seq<Optional<String>>(new Present<>("1"), ABSENT)), isPresent("1"));

        assertThat(new FirstPresent<>(new Seq<Optional<String>>(new Present<>("1"), new Present<>("2"))), isPresent("1"));
    }


    @Test
    public void testVariousVarargCases()
    {
        assertThat(new FirstPresent<>(), isAbsent());

        assertThat(new FirstPresent<>(ABSENT), AbsentMatcher.<String>isAbsent());
        assertThat(new FirstPresent<>(ABSENT, ABSENT), AbsentMatcher.<String>isAbsent());

        assertThat(new FirstPresent<>(new Present<>("1")), isPresent("1"));
        assertThat(new FirstPresent<>(ABSENT, new Present<>("1")), isPresent("1"));
        assertThat(new FirstPresent<>(new Present<>("1"), ABSENT), isPresent("1"));

        assertThat(new FirstPresent<>(new Present<>("1"), new Present<>("2")), isPresent("1"));
    }

}