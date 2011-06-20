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

	@Test
	public void mustBeGreaterThan_A$long$long$String() throws Exception {
		long arg = 1L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_longIsMinus1L() throws Exception {
		long arg = 1L;
		long lowerLimit = -1L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_longIs0L() throws Exception {
		long arg = 1L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_longIs1L() throws Exception {
		long arg = 2L;
		long lowerLimit = 1L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_longIs2L() throws Exception {
		long arg = 3L;
		long lowerLimit = 2L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_StringIsNull() throws Exception {
		long arg = 1L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$long$long$String_StringIsEmpty() throws Exception {
		long arg = 1L;
		long lowerLimit = 0L;
		String name = "";
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String() throws Exception {
		long arg = 0L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_longIsMinus1L() throws Exception {
		long arg = -1L;
		long lowerLimit = -1L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_longIs0L() throws Exception {
		long arg = 0L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_longIs1L() throws Exception {
		long arg = 1L;
		long lowerLimit = 1L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_longIs2L() throws Exception {
		long arg = 2L;
		long lowerLimit = 2L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_StringIsNull() throws Exception {
		long arg = 0L;
		long lowerLimit = 0L;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$long$long$String_StringIsEmpty() throws Exception {
		long arg = 0L;
		long lowerLimit = 0L;
		String name = "";
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String() throws Exception {
		long arg = -2L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_longIsMinus1L() throws Exception {
		long arg = -2L;
		long upperLimit = -1L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_longIs0L() throws Exception {
		long arg = -1L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_longIs1L() throws Exception {
		long arg = 0L;
		long upperLimit = 1L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_longIs2L() throws Exception {
		long arg = 1L;
		long upperLimit = 2L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_StringIsNull() throws Exception {
		long arg = -1L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$long$long$String_StringIsEmpty() throws Exception {
		long arg = -1L;
		long upperLimit = 0L;
		String name = "";
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String() throws Exception {
		long arg = 0L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_longIsMinus1L() throws Exception {
		long arg = -1L;
		long upperLimit = -1L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_longIs0L() throws Exception {
		long arg = 0L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_longIs1L() throws Exception {
		long arg = 1L;
		long upperLimit = 1L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_longIs2L() throws Exception {
		long arg = 2L;
		long upperLimit = 2L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_StringIsNull() throws Exception {
		long arg = 0L;
		long upperLimit = 0L;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$long$long$String_StringIsEmpty() throws Exception {
		long arg = 0L;
		long upperLimit = 0L;
		String name = "";
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String() throws Exception {
		double arg = 0.5;
		double lowerLimit = 0.0;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_doubleIsMinus1_0D() throws Exception {
		double arg = -0.5D;
		double lowerLimit = -1.0D;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_doubleIs0_0D() throws Exception {
		double arg = 0.5D;
		double lowerLimit = 0.0D;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_doubleIs0_5D() throws Exception {
		double arg = 1.5D;
		double lowerLimit = 0.5D;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_doubleIs1_0D() throws Exception {
		double arg = 1.1D;
		double lowerLimit = 1.0D;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String() throws Exception {
		double arg = 0.0;
		double lowerLimit = 0.0;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_doubleIsMinus1_0D() throws Exception {
		double arg = -1.0D;
		double lowerLimit = -1.0D;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_doubleIs0_0D() throws Exception {
		double arg = 0.0D;
		double lowerLimit = 0.0D;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_doubleIs0_5D() throws Exception {
		double arg = 0.5D;
		double lowerLimit = 0.5D;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_doubleIs1_0D() throws Exception {
		double arg = 1.0D;
		double lowerLimit = 1.0D;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String() throws Exception {
		double arg = -0.01;
		double upperLimit = 0.0;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_doubleIsMinus1_0D() throws Exception {
		double arg = -1.1D;
		double upperLimit = -1.0D;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_doubleIs0_0D() throws Exception {
		double arg = -0.03D;
		double upperLimit = 0.0D;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_doubleIs0_5D() throws Exception {
		double arg = 0.3D;
		double upperLimit = 0.5D;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_doubleIs1_0D() throws Exception {
		double arg = 0.01D;
		double upperLimit = 1.0D;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String() throws Exception {
		double arg = 0.0;
		double upperLimit = 0.0;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_doubleIsMinus1_0D() throws Exception {
		double arg = -1.0D;
		double upperLimit = -1.0D;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_doubleIs0_0D() throws Exception {
		double arg = 0.0D;
		double upperLimit = 0.0D;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_doubleIs0_5D() throws Exception {
		double arg = 0.5D;
		double upperLimit = 0.5D;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_doubleIs1_0D() throws Exception {
		double arg = 1.0D;
		double upperLimit = 1.0D;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_StringIsNull() throws Exception {
		double arg = 1.0;
		double lowerLimit = 0.0;
		String name = null;
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThan_A$double$double$String_StringIsEmpty() throws Exception {
		double arg = 1.0;
		double lowerLimit = 0.0;
		String name = "";
		Assertion.mustBeGreaterThan(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_StringIsNull() throws Exception {
		double arg = 0.0;
		double lowerLimit = 0.0;
		String name = null;
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeGreaterThanOrEqual_A$double$double$String_StringIsEmpty() throws Exception {
		double arg = 0.0;
		double lowerLimit = 0.0;
		String name = "";
		Assertion.mustBeGreaterThanOrEqual(arg, lowerLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_StringIsNull() throws Exception {
		double arg = -0.05;
		double upperLimit = 0.0;
		String name = null;
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThan_A$double$double$String_StringIsEmpty() throws Exception {
		double arg = -0.05;
		double upperLimit = 0.0;
		String name = "";
		Assertion.mustBeLessThan(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_StringIsNull() throws Exception {
		double arg = 0.0;
		double upperLimit = 0.0;
		String name = null;
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

	@Test
	public void mustBeLessThanOrEqual_A$double$double$String_StringIsEmpty() throws Exception {
		double arg = 0.0;
		double upperLimit = 0.0;
		String name = "";
		Assertion.mustBeLessThanOrEqual(arg, upperLimit, name);
	}

}
