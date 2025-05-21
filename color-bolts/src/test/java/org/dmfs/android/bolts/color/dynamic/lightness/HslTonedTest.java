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

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;

import org.dmfs.android.bolts.color.colors.ValueColor;
import org.dmfs.jems.function.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Unit test for {@link HslToned}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class HslTonedTest
{

    @Test
    public void test()
    {
        int original = ColorUtils.setAlphaComponent(android.graphics.Color.CYAN, 78);
        float[] originalHsl = new float[3];
        ColorUtils.colorToHSL(original, originalHsl);

        int updated = new HslToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return 0.12345f;
            }
        }, new ValueColor(original)).argb();

        float[] updatedHsl = new float[3];
        ColorUtils.colorToHSL(updated, updatedHsl);

        assertEquals(originalHsl[0], updatedHsl[0], 0);
        assertEquals(originalHsl[1], updatedHsl[1], 0);
        assertEquals(0.12345f, updatedHsl[2], 0.01);
        assertEquals(Color.alpha(original), Color.alpha(updated));

        assertNotEquals(originalHsl[2], updatedHsl[2], 0.01);
    }


    @Test
    public void test_thatLowerBoundIsAppliedAutomatically()
    {
        int updated = new HslToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return -3f;
            }
        }, new ValueColor(Color.CYAN)).argb();

        float[] updatedHsl = new float[3];
        ColorUtils.colorToHSL(updated, updatedHsl);

        assertEquals(0, updatedHsl[2], 0);
    }


    @Test
    public void test_thatUpperBoundIsAppliedAutomatically()
    {
        int updated = new HslToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return 3f;
            }
        }, new ValueColor(Color.CYAN)).argb();

        float[] updatedHsl = new float[3];
        ColorUtils.colorToHSL(updated, updatedHsl);

        assertEquals(1, updatedHsl[2], 0);
    }

}