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

package org.dmfs.jems.iterable.elementary;

import org.dmfs.jems.stack.decorators.Topped;
import org.dmfs.jems.stack.elementary.EmptyStack;
import org.dmfs.jems.stack.elementary.SingleStack;
import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.IterableMatcher.iteratesTo;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;


/**
 * @author marten
 */
public class StackIterableTest
{
    @Test
    public void test() throws Exception
    {
        assertThat(new StackIterable<>(new EmptyStack<>()), emptyIterable());
        assertThat(new StackIterable<>(new SingleStack<>("1")), iteratesTo("1"));
        assertThat(new StackIterable<>(new Topped<>("2", new SingleStack<>("1"))), iteratesTo("2", "1"));
        assertThat(new StackIterable<>(new Topped<>("3", new Topped<>("2", new SingleStack<>("1")))), iteratesTo("3", "2", "1"));
    }

}