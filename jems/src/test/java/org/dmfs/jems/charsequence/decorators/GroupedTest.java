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

package org.dmfs.jems.charsequence.decorators;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.CharSequenceMatcher.validCharSequence;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class GroupedTest
{
    @Test
    public void test()
    {
        assertThat(new Grouped(1, ':', ""), is(validCharSequence("")));
        assertThat(new Grouped(1, ':', "a"), is(validCharSequence("a")));
        assertThat(new Grouped(1, ':', "ab"), is(validCharSequence("a:b")));
        assertThat(new Grouped(1, ':', "abc"), is(validCharSequence("a:b:c")));
        assertThat(new Grouped(1, ':', "abcd"), is(validCharSequence("a:b:c:d")));
        assertThat(new Grouped(1, ':', "abcde"), is(validCharSequence("a:b:c:d:e")));
        assertThat(new Grouped(1, ':', "abcdef"), is(validCharSequence("a:b:c:d:e:f")));
        assertThat(new Grouped(1, ':', "abcdefg"), is(validCharSequence("a:b:c:d:e:f:g")));
        assertThat(new Grouped(2, ':', ""), is(validCharSequence("")));
        assertThat(new Grouped(2, ':', "a"), is(validCharSequence("a")));
        assertThat(new Grouped(2, ':', "ab"), is(validCharSequence("ab")));
        assertThat(new Grouped(2, ':', "abc"), is(validCharSequence("ab:c")));
        assertThat(new Grouped(2, ':', "abcd"), is(validCharSequence("ab:cd")));
        assertThat(new Grouped(2, ':', "abcde"), is(validCharSequence("ab:cd:e")));
        assertThat(new Grouped(2, ':', "abcdef"), is(validCharSequence("ab:cd:ef")));
        assertThat(new Grouped(2, ':', "abcdefg"), is(validCharSequence("ab:cd:ef:g")));
        assertThat(new Grouped(3, ':', ""), is(validCharSequence("")));
        assertThat(new Grouped(3, ':', "a"), is(validCharSequence("a")));
        assertThat(new Grouped(3, ':', "ab"), is(validCharSequence("ab")));
        assertThat(new Grouped(3, ':', "abc"), is(validCharSequence("abc")));
        assertThat(new Grouped(3, ':', "abcd"), is(validCharSequence("abc:d")));
        assertThat(new Grouped(3, ':', "abcde"), is(validCharSequence("abc:de")));
        assertThat(new Grouped(3, ':', "abcdef"), is(validCharSequence("abc:def")));
        assertThat(new Grouped(3, ':', "abcdefg"), is(validCharSequence("abc:def:g")));
    }
}