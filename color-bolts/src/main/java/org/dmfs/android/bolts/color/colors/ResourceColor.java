/*
 * Copyright 2017 SchedJoules
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

package org.dmfs.android.bolts.color.colors;

import android.content.Context;

import org.dmfs.android.bolts.color.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;


/**
 * A {@link Color} for a given color resource.
 *
 * @author Gabor Keszthelyi
 */
public final class ResourceColor implements Color
{
    private final Context mContext;
    private final int mColorResId;


    public ResourceColor(@NonNull Context context, @ColorRes int colorResId)
    {
        mContext = context;
        mColorResId = colorResId;
    }


    @Override
    public int argb()
    {
        return ContextCompat.getColor(mContext, mColorResId);
    }
}
