package org.junithelper.plugin.bean;

import junit.framework.TestCase;

public class ClassInfoTest extends TestCase {

	public void test_instantiate() throws Exception {
		ClassInfo classInfo = new ClassInfo();
		assertNotNull(classInfo.importList);
		assertNotNull(classInfo.methods);
	}

}
