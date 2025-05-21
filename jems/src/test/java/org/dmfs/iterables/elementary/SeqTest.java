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

package org.dmfs.iterables.elementary;

import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterableOf;
import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class SeqTest
{
    @Test
    public void testIterator() throws Exception
    {
        assertThat(new Seq<String>(), emptyIterableOf(String.class));
        assertThat(new Seq<>("1"), contains("1"));
        assertThat(new Seq<>("1", "2", "3"), contains("1", "2", "3"));
    }

}