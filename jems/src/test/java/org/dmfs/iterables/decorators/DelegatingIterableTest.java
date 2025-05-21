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

import org.dmfs.iterables.EmptyIterable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DelegatingIterableTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new TestIterable<>(EmptyIterable.<String>instance()), emptyIterable());
        assertThat(new TestIterable<String>(Collections.singleton("test")), contains("test"));
        assertThat(new TestIterable<String>(Arrays.asList("test", "xyz", "abc")), contains("test", "xyz", "abc"));
    }


    private final static class TestIterable<T> extends DelegatingIterable<T>
    {

        public TestIterable(Iterable<T> delegate)
        {
            super(delegate);
        }
    }
}