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

import org.dmfs.jems.pair.Pair;


/**
 * The most simple {@link Pair} there is. It takes two values and returns them on request.
 *
 * @author Gabor Keszthelyi
 */
public final class ValuePair<Left, Right> implements Pair<Left, Right>
{
    private final Left mLeft;
    private final Right mRight;


    public ValuePair(Left left, Right right)
    {
        mLeft = left;
        mRight = right;
    }


    @Override
    public Left left()
    {
        return mLeft;
    }


    @Override
    public Right right()
    {
        return mRight;
    }
}
