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

package org.dmfs.iterators.decorators;

import org.dmfs.iterators.EmptyIterator;
import org.dmfs.iterators.SingletonIterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;


/**
 * @author Marten Gajda
 */
public class DelegatingIteratorTest
{
    @Test
    public void testHasNext() throws Exception
    {
        // test that it return the result of `hasNext` of the delegate
        Assert.assertThat(new TestIterator<>(new EmptyIterator<>()).hasNext(), is(false));
        Assert.assertThat(new TestIterator<>(new SingletonIterator<>("1")).hasNext(), is(true));
    }


    @Test
    public void testNext() throws Exception
    {
        // test that it return the result of `next` of the delegate
        Assert.assertThat(new TestIterator<>(new SingletonIterator<>("1")).next(), is("1"));
    }


    @Test(expected = NoSuchElementException.class)
    public void testNextFail() throws Exception
    {
        new TestIterator<>(new EmptyIterator<>()).next();
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveFail() throws Exception
    {
        // should throw if delegate remove throws
        new TestIterator<>(new EmptyIterator<>()).remove();
    }


    @Test
    public void testRemovePass() throws Exception
    {
        // should pass if delegate remove doesn't throw
        new TestIterator<>(new Iterator<Object>()
        {
            @Override
            public boolean hasNext()
            {
                return false;
            }


            @Override
            public Object next()
            {
                return null;
            }


            @Override
            public void remove()
            {
                // nothing
            }
        }).remove();
    }


    private final class TestIterator<T> extends DelegatingIterator<T>
    {

        public TestIterator(Iterator<T> delegate)
        {
            super(delegate);
        }
    }
}