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

package org.dmfs.jems.single.decorators;

import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.ValueSingle;
import org.junit.Test;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;


/**
 * @author Marten Gajda
 */
public class DelegatingSingleTest
{
    @Test
    public void testValue() throws Exception
    {
        Object mockValue = new Object();
        assertThat(new TestDelegatingSingle<>(new ValueSingle<>(mockValue)).value(), sameInstance(mockValue));
    }


    private final class TestDelegatingSingle<T> extends DelegatingSingle<T>
    {
        public TestDelegatingSingle(Single<T> delegate)
        {
            super(delegate);
        }
    }
}