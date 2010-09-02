package org.junithelper.plugin.exception;

import junit.framework.TestCase;

public class InvalidPreferenceExceptionTest extends TestCase {

	public void test_instantiation() throws Exception {
		assertNotNull(new InvalidPreferenceException());
	}

}
