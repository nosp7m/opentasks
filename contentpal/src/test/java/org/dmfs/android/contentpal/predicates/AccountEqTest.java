/*
 * Copyright 2017 dmfs GmbH
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

package org.dmfs.android.contentpal.predicates;

import android.accounts.Account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.absentBackReferences;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.argumentValues;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.predicateWith;
import static org.dmfs.android.contentpal.testing.predicates.PredicateMatcher.selection;
import static org.junit.Assert.assertThat;


/**
 * Unit test for {@link AccountEq}.
 *
 * @author Gabor Keszthelyi
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public final class AccountEqTest
{

    @Test
    public void test()
    {
        assertThat(new AccountEq<>(new Account("name", "type")),
                predicateWith(
                        selection("( account_name = ? ) and ( account_type = ? )"),
                        argumentValues("name", "type"),
                        absentBackReferences(2)
                ));
    }

}