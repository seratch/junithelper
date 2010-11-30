package org.junithelper.core.config;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestingTargetTest {

	@Test
	public void type() throws Exception {
		assertNotNull(TestingTarget.class);
	}

	@Test
	public void instantiation() throws Exception {
		TestingTarget target = new TestingTarget();
		assertNotNull(target);
	}

}
