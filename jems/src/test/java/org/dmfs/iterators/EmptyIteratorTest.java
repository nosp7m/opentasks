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

package org.dmfs.iterators;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


/**
 * @author Marten Gajda
 */
public class EmptyIteratorTest
{
    @Test
    public void instance() throws Exception
    {
        assertNotNull(EmptyIterator.instance());
    }


    @Test
    public void hasNext() throws Exception
    {
        assertFalse(EmptyIterator.instance().hasNext());
        assertFalse(new EmptyIterator<>().hasNext());
    }


    @Test(expected = NoSuchElementException.class)
    public void next() throws Exception
    {
        new EmptyIterator<>().next();
    }

}