package org.junithelper.core.filter.impl;

import static org.junit.Assert.*;
import org.junit.Test;

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

}
