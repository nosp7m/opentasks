/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file has been changed from the original.
 */

package org.dmfs.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTitleStrip;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * PagerTabStrip is an interactive indicator of the current, next, and previous pages of a {@link ViewPager}. It is intended to be used as a child view of a
 * ViewPager widget in your XML layout. Add it as a child of a ViewPager in your layout file and set its android:layout_gravity to TOP or BOTTOM to pin it to
 * the top or bottom of the ViewPager. The title from each page is supplied by the method {@link PagerAdapter#getPageTitle(int)} in the adapter supplied to the
 * ViewPager.
 * 
 * <p>
 * For a non-interactive indicator, see {@link PagerTitleStrip}.
 * </p>
 */
public class DrawablePagerTabStrip extends DrawablePagerTitleStrip
{
	private static final String TAG = "PagerTabStrip";

	private static final int INDICATOR_HEIGHT = 3; // dp
	private static final int MIN_PADDING_BOTTOM = INDICATOR_HEIGHT + 3; // dp
	private static final int TAB_PADDING = 0; // dp
	private static final int TAB_SPACING = 0; // dp
	private static final int MIN_TEXT_SPACING = TAB_SPACING + TAB_PADDING * 2; // dp
	private static final int FULL_UNDERLINE_HEIGHT = 1; // dp
	private static final int MIN_STRIP_HEIGHT = 32; // dp

	private int mIndicatorColor;
	private int mIndicatorHeight;

	private int mMinPaddingBottom;
	private int mMinTextSpacing;
	private int mMinStripHeight;

	private int mTabPadding;

	private final Paint mTabPaint = new Paint();
	private final Rect mTempRect = new Rect();

	private int mTabAlpha = 0xff;

	private boolean mDrawFullUnderline = false;
	private boolean mDrawFullUnderlineSet = false;
	private int mFullUnderlineHeight;

	private boolean mIgnoreTap;
	private float mInitialMotionX;
	private float mInitialMotionY;
	private int mTouchSlop;


	public DrawablePagerTabStrip(Context context)
	{
		this(context, null);
	}


	public DrawablePagerTabStrip(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mIndicatorColor = 0; // TODO: allow to set color
		mTabPaint.setColor(mIndicatorColor);

		// Note: this follows the rules for Resources#getDimensionPixelOffset/Size:
		// sizes round up, offsets round down.
		final float density = context.getResources().getDisplayMetrics().density;
		mIndicatorHeight = (int) (INDICATOR_HEIGHT * density + 0.5f);
		mMinPaddingBottom = (int) (MIN_PADDING_BOTTOM * density + 0.5f);
		mMinTextSpacing = (int) (MIN_TEXT_SPACING * density);
		mTabPadding = (int) (TAB_PADDING * density + 0.5f);
		mFullUnderlineHeight = (int) (FULL_UNDERLINE_HEIGHT * density + 0.5f);
		mMinStripHeight = (int) (MIN_STRIP_HEIGHT * density + 0.5f);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		// Enforce restrictions
		setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
		setTextSpacing(getTextSpacing());

		setWillNotDraw(false);

		if (getBackground() == null)
		{
			mDrawFullUnderline = true;
		}
	}


	/**
	 * Set the color of the tab indicator bar.
	 * 
	 * @param color
	 *            Color to set as an 0xRRGGBB value. The high byte (alpha) is ignored.
	 */
	public void setTabIndicatorColor(int color)
	{
		mIndicatorColor = color;
		mTabPaint.setColor(mIndicatorColor);
		invalidate();
	}


	/**
	 * Set the color of the tab indicator bar from a color resource.
	 * 
	 * @param resId
	 *            Resource ID of a color resource to load
	 */
	public void setTabIndicatorColorResource(int resId)
	{
		setTabIndicatorColor(getContext().getResources().getColor(resId));
	}


	/**
	 * @return The current tab indicator color as an 0xRRGGBB value.
	 */
	public int getTabIndicatorColor()
	{
		return mIndicatorColor;
	}


	@Override
	public void setPadding(int left, int top, int right, int bottom)
	{
		if (bottom < mMinPaddingBottom)
		{
			bottom = mMinPaddingBottom;
		}
		super.setPadding(left, top, right, bottom);
	}


	@Override
	public void setTextSpacing(int textSpacing)
	{
		if (textSpacing < mMinTextSpacing)
		{
			textSpacing = mMinTextSpacing;
		}
		super.setTextSpacing(textSpacing);
	}


	@Override
	public void setBackgroundDrawable(Drawable d)
	{
		super.setBackgroundDrawable(d);
		if (!mDrawFullUnderlineSet)
		{
			mDrawFullUnderline = d == null;
		}
	}


	@Override
	public void setBackgroundColor(int color)
	{
		super.setBackgroundColor(color);
		if (!mDrawFullUnderlineSet)
		{
			mDrawFullUnderline = (color & 0xFF000000) == 0;
		}
	}


	@Override
	public void setBackgroundResource(int resId)
	{
		super.setBackgroundResource(resId);
		if (!mDrawFullUnderlineSet)
		{
			mDrawFullUnderline = resId == 0;
		}
	}


	/**
	 * Set whether this tab strip should draw a full-width underline in the current tab indicator color.
	 * 
	 * @param drawFull
	 *            true to draw a full-width underline, false otherwise
	 */
	public void setDrawFullUnderline(boolean drawFull)
	{
		mDrawFullUnderline = drawFull;
		mDrawFullUnderlineSet = true;
		invalidate();
	}


	/**
	 * Return whether or not this tab strip will draw a full-width underline. This defaults to true if no background is set.
	 * 
	 * @return true if this tab strip will draw a full-width underline in the current tab indicator color.
	 */
	public boolean getDrawFullUnderline()
	{
		return mDrawFullUnderline;
	}


	@Override
	int getMinHeight()
	{
		return Math.max(super.getMinHeight(), mMinStripHeight);
	}


	// @Override
	// public boolean onTouchEvent(MotionEvent ev)
	// {
	// final int action = ev.getAction();
	// if (action != MotionEvent.ACTION_DOWN && mIgnoreTap)
	// {
	// return false;
	// }
	//
	// // Any tap within touch slop to either side of the current item
	// // will scroll to prev/next.
	// final float x = ev.getX();
	// final float y = ev.getY();
	// switch (action)
	// {
	// case MotionEvent.ACTION_DOWN:
	// mInitialMotionX = x;
	// mInitialMotionY = y;
	// mIgnoreTap = false;
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	// if (Math.abs(x - mInitialMotionX) > mTouchSlop || Math.abs(y - mInitialMotionY) > mTouchSlop)
	// {
	// mIgnoreTap = true;
	// }
	// break;
	//
	// case MotionEvent.ACTION_UP:
	//
	// if (x < mImageViews[mImageViews.length / 2].getLeft() - mTabPadding)
	// {
	// mPager.setCurrentItem(mPager.getCurrentItem() - 1);
	// }
	// else if (x > mImageViews[mImageViews.length / 2].getRight() + mTabPadding)
	// {
	// mPager.setCurrentItem(mPager.getCurrentItem() + 1);
	// }
	//
	// break;
	// }
	//
	// return true;
	// }

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		final int height = getHeight();
		final int bottom = height;
		final int left = mImageViews[mImageViews.length / 2].getLeft() - mTabPadding;
		final int right = mImageViews[mImageViews.length / 2].getRight() + mTabPadding;
		final int top = bottom - mIndicatorHeight;

		mTabPaint.setColor(mTabAlpha << 24 | (mIndicatorColor & 0xFFFFFF));
		canvas.drawRect(left, top, right, bottom, mTabPaint);
	}


	@Override
	void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter)
	{
		super.updateAdapter(oldAdapter, newAdapter);

		if (mImageViews != null)
		{
			for (int i = 0, l = mImageViews.length; i < l; ++i)
			{
				if (i == l / 2)
				{
					continue;
				}
				final int x = i - l / 2;

				mImageViews[i].setFocusable(true);
				mImageViews[i].setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						mPager.setCurrentItem(mPager.getCurrentItem() + x);
					}
				});
			}
		}
	}
}
