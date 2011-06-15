package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigurationTest {

	@Test
	public void type() throws Exception {
		assertNotNull(Configuration.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configuration target = new Configuration();
		assertNotNull(target);
	}

}
