/*
 * Copyright (C) 2014 Marten Gajda <marten@dmfs.org>
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
 * 
 */

package org.dmfs.xmlobjects;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a qualified name. A qualified name has a regular name and a namespace (which can be empty). Qualified names are immutable. To get a
 * {@link QualifiedName} use {@link #get(String)} or {@link #get(String, String)}.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class QualifiedName
{
	/**
	 * A cache of all known {@link QualifiedName}s. This is a map of namespaces to a map of names (in the respective name space) to the {@link QualifiedName}
	 * object.
	 */
	private final static Map<String, Map<String, QualifiedName>> QUALIFIED_NAME_CACHE = new HashMap<String, Map<String, QualifiedName>>(64);

	/**
	 * The namespace of this qualified name.
	 */
	public final String namespace;

	/**
	 * The name part of this qualified name.
	 */
	public final String name;

	/**
	 * The cached hash code.
	 */
	private final int mHashCode;


	/**
	 * Returns a {@link QualifiedName} with an empty name space. If the {@link QualifiedName} already exists, the existing instance is returned, otherwise it's
	 * created.
	 * 
	 * @param name
	 *            The name of the {@link QualifiedName}.
	 * @return The {@link QualifiedName} instance.
	 */
	public static QualifiedName get(String name)
	{
		return get(null, name);
	}


	/**
	 * Returns a {@link QualifiedName} with a specific name space. If the {@link QualifiedName} already exists, the existing instance is returned, otherwise
	 * it's created.
	 * 
	 * @param namespace
	 *            The namespace of the {@link QualifiedName}.
	 * @param name
	 *            The name of the {@link QualifiedName}.
	 * @return The {@link QualifiedName} instance.
	 */
	public static QualifiedName get(String namespace, String name)
	{
		if (namespace != null && namespace.length() == 0)
		{
			namespace = null;
		}

		synchronized (QUALIFIED_NAME_CACHE)
		{
			Map<String, QualifiedName> qualifiedNameMap = QUALIFIED_NAME_CACHE.get(namespace);
			QualifiedName qualifiedName;
			if (qualifiedNameMap == null)
			{
				qualifiedNameMap = new HashMap<String, QualifiedName>();
				qualifiedName = new QualifiedName(namespace, name);
				qualifiedNameMap.put(name, qualifiedName);
				QUALIFIED_NAME_CACHE.put(namespace, qualifiedNameMap);
			}
			else
			{
				qualifiedName = qualifiedNameMap.get(name);
				if (qualifiedName == null)
				{
					qualifiedName = new QualifiedName(namespace, name);
					qualifiedNameMap.put(name, qualifiedName);
				}
			}
			return qualifiedName;
		}
	}


	/**
	 * Instantiate a new {@link QualifiedName} with the given name and namespace.
	 * 
	 * @param namespace
	 *            The namespace of the qualified name.
	 * @param name
	 *            The name part of the qualified name.
	 */
	private QualifiedName(String namespace, String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("name part of a qualified name must not be null");
		}

		this.namespace = namespace;
		this.name = name;

		mHashCode = namespace == null ? name.hashCode() : namespace.hashCode() * 31 + name.hashCode();
	}


	@Override
	public int hashCode()
	{
		return mHashCode;
	}


	@Override
	public boolean equals(Object o)
	{
		return o == this;
	}


	@Override
	public String toString()
	{
		return namespace == null ? name : namespace + ":" + name;
	}


	/**
	 * Returns the qualified name in Clark notation.
	 * 
	 * @return A {@link String} representing the qualified name.
	 * 
	 * @see <a href="https://tools.ietf.org/html/draft-saintandre-json-namespaces-00">JavaScript Object Notation (JSON) Namespaces</a>
	 */
	public String toClarkString()
	{
		return namespace == null ? name : "{" + namespace + "}" + name;
	}
}
