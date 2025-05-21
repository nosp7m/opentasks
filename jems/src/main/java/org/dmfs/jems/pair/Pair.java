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

package org.dmfs.jems.pair;

/**
 * A pair of typed values, a tuple of two (also called ordered pair).
 *
 * @author Gabor Keszthelyi
 */
public interface Pair<Left, Right>
{

    /**
     * The left value of the pair, i.e. the 1st value in this 2-tuple.
     */
    Left left();

    /**
     * The right value of the pair, i.e. the 2nd value in this 2-tuple.
     */
    Right right();

}
