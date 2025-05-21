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

package org.dmfs.android.bolts.color.dynamic.lightness;

import org.dmfs.android.bolts.color.Color;
import org.dmfs.jems.function.Function;

import androidx.core.graphics.ColorUtils;


/**
 * {@link Color} decorator to adjust lightness by applying a function to its HSV-V(Value) value.
 *
 * @author Gabor Keszthelyi
 */
public final class HsvToned implements Color
{
    private final Color mOriginal;
    private final Function<Float, Float> mLightnessAdjustingFunction;


    /**
     * Creates a new instance with adjusting the original color's lightness.
     *
     * @param lightnessAdjustingFunction
     *         applied to HSV-V(Value), but the new value will always be clipped to the valid [0,1] range regardless of the result of the function
     * @param original
     *         the color to change
     */
    public HsvToned(Function<Float, Float> lightnessAdjustingFunction, Color original)
    {
        mLightnessAdjustingFunction = new Clamping<>(0f, 1f, lightnessAdjustingFunction);
        mOriginal = original;
    }


    @Override
    public int argb()
    {
        float[] hsv = new float[3];
        int original = mOriginal.argb();
        android.graphics.Color.colorToHSV(original, hsv);

        hsv[2] = mLightnessAdjustingFunction.value(hsv[2]);

        return ColorUtils.setAlphaComponent(
                android.graphics.Color.HSVToColor(hsv),
                android.graphics.Color.alpha(original)
        );
    }
}
