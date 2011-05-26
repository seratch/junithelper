package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JUnitVersionTest {

	@Test
	public void type() throws Exception {
		assertNotNull(JUnitVersion.class);
	}

	@Test
	public void toString_A$() throws Exception {
		String actual = JUnitVersion.version3.toString();
		String expected = "version3";
		assertEquals(expected, actual);
	}

}
