package org.junithelper.core.filter.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class TrimInsideOfBraceFilterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimInsideOfBraceFilter.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
        assertNotNull(target);
    }

    @Test
    public void trimAll_A$String() throws Exception {
        TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
        String src = "public class Hoge { { } static { System.out.println(\"aaa\"); } public static void main(String[] args) { // hogehoge\r\n System.out.println(\"Hello, World!\");\r\n } }";
        String actual = target.trimAll(src);
        String expected = "public class Hoge { {} static {\"\"} public static void main(String[] args) {\"\"} }";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_AnsiDialectTest() throws Exception {
        TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
        String src = "String result = new AnsiDialect().createLogStatement(\"SELECT ? FROM dual WHERE blah = ? \",new String[] {\"blah?blah\", \"blah2'\"}, 2);";
        String actual = target.trimAll(src);
        String expected = "String result = new AnsiDialect().createLogStatement(\"\",new String[] {\"\", \"\"}, 2);";
        assertEquals(expected, actual);
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int() throws Exception {
        // given
        String str = null;
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        // when
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        // then
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_StringIsNull() throws Exception {
        TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
        String src = null;
        String actual = target.trimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAll_A$String_StringIsEmpty() throws Exception {
        TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
        String src = "";
        String actual = target.trimAll(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsMinus1() throws Exception {
        String str = null;
        int currentNotBackslashCharIndex = -1;
        int count = -1;
        try {
            TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs0() throws Exception {
        String str = null;
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs1() throws Exception {
        String str = "test";
        int currentNotBackslashCharIndex = 1;
        int count = 1;
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 1;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs2() throws Exception {
        String str = "test";
        int currentNotBackslashCharIndex = 2;
        int count = 2;
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 2;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_StringIsNull() throws Exception {
        String str = "test";
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_StringIsEmpty() throws Exception {
        String str = "";
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsMinValue() throws Exception {
        String str = "";
        int currentNotBackslashCharIndex = Integer.MIN_VALUE;
        int count = Integer.MIN_VALUE;
        try {
            TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsMaxValue() throws Exception {
        String str = "";
        int currentNotBackslashCharIndex = Integer.MAX_VALUE;
        int count = Integer.MAX_VALUE;
        try {
            TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
            fail();
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsRandom() throws Exception {
        String str = "abcdefghijklmnopqrstuvwxyz";
        int currentNotBackslashCharIndex = new Random().nextInt(10);
        int count = new Random().nextInt(10);
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        assertThat(actual, is(greaterThanOrEqualTo(0)));
        assertThat(actual, is(lessThanOrEqualTo(Integer.MAX_VALUE)));
    }

}
