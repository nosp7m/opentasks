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

package org.dmfs.android.contentpal.predicates.arguments;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Transaction;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;

import androidx.annotation.NonNull;


/**
 * An {@link Predicate.Argument} which refers to the result of an operation creating a row earlier in the same {@link Transaction}.
 *
 * @author Marten Gajda
 */
public final class BackReferenceArgument implements Predicate.Argument
{
    private final int mBackReference;


    public BackReferenceArgument(int backReference)
    {
        mBackReference = backReference;
    }


    @NonNull
    @Override
    public String value()
    {
        return "-1";
    }


    @NonNull
    @Override
    public Optional<Integer> backReference()
    {
        return new Present<>(mBackReference);
    }
}
