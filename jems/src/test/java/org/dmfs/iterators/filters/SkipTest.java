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


public class SkipTest
{

    @Test
    public void testIterate()
    {
        // negative value doesn't skip anything
        Filter<String> tested1 = new Skip<String>(-1);
        assertTrue(tested1.iterate(null));
        assertTrue(tested1.iterate("MeToo"));
        assertTrue(tested1.iterate("1"));
        assertTrue(tested1.iterate("3"));
        assertTrue(tested1.iterate(""));
        assertTrue(tested1.iterate("IncludeMe"));
        assertTrue(tested1.iterate("Not me"));
        assertTrue(tested1.iterate("2"));

        // 0 value doesn't skip anything
        Filter<String> tested2 = new Skip<String>(0);
        assertTrue(tested2.iterate(null));
        assertTrue(tested2.iterate("MeToo"));
        assertTrue(tested2.iterate("1"));
        assertTrue(tested2.iterate("3"));
        assertTrue(tested2.iterate(""));
        assertTrue(tested2.iterate("IncludeMe"));
        assertTrue(tested2.iterate("Not me"));
        assertTrue(tested2.iterate("2"));

        Filter<String> tested3 = new Skip<String>(1);
        assertFalse(tested3.iterate(null));
        assertTrue(tested3.iterate("MeToo"));
        assertTrue(tested3.iterate("1"));
        assertTrue(tested3.iterate("3"));
        assertTrue(tested3.iterate(""));
        assertTrue(tested3.iterate("IncludeMe"));
        assertTrue(tested3.iterate("Not me"));
        assertTrue(tested3.iterate("2"));

        Filter<String> tested4 = new Skip<String>(5);
        assertFalse(tested4.iterate(null));
        assertFalse(tested4.iterate("MeToo"));
        assertFalse(tested4.iterate("1"));
        assertFalse(tested4.iterate("3"));
        assertFalse(tested4.iterate(""));
        assertTrue(tested4.iterate("IncludeMe"));
        assertTrue(tested4.iterate("Not me"));
        assertTrue(tested4.iterate("2"));

        Filter<String> tested5 = new Skip<String>(10);
        assertFalse(tested5.iterate(null));
        assertFalse(tested5.iterate("MeToo"));
        assertFalse(tested5.iterate("1"));
        assertFalse(tested5.iterate("3"));
        assertFalse(tested5.iterate(""));
        assertFalse(tested5.iterate("IncludeMe"));
        assertFalse(tested5.iterate("Not me"));
        assertFalse(tested5.iterate("2"));
    }

}
