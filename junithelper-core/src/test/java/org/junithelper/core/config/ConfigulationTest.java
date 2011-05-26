package org.junithelper.core.config;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigulationTest {

	@Test
	public void type() throws Exception {
		assertNotNull(Configulation.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation target = new Configulation();
		assertNotNull(target);
	}

}
