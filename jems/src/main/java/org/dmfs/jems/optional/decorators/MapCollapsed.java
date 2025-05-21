/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.jems.optional.decorators;

import org.dmfs.jems.function.Function;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.adapters.Collapsed;
import org.dmfs.optional.decorators.DelegatingOptional;


/**
 * {@link Optional} decorator maps the given {@link Optional} to an {@link Optional} and collapses the result.
 *
 * @author Gabor Keszthelyi
 */
public final class MapCollapsed<From, To> extends DelegatingOptional<To>
{
    public MapCollapsed(Function<From, Optional<To>> mapFunction, Optional<From> original)
    {
        super(new Collapsed<>(new Mapped<>(mapFunction, original)));
    }

}
