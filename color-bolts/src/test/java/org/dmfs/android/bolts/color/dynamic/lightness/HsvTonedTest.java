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
 * Unit test for {@link HsvToned}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class HsvTonedTest
{

    @Test
    public void test()
    {
        int original = ColorUtils.setAlphaComponent(Color.CYAN, 78);
        float[] originalHsv = new float[3];
        android.graphics.Color.colorToHSV(original, originalHsv);

        int updated = new HsvToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return 0.12345f;
            }
        }, new ValueColor(original)).argb();

        float[] updatedHsv = new float[3];
        android.graphics.Color.colorToHSV(updated, updatedHsv);

        assertEquals(originalHsv[0], updatedHsv[0], 0);
        assertEquals(originalHsv[1], updatedHsv[1], 0);
        assertEquals(0.12345f, updatedHsv[2], 0.01);
        assertEquals(Color.alpha(original), Color.alpha(updated));

        assertNotEquals(originalHsv[2], updatedHsv[2], 0.01);
    }


    @Test
    public void test_thatLowerBoundIsAppliedAutomatically()
    {
        int updated = new HsvToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return -3f;
            }
        }, new ValueColor(Color.CYAN)).argb();

        float[] updatedHsv = new float[3];
        android.graphics.Color.colorToHSV(updated, updatedHsv);

        assertEquals(0, updatedHsv[2], 0);
    }


    @Test
    public void test_thatUpperBoundIsAppliedAutomatically()
    {
        int updated = new HsvToned(new Function<Float, Float>()
        {
            @Override
            public Float value(Float aFloat)
            {
                return 3f;
            }
        }, new ValueColor(Color.CYAN)).argb();

        float[] updatedHsv = new float[3];
        android.graphics.Color.colorToHSV(updated, updatedHsv);

        assertEquals(1, updatedHsv[2], 0);
    }

}