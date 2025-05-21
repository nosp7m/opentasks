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

import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.Optional;

import androidx.annotation.NonNull;


/**
 * The data of a {@link RowSnapshot}. Iterating over a {@link RowDataSnapshot} returns all keys, including the ones without a value.
 * <p>
 * Note that at most one of {@link #data(String, Function)} and {@link #byteData(String)} will return a present value. Though, if the respective column doesn't
 * contain a value, both methods will return an absent {@link Optional}.
 *
 * @param <Contract>
 *         The contract of the view the data of this snapshot belongs to.
 *
 * @author Marten Gajda
 */
public interface RowDataSnapshot<Contract> extends Iterable<String>
{
    /**
     * Returns any value assigned to the given key.
     * <p>
     * If there is no value, it returns absent, otherwise it uses the given <code>mapFunction</code> to map from the stored String value to
     * <code>ValueType</code>.
     */
    @NonNull
    <ValueType> Optional<ValueType> data(@NonNull String key, @NonNull Function<String, ValueType> mapFunction);

    /**
     * Returns any byte array assigned to the given key.
     */
    @NonNull
    Optional<byte[]> byteData(@NonNull String key);
}
