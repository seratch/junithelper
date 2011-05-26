package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestMethodNameTest {

	@Test
	public void type() throws Exception {
		assertNotNull(TestMethodName.class);
	}

	@Test
	public void instantiation() throws Exception {
		TestMethodName target = new TestMethodName();
		assertNotNull(target);
	}

}
