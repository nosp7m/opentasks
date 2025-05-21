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

import org.dmfs.optional.Optional;
import org.hamcrest.Description;
import org.junit.Test;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Test for {@link AbsentMatcher}.
 *
 * @author Gabor Keszthelyi
 */
public final class AbsentMatcherTest
{
    @Test
    public void test_absent_noArg()
    {
        assertTrue(AbsentMatcher.isAbsent().matchesSafely(new GenericTypeAbsent<>(), desc()));
        assertFalse(AbsentMatcher.<String>isAbsent().matchesSafely(new GenericTypePresent<>("3"), desc()));

        assertThrows(new Runnable()
        {
            @Override
            public void run()
            {
                AbsentMatcher.<Date>isAbsent().matchesSafely(new FixNonFinalTypeAbsent(), desc());
            }
        });
        assertFalse(AbsentMatcher.<Date>isAbsent().matchesSafely(new FixNonFinalTypePresent(), desc()));

        assertThrows(new Runnable()
        {
            @Override
            public void run()
            {
                AbsentMatcher.<String>isAbsent().matchesSafely(new FixFinalTypeAbsent(), desc());
            }
        });
        assertFalse(AbsentMatcher.<String>isAbsent().matchesSafely(new FixFinalTypePresent(), desc()));
    }


    @Test
    public void test_absent_classArg()
    {
        assertTrue(AbsentMatcher.isAbsent(CharSequence.class).matchesSafely(new GenericTypeAbsent<CharSequence>(), desc()));
        assertFalse(AbsentMatcher.isAbsent(CharSequence.class).matchesSafely(new GenericTypePresent<CharSequence>("3"), desc()));

        assertTrue(AbsentMatcher.isAbsent(Date.class).matchesSafely(new FixNonFinalTypeAbsent(), desc()));
        assertFalse(AbsentMatcher.isAbsent(Date.class).matchesSafely(new FixNonFinalTypePresent(), desc()));

        assertThrows(new Runnable()
        {
            @Override
            public void run()
            {
                AbsentMatcher.isAbsent(String.class).matchesSafely(new FixFinalTypeAbsent(), desc());
            }
        });
        assertFalse(AbsentMatcher.isAbsent(String.class).matchesSafely(new FixFinalTypePresent(), desc()));
    }


    @Test
    public void test_absent_dummyArg()
    {
        assertTrue(AbsentMatcher.isAbsent("3").matchesSafely(new GenericTypeAbsent<String>(), desc()));
        assertFalse(AbsentMatcher.isAbsent("3").matchesSafely(new GenericTypePresent<>("5"), desc()));

        assertTrue(AbsentMatcher.isAbsent(new Date()).matchesSafely(new FixNonFinalTypeAbsent(), desc()));
        assertFalse(AbsentMatcher.isAbsent(new Date()).matchesSafely(new FixNonFinalTypePresent(), desc()));

        assertTrue(AbsentMatcher.isAbsent("6").matchesSafely(new FixFinalTypeAbsent(), desc()));
        assertFalse(AbsentMatcher.isAbsent("6").matchesSafely(new FixFinalTypePresent(), desc()));
    }


    private void assertThrows(Runnable runnable)
    {
        try
        {
            runnable.run();
            fail("Should have thrown exception");
        }
        catch (Exception e)
        {
            // pass
        }
    }


    private Description.NullDescription desc()
    {
        return new Description.NullDescription();
    }


    private static final class FixFinalTypeAbsent implements Optional<String>
    {

        @Override
        public boolean isPresent()
        {
            return false;
        }


        @Override
        public String value(String defaultValue)
        {
            return defaultValue;
        }


        @Override
        public String value() throws NoSuchElementException
        {
            throw new NoSuchElementException();
        }
    }


    private static final class FixFinalTypePresent implements Optional<String>
    {

        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public String value(String defaultValue)
        {
            return "789";
        }


        @Override
        public String value() throws NoSuchElementException
        {
            return "789";
        }
    }


    private static final class FixNonFinalTypeAbsent implements Optional<Date>
    {

        @Override
        public boolean isPresent()
        {
            return false;
        }


        @Override
        public Date value(Date defaultValue)
        {
            return defaultValue;
        }


        @Override
        public Date value() throws NoSuchElementException
        {
            throw new NoSuchElementException();
        }
    }


    private static final class FixNonFinalTypePresent implements Optional<Date>
    {

        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public Date value(Date defaultValue)
        {
            return new Date(12345);
        }


        @Override
        public Date value() throws NoSuchElementException
        {
            return new Date(12345);
        }
    }


    private static final class GenericTypeAbsent<T> implements Optional<T>
    {

        @Override
        public boolean isPresent()
        {
            return false;
        }


        @Override
        public T value(T defaultValue)
        {
            return defaultValue;
        }


        @Override
        public T value() throws NoSuchElementException
        {
            throw new NoSuchElementException();
        }
    }


    private static final class GenericTypePresent<T> implements Optional<T>
    {
        private final T mValue;


        private GenericTypePresent(T value)
        {
            mValue = value;
        }


        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public T value(T defaultValue)
        {
            return mValue;
        }


        @Override
        public T value() throws NoSuchElementException
        {
            return mValue;
        }
    }

}