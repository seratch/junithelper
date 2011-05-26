package org.junithelper.core.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ThreadUtilTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ThreadUtil.class);
	}

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
