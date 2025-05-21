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

package org.dmfs.android.contentpal.tools.uriparams;

import android.net.Uri;

import org.dmfs.android.contentpal.UriParams;

import androidx.annotation.NonNull;


/**
 * {@link UriParams} to add the {@code caller_is_syncadapter} flag.
 *
 * @author Marten Gajda
 */
public final class SyncParams implements UriParams
{
    private final UriParams mDelegate;


    public SyncParams(@NonNull UriParams delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public Uri.Builder withParam(@NonNull Uri.Builder uriBuilder)
    {
        return mDelegate.withParam(uriBuilder).appendQueryParameter("caller_is_syncadapter", "true");
    }
}
