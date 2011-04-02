package org.junithelper.core.filter.impl;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void countPreviousContinuedBackslash_A$String$int$int()
            throws Exception {
        // given
        String str = "\\'";
        int currentNotBackslashCharIndex = 1;
        int count = 0;
        // when
        int actual = TrimInsideOfStringFilter.countPreviousContinuedBackslash(
                str, currentNotBackslashCharIndex, count);
        // then
        int expected = 1;
        assertEquals(expected, actual);
    }

}
