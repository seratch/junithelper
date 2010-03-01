package org.junithelper.runtime.mockito;

import junit.framework.TestCase;

import org.junithelper.runtime.data.Bean;
import org.junithelper.runtime.mockito.stubbing.exception.UnexpectedStubCalledException;

public class MockitoTest extends TestCase {

	public void test_mockStrict_A$Class_default() throws Exception {
		// given
		Class<Bean> arg0 = Bean.class;
		// when
		Bean actual = Mockito.mockStrict(arg0);
		// then
		assertNotNull(actual);
		try {
			actual.toString();
			fail("Expected exception did not occurred!");
		} catch (UnexpectedStubCalledException e) {
		}
	}

}
