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

package org.dmfs.iterators.composite;

import org.dmfs.iterators.decorators.DelegatingIterator;
import org.dmfs.jems.function.elementary.PairingFunction;
import org.dmfs.jems.pair.Pair;

import java.util.Iterator;


/**
 * An {@link Iterator} combining the elements of two given {@link Iterator}s into an {@link Iterator} of {@link Pair}s.
 * <p>
 * This iterates as many elements as the shorter of both {@link Iterator}s.
 *
 * @author Gabor Keszthelyi
 */
public final class PairZipped<Left, Right> extends DelegatingIterator<Pair<Left, Right>>
{

    public PairZipped(Iterator<? extends Left> leftIterator, Iterator<? extends Right> rightIterator)
    {
        super(new Zipped<>(leftIterator, rightIterator, PairingFunction.instance()));
    }

}
