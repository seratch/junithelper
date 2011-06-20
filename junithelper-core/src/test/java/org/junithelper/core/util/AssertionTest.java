package org.junithelper.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class AssertionTest {

	@Test
	public void type() throws Exception {
		assertThat(Assertion.class, notNullValue());
	}

	@Test
	public void mustNotBeNull_A$Object$String() throws Exception {
		Object arg = new Object();
		String name = null;
		Assertion.mustNotBeNull(arg, name);
	}

	@Test
	public void mustNotBeNull_A$Object$String_StringIsNull() throws Exception {
		Object arg = new Object();
		String name = null;
		Assertion.mustNotBeNull(arg, name);
	}

	@Test
	public void mustNotBeNull_A$Object$String_StringIsEmpty() throws Exception {
		Object arg = new Object();
		String name = "";
		Assertion.mustNotBeNull(arg, name);
	}

	@Test
	public void mustNotBeEmpty_A$String$String() throws Exception {
		String arg = "abc";
		String name = null;
		Assertion.mustNotBeEmpty(arg, name);
	}

	@Test
	public void mustNotBeEmpty_A$String$String_StringIsNull() throws Exception {
		String arg = null;
		String name = null;
		try {
			Assertion.mustNotBeEmpty(arg, name);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void mustNotBeEmpty_A$String$String_StringIsEmpty() throws Exception {
		String arg = "";
		String name = "";
		try {
			Assertion.mustNotBeEmpty(arg, name);
			fail();
		} catch (JUnitHelperCoreException e) {
		}
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String() throws Exception {
		int arg = 1;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_intIsMinus1() throws Exception {
		int arg = 0;
		int lowerLimit = -1;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_intIs0() throws Exception {
		int arg = 1;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_intIs1() throws Exception {
		int arg = 2;
		int lowerLimit = 1;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_intIs2() throws Exception {
		int arg = 3;
		int lowerLimit = 2;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_StringIsNull() throws Exception {
		int arg = 1;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$int$int$String_StringIsEmpty() throws Exception {
		int arg = 1;
		int lowerLimit = 0;
		String name = "";
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String() throws Exception {
		int arg = 0;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_intIsMinus1() throws Exception {
		int arg = -1;
		int lowerLimit = -1;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_intIs0() throws Exception {
		int arg = 0;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_intIs1() throws Exception {
		int arg = 1;
		int lowerLimit = 1;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_intIs2() throws Exception {
		int arg = 2;
		int lowerLimit = 2;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_StringIsNull() throws Exception {
		int arg = 0;
		int lowerLimit = 0;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$int$int$String_StringIsEmpty() throws Exception {
		int arg = 0;
		int lowerLimit = 0;
		String name = "";
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String() throws Exception {
		int arg = -1;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_intIsMinus1() throws Exception {
		int arg = -2;
		int upperLimit = -1;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_intIs0() throws Exception {
		int arg = -1;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_intIs1() throws Exception {
		int arg = 0;
		int upperLimit = 1;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_intIs2() throws Exception {
		int arg = 1;
		int upperLimit = 2;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_StringIsNull() throws Exception {
		int arg = -1;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$int$int$String_StringIsEmpty() throws Exception {
		int arg = -1;
		int upperLimit = 0;
		String name = "";
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String() throws Exception {
		int arg = 0;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_intIsMinus1() throws Exception {
		int arg = -1;
		int upperLimit = -1;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_intIs0() throws Exception {
		int arg = 0;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_intIs1() throws Exception {
		int arg = 1;
		int upperLimit = 1;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_intIs2() throws Exception {
		int arg = 2;
		int upperLimit = 2;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_StringIsNull() throws Exception {
		int arg = 0;
		int upperLimit = 0;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$int$int$String_StringIsEmpty() throws Exception {
		int arg = 0;
		int upperLimit = 0;
		String name = "";
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

}
