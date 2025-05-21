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

package org.dmfs.jems.procedure.decorators;

import org.dmfs.jems.predicate.Predicate;
import org.dmfs.jems.procedure.Procedure;


/**
 * A {@link Procedure} which only executes if the argument satisfies a {@link Predicate}.
 *
 * @author Marten Gajda
 */
public final class Conditional<T> implements Procedure<T>
{
    private final Predicate<? super T> mPredicate;
    private final Procedure<T> mDelegate;


    public Conditional(Predicate<? super T> predicate, Procedure<T> delegate)
    {
        mPredicate = predicate;
        mDelegate = delegate;
    }


    @Override
    public void process(T argument)
    {
        if (mPredicate.satisfiedBy(argument))
        {
            mDelegate.process(argument);
        }
    }
}
