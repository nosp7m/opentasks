/*
 * Copyright 2017 SchedJoules
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


/**
 * {@link Color} for {@link android.graphics.Color#WHITE}.
 *
 * @author Gabor Keszthelyi
 */
public final class White implements Color
{
    public static final Color INSTANCE = new White();


    @Override
    public int argb()
    {
        return android.graphics.Color.WHITE;
    }
}
