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

package org.dmfs.jems.procedure.composite;

import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.procedure.Procedure;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.dmfs.jems.hamcrest.matchers.mockito.MockInteractionMatcher.calledInOrder;
import static org.dmfs.jems.hamcrest.matchers.procedure.ProcedureMatcher2.processes;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * Unit test for {@link Composite}.
 */
public class CompositeTest
{
    @Test
    public void testIterableCtor()
    {
        assertThat(delegates -> new Composite<>(delegates),
                allOf(
                        processes(
                                () -> new Seq<Procedure<String>>(),
                                "x",
                                is(emptyIterable())),
                        processes(
                                () -> new Seq<Procedure<String>>(mock(Procedure.class)),
                                "x",
                                iteratesTo(
                                        is(calledInOrder(p -> p.process("x"))))),
                        processes(
                                () -> new Seq<Procedure<String>>(mock(Procedure.class), mock(Procedure.class)),
                                "x",
                                iteratesTo(
                                        is(calledInOrder(p -> p.process("x"))),
                                        is(calledInOrder(p -> p.process("x")))))));
    }


    @Test
    public void testVarargCtor()
    {
        assertThat(delegates -> new Composite<>(delegates),
                allOf(
                        processes(
                                () -> new Procedure[0],
                                "x",
                                is(emptyArray())),
                        processes(
                                () -> new Procedure[] { mock(Procedure.class) },
                                "x",
                                arrayContaining(
                                        is(calledInOrder(p -> p.process("x"))))),
                        processes(
                                () -> new Procedure[] { mock(Procedure.class), mock(Procedure.class) },
                                "x",
                                arrayContaining(
                                        is(calledInOrder(p -> p.process("x"))),
                                        is(calledInOrder(p -> p.process("x")))))));
    }
}