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

package org.dmfs.jems.generator.elementary;

import org.dmfs.jems.generator.Generator;


/**
 * An abstract {@link Generator} which delegates to another {@link Generator}.
 */
public abstract class DelegatingGenerator<Type> implements Generator<Type>
{
    private final Generator<Type> mDelegate;


    public DelegatingGenerator(Generator<Type> mDelegate) {this.mDelegate = mDelegate;}


    @Override
    public final Type next()
    {
        return mDelegate.next();
    }
}
