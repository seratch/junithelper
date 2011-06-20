package org.junithelper.core.util;

import org.junithelper.core.exception.JUnitHelperCoreException;

public final class Assertion {

	private Assertion() {
	}

	public static <T> void mustNotBeNull(T arg, String name) {
		if (arg == null) {
			throw new JUnitHelperCoreException(name + " must not be null.");
		}
	}

	public static void mustNotBeEmpty(String arg, String name) {
		if (arg == null || arg.length() == 0) {
			throw new JUnitHelperCoreException(name + " must not be empty.");
		}
	}

	public static void mustBeGreaterThan(int arg, int lowerLimit, String name) {
		if (arg <= lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
		}
	}

	public static void mustBeGreaterThanOrEqual(int arg, int lowerLimit, String name) {
		if (arg < lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
		}
	}

	public static void mustBeLessThan(int arg, int upperLimit, String name) {
		if (arg >= upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
		}
	}

	public static void mustBeLessThanOrEqual(int arg, int upperLimit, String name) {
		if (arg > upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
		}
	}

	public static void mustBeGreaterThan(long arg, long lowerLimit, String name) {
		if (arg <= lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
		}
	}

	public static void mustBeGreaterThanOrEqual(long arg, long lowerLimit, String name) {
		if (arg < lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
		}
	}

	public static void mustBeLessThan(long arg, long upperLimit, String name) {
		if (arg >= upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
		}
	}

	public static void mustBeLessThanOrEqual(long arg, long upperLimit, String name) {
		if (arg > upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
		}
	}

	public static void mustBeGreaterThan(double arg, double lowerLimit, String name) {
		if (arg <= lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
		}
	}

	public static void mustBeGreaterThanOrEqual(double arg, double lowerLimit, String name) {
		if (arg < lowerLimit) {
			throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
		}
	}

	public static void mustBeLessThan(double arg, double upperLimit, String name) {
		if (arg >= upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
		}
	}

	public static void mustBeLessThanOrEqual(double arg, double upperLimit, String name) {
		if (arg > upperLimit) {
			throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
		}
	}

}
