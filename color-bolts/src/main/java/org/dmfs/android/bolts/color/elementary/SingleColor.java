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
import org.dmfs.jems.single.Single;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


/**
 * A {@link Color} that takes a {@link Single} to delegate to.
 *
 * @author Gabor Keszthelyi
 */
public final class SingleColor implements Color
{
    private final Single<Color> mDelegate;


    public SingleColor(@NonNull Single<Color> delegate)
    {
        mDelegate = delegate;
    }


    @ColorInt
    @Override
    public int argb()
    {
        return mDelegate.value().argb();
    }
}
