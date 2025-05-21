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

import org.dmfs.android.contentpal.Projection;

import androidx.annotation.NonNull;


/**
 * A {@link Projection} which delegates all method calls to another {@link Projection}.
 *
 * @author Marten Gajda
 */
public abstract class DelegatingProjection<T> implements Projection<T>
{
    private final Projection<T> mDelegate;


    public DelegatingProjection(@NonNull Projection<T> delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public final String[] toArray()
    {
        return mDelegate.toArray();
    }
}
