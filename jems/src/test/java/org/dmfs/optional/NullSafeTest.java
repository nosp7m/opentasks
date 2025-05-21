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

package org.dmfs.optional;

import org.dmfs.jems.hamcrest.matchers.AbsentMatcher;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.dmfs.jems.hamcrest.matchers.PresentMatcher.isPresent;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class NullSafeTest
{
    @Test
    public void testIsPresent() throws Exception
    {
        assertThat(new NullSafe<String>(null), AbsentMatcher.<String>isAbsent());
        assertThat(new NullSafe<>("test"), isPresent("test"));
    }


    @Test
    public void testValue1() throws Exception
    {
        assertEquals("xyz", new NullSafe<>(null).value("xyz"));
        assertEquals("test", new NullSafe<>("test").value("xyz"));
    }

}