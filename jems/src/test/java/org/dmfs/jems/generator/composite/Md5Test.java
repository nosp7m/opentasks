/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.jems.generator.composite;

import org.junit.Test;

import java.security.MessageDigest;

import static org.dmfs.jems.hamcrest.matchers.GeneratableMatcher.startsWith;
import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Md5}.
 */
public class Md5Test
{
    @Test
    public void test()
    {
        assertThat(
            Md5::new,
            startsWith(
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5"))));
    }

}