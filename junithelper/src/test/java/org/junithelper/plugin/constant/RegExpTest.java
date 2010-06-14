package org.junithelper.plugin.constant;

import java.util.regex.Matcher;

import junit.framework.TestCase;

public class RegExpTest extends TestCase {

	public void test_groupMethodArgNamePattern() {
		Matcher nameMatcher = RegExp.groupMethodArgNamePattern
				.matcher("boolean hoge");
		if (nameMatcher.find()) {
			assertEquals("hoge", nameMatcher.group(1));
		} else {
			fail("test ng");
		}
	}

}
