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

package org.dmfs.android.contentpal.transactions.contexts;

import org.dmfs.android.contentpal.RowReference;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.TransactionContext;

import androidx.annotation.NonNull;


/**
 * A {@link TransactionContext} which takes a shortcut if a reference is not virtual.
 * <p>
 * A normal {@link TransactionContext} implementation tries to resolve all {@link SoftRowReference}s, which doesn't make sense for non-virtual {@link
 * SoftRowReference}s. This decorator only delegates the call if the reference needs resolution.
 *
 * @author Marten Gajda
 */
public final class Quick implements TransactionContext
{
    private final TransactionContext mDelegate;


    public Quick(@NonNull TransactionContext delegate)
    {
        mDelegate = delegate;
    }


    @NonNull
    @Override
    public <T> RowReference<T> resolved(@NonNull SoftRowReference<T> reference)
    {
        // if a reference is not virtual, there is nothing to resolve
        return reference.isVirtual() ? mDelegate.resolved(reference) : reference;
    }

}
