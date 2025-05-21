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

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link ValuePair}.
 *
 * @author Gabor Keszthelyi
 */
public final class ValuePairTest
{
    @Test
    public void testThatSameInstancesAreReturned()
    {
        String left = new String("a");
        Integer right = new Integer(1);

        assertThat(new ValuePair<>(left, right), pair(sameInstance(left), sameInstance(right)));
    }

}