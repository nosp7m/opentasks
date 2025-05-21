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

package org.dmfs.jems.messagedigest.elementary;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DigestFactoryTest
{
    @Test
    public void testNewInstance() throws Exception
    {
        assertThat(new DigestFactory("MD5").newInstance().getAlgorithm(), is("MD5"));
        assertThat(new DigestFactory("SHA-1").newInstance().getAlgorithm(), is("SHA-1"));
        assertThat(new DigestFactory("SHA-256").newInstance().getAlgorithm(), is("SHA-256"));
    }


    @Test(expected = RuntimeException.class)
    public void testInvalid() throws Exception
    {
        new DigestFactory("BOGUS-FAKE-INVALID").newInstance();
    }
}