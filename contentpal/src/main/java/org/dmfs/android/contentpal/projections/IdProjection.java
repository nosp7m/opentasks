/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.projections;

import android.provider.BaseColumns;

import org.dmfs.android.contentpal.Projection;

import androidx.annotation.NonNull;


/**
 * A {@link Projection} which contains only the {@link BaseColumns#_ID} column.
 */
public final class IdProjection<T> implements Projection<T>
{
    @NonNull
    @Override
    public String[] toArray()
    {
        return new String[] { BaseColumns._ID };
    }
}
