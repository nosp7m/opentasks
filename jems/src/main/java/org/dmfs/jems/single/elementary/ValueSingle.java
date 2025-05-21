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

package org.dmfs.jems.single.elementary;

import org.dmfs.jems.single.Single;


/**
 * The most simple {@link Single} there is. It takes a value and returns exactly the same value on request.
 *
 * @author Marten Gajda
 */
public final class ValueSingle<T> implements Single<T>
{
    private final T mValue;


    public ValueSingle(T value)
    {
        mValue = value;
    }


    @Override
    public T value()
    {
        return mValue;
    }
}
