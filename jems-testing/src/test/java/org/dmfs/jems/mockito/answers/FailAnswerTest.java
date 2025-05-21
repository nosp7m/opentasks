/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.jems.mockito.answers;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;

import static org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher.isBroken;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link FailAnswer}.
 *
 * @author Marten Gajda
 */
public class FailAnswerTest
{
    @Test
    public void test()
    {
        assertThat(() -> new FailAnswer().answer(mock(InvocationOnMock.class, invocation -> {
            if (!"toString".equals(invocation.getMethod().getName()))
            {
                throw new RuntimeException("Unexpected call on mock object");
            }
            else
            {
                return "invocationName";
            }
        })), isBroken(AssertionError.class));
    }
}