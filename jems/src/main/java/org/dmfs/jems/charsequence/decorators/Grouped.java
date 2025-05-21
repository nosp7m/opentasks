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

package org.dmfs.jems.charsequence.decorators;

import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.Frozen;


/**
 * A {@link CharSequence} decorator which divides the delegate into groups of a specific size and inserts a separator char between them.
 * <p>
 * Example:
 * <pre><code>
 * assertThat(new Grouped(2, ':', "abcdef"), is(validCharSequence("ab:cd:ef")));
 * </code></pre>
 *
 * @author Marten Gajda
 */
public final class Grouped implements CharSequence
{
    private final CharSequence mDelegate;
    private final int mGroupSize;
    private final char mSeparator;
    private final Single<String> mToString;


    public Grouped(final int groupSize, final char separator, final CharSequence delegate)
    {
        mDelegate = delegate;
        mGroupSize = groupSize;
        mSeparator = separator;
        mToString = new Frozen<>(new Single<String>()
        {
            @Override
            public String value()
            {
                int len = delegate.length();
                StringBuilder stringBuilder = new StringBuilder(length());
                int i = 0;
                while (i < len)
                {
                    stringBuilder.append(delegate.charAt(i++));
                    if ((i < len) && i % groupSize == 0)
                    {
                        stringBuilder.append(separator);
                    }
                }
                return stringBuilder.toString();
            }
        });
    }


    @Override
    public int length()
    {
        int delegateLength = mDelegate.length();
        return delegateLength + Math.max(0, (delegateLength + mGroupSize - 1) / mGroupSize - 1);
    }


    @Override
    public char charAt(int i)
    {
        if (i < 0 || i >= length())
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        int group = i / (mGroupSize + 1);
        return (i + 1) % (mGroupSize + 1) == 0 ? mSeparator : mDelegate.charAt(i - group);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        // creating a subsequence of a Grouped CharSequence would be too much of a hassle with a decorator.
        // So at this point we just create a string and return the subsequence of it.
        return toString().subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        return mToString.value();
    }
}
