package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestCaseMetaTest {

	@Test
	public void type() throws Exception {
		assertNotNull(TestCaseMeta.class);
	}

	@Test
	public void instantiation() throws Exception {
		TestCaseMeta target = new TestCaseMeta();
		assertNotNull(target);
	}

}
