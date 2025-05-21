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

package org.dmfs.jems.hamcrest.matchers;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceMatcher.validCharSequence;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class CharSequenceMatcherTest
{
    @Test
    public void testCharSequenceMatches() throws Exception
    {
        assertTrue(validCharSequence("").matches(""));
        assertTrue(validCharSequence("1").matches("1"));
        assertTrue(validCharSequence("1234").matches("1234"));
    }


    @Test
    public void testCharSequenceCharAtMismatch() throws Exception
    {
        // test charAt with wrong result
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i != 1 ? super.charAt(i) : 'x';
            }
        }));
    }


    @Test
    public void testCharSequenceCharAtNoException1() throws Exception
    {
        // test charAt which doesn't throw at invalid index
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i < 3 ? super.charAt(i) : '1';
            }
        }));
    }


    @Test
    public void testCharSequenceCharAtNoException2() throws Exception
    {
        // test charAt which doesn't throw at invalid index
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public char charAt(int i)
            {
                return i >= 0 ? super.charAt(i) : '1';
            }
        }));
    }


    @Test
    public void testCharSequenceWrongToString() throws Exception
    {
        // test wrong result of toString()
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public String toString()
            {
                return "1234";
            }
        }));
    }


    @Test
    public void testCharSequenceWrongLength() throws Exception
    {
        // wrong length
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public int length()
            {
                return 2;
            }
        }));
    }


    @Test
    public void testCharSequenceWrongSubSequence() throws Exception
    {
        // test subSequence with wrong result
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public CharSequence subSequence(int i, int i1)
            {
                return "122".subSequence(i, i1);
            }
        }));
    }


    @Test
    public void testCharSequenceNotThrowing1() throws Exception
    {
        // test subSequence which doesn't throw at invalid index
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public CharSequence subSequence(int i, int i1)
            {
                return i < 0 ? "1" : super.subSequence(i, i1);
            }
        }));
    }


    @Test
    public void testCharSequenceNotThrowing2() throws Exception
    {
        // test subSequence which doesn't throw at invalid index
        assertFalse(validCharSequence("123").matches(new TestSequence("123")
        {
            @Override
            public CharSequence subSequence(int i, int i1)
            {
                return i1 > 3 ? "1" : super.subSequence(i, i1);
            }
        }));
    }


    public abstract static class TestSequence implements CharSequence
    {
        private final CharSequence mDelegate;


        protected TestSequence(CharSequence delegate)
        {
            mDelegate = delegate;
        }


        @Override
        public int length()
        {
            return mDelegate.length();
        }


        @Override
        public char charAt(int i)
        {
            return mDelegate.charAt(i);
        }


        @Override
        public CharSequence subSequence(int i, int i1)
        {
            return mDelegate.subSequence(i, i1);
        }


        @Override
        public String toString()
        {
            return mDelegate.toString();
        }
    }
}