package org.junithelper.core.filter.impl;

import static org.junit.Assert.*;
import org.junit.Test;

public class TrimCommentFilterTest {

	@Test
	public void type() throws Exception {
		assertNotNull(TrimCommentFilter.class);
	}

	@Test
	public void instantiation() throws Exception {
		TrimCommentFilter target = new TrimCommentFilter();
		assertNotNull(target);
	}

	@Test
	public void trimAll_A$String() throws Exception {
		TrimCommentFilter target = new TrimCommentFilter();
		String src = "// hogehoge \r\npublic void do(String aaa) {\r\n /** aaa\r\n */ /* \r\nbbb */";
		String actual = target.trimAll(src);
		String expected = " public void do(String aaa) {  ";
		assertEquals(expected, actual);
	}

}
