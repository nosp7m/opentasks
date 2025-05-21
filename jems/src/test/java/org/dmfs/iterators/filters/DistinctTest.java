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

package org.dmfs.iterators.filters;

import org.dmfs.iterators.Filter;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


public class DistinctTest
{

    @Test
    public void test()
    {
        Filter<String> filter = new Distinct<>();

        assertTrue(filter.iterate("a"));
        assertFalse(filter.iterate("a"));
        assertFalse(filter.iterate("a"));
        assertTrue(filter.iterate("b"));
        assertFalse(filter.iterate("b"));
        assertFalse(filter.iterate("a"));
    }
}
