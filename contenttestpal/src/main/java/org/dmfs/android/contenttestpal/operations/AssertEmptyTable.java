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

package org.dmfs.android.contenttestpal.operations;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.operations.BulkAssert;
import org.dmfs.android.contentpal.operations.Counted;
import org.dmfs.android.contentpal.operations.DelegatingOperation;

import androidx.annotation.NonNull;


/**
 * Assert {@link Operation} to verify that the provided table is empty.
 *
 * @author Gabor Keszthelyi
 */
public final class AssertEmptyTable<T> extends DelegatingOperation<T>
{
    public AssertEmptyTable(@NonNull Table<T> table)
    {
        super(new Counted<>(0, new BulkAssert<>(table)));
    }
}
