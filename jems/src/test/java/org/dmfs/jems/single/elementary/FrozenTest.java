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

package org.dmfs.jems.single.elementary;

import org.dmfs.jems.single.Single;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.SingleMatcher.hasValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link Frozen}.
 *
 * @author Gabor Keszthelyi
 */
public final class FrozenTest
{
    @Test
    public void test()
    {
        Object firstValue = new Object();

        Single<Object> frozen = new Frozen<>(new ChangingValueSingle(firstValue));

        assertThat(frozen, hasValue(sameInstance(firstValue)));
        assertThat(frozen, hasValue(sameInstance(firstValue)));
        assertThat(frozen, hasValue(sameInstance(firstValue)));
    }


    private static class ChangingValueSingle implements Single<Object>
    {
        private final Object mFirstValue;

        private boolean mFirst = true;


        private ChangingValueSingle(Object firstValue)
        {
            mFirstValue = firstValue;
        }


        @Override
        public Object value()
        {
            if (mFirst)
            {
                mFirst = false;
                return mFirstValue;
            }
            throw new RuntimeException("Shouldn't be called");
        }
    }

}