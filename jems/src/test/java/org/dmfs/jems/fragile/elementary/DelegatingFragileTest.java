/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.fragile.elementary;

import org.dmfs.jems.fragile.Fragile;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher.isBroken;
import static org.dmfs.jems.hamcrest.matchers.IntactFragileMatcher.isIntact;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Test for {@link DelegatingFragile}.
 *
 * @author Marten Gajda
 */
public class DelegatingFragileTest
{
    @Test
    public void testValue() throws Exception
    {
        assertThat(new TestFragile<>(new Broken<>(new UnsupportedEncodingException())), isBroken(UnsupportedEncodingException.class));
        assertThat(new TestFragile<>(new Intact<>("abc")), isIntact("abc"));
    }


    private final class TestFragile<T, E extends Exception> extends DelegatingFragile<T, E>
    {
        TestFragile(Fragile<T, E> delegate)
        {
            super(delegate);
        }
    }
}