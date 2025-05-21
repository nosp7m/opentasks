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

package org.dmfs.optional.adapters;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author Marten Gajda
 */
public class MapEntryTest
{
    @Test
    public void testIsPresent() throws Exception
    {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("key", "value");

        assertThat(new MapEntry<>(testMap, "key").isPresent(), CoreMatchers.is(true));
        assertThat(new MapEntry<>(testMap, "anotherKey").isPresent(), CoreMatchers.is(false));
    }


    @Test
    public void testValue() throws Exception
    {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("key", "value");

        assertThat(new MapEntry<>(testMap, "key").value(), CoreMatchers.is("value"));
        try
        {
            new MapEntry<>(testMap, "anotherKey").value();
            fail("Did not throw  NoSuchElementException");
        }
        catch (NoSuchElementException e)
        {
            // pass
        }
    }


    @Test
    public void testValue1() throws Exception
    {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("key", "value");

        assertThat(new MapEntry<>(testMap, "key").value("default"), CoreMatchers.is("value"));
        assertThat(new MapEntry<>(testMap, "anotherKey").value("default"), CoreMatchers.is("default"));
    }

}