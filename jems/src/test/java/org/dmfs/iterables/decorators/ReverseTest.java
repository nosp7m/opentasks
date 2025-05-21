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
 * Test {@link Reverse}.
 *
 * @author Marten Gajda
 */
public class ReverseTest
{
    @Test
    public void testReverseTrivial() throws Exception
    {
        assertThat(new Reverse<>(Collections.<String>emptyList()), emptyIterableOf(String.class));
    }


    @Test
    public void testReverse1Arg() throws Exception
    {
        assertThat(new Reverse<>(Collections.singletonList("1")), contains("1"));
    }


    @Test
    public void testReverseMultiArg() throws Exception
    {
        assertThat(new Reverse<>(Arrays.asList("1", "2", "3")), contains("3", "2", "1"));
    }


    @Test
    public void testReverseNullTolerance() throws Exception
    {
        assertThat(new Reverse<>(Arrays.asList("1", "2", "3", null)), contains(null, "3", "2", "1"));
    }

}