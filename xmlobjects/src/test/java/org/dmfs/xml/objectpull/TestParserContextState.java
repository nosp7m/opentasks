package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.io.StringReader;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.builder.AbstractObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPull;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.dmfs.xmlobjects.pull.XmlPath;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


/**
 * Test if the state object returned by {@link ParserContext} is always the one that has been set previously for the same ElementDescriptor at the same level.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class TestParserContextState
{

	/**
	 * A simple test class that doesn't store anyting.
	 */
	private static class StateTestElement
	{
	}

	/**
	 * Another simple test class that doesn't store anyting.
	 */
	private static class StateTestElement2
	{
	}

	/**
	 * The {@link ElementDescriptor} of a {@link StateTestElement}. The builder stores the built object as the current state and checks if the returned state is
	 * correct at all times.
	 */
	private final static ElementDescriptor<StateTestElement> STATE_TEST_ELEMENT = ElementDescriptor.register(QualifiedName.get("test"),
		new AbstractObjectBuilder<StateTestElement>()
		{
			@Override
			public StateTestElement get(ElementDescriptor<StateTestElement> descriptor, StateTestElement recycle, ParserContext context)
				throws XmlObjectPullParserException
			{
				StateTestElement result = new StateTestElement();

				// set the current element as state. All update methods check if the object and the state are the same object.
				context.setState(result);
				assertSame(result, context.getState());
				return result;
			};


			@Override
			public <V> StateTestElement update(org.dmfs.xmlobjects.ElementDescriptor<StateTestElement> descriptor, StateTestElement object,
				org.dmfs.xmlobjects.ElementDescriptor<V> childDescriptor, V child, ParserContext context) throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public <V> StateTestElement update(ElementDescriptor<StateTestElement> descriptor, StateTestElement object, QualifiedName anonymousChildName,
				org.dmfs.xmlobjects.builder.IObjectBuilder<V> anonymousChildBuilder, V anonymousChild, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement update(ElementDescriptor<StateTestElement> descriptor, StateTestElement object, QualifiedName attribute, String value,
				ParserContext context) throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement update(ElementDescriptor<StateTestElement> descriptor, StateTestElement object, String text, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement finish(ElementDescriptor<StateTestElement> descriptor, StateTestElement object, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};
		});

	/**
	 * The {@link ElementDescriptor} of a {@link StateTestElement2}. The builder stores the built object as the current state and checks if the returned state
	 * is correct at all times.
	 */
	@SuppressWarnings("unused")
	private final static ElementDescriptor<StateTestElement2> STATE_TEST_ELEMENT2 = ElementDescriptor.register(QualifiedName.get("test2"),
		new AbstractObjectBuilder<StateTestElement2>()
		{
			@Override
			public StateTestElement2 get(ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 recycle, ParserContext context)
				throws XmlObjectPullParserException
			{
				StateTestElement2 result = new StateTestElement2();

				// set the current element as state. All update methods check if the object and the state are the same object.
				context.setState(result);
				assertSame(result, context.getState());
				return result;
			};


			@Override
			public <V> StateTestElement2 update(org.dmfs.xmlobjects.ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 object,
				org.dmfs.xmlobjects.ElementDescriptor<V> childDescriptor, V child, ParserContext context) throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public <V> StateTestElement2 update(ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 object, QualifiedName anonymousChildName,
				org.dmfs.xmlobjects.builder.IObjectBuilder<V> anonymousChildBuilder, V anonymousChild, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement2 update(ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 object, QualifiedName attribute, String value,
				ParserContext context) throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement2 update(ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 object, String text, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};


			@Override
			public StateTestElement2 finish(ElementDescriptor<StateTestElement2> descriptor, StateTestElement2 object, ParserContext context)
				throws XmlObjectPullParserException
			{
				assertSame(object, context.getState());
				return object;
			};
		});


	/**
	 * Feeds an XML steam with containing various alternating patterns of the elements &lt;test> and &lt;test2> into the parser and checks if the state is
	 * always correctly returned.
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws XmlObjectPullParserException
	 */
	@Test
	public void test() throws XmlPullParserException, IOException, XmlObjectPullParserException
	{
		String testString = "<?xml version=\"1.0\"?><test><test><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"><test><test><test><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"><test>Some text<test><test/></test></test></test></test></test></test></test>More text</test><test><test><test><test><test><test>Text</test><test>More text</test><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"/><test/><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"/><test/><test2/><test2 attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\">Another Text</test2><test2/><test2/></test></test></test></test><test><test2 attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"/></test2><test attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"><test2/></test><test2 attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"><test/></test2><test><test2 attr1=\"1\" attr2=\"2\" attr=\"3\" attr4=\"4\"/></test></test><test><test2><test><test>1</test>2<test2>3</test2>4<test>5</test>6</test></test2><test><test2><test2/><test/><test2/></test2></test><test2><test><test2>1</test2>2<test>3</test>4<test2>5</test2>6</test></test2><test><test2><test/><test2/><test/></test2></test></test></test></test>";

		XmlPullParserFactory ppfactory = XmlPullParserFactory.newInstance();
		ppfactory.setNamespaceAware(true);
		XmlPullParser parser = ppfactory.newPullParser();
		parser.setInput(new StringReader(testString));

		XmlObjectPull op = new XmlObjectPull(parser);

		// parse the xml stream
		op.pull(STATE_TEST_ELEMENT, null, new XmlPath());
	}
}