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
import android.content.res.Resources;
import android.util.TypedValue;

import org.dmfs.android.bolts.color.Color;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


/**
 * Represents the {@link Color} referenced by a specific attribute.
 *
 * @author Marten Gajda
 */
public final class AttributeColor implements Color
{
    private final Single<Resources.Theme> mTheme;
    @AttrRes
    private final int mColorAttr;


    public AttributeColor(@NonNull final Context context, @AttrRes final int colorAttr)
    {
        this(
                // be lazy
                new Single<Resources.Theme>()
                {
                    @Override
                    public Resources.Theme value()
                    {
                        return context.getTheme();
                    }
                },
                colorAttr);
    }


    public AttributeColor(@NonNull Resources.Theme theme, @AttrRes int colorAttr)
    {
        this(new ValueSingle<>(theme), colorAttr);
    }


    public AttributeColor(@NonNull Single<Resources.Theme> theme, @AttrRes int colorAttr)
    {
        mTheme = theme;
        mColorAttr = colorAttr;
    }


    @ColorInt
    @Override
    public int argb()
    {
        TypedValue typedValue = new TypedValue();
        mTheme.value().resolveAttribute(mColorAttr, typedValue, true);
        return typedValue.data;
    }
}
