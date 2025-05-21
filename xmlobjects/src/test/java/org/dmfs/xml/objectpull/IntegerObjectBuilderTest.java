package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.builder.IntegerObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.junit.Test;


public class IntegerObjectBuilderTest
{
	XmlContext testContext = new XmlContext();

	ElementDescriptor<Integer> integerElementNonStrict = ElementDescriptor.register("Test", IntegerObjectBuilder.INSTANCE, testContext);

	ElementDescriptor<Integer> integerElementStrict = ElementDescriptor.register("TestString", IntegerObjectBuilder.INSTANCE_STRICT, testContext);

	ElementDescriptor<Integer> integerChildElement = ElementDescriptor.register("TestChild", IntegerObjectBuilder.INSTANCE, testContext);

	ElementDescriptor<String> stringChildElement = ElementDescriptor.register("TestChild2", StringObjectBuilder.INSTANCE, testContext);


	@Test
	public void testNonStrictBuilder() throws XmlObjectPullParserException
	{
		// get the builder instance
		IObjectBuilder<Integer> sob = integerElementNonStrict.builder;

		ParserContext pc = new ParserContext();

		// test getting a new element
		assertNull(sob.get(integerElementNonStrict, null, pc));

		// test getting a new element when providing a recycled element
		assertNull(sob.get(integerElementNonStrict, 1, pc));

		// test adding an attribute
		assertNull(sob.update(integerElementNonStrict, null, QualifiedName.get("testattr"), "testvalue", pc));

		// test adding child elements without previous value
		assertNull(sob.update(integerElementNonStrict, null, integerChildElement, 123, pc));
		assertNull(sob.update(integerElementNonStrict, null, stringChildElement, "abc", pc));

		// test adding child elements with previous value
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, 12, integerChildElement, 456, pc));
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, 12, stringChildElement, "abc", pc));

		// test adding a text element without previous value
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, null, "12", pc));

		// test adding a text element with previous value, the new value should override the old one.
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, 456, "12", pc));

		// test adding a text element with spaces without previous value
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, null, " 12 ", pc));

		// test adding a text element with spaces with previous value, the new value should override the old one.
		assertEquals((Integer) 12, sob.update(integerElementNonStrict, 456, " 12 ", pc));

		// test adding null element without previous value
		assertNull(sob.update(integerElementNonStrict, null, null, pc));

		// test adding null element with previous value, the new value should override the old one.
		assertNull(sob.update(integerElementNonStrict, 123, null, pc));

		// check that finish returns the previous value
		assertNull(sob.finish(integerElementNonStrict, null, pc));
		assertEquals((Integer) 928374, sob.finish(integerElementNonStrict, 928374, pc));
	}


	@Test
	public void testStrictBuilder() throws XmlObjectPullParserException
	{
		// get the builder instance
		IObjectBuilder<Integer> sob = integerElementStrict.builder;

		ParserContext pc = new ParserContext();

		// test getting a new element
		assertNull(sob.get(integerElementStrict, null, pc));

		// test getting a new element when providing a recycled element
		assertNull(sob.get(integerElementStrict, 1, pc));

		// test adding an attribute
		assertNull(sob.update(integerElementStrict, null, QualifiedName.get("testattr"), "testvalue", pc));

		// test adding a child element without previous value
		assertNull(sob.update(integerElementStrict, null, integerChildElement, 123, pc));

		// test adding a child element with previous value
		assertEquals((Integer) 12, sob.update(integerElementStrict, 12, integerChildElement, 456, pc));

		// test adding a text element without previous value
		assertEquals((Integer) 12, sob.update(integerElementStrict, null, "12", pc));

		// test adding a text element with previous value, the new value should override the old one.
		assertEquals((Integer) 12, sob.update(integerElementStrict, 456, "12", pc));

		// TODO: check that illegal values cause an exception

		// check that finish returns the previous value
		assertNull(sob.finish(integerElementStrict, null, pc));
		assertEquals((Integer) 928374, sob.finish(integerElementStrict, 928374, pc));
	}
}
