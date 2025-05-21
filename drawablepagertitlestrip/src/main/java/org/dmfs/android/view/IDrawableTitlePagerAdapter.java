/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
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
 */
package org.dmfs.android.view;

import android.graphics.drawable.Drawable;
import androidx.viewpager.widget.PagerAdapter;


/**
 * This interface must be implemented by the {@link PagerAdapter} that is passed to the {@link ViewPager}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 * 
 */
public interface IDrawableTitlePagerAdapter
{
	/**
	 * Return a {@link Drawable} for the item at the given position.
	 * 
	 * @param position
	 *            The position.
	 * @return A {@link Drawable}.
	 */
	public Drawable getDrawableTitle(int position);
}
