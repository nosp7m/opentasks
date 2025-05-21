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

import android.content.Context;

import org.dmfs.android.bolts.R;
import org.dmfs.android.bolts.color.Color;

import androidx.annotation.NonNull;


/**
 * {@link Color} for <code>colorPrimaryDark</code> theme attribute.
 *
 * @author Marten Gajda
 */
public final class PrimaryDarkColor extends DelegatingColor
{
    public PrimaryDarkColor(@NonNull Context context)
    {
        super(new AttributeColor(context, R.attr.colorPrimaryDark));
    }
}
