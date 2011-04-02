package org.junithelper.core.filter.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void countPreviousContinuedBackslash_A$String$int$int()
            throws Exception {
        // given
        String str = null;
        int currentNotBackslashCharIndex = 0;
        int count = 0;
        // when
        int actual = TrimInsideOfBraceFilter.countPreviousContinuedBackslash(
                str, currentNotBackslashCharIndex, count);
        // then
        int expected = 0;
        assertEquals(expected, actual);
    }

}
