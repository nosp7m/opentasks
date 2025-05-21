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

import androidx.annotation.NonNull;


/**
 * A predicate which matches a specific account. Only to be used with tables which have {@code account_name} and {@code account_type} columns;
 *
 * @author Marten Gajda
 */
public final class AccountEq<Contract> extends DelegatingPredicate<Contract>
{

    public AccountEq(@NonNull Account account)
    {
        super(new AllOf<>(new EqArg<>("account_name", account.name), new EqArg<>("account_type", account.type)));
    }

}
