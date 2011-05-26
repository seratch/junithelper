package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReturnTypeMetaTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ReturnTypeMeta.class);
	}

	@Test
	public void instantiation() throws Exception {
		ReturnTypeMeta target = new ReturnTypeMeta();
		assertNotNull(target);
	}

	@Test
	public void getGenericsAsString_A$() throws Exception {
		ReturnTypeMeta target = new ReturnTypeMeta();
		// given
		target.generics.add("String");
		target.generics.add("Object");
		// when
		String actual = target.getGenericsAsString();
		// then
		String expected = "<String, Object>";
		assertEquals(expected, actual);
	}

}
