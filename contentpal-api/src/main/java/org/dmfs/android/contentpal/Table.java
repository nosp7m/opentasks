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

import android.content.ContentProviderClient;
import android.net.Uri;

import androidx.annotation.NonNull;


/**
 * Represents a table of a content provider.
 *
 * @param <T>
 *         The contract of this table.
 *
 * @author Marten Gajda
 */
public interface Table<T>
{
    /**
     * Returns an {@link InsertOperation} to create new rows in this table.
     *
     * @param uriParams
     *         Additional parameters to add to the insert {@link Uri}.
     *
     * @return An {@link InsertOperation} for this table.
     */
    @NonNull
    InsertOperation<T> insertOperation(@NonNull UriParams uriParams);

    /**
     * Returns an {@link Operation} to update rows of this table.
     *
     * @param uriParams
     *         Additional parameters to add to the update {@link Uri}.
     * @param predicate
     *
     * @return An {@link Operation} for this table.
     */
    @NonNull
    Operation<T> updateOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate);

    /**
     * Returns an {@link Operation} to delete rows of this table.
     *
     * @param uriParams
     *         Additional parameters to add to the delete {@link Uri}.
     *
     * @return An {@link Operation} for this table.
     */
    @NonNull
    Operation<T> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate);

    /**
     * Returns an {@link Operation} to assert rows of this table.
     *
     * @param uriParams
     *         Additional parameters to add to the delete {@link Uri}.
     * @param predicate
     *
     * @return An {@link Operation} for this table.
     */
    @NonNull
    Operation<T> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super T> predicate);

    /**
     * Returns a {@link View} on this table.
     *
     * @param client
     *         A {@link ContentProviderClient}.
     *
     * @return A {@link View} on this table.
     */
    @NonNull
    View<T> view(@NonNull ContentProviderClient client);
}
