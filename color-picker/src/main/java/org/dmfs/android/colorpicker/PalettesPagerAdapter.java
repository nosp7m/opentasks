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

package org.dmfs.android.colorpicker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.collection.LruCache;

import org.dmfs.android.colorpicker.palettes.Palette;
import org.dmfs.android.view.IDrawableTitlePagerAdapter;


/**
 * Pager adapter for the {@link PaletteFragment}s.
 *
 * @author Marten Gajda
 */
public final class PalettesPagerAdapter extends FragmentStatePagerAdapter implements IDrawableTitlePagerAdapter
{
    private final static int PREVIEW_SIZE = 32; // dp

    private final static int FACTOR = 100;
    private final Resources mResources;
    private final float mDensity;
    private final PreviewCache mCache = new PreviewCache(15);
    private final Palette[] mPalettes;


    public PalettesPagerAdapter(Resources res, FragmentManager fm, Palette... palettes)
    {
        super(fm);
        mPalettes = palettes;
        mResources = res;
        mDensity = res.getDisplayMetrics().density;
    }


    /*
     * (non-Javadoc)
     *
     * @see androidx.fragment.app.FragmentStatePagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position)
    {
        PaletteFragment fragment = new PaletteFragment();
        fragment.setPalette(mPalettes[mapPosition(position)]);
        return fragment;
    }


    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount()
    {
        return mPalettes.length > 1 ? mPalettes.length * FACTOR : 1;
    }


    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mPalettes[mapPosition(position)].name();
    }


    /**
     * Get the actual position from the "infinite" position.
     *
     * @param position
     *         The position in the "infinite" list of palettes.
     *
     * @return The actual position.
     */
    private int mapPosition(int position)
    {
        return position % mPalettes.length;
    }


    @Override
    public Drawable getDrawableTitle(int position)
    {
        return mCache.get(mapPosition(position));
    }


    /**
     * A cache for palette preview images.
     */
    private final class PreviewCache extends LruCache<Integer, Drawable>
    {

        public PreviewCache(int maxSize)
        {
            super(maxSize);
        }


        @Override
        protected Drawable create(Integer key)
        {
            Palette palette = mPalettes[key];
            final int size = (int) (PREVIEW_SIZE * mDensity);
            final int cols = palette.numberOfColumns();

            Bitmap preview = Bitmap.createBitmap(size, size, Config.ARGB_8888);
            Canvas canvas = new Canvas(preview);

            final float spacing = 1.2f * mDensity;
            final float halfSpacing = spacing / 2;
            final float grid = (size + spacing) / cols;
            final float radius = (grid - spacing) / 2;
            Paint paint = new Paint();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);

            for (int j = 0, k = palette.numberOfColors() / cols; j < k; ++j)
            {
                for (int i = 0, l = cols; i < l; ++i)
                {
                    paint.setColor(palette.colorAt(j * cols + i) | 0xff000000);
                    // canvas.drawRect(i * grid, j * grid, (i + 1) * grid - spacing, (j + 1) * grid - spacing, paint);
                    canvas.drawCircle((i + 0.5f) * grid - halfSpacing, (j + 0.5f) * grid - halfSpacing, radius, paint);
                }
            }

            return new BitmapDrawable(mResources, preview);
        }
    }
}
