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

package org.dmfs.jems.comparator.decorators;

import org.junit.Test;

import java.util.Comparator;

import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link By}.
 *
 * @author Marten Gajda
 */
public class ByTest
{
    @Test
    public void test()
    {
        assertThat(new By<String>(Integer::parseInt), having(b -> b.compare("50", "50"), equalTo(0)));
        assertThat(new By<String>(Integer::parseInt), having(b -> b.compare("9", "50"), lessThan(0)));
        assertThat(new By<String>(Integer::parseInt), having(b -> b.compare("50", "9"), greaterThan(0)));

        assertThat(new By<String>(Integer::parseInt, Comparator.reverseOrder()), having(b -> b.compare("50", "50"), equalTo(0)));
        assertThat(new By<String>(Integer::parseInt, Comparator.reverseOrder()), having(b -> b.compare("9", "50"), greaterThan(0)));
        assertThat(new By<String>(Integer::parseInt, Comparator.reverseOrder()), having(b -> b.compare("50", "9"), lessThan(0)));
    }
}