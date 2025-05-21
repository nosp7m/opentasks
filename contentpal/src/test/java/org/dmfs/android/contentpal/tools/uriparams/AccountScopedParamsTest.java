/*
 * Copyright 2018 dmfs GmbH
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

package org.dmfs.android.contentpal.tools.uriparams;

import android.accounts.Account;
import android.net.Uri;

import org.dmfs.android.contentpal.UriParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.android.contentpal.testing.android.uri.UriBuilderMatcher.builds;
import static org.dmfs.android.contentpal.testing.android.uri.UriMatcher.hasParamSet;
import static org.dmfs.jems.hamcrest.matchers.PairMatcher.pair;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;


/**
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AccountScopedParamsTest
{
    @Test
    public void testWithParam()
    {
        Uri.Builder builder = new Uri.Builder();
        UriParams params = failingMock(UriParams.class);
        doReturn(builder).when(params).withParam(builder);
        assertThat(
                new AccountScopedParams(new Account("name", "type"), params).withParam(builder),
                builds(hasParamSet(containsInAnyOrder(pair("account_type", "type"), pair("account_name", "name")))));
    }

}