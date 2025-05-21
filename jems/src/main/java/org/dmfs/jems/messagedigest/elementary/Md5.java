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

import org.dmfs.jems.messagedigest.MessageDigestFactory;


/**
 * An {@code MD5} {@link java.security.MessageDigest} factory.
 *
 * @author Marten Gajda
 * @deprecated in favour of {@link org.dmfs.jems.generator.composite.Md5}.
 */
@Deprecated
public final class Md5 implements MessageDigestFactory
{
    private final MessageDigestFactory mDelegate;


    public Md5()
    {
        mDelegate = new DigestFactory("MD5");
    }


    @Override
    public java.security.MessageDigest newInstance()
    {
        return mDelegate.newInstance();
    }
}
