package org.dmfs.xml.objectpull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.dmfs.xmlobjects.ElementDescriptor;
import org.dmfs.xmlobjects.QualifiedName;
import org.dmfs.xmlobjects.XmlContext;
import org.dmfs.xmlobjects.builder.IObjectBuilder;
import org.dmfs.xmlobjects.builder.StringObjectBuilder;
import org.dmfs.xmlobjects.pull.ParserContext;
import org.dmfs.xmlobjects.pull.XmlObjectPullParserException;
import org.junit.Test;


public class StringObjectBuilderTest
{
	XmlContext testContext = new XmlContext();

	ElementDescriptor<String> stringElement = ElementDescriptor.register("Test", StringObjectBuilder.INSTANCE, testContext);

	ElementDescriptor<String> childElement = ElementDescriptor.register("TestChild", StringObjectBuilder.INSTANCE, testContext);


	@Test
	public void test() throws XmlObjectPullParserException
	{
		// get the builder instance
		IObjectBuilder<String> sob = stringElement.builder;

		ParserContext pc = new ParserContext();

		// test getting a new element
		assertNull(sob.get(stringElement, null, pc));

		// test getting a new element when providing a recycled element
		assertNull(sob.get(stringElement, "recycled", pc));

		// test adding an attribute
		assertNull(sob.update(stringElement, null, QualifiedName.get("testattr"), "testvalue", pc));

		// test adding a child element without previous value
		assertNull(sob.update(stringElement, null, childElement, "childElementValue", pc));

		// test adding a child element with previous value
		assertEquals("oldValue", sob.update(stringElement, "oldValue", childElement, "childElementValue", pc));

		// test adding a text element without previous value
		assertEquals("testxyz", sob.update(stringElement, null, "testxyz", pc));

		// test adding a text element with previous value, the new value should override the old one.
		assertEquals("testxyz", sob.update(stringElement, "oldvalue", "testxyz", pc));

		// test adding null element without previous value
		assertNull(sob.update(stringElement, null, null, pc));

		// test adding null element with previous value, the new value should override the old one.
		assertNull(sob.update(stringElement, "oldvalue", null, pc));

		// check that finish returns the previous value
		assertNull(sob.finish(stringElement, null, pc));
		assertEquals("abdef", sob.finish(stringElement, "abdef", pc));
	}

}
