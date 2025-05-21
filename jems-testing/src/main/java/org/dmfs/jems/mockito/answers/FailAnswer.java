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

package org.dmfs.jems.mockito.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 * An {@link Answer} which always throws an {@link AssertionError}. For some reason this doesn't seem to exist yet in Mockito.
 *
 * @author Marten Gajda
 */
public final class FailAnswer implements Answer<Object>
{
    public static final Answer<Object> INSTANCE = new FailAnswer();


    @Override
    public Object answer(InvocationOnMock invocation)
    {
        throw new AssertionError("Unexpected invocation: " + invocation);
    }
}