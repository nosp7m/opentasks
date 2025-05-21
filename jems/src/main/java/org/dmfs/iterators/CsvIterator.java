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

package org.dmfs.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterator} that iterates the elements of a string of a comma (or other character) separated value list .
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * 	Iterator&lt;String&gt; i = new CsvIterator&lt;String&gt;("a, b,def,123", ',');
 * 	i.next(); // returns &quot;a&quot;
 * 	i.next(); // returns &quot; b&quot;
 * 	i.next(); // returns &quot;def&quot;
 * 	i.next(); // returns &quot;123&quot;
 * 	i.hasNext(); // false
 * </code>
 * </pre>
 * <p>
 * Separators between quote characters (") will be ignored.
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * 	Iterator&lt;String&gt; i = new CsvIterator&lt;String&gt;("\"a, b\",\"def,123\"", ',');
 * 	i.next(); // returns &quot;a , b&quot;
 * 	i.next(); // returns &quot;def,123&quot;
 * 	i.hasNext(); // false
 * </code>
 * </pre>
 * <p>
 * Iterating an empty String or a string without (unquoted) separators will return exactly one element.
 * <p>
 * Example:
 * <p>
 * <pre>
 * <code>
 * 	Iterator&lt;String&gt; i = new CsvIterator&lt;String&gt;("", ',');
 * 	i.next(); // returns &quot;&quot;
 * 	i.hasNext(); // false
 *
 * 	Iterator&lt;String&gt; i2 = new CsvIterator&lt;String&gt;("\"abc,def\"", ',');
 * 	i2.next(); // returns &quot;abc,def&quot;
 * 	i2.hasNext(); // false
 * </code>
 * </pre>
 *
 * @author Marten Gajda
 * @deprecated in favor of {@link UnquotedSplit}, to be removed in version 2.0
 */
@Deprecated
public final class CsvIterator extends AbstractBaseIterator<String>
{
    private final String mValue;
    private final char mSeparator;

    private int mLastSeparatorPos = -1;
    private int mNextSeparatorPos = -1;


    /**
     * Creates an {@link Iterator} that iterates all elements of the given string which are separated by the given
     * <code>separator</code>.
     *
     * @param value
     *         The string that contains a list of values.
     * @param separator
     *         The separator that separates the values.
     */
    public CsvIterator(String value, char separator)
    {
        mValue = value;
        mSeparator = separator;
    }


    @Override
    public boolean hasNext()
    {
        if (mNextSeparatorPos == -1)
        {
            findNextSeparator();
        }
        return mLastSeparatorPos < mValue.length();
    }


    @Override
    public String next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException("Last element has already been iterated.");
        }
        String result = mValue.substring(mLastSeparatorPos + 1, mNextSeparatorPos);
        findNextSeparator();
        return result;
    }


    /**
     * Move {@link #mNextSeparatorPos} to the next (unquoted) separator (or the end of the {@link String} if no other separator exists in {@link #mValue}).
     */
    private void findNextSeparator()
    {
        boolean isQuoted = false;
        mLastSeparatorPos = mNextSeparatorPos;
        while (++mNextSeparatorPos < mValue.length())
        {
            char c = mValue.charAt(mNextSeparatorPos);
            if (c == mSeparator)
            {
                if (!isQuoted)
                {
                    return;
                }
                // else: ignore quoted speparator
            }
            else if (c == '"')
            {
                isQuoted = !isQuoted;
            }
        }
    }

}
