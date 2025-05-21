/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.iterables;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link UnquotedSplit}.
 *
 * @author Marten Gajda
 */
public class UnquotedSplitTest
{
    @Test
    public void test()
    {
        assertThat(new UnquotedSplit("", ';'), iteratesTo(""));
        assertThat(new UnquotedSplit("a", ';'), iteratesTo("a"));
        assertThat(new UnquotedSplit("abc", ';'), iteratesTo("abc"));
        assertThat(new UnquotedSplit("abc;", ';'), iteratesTo("abc", ""));
        assertThat(new UnquotedSplit(";abc", ';'), iteratesTo("", "abc"));
        assertThat(new UnquotedSplit(";abc;", ';'), iteratesTo("", "abc", ""));
        assertThat(new UnquotedSplit("abc;xyz;123", ';'), iteratesTo("abc", "xyz", "123"));
        assertThat(new UnquotedSplit("abc;;xyz;;123", ';'), iteratesTo("abc", "", "xyz", "", "123"));
        assertThat(new UnquotedSplit(";abc;xyz;123;", ';'), iteratesTo("", "abc", "xyz", "123", ""));
        assertThat(new UnquotedSplit(";;abc;;xyz;;123;;", ';'), iteratesTo("", "", "abc", "", "xyz", "", "123", "", ""));

        assertThat(new UnquotedSplit("\"\"", ';'), iteratesTo("\"\""));
        assertThat(new UnquotedSplit("\"a\"", ';'), iteratesTo("\"a\""));
        assertThat(new UnquotedSplit("\"abc\"", ';'), iteratesTo("\"abc\""));
        assertThat(new UnquotedSplit("\"abc\";", ';'), iteratesTo("\"abc\"", ""));
        assertThat(new UnquotedSplit("\"abc;\"", ';'), iteratesTo("\"abc;\""));
        assertThat(new UnquotedSplit(";\"abc\"", ';'), iteratesTo("", "\"abc\""));
        assertThat(new UnquotedSplit("\";abc\"", ';'), iteratesTo("\";abc\""));
        assertThat(new UnquotedSplit(";\"abc\";", ';'), iteratesTo("", "\"abc\"", ""));
        assertThat(new UnquotedSplit("\";abc;\"", ';'), iteratesTo("\";abc;\""));
        assertThat(new UnquotedSplit("abc;\"xyz;123\"", ';'), iteratesTo("abc", "\"xyz;123\""));
        assertThat(new UnquotedSplit("\"abc;;xyz\";;123", ';'), iteratesTo("\"abc;;xyz\"", "", "123"));
        assertThat(new UnquotedSplit(";abc;\"xyz;123;\"", ';'), iteratesTo("", "abc", "\"xyz;123;\""));
        assertThat(new UnquotedSplit("\";;abc;;xyz;\";123;;", ';'), iteratesTo("\";;abc;;xyz;\"", "123", "", ""));

    }
}