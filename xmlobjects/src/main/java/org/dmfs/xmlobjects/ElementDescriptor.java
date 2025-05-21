/*
 * Copyright (C) 2015 Marten Gajda <marten@dmfs.org>
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

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dmfs.xmlobjects.builder.IObjectBuilder;


/**
 * A description of an XML element. This descriptor can return an {@link IObjectBuilder} for a specific XML element.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ElementDescriptor<T>
{
	public final static XmlContext DEFAULT_CONTEXT = new XmlContext()
	{
	};

	/**
	 * The {@link QualifiedName} of this element.
	 */
	public final QualifiedName qualifiedName;

	/**
	 * An {@link IObjectBuilder} for elements of this type.
	 */
	public final IObjectBuilder<T> builder;

	/**
	 * A {@link WeakReference} to the context this element was registered in.
	 */
	private final WeakReference<XmlContext> mContext;

	/**
	 * A synchronized map of {@link QualifiedName}s to {@link ElementDescriptor}s of Elements valid in the context of this element. May be <code>null</code>.
	 */
	private Map<QualifiedName, ElementDescriptor<?>> mElementContext;


	/**
	 * Return the {@link ElementDescriptor} of the element having the given {@link QualifiedName} from the default {@link XmlContext}.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the {@link ElementDescriptor} to return.
	 * @return The {@link ElementDescriptor} or <code>null</code> if there is no such element in the default {@link XmlContext}.
	 */
	public static ElementDescriptor<?> get(QualifiedName qname)
	{
		synchronized (DEFAULT_CONTEXT)
		{
			return DEFAULT_CONTEXT.DESCRIPTOR_MAP.get(qname);
		}
	}


	/**
	 * Return the {@link ElementDescriptor} of the element having the given {@link QualifiedName} from the given {@link XmlContext}.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the {@link ElementDescriptor} to return.
	 * @param context
	 *            The {@link XmlContext}.
	 * @return The {@link ElementDescriptor} or <code>null</code> if there is no such element in the given {@link XmlContext}.
	 */
	public static ElementDescriptor<?> get(QualifiedName qname, XmlContext context)
	{
		if (context == null)
		{
			context = DEFAULT_CONTEXT;
		}

		synchronized (context)
		{
			final Map<QualifiedName, ElementDescriptor<?>> contextMap = context.DESCRIPTOR_MAP;
			ElementDescriptor<?> result = contextMap.get(qname);
			if (result != null)
			{
				return result;
			}
		}

		synchronized (DEFAULT_CONTEXT)
		{
			return DEFAULT_CONTEXT.DESCRIPTOR_MAP.get(qname);
		}
	}


	/**
	 * Return the {@link ElementDescriptor} of the element having the given {@link QualifiedName}. This method returns first checks the context of the given
	 * parent {@link ElementDescriptor} for matching element and falls back to the default {@link XmlContext}if no matching element is found in the parent
	 * context.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the {@link ElementDescriptor} to return.
	 * @param parentElement
	 *            The parent {@link ElementDescriptor}.
	 * @return The {@link ElementDescriptor} or <code>null</code> if there is no such element in the parent context of the default {@link XmlContext}.
	 */
	public static ElementDescriptor<?> get(QualifiedName qname, ElementDescriptor<?> parentElement)
	{
		if (parentElement == null || parentElement.mElementContext == null)
		{
			return get(qname);
		}

		ElementDescriptor<?> result = parentElement.mElementContext.get(qname);
		return result == null ? get(qname) : result;
	}


	/**
	 * Return the {@link ElementDescriptor} of the element having the given {@link QualifiedName}. This method returns first checks the context of the given
	 * parent {@link ElementDescriptor} for matching element and falls back to the given {@link XmlContext}if no matching element is found in the parent
	 * context.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the {@link ElementDescriptor} to return.
	 * @param parentElement
	 *            The parent {@link ElementDescriptor}.
	 * @param context
	 *            An {@link XmlContext}, may be <code>null</code> for the default context.
	 * @return The {@link ElementDescriptor} or <code>null</code> if there is no such element in the parent context of the given {@link XmlContext}.
	 */
	public static ElementDescriptor<?> get(QualifiedName qname, ElementDescriptor<?> parentElement, XmlContext context)
	{
		if (parentElement == null || parentElement.mElementContext == null)
		{
			return get(qname, context);
		}

		ElementDescriptor<?> result = parentElement.mElementContext.get(qname);
		return result == null ? get(qname, context) : result;
	}


	/**
	 * Register an element with the given name and no name space using the given {@link IObjectBuilder} in the default {@link XmlContext}.
	 * 
	 * @param name
	 *            The name of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> register(String name, IObjectBuilder<T> builder)
	{
		return register(QualifiedName.get(name), builder, DEFAULT_CONTEXT);
	}


	/**
	 * Register an element with the given {@link QualifiedName} using the given {@link IObjectBuilder} in the default {@link XmlContext}.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> register(QualifiedName qname, IObjectBuilder<T> builder)
	{
		return register(qname, builder, DEFAULT_CONTEXT);
	}


	/**
	 * Register an element with the given name and no name space using the given {@link IObjectBuilder} in the given {@link XmlContext}.
	 * 
	 * @param name
	 *            The name of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @param context
	 *            An {@link XmlContext}.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> register(String name, IObjectBuilder<T> builder, XmlContext context)
	{
		return register(QualifiedName.get(name), builder, context);
	}


	/**
	 * Register an element with the given {@link QualifiedName} using the given {@link IObjectBuilder} in the given {@link XmlContext}.
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @param context
	 *            An {@link XmlContext} or <code>null</code> to use the default {@link XmlContext}.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> register(QualifiedName qname, IObjectBuilder<T> builder, XmlContext context)
	{
		if (context == null)
		{
			context = DEFAULT_CONTEXT;
		}

		synchronized (context)
		{
			Map<QualifiedName, ElementDescriptor<?>> descriptorMap = context.DESCRIPTOR_MAP;

			if (descriptorMap.containsKey(qname))
			{
				throw new IllegalStateException("descriptor for " + qname + " already exists, use 'overload' to override the definition");
			}

			ElementDescriptor<T> descriptor = new ElementDescriptor<T>(qname, builder, context);
			descriptorMap.put(qname, descriptor);
			return descriptor;
		}
	}


	/**
	 * Register an element with the given name and no name space using the given {@link IObjectBuilder} in the context of the given parent
	 * {@link ElementDescriptor}s. An element that is registered with this method will not be available in a general {@link XmlContext}, but only when providing
	 * the parent {@link ElementDescriptor} using {@link #get(QualifiedName, ElementDescriptor)} or {@link #get(QualifiedName, ElementDescriptor, XmlContext)}.
	 * <p>
	 * Note: All parents must be registered within in the same {@link XmlContext}.
	 * </p>
	 * 
	 * @param name
	 *            The name of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @param parentElements
	 *            The {@link ElementDescriptor}s of the parent elements to register the new element with.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> registerWithParents(String name, IObjectBuilder<T> builder, ElementDescriptor<?>... parentElements)
	{
		return registerWithParents(QualifiedName.get(name), builder, parentElements);
	}


	/**
	 * Register an element with the given {@link QualifiedName} using the given {@link IObjectBuilder} in the context of the given parent
	 * {@link ElementDescriptor}s. An element that is registered with this method will not be available in a general {@link XmlContext}, but only when providing
	 * the parent {@link ElementDescriptor} using {@link #get(QualifiedName, ElementDescriptor)} or {@link #get(QualifiedName, ElementDescriptor, XmlContext)}.
	 * <p>
	 * Note: All parents must be registered within in the same {@link XmlContext}.
	 * </p>
	 * 
	 * @param qname
	 *            The {@link QualifiedName} of the element.
	 * @param builder
	 *            The {@link IObjectBuilder} to build and serialize this element.
	 * @param parentElements
	 *            The {@link ElementDescriptor}s of the parent elements to register the new element with.
	 * @return The {@link ElementDescriptor}.
	 */
	public static <T> ElementDescriptor<T> registerWithParents(QualifiedName qname, IObjectBuilder<T> builder, ElementDescriptor<?>... parentElements)
	{
		if (parentElements == null || parentElements.length == 0)
		{
			throw new IllegalArgumentException("no parent elements provided");
		}

		// create the new descriptor with the context of the first parent
		ElementDescriptor<T> descriptor = new ElementDescriptor<T>(qname, builder, parentElements[0].getContext());

		// register the descriptor with all parents
		for (ElementDescriptor<?> parentElement : parentElements)
		{
			if (descriptor.getContext() != parentElement.getContext())
			{
				throw new IllegalArgumentException("Parent descriptors don't belong to the same XmlContext");
			}
			Map<QualifiedName, ElementDescriptor<?>> descriptorMap = parentElement.mElementContext;

			if (descriptorMap == null)
			{
				descriptorMap = parentElement.mElementContext = Collections.synchronizedMap(new HashMap<QualifiedName, ElementDescriptor<?>>(8));
			}
			else if (descriptorMap.containsKey(qname))
			{
				throw new IllegalStateException("descriptor for " + qname + " already exists in parent " + parentElement.qualifiedName);
			}

			descriptorMap.put(qname, descriptor);
		}
		return descriptor;
	}


	public static <T> ElementDescriptor<T> overload(ElementDescriptor<? super T> oldDescriptor, IObjectBuilder<T> builder)
	{
		XmlContext context = oldDescriptor.mContext.get();
		if (context == null)
		{
			throw new IllegalStateException("can not overload element in gc'ed context");
		}

		synchronized (context)
		{
			Map<QualifiedName, ElementDescriptor<?>> descriptorMap = context.DESCRIPTOR_MAP;
			QualifiedName qname = oldDescriptor.qualifiedName;
			ElementDescriptor<T> descriptor = new ElementDescriptor<T>(qname, builder, context);

			// both elements have the same child descriptors, if any
			descriptor.mElementContext = oldDescriptor.mElementContext;
			descriptorMap.put(qname, descriptor);
			return descriptor;
		}
	}


	private ElementDescriptor(QualifiedName qname, IObjectBuilder<T> builder, XmlContext context)
	{
		if (qname == null)
		{
			throw new IllegalArgumentException("qname must not be null");
		}

		this.qualifiedName = qname;
		this.builder = builder;
		this.mContext = new WeakReference<XmlContext>(context);
	}


	public XmlContext getContext()
	{
		return mContext.get();
	}


	@Override
	public int hashCode()
	{
		return qualifiedName.hashCode();
	}


	@Override
	public boolean equals(Object o)
	{
		return o == this;
	}
}
