package org.junithelper.core.filter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junithelper.core.util.IOUtil;

public class TrimFilterUtilTest {

    @Test
    public void type() throws Exception {
        assertNotNull(TrimFilterUtil.class);
    }

    @Test
    public void instantiation() throws Exception {
        TrimFilterUtil target = new TrimFilterUtil();
        assertNotNull(target);
    }

    @Test
    public void doAllFilters_A$String() throws Exception {
        String src = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
        String actual = TrimFilterUtil.doAllFilters(src);
        String expected = "package hoge.foo;  public class Sample { public Sample() {}  public void doSomething(String str) {} }";
        assertEquals(expected, actual);
    }

    @Test
    public void doAllFilters_A$String_Enum_ContentType() throws Exception {
        String src = IOUtil.readAsString(IOUtil.getResourceAsStream("inputs/Enum_ContentType.txt"), "UTF-8");
        String actual = TrimFilterUtil.doAllFilters(src);
        String expected = "package a.b.c;  public enum ContentType {  	relax(), 	nurturing(), 	word();  	private String name; 	 	public String lable;  	private ContentType(String name) {}  	public String toString() {} 	 	public String toLable() {}  } ";
        assertEquals(expected, actual);
    }

    @Test
    public void doAllFilters_A$String_StringIsNull() throws Exception {
        String src = null;
        String actual = TrimFilterUtil.doAllFilters(src);
        String expected = null;
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void doAllFilters_A$String_StringIsEmpty() throws Exception {
        String src = "";
        String actual = TrimFilterUtil.doAllFilters(src);
        String expected = "";
        assertThat(actual, is(equalTo(expected)));
    }

}
