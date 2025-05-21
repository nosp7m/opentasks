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

package org.dmfs.jems.generator.elementary;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher.throwing;
import static org.dmfs.jems.hamcrest.matchers.GeneratableMatcher.startsWith;
import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.causedBy;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.throwable;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link DigestGenerator}.
 *
 * @author marten
 */
public class DigestGeneratorTest
{
    @Test
    public void testMd5()
    {
        assertThat(
            () -> new DigestGenerator("MD5"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5"))));
        assertThat(
            () -> new DigestGenerator(() -> "MD5"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5")),
                having(MessageDigest::getAlgorithm, is("MD5"))));
    }


    @Test
    public void testSha1()
    {
        assertThat(
            () -> new DigestGenerator("SHA-1"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1"))));

        assertThat(
            () -> new DigestGenerator(() -> "SHA-1"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1")),
                having(MessageDigest::getAlgorithm, is("SHA-1"))));
    }


    @Test
    public void testSha256()
    {
        assertThat(
            () -> new DigestGenerator("SHA-256"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256"))));
        assertThat(
            () -> new DigestGenerator(() -> "SHA-256"),
            startsWith(
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256")),
                having(MessageDigest::getAlgorithm, is("SHA-256"))));
    }


    @Test
    public void testBogusDigest()
    {
        assertThat(
            new DigestGenerator("BOGUS-TEST-DIGEST"),
            having(
                gen -> gen::next,
                is(
                    throwing(
                        throwable(
                            RuntimeException.class,
                            causedBy(
                                throwable(NoSuchAlgorithmException.class)))))));
    }

}