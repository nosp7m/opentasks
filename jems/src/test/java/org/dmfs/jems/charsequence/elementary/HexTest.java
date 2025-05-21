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

package org.dmfs.jems.charsequence.elementary;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceMatcher.validCharSequence;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class HexTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new Hex(new byte[0]), is(validCharSequence("")));
        assertThat(new Hex(new byte[] { 1 }), is(validCharSequence("01")));
        assertThat(new Hex(new byte[] { (byte) 0xff }), is(validCharSequence("ff")));
        assertThat(new Hex(new byte[] { 1, 2, 3 }), is(validCharSequence("010203")));
        assertThat(new Hex(new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef }),
                is(validCharSequence("0123456789abcdef")));

        assertThat(new Hex(new byte[0], 0, 0), is(validCharSequence("")));

        assertThat(new Hex(new byte[] { 1 }, 0, 1), is(validCharSequence("01")));
        assertThat(new Hex(new byte[] { 1 }, 0, 0), is(validCharSequence("")));
        assertThat(new Hex(new byte[] { 1 }, 1, 1), is(validCharSequence("")));

        assertThat(new Hex(new byte[] { (byte) 0xff }, 0, 1), is(validCharSequence("ff")));
        assertThat(new Hex(new byte[] { (byte) 0xff }, 0, 0), is(validCharSequence("")));
        assertThat(new Hex(new byte[] { (byte) 0xff }, 1, 1), is(validCharSequence("")));

        assertThat(new Hex(new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef }, 0, 8),
                is(validCharSequence("0123456789abcdef")));
        assertThat(new Hex(new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef }, 0, 1),
                is(validCharSequence("01")));
        assertThat(new Hex(new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef }, 7, 8),
                is(validCharSequence("ef")));
        assertThat(new Hex(new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef }, 3, 7),
                is(validCharSequence("6789abcd")));

    }
}
