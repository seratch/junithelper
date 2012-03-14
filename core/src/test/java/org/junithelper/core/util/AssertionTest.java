package org.junithelper.core.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

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
        Assertion.on("test").mustNotBeNull(arg);
    }

    @Test
    public void mustNotBeEmpty_A$String$String() throws Exception {
        String arg = "abc";
        Assertion.on("test").mustNotBeEmpty(arg);
    }

    @Test
    public void mustBeGreaterThan_A$int$int() throws Exception {
        int arg = 1;
        int lowerLimit = 0;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIsMinus1() throws Exception {
        int arg = 0;
        int lowerLimit = -1;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIs0() throws Exception {
        int arg = 1;
        int lowerLimit = 0;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIs1() throws Exception {
        int arg = 2;
        int lowerLimit = 1;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIs2() throws Exception {
        int arg = 3;
        int lowerLimit = 2;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int() throws Exception {
        int arg = 0;
        int lowerLimit = 0;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIsMinus1() throws Exception {
        int arg = -1;
        int lowerLimit = -1;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIs0() throws Exception {
        int arg = 0;
        int lowerLimit = 0;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIs1() throws Exception {
        int arg = 1;
        int lowerLimit = 1;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIs2() throws Exception {
        int arg = 2;
        int lowerLimit = 2;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int() throws Exception {
        int arg = -1;
        int upperLimit = 0;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIsMinus1() throws Exception {
        int arg = -2;
        int upperLimit = -1;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIs0() throws Exception {
        int arg = -1;
        int upperLimit = 0;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIs1() throws Exception {
        int arg = 0;
        int upperLimit = 1;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIs2() throws Exception {
        int arg = 1;
        int upperLimit = 2;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int() throws Exception {
        int arg = 0;
        int upperLimit = 0;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIsMinus1() throws Exception {
        int arg = -1;
        int upperLimit = -1;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIs0() throws Exception {
        int arg = 0;
        int upperLimit = 0;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIs1() throws Exception {
        int arg = 1;
        int upperLimit = 1;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIs2() throws Exception {
        int arg = 2;
        int upperLimit = 2;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long() throws Exception {
        long arg = 1L;
        long lowerLimit = 0L;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIsMinus1L() throws Exception {
        long arg = 1L;
        long lowerLimit = -1L;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIs0L() throws Exception {
        long arg = 1L;
        long lowerLimit = 0L;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIs1L() throws Exception {
        long arg = 2L;
        long lowerLimit = 1L;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIs2L() throws Exception {
        long arg = 3L;
        long lowerLimit = 2L;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long() throws Exception {
        long arg = 0L;
        long lowerLimit = 0L;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIsMinus1L() throws Exception {
        long arg = -1L;
        long lowerLimit = -1L;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIs0L() throws Exception {
        long arg = 0L;
        long lowerLimit = 0L;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIs1L() throws Exception {
        long arg = 1L;
        long lowerLimit = 1L;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIs2L() throws Exception {
        long arg = 2L;
        long lowerLimit = 2L;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long() throws Exception {
        long arg = -2L;
        long upperLimit = 0L;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIsMinus1L() throws Exception {
        long arg = -2L;
        long upperLimit = -1L;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIs0L() throws Exception {
        long arg = -1L;
        long upperLimit = 0L;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIs1L() throws Exception {
        long arg = 0L;
        long upperLimit = 1L;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIs2L() throws Exception {
        long arg = 1L;
        long upperLimit = 2L;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long() throws Exception {
        long arg = 0L;
        long upperLimit = 0L;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIsMinus1L() throws Exception {
        long arg = -1L;
        long upperLimit = -1L;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIs0L() throws Exception {
        long arg = 0L;
        long upperLimit = 0L;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIs1L() throws Exception {
        long arg = 1L;
        long upperLimit = 1L;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIs2L() throws Exception {
        long arg = 2L;
        long upperLimit = 2L;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeGreaterThan_A$double$double() throws Exception {
        double arg = 0.5;
        double lowerLimit = 0.0;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$double$double_doubleIsMinus1_0D() throws Exception {
        double arg = -0.5D;
        double lowerLimit = -1.0D;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$double$double_doubleIs0_0D() throws Exception {
        double arg = 0.5D;
        double lowerLimit = 0.0D;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$double$double_doubleIs0_5D() throws Exception {
        double arg = 1.5D;
        double lowerLimit = 0.5D;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$double$double_doubleIs1_0D() throws Exception {
        double arg = 1.1D;
        double lowerLimit = 1.0D;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$double$double() throws Exception {
        double arg = 0.0;
        double lowerLimit = 0.0;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$double$double_doubleIsMinus1_0D() throws Exception {
        double arg = -1.0D;
        double lowerLimit = -1.0D;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$double$double_doubleIs0_0D() throws Exception {
        double arg = 0.0D;
        double lowerLimit = 0.0D;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$double$double_doubleIs0_5D() throws Exception {
        double arg = 0.5D;
        double lowerLimit = 0.5D;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$double$double_doubleIs1_0D() throws Exception {
        double arg = 1.0D;
        double lowerLimit = 1.0D;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$double$double() throws Exception {
        double arg = -0.01;
        double upperLimit = 0.0;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$double$double_doubleIsMinus1_0D() throws Exception {
        double arg = -1.1D;
        double upperLimit = -1.0D;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$double$double_doubleIs0_0D() throws Exception {
        double arg = -0.03D;
        double upperLimit = 0.0D;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$double$double_doubleIs0_5D() throws Exception {
        double arg = 0.3D;
        double upperLimit = 0.5D;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$double$double_doubleIs1_0D() throws Exception {
        double arg = 0.01D;
        double upperLimit = 1.0D;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$double$double() throws Exception {
        double arg = 0.0;
        double upperLimit = 0.0;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$double$double_doubleIsMinus1_0D() throws Exception {
        double arg = -1.0D;
        double upperLimit = -1.0D;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$double$double_doubleIs0_0D() throws Exception {
        double arg = 0.0D;
        double upperLimit = 0.0D;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$double$double_doubleIs0_5D() throws Exception {
        double arg = 0.5D;
        double upperLimit = 0.5D;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$double$double_doubleIs1_0D() throws Exception {
        double arg = 1.0D;
        double upperLimit = 1.0D;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIsMinValue() throws Exception {
        int arg = Integer.MIN_VALUE + 1;
        int lowerLimit = Integer.MIN_VALUE;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIsMinValue() throws Exception {
        int arg = Integer.MIN_VALUE;
        int lowerLimit = Integer.MIN_VALUE;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIsMaxValue() throws Exception {
        int arg = Integer.MAX_VALUE;
        int lowerLimit = Integer.MAX_VALUE;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIsMinValue() throws Exception {
        int arg = Integer.MIN_VALUE;
        int upperLimit = Integer.MIN_VALUE + 1;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIsMaxValue() throws Exception {
        int arg = Integer.MAX_VALUE - 1;
        int upperLimit = Integer.MAX_VALUE;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIsMinValue() throws Exception {
        int arg = Integer.MIN_VALUE;
        int upperLimit = Integer.MIN_VALUE;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIsMaxValue() throws Exception {
        int arg = Integer.MAX_VALUE;
        int upperLimit = Integer.MAX_VALUE;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIsMaxValue() throws Exception {
        int arg = Integer.MAX_VALUE;
        int lowerLimit = Integer.MAX_VALUE - 1;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIsMinValue() throws Exception {
        long arg = Long.MIN_VALUE + 1;
        long lowerLimit = Long.MIN_VALUE;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIsMinValue() throws Exception {
        long arg = Long.MIN_VALUE;
        long lowerLimit = Long.MIN_VALUE;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$long$long_longIsMaxValue() throws Exception {
        long arg = Long.MAX_VALUE;
        long lowerLimit = Long.MAX_VALUE;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIsMinValue() throws Exception {
        long arg = Long.MIN_VALUE;
        long upperLimit = Long.MIN_VALUE + 1;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThan_A$long$long_longIsMaxValue() throws Exception {
        long arg = Long.MAX_VALUE - 1;
        long upperLimit = Long.MAX_VALUE;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIsMinValue() throws Exception {
        long arg = Long.MIN_VALUE;
        long upperLimit = Long.MIN_VALUE;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$long$long_longIsMaxValue() throws Exception {
        long arg = Long.MAX_VALUE;
        long upperLimit = Long.MAX_VALUE;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void mustBeGreaterThan_A$long$long_longIsMaxValue() throws Exception {
        long arg = Long.MAX_VALUE;
        long lowerLimit = Long.MAX_VALUE - 1;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThan_A$int$int_intIsRandom() throws Exception {
        int arg = new Random().nextInt(10);
        int lowerLimit = arg - 1;
        Assertion.on("test").mustBeGreaterThan(arg, lowerLimit);
    }

    @Test
    public void mustBeGreaterThanOrEqual_A$int$int_intIsRandom() throws Exception {
        int arg = new Random().nextInt(10);
        int lowerLimit = arg - 1;
        Assertion.on("test").mustBeGreaterThanOrEqual(arg, lowerLimit);
    }

    @Test
    public void mustBeLessThan_A$int$int_intIsRandom() throws Exception {
        int arg = new Random().nextInt(10);
        int upperLimit = arg + 1;
        Assertion.on("test").mustBeLessThan(arg, upperLimit);
    }

    @Test
    public void mustBeLessThanOrEqual_A$int$int_intIsRandom() throws Exception {
        int arg = new Random().nextInt(10);
        int upperLimit = arg + 1;
        Assertion.on("test").mustBeLessThanOrEqual(arg, upperLimit);
    }

    @Test
    public void on_A$String() throws Exception {
        String name_ = "test";
        Assertion actual = Assertion.on(name_);
        assertThat(actual, notNullValue());
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void on_A$String_StringIsNull() throws Exception {
        String name_ = null;
        Assertion.on(name_);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void on_A$String_StringIsEmpty() throws Exception {
        String name_ = "";
        Assertion.on(name_);
    }

    @Test
    public void mustNotBeNull_A$Object() throws Exception {
        Object arg = new Object();
        Assertion.on("test").mustNotBeNull(arg);
    }

    @Test
    public void mustNotBeEmpty_A$String() throws Exception {
        String arg = "v";
        Assertion.on("test").mustNotBeEmpty(arg);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void mustNotBeEmpty_A$String_StringIsNull() throws Exception {
        String arg = null;
        Assertion.on("test").mustNotBeEmpty(arg);
    }

    @Test(expected = JUnitHelperCoreException.class)
    public void mustNotBeEmpty_A$String_StringIsEmpty() throws Exception {
        String arg = "";
        Assertion.on("test").mustNotBeEmpty(arg);
    }

}
