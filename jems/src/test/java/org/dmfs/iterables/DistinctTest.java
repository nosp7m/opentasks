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

package org.dmfs.iterables;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class DistinctTest
{
    @Test
    public void testTrivial() throws Exception
    {
        assertThat(new Distinct<>(Collections.<String>emptyList()), emptyIterableOf(String.class));
    }


    @Test
    public void testSingle() throws Exception
    {
        assertThat(new Distinct<>(Collections.singletonList("1")), contains("1"));
    }


    @Test
    public void testManyDuplicates() throws Exception
    {
        assertThat(new Distinct<>(Arrays.asList("1", "1", "1")), contains("1"));
    }


    @Test
    public void testManyNoDuplicates() throws Exception
    {
        assertThat(new Distinct<>(Arrays.asList("1", "2", "3")), contains("1", "2", "3"));
    }


    @Test
    public void testManySomeDuplicates() throws Exception
    {
        assertThat(new Distinct<>(Arrays.asList("1", "2", "3", "1", "2", "1")), contains("1", "2", "3"));
    }


    @Test
    public void testManySomeMoreDuplicates() throws Exception
    {
        assertThat(new Distinct<>(Arrays.asList("1", "1", "1", "2", "2", "2", "3", "3", "3", "1", "2", "1")), contains("1", "2", "3"));
    }

}