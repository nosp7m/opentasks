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

package org.dmfs.jems.procedure.composite;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.procedure.Procedure;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.calledInOrder;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.notCalled;
import static org.dmfs.jems.hamcrest.matchers.procedure.ProcedureMatcher2.processes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link Batch}.
 *
 * @author Marten Gajda
 */
public class BatchTest
{

    @Test
    public void test()
    {
        assertThat(Batch::new,
                processes(
                        () -> mock(Procedure.class),
                        new EmptyIterable<>(),
                        is(notCalled())
                ));

        assertThat(Batch::new,
                processes(
                        () -> mock(Procedure.class),
                        new Seq<>("x"),
                        is(calledInOrder(
                                p -> p.process("x")))));

        assertThat(Batch::new,
                processes(
                        () -> mock(Procedure.class),
                        new Seq<>("x", "y", "z"),
                        is(calledInOrder(
                                p -> p.process("x"),
                                p -> p.process("y"),
                                p -> p.process("z")))));
    }
}