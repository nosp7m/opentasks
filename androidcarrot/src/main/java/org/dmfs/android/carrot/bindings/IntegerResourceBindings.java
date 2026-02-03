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
 * 
 * This file has been changed from the original.
 */

package org.dmfs.android.carrot.bindings;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for string resources.
 *
 * @author Marten Gajda
 */
public final class IntegerResourceBindings implements Bindings
{
    private final Context mContext;


    public IntegerResourceBindings(Context context)
    {
        mContext = context;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(key, "integer", mContext.getPackageName());
        return id == 0 ? null : resources.getInteger(id);
    }


    @Override
    public boolean isEmpty()
    {
        // we presume there is always at least one integer resource.
        return false;
    }
}
