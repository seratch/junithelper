package org.junithelper.core.filter.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junithelper.core.exception.JUnitHelperCoreException;

public class TrimInsideOfStringFilterTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimInsideOfStringFilter.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimInsideOfStringFilter target = new TrimInsideOfStringFilter();
        assertNotNull(target);
    }

    @Test
    public void trimAll_A$String() throws Exception {
        TrimInsideOfStringFilter target = new TrimInsideOfStringFilter();
        // given
        String src = "public class Hoge { { } static { System.out.println(\"aaa\"); } public static void main(String[] args) { // hogehoge\r\n System.out.println(\"Hello, World!\");\r\n } }";
        // when
        String actual = target.trimAll(src);
        // then
        String expected = "public class Hoge { { } static { System.out.println(\"\"); } public static void main(String[] args) { // hogehoge\r\n System.out.println(\"\");\r\n } }";
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_doubleQuateInChar() throws Exception {
        TrimInsideOfStringFilter target = new TrimInsideOfStringFilter();
        // given
        String src = "specialCharactersRepresentation['\"'] = \"&#034;\".toCharArray();specialCharactersRepresentation['\\''] = \"&#039;\".toCharArray();";
        // when
        String actual = target.trimAll(src);
        // then
        String expected = "specialCharactersRepresentation[''] = \"\".toCharArray();specialCharactersRepresentation[''] = \"\".toCharArray();";
        assertEquals(expected, actual);
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int() throws Exception {
        // given
        String str = "\\'";
        int currentNotBackslashCharIndex = 1;
        int count = 0;
        // when
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        // then
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void trimAll_A$String_StringIsNull() throws Exception {
        TrimInsideOfStringFilter target = new TrimInsideOfStringFilter();
        String src = null;
        String actual = target.trimAll(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void trimAll_A$String_StringIsEmpty() throws Exception {
        TrimInsideOfStringFilter target = new TrimInsideOfStringFilter();
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
            TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
            fail();
        } catch (JUnitHelperCoreException e) {
        }
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs0() throws Exception {
        String str = null;
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs1() throws Exception {
        String str = "test";
        int currentNotBackslashCharIndex = 1;
        int count = 1;
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 1;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIs2() throws Exception {
        String str = "test";
        int currentNotBackslashCharIndex = 2;
        int count = 2;
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 2;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_StringIsNull() throws Exception {
        String str = null;
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_StringIsEmpty() throws Exception {
        String str = "";
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        int expected = 0;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsMinValue() throws Exception {
        String str = "";
        int currentNotBackslashCharIndex = Integer.MIN_VALUE;
        int count = Integer.MIN_VALUE;
        try {
            TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
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
            TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
            fail();
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void countPreviousContinuedBackslash_A$String$int$int_intIsRandom() throws Exception {
        String str = "123456789012345";
        int currentNotBackslashCharIndex = new Random().nextInt(10);
        int count = new Random().nextInt(10);
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(str, currentNotBackslashCharIndex, count);
        assertThat(actual, is(greaterThanOrEqualTo(0)));
        assertThat(actual, is(lessThanOrEqualTo(Integer.MAX_VALUE)));
    }

}
