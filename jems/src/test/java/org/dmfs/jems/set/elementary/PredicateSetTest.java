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

package org.dmfs.jems.set.elementary;

import org.junit.Test;

import static org.dmfs.jems.hamcrest.matchers.set.SetContainsMatcher.contains;
import static org.dmfs.jems.hamcrest.matchers.set.SetLacksMatcher.lacks;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link PredicateSet}.
 *
 * @author Marten Gajda
 */
public class PredicateSetTest
{

    @Test
    public void test()
    {
        assertThat(new PredicateSet<>(i -> i > 3),
                allOf(
                        contains(4, 5, 6),
                        lacks(1, 2, 3)));
    }
}