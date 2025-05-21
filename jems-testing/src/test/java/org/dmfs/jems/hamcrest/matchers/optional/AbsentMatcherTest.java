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

package org.dmfs.jems.hamcrest.matchers.optional;

import org.dmfs.jems.optional.Optional;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertThat;


/**
 * Test for {@link AbsentMatcher}.
 *
 * @author Marten Gajda
 */
public final class AbsentMatcherTest
{
    @Test
    public void test_absent_noArg()
    {
        assertThat(AbsentMatcher.absent().matchesSafely(new RegularAbsent<>(), desc()), is(true));

        assertThat(AbsentMatcher.<String>absent().matchesSafely(new RegularPresent<>("3"), desc()), is(false));
        assertThat(AbsentMatcher.<Date>absent().matchesSafely(new BrokenAbsent(), desc()), is(false));
        assertThat(AbsentMatcher.<Date>absent().matchesSafely(new BrokenPresent<>(), desc()), is(false));
    }


    @Test
    public void testPresenceMismatchDescription() throws Exception
    {
        Description mismatchMsg = new StringDescription();
        new AbsentMatcher<>().describeMismatch(new RegularPresent<>("3"), mismatchMsg);
        assertThat(mismatchMsg.toString(), CoreMatchers.is("present"));
    }


    @Test
    public void testDescribeTo() throws Exception
    {
        Description description = new StringDescription();
        new AbsentMatcher<>().describeTo(description);
        assertThat(description, hasToString("absent"));
    }


    private Description.NullDescription desc()
    {
        return new Description.NullDescription();
    }


    private static final class BrokenAbsent implements Optional<Date>
    {
        @Override
        public boolean isPresent()
        {
            return false;
        }


        @Override
        public Date value() throws NoSuchElementException
        {
            return new Date();
        }
    }


    private static final class BrokenPresent<T> implements Optional<T>
    {

        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public T value() throws NoSuchElementException
        {
            throw new NoSuchElementException();
        }
    }


    private static final class RegularAbsent<T> implements Optional<T>
    {
        @Override
        public boolean isPresent()
        {
            return false;
        }


        @Override
        public T value() throws NoSuchElementException
        {
            throw new NoSuchElementException();
        }
    }


    private static final class RegularPresent<T> implements Optional<T>
    {
        private final T mValue;


        private RegularPresent(T value)
        {
            mValue = value;
        }


        @Override
        public boolean isPresent()
        {
            return true;
        }


        @Override
        public T value() throws NoSuchElementException
        {
            return mValue;
        }
    }

}