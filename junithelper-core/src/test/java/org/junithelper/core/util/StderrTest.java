package org.junithelper.core.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class StderrTest {

	@Test
	public void type() throws Exception {
		assertNotNull(Stderr.class);
	}

	@Test
	public void printf_A$String$ObjectArray() throws Exception {
		// given
		String format = "%s";
		Object[] values = new Object[] { "aaaa" };
		// when
		Stderr.printf(format, values);
		// then
	}

	@Test
	public void p_A$String() throws Exception {
		// given
		String str = "test";
		// when
		Stderr.p(str);
		// then
	}

}
