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

package org.dmfs.jems.optional.adapters;

import org.dmfs.jems.iterator.elementary.Seq;
import org.dmfs.jems.hamcrest.matchers.optional.AbsentMatcher;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;

import java.util.Iterator;

import static org.dmfs.jems.hamcrest.matchers.optional.PresentMatcher.present;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link NextPresent}.
 *
 * @author Gabor Keszthelyi
 */
public final class NextPresentTest
{

    @Test
    public void testVariousCases()
    {
        assertThat(new NextPresent<>(new Seq<Optional<String>>()), is(AbsentMatcher.absent()));

        assertThat(new NextPresent<>(new Seq<Optional<String>>(absent())), is(AbsentMatcher.absent()));
        assertThat(new NextPresent<>(new Seq<Optional<String>>(absent(), absent())), is(AbsentMatcher.absent()));

        assertThat(new NextPresent<>(new Seq<>(new Present<>("1"))), is(present("1")));
        assertThat(new NextPresent<>(new Seq<>(absent(), new Present<>("1"))), is(present("1")));
        assertThat(new NextPresent<>(new Seq<>(new Present<>("1"), absent())), is(present("1")));

        assertThat(new NextPresent<>(new Seq<>(new Present<>("1"), new Present<>("2"))), is(present("1")));
    }


    @Test
    public void testUsedIterator()
    {
        Iterator<Optional<String>> it1 = new Seq<>(new Present<>("1"), absent());
        it1.next();
        assertThat(new NextPresent<>(it1), is(AbsentMatcher.absent()));

        Iterator<Optional<String>> it2 = new Seq<Optional<String>>(new Present<>("1"), new Present<>("2"));
        it2.next();
        assertThat(new NextPresent<>(it2), is(present("2")));

        Iterator<Optional<String>> it3 = new Seq<Optional<String>>(new Present<>("1"), new Present<>("2"));
        it3.next();
        it3.next();
        assertThat(new NextPresent<>(it3), is(AbsentMatcher.absent()));
    }

}