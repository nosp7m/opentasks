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

package org.dmfs.jems.generatable;

import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.iterable.adapters.Infinite;


/**
 * A Generatable can return a {@link Generator}. This is the equivalent of {@link Iterable} but for infinite generators. For the sake of single responsibility
 * and for avoiding the risk of infinite loops this doesn't extent {@link Iterable}. Instead you should use an explicit {@link Iterable} adapter like {@link
 * Infinite}.
 * <p>
 * Instances of this type must be immutable.
 *
 * @author Marten Gajda
 */
public interface Generatable<T>
{
    Generator<T> generator();
}
