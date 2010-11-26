package org.junithelper.plugin.constant;

import java.util.regex.Matcher;

import junit.framework.TestCase;

public class RegExpTest extends TestCase {

	public void test_matchesMethod() {

		String[] expectedFalse = new String[] {
				"",
				"public RegExp() { ",
				"String get(String name) { ",
				"String get(String name) { ",
				"public static String matchesMethod",
				"	static \\{",
				"/\\*\\* public void run(IAction action, ISelection selection) \\*\\*/",
				"/\\*\\* public void run(List<String> list, Map<String,Object> values) \\*\\*/",
				"public void run(String name)",

		};
		for (int i = 0; i < expectedFalse.length; i++) {
			assertFalse(expectedFalse[i], expectedFalse[i]
					.matches(RegExp.matchesMethod));
		}

		String[] expectedTrue = new String[] {
				" String get(String name)		 { ",
				"public void run(String name){ ",
				"public void  run( String name )		 { ",
				"public static void main(String[] args) { ",
				"public 		static final void main(String[] args) { ",
				"public static final void main(String[] args) { ",
				"static public final void main(String[]		 args) { ",
				"final public static void main(String[] args) { ",
				"private String run(String name) { ",
				"protected String 		run(String name) { ",
				"final String run(String name) 			{ ",
				"public boolean 		run(List<String> array, Map<String,Object> map){ ",
				"public			 boolean run(Map<String,Object> map) 		   { } ",
				"public static boolean 		run(Map<String,Object> map)  	  { } ",

		};
		for (int i = 0; i < expectedTrue.length; i++) {
			assertTrue(expectedTrue[i], expectedTrue[i]
					.matches(RegExp.matchesMethod));
		}

	}

	public void test_matchesStaticMethod() {

		String[] expectedFalse = new String[] {
				"",
				"public RegExp() { ",
				"String get(String name) { ",
				"public static String matchesMethod",
				"	static \\{",
				"/\\*\\* public void run(IAction action, ISelection selection) \\*\\*/",
				"/\\*\\* public void run(List<String> list, Map<String,Object> values) \\*\\*/",
				"public void run(String name)",
				" String get(String name) { ",
				"public void run(String name){ ",
				"public void  run( String name ) { ",
				"private String run(String name) { ",
				"protected String run(String name) { ",
				"final String run(String name) { ",
				"public boolean run(List<String> array, Map<String,Object> map){ ",
				"public boolean run(Map<String,Object> map)    { } ",

		};
		for (int i = 0; i < expectedFalse.length; i++) {
			assertFalse(expectedFalse[i], expectedFalse[i]
					.matches(RegExp.matchesStaticMethod));
		}

		String[] expectedTrue = new String[] {
				"public static 		void main(String[] args) { ",
				"public static final void 		main(String[] args)		 { ",
				"public static final 	void main(String[] args)		 { ",
				"static public final void main(String[] args) { ",
				"final		 public static void main(String[] args) 			{ ",
				"public static boolean 		run(Map<String,Object> map)  	  { } ",

		};
		for (int i = 0; i < expectedTrue.length; i++) {
			assertTrue(expectedTrue[i], expectedTrue[i]
					.matches(RegExp.matchesStaticMethod));
		}
	}

	public void test_groupMethodPattern() {
		Matcher methodMatcher = RegExp.groupMethodPattern
				.matcher("public static boolean 		run(    Map<String,Object>  	   map  )  	  { }");
		if (methodMatcher.find()) {
			assertEquals("boolean", methodMatcher.group(1));
			assertEquals("run", methodMatcher.group(2));
			assertEquals("    Map<String,Object>  	   map  ", methodMatcher
					.group(3));
		} else {
			fail("not matched!");
		}
		methodMatcher = RegExp.groupMethodPattern
				.matcher("public static boolean 		run(  String hoge ,  Map<String,Object>  	   map  )  	  { }");
		if (methodMatcher.find()) {
			assertEquals("boolean", methodMatcher.group(1));
			assertEquals("run", methodMatcher.group(2));
			assertEquals("  String hoge ,  Map<String,Object>  	   map  ",
					methodMatcher.group(3));
		} else {
			fail("not matched!");
		}
	}

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
