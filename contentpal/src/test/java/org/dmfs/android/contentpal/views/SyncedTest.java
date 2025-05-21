/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.android.contentpal.views;

import android.accounts.Account;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.Projection;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.android.contentpal.tools.uriparams.EmptyUriParams;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.dmfs.jems.mockito.doubles.TestDoubles.dummy;
import static org.dmfs.jems.mockito.doubles.TestDoubles.failingMock;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;


/**
 * Test @{@link Synced}.
 *
 * @author Marten Gajda
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SyncedTest
{
    @Test
    public void test() throws RemoteException
    {
        Cursor dummyResult = dummy(Cursor.class);
        Predicate<Object> dummyPredicate = dummy(Predicate.class);
        Projection<Object> dummyProjection = dummy(Projection.class);
        View<Object> mockView = failingMock(View.class);
        Account account = new Account("name", "type");
        doReturn(dummyResult).when(mockView)
                .rows(argThat(new UriParamsArgumentMatcher(account)), same(dummyProjection), same(dummyPredicate), any(Optional.class));

        assertThat(new Synced<>(account, mockView).rows(new EmptyUriParams(), dummyProjection, dummyPredicate, new Present<>("sorting")),
                sameInstance(dummyResult));
    }


    private static class UriParamsArgumentMatcher implements ArgumentMatcher<UriParams>
    {
        private final Account mAccount;


        private UriParamsArgumentMatcher(Account account)
        {
            mAccount = account;
        }


        @Override
        public boolean matches(UriParams argument)
        {
            Uri uri = argument.withParam(new Uri.Builder()).build();
            return uri.getBooleanQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, false) &&
                    mAccount.equals(new Account(uri.getQueryParameter("account_name"), uri.getQueryParameter("account_type")));
        }
    }
}