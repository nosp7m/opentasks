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

package org.dmfs.jems.mockito.doubles;

import org.dmfs.jems.mockito.answers.FailAnswer;
import org.mockito.Mockito;


/**
 * Static factory methods for creating test doubles.
 *
 * @author Gabor Keszthelyi
 */
public abstract class TestDoubles
{

    /**
     * Creates a 'dummy' object of the provided class, i.e. a test-double that is only used as a 'placeholder' instance for some argument.
     */
    public static <T> T dummy(Class<T> clazz)
    {
        return Mockito.mock(clazz, FailAnswer.INSTANCE);
    }


    /**
     * Creates a mock for the provided class that fails on every method call by default.
     */
    public static <T> T failingMock(Class<T> clazz)
    {
        return Mockito.mock(clazz, FailAnswer.INSTANCE);
    }
}
