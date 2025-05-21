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

package org.dmfs.jems.mockito.doubles;

import org.dmfs.jems.single.Single;
import org.mockito.exceptions.base.MockitoException;


/**
 * {@link Single} for a dummy test double instance of <code>T</code>.
 *
 * @author Gabor Keszthelyi
 */
public final class DummySingle<T> implements Single<T>
{
    private final Class<T> mClass;
    private final String mFailExtraMessage;


    public DummySingle(Class<T> clazz, String failExtraMessage)
    {
        mClass = clazz;
        mFailExtraMessage = failExtraMessage;
    }


    public DummySingle(Class<T> clazz)
    {
        this(clazz, "");
    }


    @Override
    public T value()
    {
        try
        {
            return TestDoubles.dummy(mClass);
        }
        catch (MockitoException e)
        {
            throw new RuntimeException(String.format(
                    "Can't create dummy for class %s %s", mClass.getSimpleName(), mFailExtraMessage), e);
        }
    }
}
