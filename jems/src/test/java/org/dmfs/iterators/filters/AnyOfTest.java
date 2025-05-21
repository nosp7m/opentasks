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


public class AnyOfTest
{

    @Test
    public void testIterate()
    {
        Filter<String> tested1 = new AnyOf<String>("IncludeMe", "MeToo", "1", "3", null, "");
        assertTrue(tested1.iterate(null));
        assertTrue(tested1.iterate("MeToo"));
        assertTrue(tested1.iterate("1"));
        assertTrue(tested1.iterate("3"));
        assertTrue(tested1.iterate(""));
        assertTrue(tested1.iterate("IncludeMe"));
        assertFalse(tested1.iterate("Not me"));
        assertFalse(tested1.iterate("2"));

        Filter<String> tested2 = new AnyOf<String>();
        assertFalse(tested2.iterate(null));
        assertFalse(tested2.iterate("MeToo"));
        assertFalse(tested2.iterate("1"));
        assertFalse(tested2.iterate("3"));
        assertFalse(tested2.iterate(""));
        assertFalse(tested2.iterate("IncludeMe"));
        assertFalse(tested2.iterate("Not me"));
        assertFalse(tested2.iterate("2"));
    }

}
