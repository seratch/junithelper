package org.junithelper.core.filter;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrimFilterUtilTest {

	@Test
	public void doAllFilters_A$String() throws Exception {
		String src = "package hoge.foo; @SuppressWarnings(value = { \"issue 28\" })public class Sample { public Sample() {}\r\n public void doSomething(String str) { System.out.println(\"aaaa\") } }";
		String actual = TrimFilterUtil.doAllFilters(src);
		String expected = "package hoge.foo;  public class Sample { public Sample() {} public void doSomething(String str) {} }";
		assertEquals(expected, actual);
	}

}
