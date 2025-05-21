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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class NoneOfTest
{

    @Test
    public void testIterate()
    {
        Filter<String> tested1 = new NoneOf<String>("IncludeMe", "MeToo", "1", "3", null, "");
        assertFalse(tested1.iterate(null));
        assertFalse(tested1.iterate("MeToo"));
        assertFalse(tested1.iterate("1"));
        assertFalse(tested1.iterate("3"));
        assertFalse(tested1.iterate(""));
        assertFalse(tested1.iterate("IncludeMe"));
        assertTrue(tested1.iterate("Not me"));
        assertTrue(tested1.iterate("2"));

        Filter<String> tested2 = new NoneOf<String>();
        assertTrue(tested2.iterate(null));
        assertTrue(tested2.iterate("MeToo"));
        assertTrue(tested2.iterate("1"));
        assertTrue(tested2.iterate("3"));
        assertTrue(tested2.iterate(""));
        assertTrue(tested2.iterate("IncludeMe"));
        assertTrue(tested2.iterate("Not me"));
        assertTrue(tested2.iterate("2"));
    }

}
