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

package org.dmfs.android.bolts.color.colors;

import org.dmfs.android.bolts.color.Color;

import androidx.annotation.NonNull;


/**
 * A {@link Color} which delegates to another {@link Color}.
 *
 * @author Marten Gajda
 * @deprecated use {@link org.dmfs.android.bolts.color.elementary.DelegatingColor} instead
 */
@Deprecated
public abstract class DelegatingColor implements Color
{
    private final Color mDelegate;


    public DelegatingColor(@NonNull Color delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public final int argb()
    {
        return mDelegate.argb();
    }
}
