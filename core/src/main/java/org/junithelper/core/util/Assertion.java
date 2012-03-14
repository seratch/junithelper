package org.junithelper.core.util;

import org.junithelper.core.exception.JUnitHelperCoreException;

public final class Assertion {

    private String name;

    private Assertion(String name) {
        if (name == null || name.length() == 0) {
            throw new JUnitHelperCoreException("assertion target name must not be empty.");
        }
        this.name = name;
    }

    public static Assertion on(String name) {
        return new Assertion(name);
    }

    public <T> void mustNotBeNull(T arg) {
        if (arg == null) {
            throw new JUnitHelperCoreException(name + " must not be null.");
        }
    }

    public void mustNotBeEmpty(String arg) {
        if (arg == null || arg.length() == 0) {
            throw new JUnitHelperCoreException(name + " must not be empty.");
        }
    }

    public void mustBeGreaterThan(int arg, int lowerLimit) {
        if (arg <= lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
        }
    }

    public void mustBeGreaterThanOrEqual(int arg, int lowerLimit) {
        if (arg < lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
        }
    }

    public void mustBeLessThan(int arg, int upperLimit) {
        if (arg >= upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
        }
    }

    public void mustBeLessThanOrEqual(int arg, int upperLimit) {
        if (arg > upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
        }
    }

    public void mustBeGreaterThan(long arg, long lowerLimit) {
        if (arg <= lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
        }
    }

    public void mustBeGreaterThanOrEqual(long arg, long lowerLimit) {
        if (arg < lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
        }
    }

    public void mustBeLessThan(long arg, long upperLimit) {
        if (arg >= upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
        }
    }

    public void mustBeLessThanOrEqual(long arg, long upperLimit) {
        if (arg > upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
        }
    }

    public void mustBeGreaterThan(double arg, double lowerLimit) {
        if (arg <= lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than " + lowerLimit + ".");
        }
    }

    public void mustBeGreaterThanOrEqual(double arg, double lowerLimit) {
        if (arg < lowerLimit) {
            throw new JUnitHelperCoreException(name + " must not be greater than or equal " + lowerLimit + ".");
        }
    }

    public void mustBeLessThan(double arg, double upperLimit) {
        if (arg >= upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than " + upperLimit + ".");
        }
    }

    public void mustBeLessThanOrEqual(double arg, double upperLimit) {
        if (arg > upperLimit) {
            throw new JUnitHelperCoreException(name + " must not be less than or equal " + upperLimit + ".");
        }
    }

}
