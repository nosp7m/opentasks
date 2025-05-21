/*
 * Copyright 2020 dmfs GmbH
 *
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

package org.dmfs.jems.predicate.composite;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.predicate.Predicate;


/**
 * A {@link Predicate} which delegates the test to another {@link Predicate} testing a value derived from the original testee.
 * <p>
 * Example: Filter any Strings which are not 5 characters long.
 * <pre>{@code
 * new Sieved<>(new Having<>(String::length, new Equals<>(5)), strings);
 * }</pre>
 */
public final class Having<Original, Derived> implements Predicate<Original>
{
    private final Function<? super Original, ? extends Derived> mConversion;
    private final Predicate<? super Derived> mDelegate;


    public Having(
        Function<? super Original, ? extends Derived> conversion,
        Predicate<? super Derived> delegate)
    {
        mConversion = conversion;
        mDelegate = delegate;
    }


    @Override
    public boolean satisfiedBy(Original testedInstance)
    {
        return mDelegate.satisfiedBy(mConversion.value(testedInstance));
    }
}
