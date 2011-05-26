package org.junithelper.core.constant;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class StringValueTest {

	@Test
	public void type() throws Exception {
		assertNotNull(StringValue.class);
	}

	@Test
	public void instantiation() throws Exception {
		StringValue target = new StringValue();
		assertNotNull(target);
	}

}
