package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MockObjectFrameworkTest {

	@Test
	public void type() throws Exception {
		assertNotNull(MockObjectFramework.class);
	}

	@Test
	public void toString_A$() throws Exception {
		String actual = MockObjectFramework.EasyMock.toString();
		String expected = "EasyMock";
		assertEquals(expected, actual);
	}

}
