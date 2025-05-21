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

import org.dmfs.jems.generator.elementary.DigestGenerator;
import org.dmfs.jems.messagedigest.MessageDigestFactory;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;


/**
 * A factory for {@link java.security.MessageDigest} instances of a specific digest algorithm.
 *
 * @author Marten Gajda
 * @deprecated in favour of {@link DigestGenerator}.
 */
@Deprecated
public final class DigestFactory implements MessageDigestFactory
{
    private final String mAlgorithm;


    public DigestFactory(String algorithm)
    {
        mAlgorithm = algorithm;
    }


    @Override
    public java.security.MessageDigest newInstance() throws RuntimeException
    {
        try
        {
            return java.security.MessageDigest.getInstance(mAlgorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(String.format(Locale.ENGLISH, "Algorithm %s not supported by runtime.", mAlgorithm), e);
        }
    }
}
