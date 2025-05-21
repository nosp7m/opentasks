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

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.net.Uri;

import org.dmfs.android.contentpal.InsertOperation;
import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.Predicate;
import org.dmfs.android.contentpal.SoftRowReference;
import org.dmfs.android.contentpal.Table;
import org.dmfs.android.contentpal.TransactionContext;
import org.dmfs.android.contentpal.UriParams;
import org.dmfs.android.contentpal.View;
import org.dmfs.jems.generator.Generator;
import org.dmfs.jems.optional.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.annotation.NonNull;

import static org.dmfs.android.contentpal.testing.table.TableMatcher.table;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.matches;
import static org.dmfs.jems.hamcrest.matchers.matcher.MatcherMatcher.mismatches;
import static org.dmfs.jems.optional.elementary.Absent.absent;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TableMatcherTest
{
    @Test
    public void test()
    {
        assertThat(table(Uri.parse("content://xyz")),
            allOf(
                matches(new MockTable(Uri.parse("content://xyz"))),
                mismatches(new MockTable(Uri.parse("content://abc")),
                    "insert (is a INSERT operation and Operation target is <content://xyz>)" +
                        " insert Operation target is <content://xyz> Operation target was <content://abc>")
            ));
    }


    private static final class MockTable implements Table<Object>
    {

        private final Uri mUri;


        private MockTable(Uri uri)
        {
            mUri = uri;
        }


        @NonNull
        @Override
        public InsertOperation<Object> insertOperation(@NonNull UriParams uriParams)
        {
            return new MockOperation(() -> ContentProviderOperation.newInsert(mUri));
        }


        @NonNull
        @Override
        public Operation<Object> updateOperation(
            @NonNull UriParams uriParams, @NonNull Predicate<? super Object> predicate)
        {
            return new MockOperation(() -> ContentProviderOperation.newUpdate(mUri));

        }


        @NonNull
        @Override
        public Operation<Object> deleteOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super Object> predicate)
        {
            return new MockOperation(() -> ContentProviderOperation.newDelete(mUri));
        }


        @NonNull
        @Override
        public Operation<Object> assertOperation(@NonNull UriParams uriParams, @NonNull Predicate<? super Object> predicate)
        {
            return new MockOperation(() -> ContentProviderOperation.newAssertQuery(mUri));
        }


        @NonNull
        @Override
        public View<Object> view(@NonNull ContentProviderClient client)
        {
            fail();
            return null;
        }


        @Override
        public String toString()
        {
            return "MockTable";
        }
    }


    private static final class MockOperation implements InsertOperation<Object>, Operation<Object>
    {

        private final Generator<? extends ContentProviderOperation.Builder> mBuilderGenerator;


        private MockOperation(Generator<? extends ContentProviderOperation.Builder> builderGenerator)
        {
            mBuilderGenerator = builderGenerator;
        }


        @NonNull
        @Override
        public Optional<SoftRowReference<Object>> reference()
        {
            return absent();
        }


        @NonNull
        @Override
        public ContentProviderOperation.Builder contentOperationBuilder(@NonNull TransactionContext transactionContext) throws UnsupportedOperationException
        {
            return mBuilderGenerator.next();
        }
    }
}