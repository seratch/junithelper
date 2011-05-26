package org.junithelper.core.meta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccessModifierTest {

	@Test
	public void type() throws Exception {
		assertNotNull(AccessModifier.class);
	}

	@Test
	public void toString_A$() throws Exception {
		// given
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = AccessModifier.PackageLocal.toString();
		// then
		// e.g. : verify(mocked).called();
		String expected = "";
		assertEquals(expected, actual);
	}

}
