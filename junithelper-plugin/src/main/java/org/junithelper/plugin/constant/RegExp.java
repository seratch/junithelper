package org.junithelper.plugin.constant;

import java.util.regex.Pattern;

public final class RegExp {

	public static final String javaFileExp = "\\.java";

	public static final String crlf = "[\\r\\n]";

	public static final String wsPlusMax = "\\s+";
	public static final String wsAsteriskMax = "\\s*";
	public static final String notWsPlusMin = "[^\\s]+?";
	public static final String notWsAsteriskMin = "[^\\s]*?";

	public static final String methodModifiers = "[<\\w+?>|static|final|\\s]*";
	public static final String methodModifiersExcludedStatic = "[<\\w+?>|final|\\s]*";
	public static final String methodReturnType = "[a-zA-Z1-9\\[\\]_,\\$<>\\.]+?";
	public static final String generics = "<[^>]+?>";
	public static final String genericsGroup = "<([a-zA-Z0-9,\\$_\\?]+?)>";

	/**
	 * Regular expression to search method syntax
	 */
	public static String matchesMethod = "";
	static {
		matchesMethod = wsAsteriskMax + methodModifiers + wsPlusMax
				+ methodReturnType + wsPlusMax + notWsPlusMin + wsAsteriskMax
				+ "\\(" + "[^\\)]*?" + "\\)" + wsAsteriskMax + ".*?"
				+ wsAsteriskMax + "\\{.*";
	}

	/**
	 * Regular expression to search static method syntax
	 */
	public static String matchesStaticMethod = "";
	static {
		matchesStaticMethod = wsAsteriskMax + methodModifiersExcludedStatic
				+ "static" + methodModifiersExcludedStatic + notWsPlusMin
				+ wsPlusMax + notWsPlusMin + wsAsteriskMax + "\\(" + "[^\\)]*?"
				+ "\\)" + wsAsteriskMax + ".*?" + wsAsteriskMax + "\\{.*";
	}

	/**
	 * Regular expression to search method syntax grouped<br>
	 * $1 : return value <br>
	 * $2 : method name <br>
	 * $3 : args<br>
	 */
	public static String groupMethod = "";
	static {
		groupMethod = wsAsteriskMax + methodModifiers + "\\s+("
				+ methodReturnType + ")" + wsPlusMax + "(" + notWsPlusMin + ")"
				+ wsAsteriskMax + "\\((" + "[^\\)]*?" + ")\\)" + wsAsteriskMax
				+ "(throws .+)*.*?" + wsAsteriskMax + "\\{.*";
	}
	public static Pattern groupMethodPattern = Pattern.compile(groupMethod);

	/**
	 * Regular expression to search method arg name grouped<br>
	 * $1 : arg name
	 */
	public static String groupMethodArgName = "";
	static {
		groupMethodArgName = notWsPlusMin + wsPlusMax + "(" + notWsPlusMin
				+ "$)";
	}
	public static Pattern groupMethodArgNamePattern = Pattern
			.compile(groupMethodArgName);

}
