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

package org.dmfs.android.contentpal;

/**
 * A soft reference to a content provider {@link RowSnapshot}. A soft row reference may be "virtual" which means it doesn't belong to any actual content
 * provider row (yet).
 *
 * @param <T>
 *     The contract of the {@link Table} of the {@link RowSnapshot} this reference refers to.
 *
 * @author Marten Gajda
 */
public interface SoftRowReference<T> extends RowReference<T>
{
    /**
     * Returns whether this {@link SoftRowReference} is virtual. A virtual row reference needs to be resolved before you can refer to it.
     *
     * @return {@code true} if any only if this {@link SoftRowReference} is virtual.
     */
    boolean isVirtual();

}
