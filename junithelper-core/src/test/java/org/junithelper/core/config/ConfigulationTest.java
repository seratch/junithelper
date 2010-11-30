package org.junithelper.core.config;

import static org.junit.Assert.*;
import org.junit.Test;

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
