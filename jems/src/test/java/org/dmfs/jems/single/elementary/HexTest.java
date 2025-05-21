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

package org.dmfs.jems.single.elementary;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 * @author marten
 */
public class HexTest
{
    @Test
    public void testValue() throws Exception
    {
        assertThat(new Hex(new ValueSingle<>(new byte[0])).value().toString(), is(""));
        assertThat(new Hex(new ValueSingle<>(new byte[]{0x01})).value().toString(), is("01"));
        assertThat(new Hex(new ValueSingle<>(new byte[]{0x01, 0x02})).value().toString(), is("0102"));
    }

}