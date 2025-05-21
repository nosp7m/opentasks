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
 * An empty {@link TransactionContext}.
 *
 * @author Marten Gajda
 */
public final class EmptyTransactionContext implements TransactionContext
{
    @NonNull
    public final static TransactionContext INSTANCE = new EmptyTransactionContext();


    @NonNull
    @Override
    public <T> RowReference<T> resolved(@NonNull SoftRowReference<T> reference)
    {
        return reference;
    }

}
