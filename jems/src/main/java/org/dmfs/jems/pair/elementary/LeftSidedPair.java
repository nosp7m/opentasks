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

package org.dmfs.jems.pair.elementary;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.pair.Pair;

import static org.dmfs.optional.Absent.absent;


/**
 * A {@link Pair} with an absent right element.
 *
 * @author Marten Gajda
 */
public final class LeftSidedPair<Left, Right> implements Pair<Left, Optional<Right>>
{
    private final Left mLeft;


    public LeftSidedPair(Left left)
    {
        mLeft = left;
    }


    @Override
    public Left left()
    {
        return mLeft;
    }


    @Override
    public Optional<Right> right()
    {
        return absent();
    }
}
