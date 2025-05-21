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

package org.dmfs.jems.iterator.generators;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author marten
 */
public class IntSequenceGeneratorTest
{
    @Test
    public void testIterator() throws Exception
    {
        Iterator<Integer> sequence = new IntSequenceGenerator();
        for (int i = 0; i < 1_000_000; i++)
        {
            // multiple calls to hasNext should not advance the counter
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.next(), is(i));
        }
        assertThat(sequence.hasNext(), is(true));
    }


    @Test
    public void testIteratorBase10() throws Exception
    {
        Iterator<Integer> sequence = new IntSequenceGenerator(10);
        for (int i = 0; i < 1_000_000; i++)
        {
            // multiple calls to hasNext should not advance the counter
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.next(), is(i + 10));
        }
        assertThat(sequence.hasNext(), is(true));
    }


    @Test
    public void testIteratorBase10Step10() throws Exception
    {
        Iterator<Integer> sequence = new IntSequenceGenerator(10, 10);
        for (int i = 0; i < 1_000_000; i++)
        {
            // multiple calls to hasNext should not advance the counter
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.next(), is(i * 10 + 10));
        }
        assertThat(sequence.hasNext(), is(true));
    }


    @Test
    public void testIteratorBase10StepBackwards() throws Exception
    {
        Iterator<Integer> sequence = new IntSequenceGenerator(10, -1);
        for (int i = 0; i < 1_000_000; i++)
        {
            // multiple calls to hasNext should not advance the counter
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.hasNext(), is(true));
            assertThat(sequence.next(), is(-i + 10));
        }
        assertThat(sequence.hasNext(), is(true));
    }


    @Test
    public void testOverflow()
    {
        Iterator<Integer> sequence = new IntSequenceGenerator(Integer.MAX_VALUE - 1);
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));

        assertThat(sequence.next(), is(Integer.MAX_VALUE - 1));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));

        assertThat(sequence.next(), is(Integer.MAX_VALUE));

        assertThat(sequence.hasNext(), is(false));
        assertThat(sequence.hasNext(), is(false));
        assertThat(sequence.hasNext(), is(false));

        try
        {
            sequence.next();
            fail("Did not throw");
        }
        catch (NoSuchElementException e)
        {
            // pass
        }
    }


    @Test
    public void testUnderflow()
    {
        Iterator<Integer> sequence = new IntSequenceGenerator(Integer.MIN_VALUE + 1, -1);
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));

        assertThat(sequence.next(), is(Integer.MIN_VALUE + 1));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));
        assertThat(sequence.hasNext(), is(true));

        assertThat(sequence.next(), is(Integer.MIN_VALUE));

        assertThat(sequence.hasNext(), is(false));
        assertThat(sequence.hasNext(), is(false));
        assertThat(sequence.hasNext(), is(false));

        try
        {
            sequence.next();
            fail("Did not throw");
        }
        catch (NoSuchElementException e)
        {
            // pass
        }
    }

}