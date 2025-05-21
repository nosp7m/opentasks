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

package org.dmfs.iterables.decorators;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Fluent}.
 *
 * @author Gabor Keszthelyi
 * @author Marten Gajda
 */
public class FluentIterableTest
{

    @Test
    public void filtered_empty_iterable()
    {
        assertThat(new Fluent<>(new EmptyIterable<>()).filtered(e -> true),
                is(emptyIterable()));
    }


    @Test
    public void filtered_none_pass()
    {
        assertThat(new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered(e -> false),
                is(emptyIterable()));
    }


    @Test
    public void filtered_some_pass()
    {
        assertThat(new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered(e -> e.length() != 2),
                iteratesTo("1", "2", "3", "100", "200", "300"));
    }


    @Test
    public void filtered_all_pass()
    {
        assertThat(new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).filtered(e -> true),
                iteratesTo("1", "2", "3", "10", "20", "30", "100", "200", "300"));
    }


    @Test
    public void mapped()
    {
        assertThat(new Fluent<>(new Seq<>("1", "2", "3", "10", "20", "30", "100", "200", "300")).mapped(Integer::parseInt),
                iteratesTo(1, 2, 3, 10, 20, 30, 100, 200, 300));
    }

}