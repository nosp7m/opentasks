/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.jems.procedure.decorators;

import org.dmfs.jems.procedure.Procedure;
import org.dmfs.jems.procedure.decorators.Conditional;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.calledInOrder;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.notCalled;
import static org.dmfs.jems.hamcrest.matchers.procedure.ProcedureMatcher2.processes;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link Conditional}.
 */
public class ConditionalTest
{
    @Test
    public void test()
    {
        assertThat(delegate -> new Conditional<Integer>(x -> x > 3, delegate),
                allOf(
                        processes(
                                () -> mock(Procedure.class),
                                4,
                                is(calledInOrder(p -> p.process(4)))
                        ),
                        processes(
                                () -> mock(Procedure.class),
                                3,
                                is(notCalled())
                        )));
    }
}