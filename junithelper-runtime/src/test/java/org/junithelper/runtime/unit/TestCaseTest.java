package org.junithelper.runtime.unit;

public class TestCaseTest extends TestCase {

	public void test_getNewJMock2Mockey_A$() throws Exception {
		org.junithelper.runtime.unit.TestCase target = new SampleCase();
		assertNotNull(target.getNewJMock2Mockey());
	}

	public void test_setUp_A$() throws Exception {
		org.junithelper.runtime.unit.TestCase target = new SampleCase();
		target.setUp();
	}

}
