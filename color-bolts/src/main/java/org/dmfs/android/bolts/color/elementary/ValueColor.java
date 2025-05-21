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

package org.dmfs.android.bolts.color.elementary;

import org.dmfs.android.bolts.color.Color;

import androidx.annotation.ColorInt;


/**
 * A {@link Color} that simply takes a ready {@link ColorInt} value.
 * <p>
 * Note: When the project uses {@link Color} to represent colors in code everywhere, this class is usually not required.
 *
 * @author Gabor Keszthelyi
 */
public final class ValueColor implements Color
{
    private final int mColorInt;


    public ValueColor(@ColorInt int colorInt)
    {
        mColorInt = colorInt;
    }


    @ColorInt
    @Override
    public int argb()
    {
        return mColorInt;
    }
}
