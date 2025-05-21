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
 * The set of rows of a specific view. Iterating over this will return {@link RowSnapshot}s of all rows that exist at that time. Note, if the database has been
 * modified between two iterations, you'll get different results.
 *
 * @author Marten Gajda
 */
public interface RowSet<T> extends Iterable<RowSnapshot<T>>
{
    @NonNull
    @Override
    ClosableIterator<RowSnapshot<T>> iterator();
}