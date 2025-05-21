/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.iterables.composite;

import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.function.elementary.PairingFunction;
import org.dmfs.jems.pair.Pair;


/**
 * An {@link Iterable} combining the elements of two given {@link Iterable}s into an {@link Iterable} of {@link Pair}s.
 * <p>
 * This iterates as many elements as the shorter of both {@link Iterable}s.
 *
 * @author Gabor Keszthelyi
 */
public final class PairZipped<Left, Right> extends DelegatingIterable<Pair<Left, Right>>
{

    public PairZipped(Iterable<Left> leftIterable, Iterable<Right> rightIterable)
    {
        super(new Zipped<>(leftIterable, rightIterable, PairingFunction.<Left, Right>instance()));
    }

}
