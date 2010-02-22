package org.junithelper.plugin.constant;

import java.util.regex.Pattern;

public class RegExp {

	public static final String javaFileExp = "\\.java";

	public static final String wsReq = "\\s+";
	public static final String wsNotReq = "\\s*";
	public static final String notWsReq = "[^\\s]+?";
	public static final String notWsNotReq = "[^\\s]*?";

	public static final String methodModifiers = "[<\\w+?>|static|final|\\s]*";
	public static final String methodModifiersExcludedStatic = "[<\\w+?>|final|\\s]*";
	public static final String methodReturnType = "[a-zA-Z1-9\\[\\]_,\\$<>\\.]+?";
	public static final String generics = "<[a-zA-Z0-9,\\$_\\?]+?>";
	public static final String genericsGroup = "<([a-zA-Z0-9,\\$_\\?]+?)>";

	/**
	 * Regular expression to search method syntax
	 */
	public static String matchesMethod = "";
	static {
		matchesMethod = wsNotReq + methodModifiers + wsReq + methodReturnType
				+ wsReq + notWsReq + wsNotReq + "\\(" + "[^\\)]*?" + "\\)"
				+ wsNotReq + ".*?" + wsNotReq + "\\{.*";
	}

	/**
	 * Regular expression to search static method syntax
	 */
	public static String matchesStaticMethod = "";
	static {
		matchesStaticMethod = wsNotReq + methodModifiersExcludedStatic
				+ "static" + methodModifiersExcludedStatic + notWsReq + wsReq
				+ notWsReq + wsNotReq + "\\(" + "[^\\)]*?" + "\\)" + wsNotReq
				+ ".*?" + wsNotReq + "\\{.*";
	}

	/**
	 * Regular expression to search method syntax grouped<br>
	 * $1 : return value <br>
	 * $2 : method name <br>
	 * $3 : args<br>
	 */
	public static String groupMethod = "";
	static {
		groupMethod = wsNotReq + methodModifiers + "\\s+(" + methodReturnType
				+ ")" + wsReq + "(" + notWsReq + ")" + wsNotReq + "\\(("
				+ "[^\\)]*?" + ")\\)" + wsNotReq + "(throws .+)*.*?" + wsNotReq
				+ "\\{.*";
	}
	public static Pattern groupMethodPattern = Pattern.compile(groupMethod);

}
