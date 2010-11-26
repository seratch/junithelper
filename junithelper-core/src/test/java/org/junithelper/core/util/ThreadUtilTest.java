package org.junithelper.core.util;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ThreadUtilTest {

	@Test
	public void sleep_A$long() throws Exception {
		ThreadUtil.sleep(10L);
		ThreadUtil.sleep(0L);
		try {
			ThreadUtil.sleep(-10L);
			fail("Expected exception did not occurred!");
		} catch (IllegalArgumentException expected) {
		}
	}

}
