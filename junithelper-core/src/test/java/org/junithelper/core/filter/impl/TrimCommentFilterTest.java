package org.junithelper.core.filter.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrimCommentFilterTest {

	@Test
	public void trimAll_A$String() throws Exception {
		TrimCommentFilter target = new TrimCommentFilter();
		String src = "// hogehoge \r\npublic void do(String aaa) {\r\n /** aaa\r\n */ /* \r\nbbb */";
		String actual = target.trimAll(src);
		String expected = " public void do(String aaa) {  ";
		assertEquals(expected, actual);
	}

}
