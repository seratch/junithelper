package org.junithelper.core.filter;

import org.junit.Test;
import org.junithelper.core.util.IOUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		String src = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Enum_ContentType.txt"),
				"UTF-8");
		String actual = TrimFilterUtil.doAllFilters(src);
		String expected = "package a.b.c;  public enum ContentType {  	relax(), 	nurturing(), 	word();  	private String name; 	 	public String lable;  	private ContentType(String name) {}  	public String toString() {} 	 	public String toLable() {}  } ";
		assertEquals(expected, actual);
	}

}
