package org.junithelper.plugin.bean;

import junit.framework.TestCase;

public class MethodInfoTest extends TestCase {

	public void test_instantiate() throws Exception {
		MethodInfo methodInfo = new MethodInfo();
		assertNotNull(methodInfo.argTypes);
		assertNotNull(methodInfo.returnType);
	}

}
