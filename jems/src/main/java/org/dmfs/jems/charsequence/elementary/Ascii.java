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

package org.dmfs.jems.charsequence.elementary;

import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.elementary.Frozen;

import java.util.Locale;


/**
 * A {@link CharSequence} adapter to ASCII encoded byte arrays.
 *
 * @author Marten Gajda
 */
public final class Ascii implements CharSequence
{
    private final byte[] mData;
    private final int mStart;
    private final int mEnd;
    private final Single<String> mToString;


    public Ascii(byte... data)
    {
        this(data, 0, data.length);
    }


    public Ascii(byte[] data, int start, int end)
    {
        mData = data;
        mStart = start;
        mEnd = end;
        mToString = new Frozen<>(new Single<String>()
        {
            @Override
            public String value()
            {
                StringBuilder stringBuilder = new StringBuilder(mEnd - mStart);
                for (int i = mStart; i < mEnd; i++)
                {
                    stringBuilder.append((char) mData[i]);
                }
                return stringBuilder.toString();
            }
        });
    }


    @Override
    public int length()
    {
        return mEnd - mStart;
    }


    @Override
    public char charAt(int i)
    {
        if (i < 0 || i >= mEnd - mStart)
        {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ENGLISH, "Illegal position %d in CharSequence of length %d", i, mEnd - mStart));
        }
        return (char) mData[i + mStart];
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        if (i < 0)
        {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ENGLISH, "sub-sequences can not start at a negative index %d", i));
        }
        if (i1 < i)
        {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ENGLISH, "end (%d) can not be smaller than start (%d)", i1, i));
        }
        if (i1 > mEnd - mStart)
        {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ENGLISH, "End index %d exceeds length of CharSequence %d", i1, mEnd - mStart));
        }
        return new Ascii(mData, mStart + i, mStart + i1);
    }


    @Override
    public String toString()
    {
        return mToString.value();
    }
}
