/*
 * Copyright 2021 dmfs GmbH
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

package org.dmfs.android.contentpal.testing.table;

import android.net.Uri;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.testing.predicates.Mocked;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.iterable.elementary.Seq;
import org.hamcrest.Matcher;

import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.assertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.deleteOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.insertOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.OperationType.updateOperation;
import static org.dmfs.android.contentpal.testing.contentoperationbuilder.TargetMatcher.targets;
import static org.dmfs.android.contentpal.testing.operations.OperationMatcher.builds;
import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.hamcrest.Matchers.allOf;


public final class TableMatcher
{
    @SafeVarargs
    public static <Contract> Matcher<Table<Contract>> table(Uri contentUri, Matcher<? super Table<Contract>>... tableMatcher)
    {
        return allOf(
            new Joined<>(
                new Seq<>(
                    having("insert", table -> table.insertOperation(uriBuilder -> uriBuilder), builds(insertOperation(), targets(contentUri))),
                    having("update",
                        table -> table.updateOperation(uriBuilder -> uriBuilder, new Mocked<>("")),
                        builds(updateOperation(), targets(contentUri))),
                    having("delete",
                        table -> table.deleteOperation(uriBuilder -> uriBuilder, new Mocked<>("")),
                        builds(deleteOperation(), targets(contentUri))),
                    having("assert",
                        table -> table.assertOperation(uriBuilder -> uriBuilder, new Mocked<>("")),
                        builds(assertOperation(), targets(contentUri)))),
                new Seq<>(tableMatcher)));
    }


    public static <Contract> Matcher<Table<Contract>> inserts(Matcher<? super Operation<Contract>> operationMatcher)
    {
        return having(table -> table.insertOperation(uriBuilder -> uriBuilder), allOf(operationMatcher, builds(insertOperation())));
    }


    public static <Contract> Matcher<Table<Contract>> updates(Matcher<? super Operation<Contract>> operationMatcher)
    {
        return having(table -> table.updateOperation(uriBuilder -> uriBuilder, new Mocked<>("")), allOf(operationMatcher, builds(updateOperation())));
    }


    public static <Contract> Matcher<Table<Contract>> deletes(Matcher<? super Operation<Contract>> operationMatcher)
    {
        return having(table -> table.deleteOperation(uriBuilder -> uriBuilder, new Mocked<>("")), allOf(operationMatcher, builds(deleteOperation())));
    }


    public static <Contract> Matcher<Table<Contract>> asserts(Matcher<? super Operation<Contract>> operationMatcher)
    {
        return having(table -> table.assertOperation(uriBuilder -> uriBuilder, new Mocked<>("")), allOf(operationMatcher, builds(assertOperation())));
    }

}
