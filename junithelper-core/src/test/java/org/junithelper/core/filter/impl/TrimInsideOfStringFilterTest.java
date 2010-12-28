package org.junithelper.core.filter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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

}
