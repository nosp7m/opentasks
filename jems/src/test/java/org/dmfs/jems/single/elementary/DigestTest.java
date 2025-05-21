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

package org.dmfs.jems.single.elementary;

import org.dmfs.jems.generator.composite.Md5;
import org.dmfs.jems.iterable.elementary.Seq;
import org.dmfs.jems.single.Single;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.dmfs.jems.hamcrest.matchers.BrokenFragileMatcher.throwing;
import static org.dmfs.jems.hamcrest.matchers.LambdaMatcher.having;
import static org.dmfs.jems.hamcrest.matchers.SingleMatcher.hasValue;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.causedBy;
import static org.dmfs.jems.hamcrest.matchers.throwable.ThrowableMatcher.throwable;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class DigestTest
{
    @Test
    public void value() throws Exception
    {
        assertThat(
            new Digest(new Md5(), new byte[0]),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new byte[0], new byte[0]),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new byte[0], new byte[0], new byte[0]),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new byte[] { 1, 2, 3 }),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3 })));
        assertThat(
            new Digest(new Md5(), new byte[] { 1, 2, 3 }, new byte[] { 4, 5, 6 }),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(
            new Digest(new Md5(), new byte[] { 1, 2, 3 }, new byte[] { 4, 5, 6 }, new byte[] { 7, 8, 9 }),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));

        assertThat(
            new Digest(new Md5(), (CharSequence) ""),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), (CharSequence) "", ""),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), (CharSequence) "", "", ""),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), (CharSequence) "1äöü"),
            hasValue(new Md5().next().digest("1äöü".getBytes("UTF-8"))));
        assertThat(
            new Digest(new Md5(), (CharSequence) "1äöü", "456"),
            hasValue(new Md5().next().digest("1äöü456".getBytes("UTF-8"))));
        assertThat(
            new Digest(new Md5(), (CharSequence) "1äöü", "456", "789"),
            hasValue(new Md5().next().digest("1äöü456789".getBytes("UTF-8"))));

        assertThat(
            new Digest(new Md5(), "latin1", (CharSequence) ""),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), "latin1", (CharSequence) "1äöü"),
            hasValue(new Md5().next().digest("1äöü".getBytes("latin1"))));
        assertThat(
            new Digest(new Md5(), "latin1", (CharSequence) "1äöü", "456"),
            hasValue(new Md5().next().digest("1äöü456".getBytes("latin1"))));
        assertThat(
            new Digest(new Md5(), "latin1", (CharSequence) "1äöü", "456", "789"),
            hasValue(new Md5().next().digest("1äöü456789".getBytes("latin1"))));

        assertThat(
            new Digest(new Md5(), new ValueSingle<>(new byte[0])),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0])),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0])),
            hasValue(new Md5().next().digest()));

        assertThat(
            new Digest(new Md5(), new ValueSingle<>(new byte[] { 1, 2, 3 })),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3 })));
        assertThat(
            new Digest(new Md5(),
                new ValueSingle<>(new byte[] { 1, 2, 3 }),
                new ValueSingle<>(new byte[] { 4, 5, 6 })),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(
            new Digest(new Md5(),
                new ValueSingle<>(new byte[] { 1, 2, 3 }),
                new ValueSingle<>(new byte[] { 4, 5, 6 }),
                new ValueSingle<>(new byte[] { 7, 8, 9 })),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));

        assertThat(
            new Digest(new Md5(), new Seq<>(new ValueSingle<>(new byte[0]))),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new Seq<>(new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]))),
            hasValue(new Md5().next().digest()));
        assertThat(
            new Digest(new Md5(), new Seq<>(new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]))),
            hasValue(new Md5().next().digest()));

        assertThat(
            new Digest(new Md5(), new Seq<>(new ValueSingle<>(new byte[] { 1, 2, 3 }))),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3 })));
        assertThat(
            new Digest(new Md5(),
                new Seq<>(
                    new ValueSingle<>(new byte[] { 1, 2, 3 }),
                    new ValueSingle<>(new byte[] { 4, 5, 6 }))),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(
            new Digest(new Md5(),
                new Seq<>(
                    new ValueSingle<>(new byte[] { 1, 2, 3 }),
                    new ValueSingle<>(new byte[] { 4, 5, 6 }),
                    new ValueSingle<>(new byte[] { 7, 8, 9 }))),
            hasValue(new Md5().next().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));
    }


    @Test
    public void testDeprecated() throws Exception
    {
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[0]),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[0], new byte[0]),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[0], new byte[0], new byte[0]),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[] { 1, 2, 3 }),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3 })));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[] { 1, 2, 3 }, new byte[] { 4, 5, 6 }).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new byte[] { 1, 2, 3 }, new byte[] { 4, 5, 6 }, new byte[] { 7, 8, 9 }).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) ""),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) "", ""),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) "", "", ""),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) "1äöü"),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü".getBytes("UTF-8"))));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) "1äöü", "456"),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü456".getBytes("UTF-8"))));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), (CharSequence) "1äöü", "456", "789"),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü456789".getBytes("UTF-8"))));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), "latin1", (CharSequence) ""),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), "latin1", (CharSequence) "1äöü"),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü".getBytes("latin1"))));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), "latin1", (CharSequence) "1äöü", "456"),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü456".getBytes("latin1"))));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), "latin1", (CharSequence) "1äöü", "456", "789").value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest("1äöü456789".getBytes("latin1"))));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new ValueSingle<>(new byte[0])),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0])),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new ValueSingle<>(new byte[0]),
                new ValueSingle<>(new byte[0]),
                new ValueSingle<>(new byte[0])).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new ValueSingle<>(new byte[] { 1, 2, 3 })),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3 })));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new ValueSingle<>(new byte[] { 1, 2, 3 }),
                new ValueSingle<>(new byte[] { 4, 5, 6 })).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new ValueSingle<>(new byte[] { 1, 2, 3 }),
                new ValueSingle<>(new byte[] { 4, 5, 6 }),
                new ValueSingle<>(new byte[] { 7, 8, 9 })),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new Seq<Single<byte[]>>(new ValueSingle<>(new byte[0]))),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new Seq<Single<byte[]>>(new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]))).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));
        assertThat(
            new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new Seq<Single<byte[]>>(new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0]), new ValueSingle<>(new byte[0])))
            , hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest()));

        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), new Seq<Single<byte[]>>(new ValueSingle<>(new byte[] { 1, 2, 3 }))).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3 })));
        assertThat(
            new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new Seq<Single<byte[]>>(new ValueSingle<>(new byte[] { 1, 2, 3 }), new ValueSingle<>(new byte[] { 4, 5, 6 }))).value(),
            is(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6 })));
        assertThat(new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(),
                new Seq<Single<byte[]>>(new ValueSingle<>(new byte[] { 1, 2, 3 }), new ValueSingle<>(new byte[] { 4, 5, 6 }),
                    new ValueSingle<>(new byte[] { 7, 8, 9 }))),
            hasValue(new org.dmfs.jems.messagedigest.elementary.Md5().newInstance().digest(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 })));
    }


    @Test
    public void testIllegalEncoding()
    {
        assertThat(
            new Digest(new Md5(), "bogus-fake-invalid", ""),
            having(
                digest -> digest::value,
                is(
                    throwing(
                        throwable(
                            RuntimeException.class,
                            causedBy(
                                throwable(UnsupportedEncodingException.class)))))));
    }


    @Test
    public void testIllegalEncodingDeprecated()
    {
        assertThat(
            new Digest(new org.dmfs.jems.messagedigest.elementary.Md5(), "bogus-fake-invalid", ""),
            having(
                digest -> digest::value,
                is(
                    throwing(
                        throwable(
                            RuntimeException.class,
                            causedBy(
                                throwable(UnsupportedEncodingException.class)))))));
    }

}