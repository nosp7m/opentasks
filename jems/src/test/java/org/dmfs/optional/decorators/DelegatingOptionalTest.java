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

package org.dmfs.optional.decorators;

import org.dmfs.optional.Absent;
import org.dmfs.optional.Optional;
import org.dmfs.optional.Present;
import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DelegatingOptionalTest
{
    @Test
    public void testIsPresent() throws Exception
    {
        assertThat(new TestOptional<>(new Absent<String>()), AbsentMatcher.<String>isAbsent());
        assertThat(new TestOptional<>(new Present<>("test")), isPresent("test"));
    }


    @Test
    public void testDefaultValue() throws Exception
    {
        assertThat(new TestOptional<>(new Present<>("test")).value("default"), is("test"));
        assertThat(new TestOptional<>(new Absent<String>()).value("default"), is("default"));
    }


    private final class TestOptional<T> extends DelegatingOptional<T>
    {
        public TestOptional(Optional<T> delegate)
        {
            super(delegate);
        }
    }
}