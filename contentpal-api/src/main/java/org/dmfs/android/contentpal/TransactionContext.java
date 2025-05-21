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

package org.dmfs.android.contentpal;

import androidx.annotation.NonNull;


/**
 * Transaction context information. A {@link TransactionContext} hold information about rows which have been inserted in a {@link Transaction} or in a previous
 * {@link Transaction}.
 *
 * @author Marten Gajda
 */
public interface TransactionContext
{
    /**
     * Returns a resolved version of the given {@link SoftRowReference}, if possible. Do not store the returned reference, as it might be valid in the current
     * {@link Transaction} only.
     *
     * @param <T>
     *         The contract of the referenced table.
     * @param reference
     *         The {@link SoftRowReference} to resolve.
     *
     * @return A resolve {@link RowReference} or the same {@link SoftRowReference}.
     */
    @NonNull
    <T> RowReference<T> resolved(@NonNull SoftRowReference<T> reference);

}
