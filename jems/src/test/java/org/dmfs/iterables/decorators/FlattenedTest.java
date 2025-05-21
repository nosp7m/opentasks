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

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class FlattenedTest
{
    @Test
    public void testTrivial() throws Exception
    {
        assertThat(new Flattened<>(Collections.<String>emptyList()), emptyIterableOf(String.class));
    }


    @Test
    public void testSingle() throws Exception
    {
        assertThat(new Flattened<>(Collections.singletonList("1")), contains("1"));
    }


    @Test
    public void testMultiSingle() throws Exception
    {
        assertThat(new Flattened<>(Collections.singletonList("1"), Collections.singletonList("2"), Collections.singletonList("3")), contains("1", "2", "3"));
    }


    @Test
    public void testMulti() throws Exception
    {
        assertThat(new Flattened<>(Arrays.asList("1", "2", "3")), contains("1", "2", "3"));
    }


    @Test
    public void testMultiMulti() throws Exception
    {
        assertThat(new Flattened<>(Arrays.asList("1", "2", "3"), Arrays.asList("a", "b", "c"), Arrays.asList("z", "zz", "zzz")),
                contains("1", "2", "3", "a", "b", "c", "z", "zz", "zzz"));
    }

}