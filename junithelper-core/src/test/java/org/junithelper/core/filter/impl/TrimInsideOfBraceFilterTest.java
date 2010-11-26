package org.junithelper.core.filter.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrimInsideOfBraceFilterTest {

	@Test
	public void trimAll_A$String() throws Exception {
		TrimInsideOfBraceFilter target = new TrimInsideOfBraceFilter();
		String src = "public class Hoge { { } static { System.out.println(\"aaa\"); } public static void main(String[] args) { // hogehoge\r\n System.out.println(\"Hello, World!\");\r\n } }";
		String actual = target.trimAll(src);
		String expected = "public class Hoge { {} static {\"\"} public static void main(String[] args) {\"\"} }";
		assertEquals(expected, actual);
	}

}
