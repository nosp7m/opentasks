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

import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.single.Single;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


/**
 * A {@link Generator} for {@link java.security.MessageDigest} instances of a specific digest algorithm.
 */
public final class DigestGenerator implements Generator<MessageDigest>
{
    private final Single<String> mAlgorithm;


    public DigestGenerator(String algorithm)
    {
        this(() -> algorithm);
    }


    public DigestGenerator(Single<String> algorithm)
    {
        mAlgorithm = algorithm;
    }


    @Override
    public MessageDigest next() throws RuntimeException
    {
        try
        {
            return java.security.MessageDigest.getInstance(mAlgorithm.value());
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(
                String.format(
                    Locale.ENGLISH,
                    "Algorithm %s not supported by runtime.", mAlgorithm.value()),
                e);
        }
    }
}
