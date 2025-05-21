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

package org.dmfs.jems.function.decorators;

import org.dmfs.jems.function.elementary.IdentityFunction;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test for {@link Clamping}
 *
 * @author Gabor Keszthelyi
 */
public final class ClampingTest
{
    @Test
    public void test()
    {
        assertThat(new Clamping<>(0, 1, new IdentityFunction<Integer>()).value(-1), is(0));
        assertThat(new Clamping<>(0, 1, new IdentityFunction<Integer>()).value(0), is(0));
        assertThat(new Clamping<>(0f, 1f, new IdentityFunction<Float>()).value(0.5f), is(0.5f));
        assertThat(new Clamping<>(0f, 1f, new IdentityFunction<Float>()).value(1f), is(1f));
        assertThat(new Clamping<>(0f, 1f, new IdentityFunction<Float>()).value(2f), is(1f));

        assertThat(new Clamping<>(0, 1).value(-1), is(0));
        assertThat(new Clamping<>(0, 1).value(0), is(0));
        assertThat(new Clamping<>(0f, 1f).value(0.5f), is(0.5f));
        assertThat(new Clamping<>(0f, 1f).value(1f), is(1f));
        assertThat(new Clamping<>(0f, 1f).value(2f), is(1f));

        assertThat(new Clamping<>("b", "d").value("a"), is("b"));
        assertThat(new Clamping<>("b", "d").value("e"), is("d"));
    }

}