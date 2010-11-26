package org.junithelper.plugin.util;

import junit.framework.TestCase;

public class ThreadUtilTest extends TestCase {

	public void test_sleep_A$long() throws Exception {
		ThreadUtil.sleep(10L);
		ThreadUtil.sleep(0L);
		try {
			ThreadUtil.sleep(-10L);
			fail("Expected exception did not occurred!");
		} catch (IllegalArgumentException expected) {
		}
	}
}
