/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.testing.tools;

import org.dmfs.jems.single.Single;

import androidx.annotation.NonNull;


/**
 * Holds the value of a field of an object retrieved with reflection.
 *
 * @author Gabor Keszthelyi
 */
public final class Field<T> implements Single<T>
{
    private final Object mObject;
    private final String mFieldName;


    public Field(@NonNull Object object, @NonNull String fieldName)
    {
        mObject = object;
        mFieldName = fieldName;
    }


    @Override
    public T value()
    {
        try
        {
            java.lang.reflect.Field field = mObject.getClass().getDeclaredField(mFieldName);
            field.setAccessible(true);
            //noinspection unchecked
            return (T) field.get(mObject);
        }
        catch (Exception e)
        {
            throw new RuntimeException(String.format("Could not read field with name '%s' of object %s", mFieldName, mObject));
        }
    }
}
