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

package org.dmfs.android.carrot.locaters;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.Nullable;

import au.com.codeka.carrot.resource.ResourceName;


/**
 * A {@link ResourceName} of a raw resource identified by its name (like it would be used in a template).
 *
 * @author Marten Gajda
 */
public final class RawResourceName implements ResourceName
{
    private final Context mContext;
    private final String mName;


    RawResourceName(Context context, String name)
    {
        mContext = context;
        mName = name;
    }


    @Override
    public String getName()
    {
        // try to resolve the name to an id
        Resources resources = mContext.getResources();
        return String.valueOf(resources.getIdentifier(mName, "raw", mContext.getPackageName()));
    }


    @Nullable
    @Override
    public ResourceName getParent()
    {
        return null;
    }
}
