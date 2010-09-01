/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.plugin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.junithelper.plugin.constant.RegExp;
import org.junithelper.plugin.constant.StrConst;

/**
 * Source code parse util
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class SourceCodeParseUtil {

	/**
	 * Get source code lines.
	 * 
	 * @param javaFile
	 * @return source code lines
	 * @throws Exception
	 */
	public static List<String> getLineList(IFile javaFile) throws Exception {
		List<String> lines = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			// detect charset
			is = FileResourceUtil.readFile(javaFile);
			String encoding = FileResourceUtil.detectEncoding(javaFile);
			// read file
			is = FileResourceUtil.readFile(javaFile);
			br = new BufferedReader(new InputStreamReader(is, encoding));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.replace(StrConst.carriageReturn, StrConst.empty);
				lines.add(line);
			}
		} finally {
			FileResourceUtil.close(is);
			FileResourceUtil.close(br);
		}
		return lines;
	}

	/**
	 * Trim the line comment areas from source code string.
	 * 
	 * @param source
	 * @return result without line comments
	 */
	public static String trimLineComments(String source) {
		return source.replaceAll("^[\\s\\t]*//[^\n]+\n", StrConst.empty);
	}

	/**
	 * Trim the all comment areas from source code string.
	 * 
	 * @param source
	 * @return result without line comments
	 */
	public static String trimAllComments(String source) {
		String withoutLineComments = trimLineComments(source);
		String withoutLineBreak = withoutLineComments.replaceAll("\r|\n",
				StrConst.empty);
		String withoutComments = withoutLineBreak.replaceAll("/\\*.*?\\*/",
				StrConst.empty);
		return withoutComments;
	}

	/**
	 * trim inside of the second level braces.
	 * 
	 * @param source
	 * @return
	 */
	public static String trimInsideOfBraces(String source) {
		int len = source.length();
		boolean isInsideOfString = false;
		boolean isInsideOfChar = false;
		boolean isInsideOfTargetClass = false;
		boolean isInsideOfFirstLevel = false;
		boolean isInsideOfSecondLevel = false;
		Stack<Character> braceStack = new Stack<Character>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			char current = source.charAt(i);
			// check inside of String or char
			if (i > 0) {
				if (current == '"' && source.charAt(i - 1) != '\\') {
					isInsideOfString = (isInsideOfString) ? false : true;
					sb.append(current);
					continue;
				}
				if (current == '\'' && source.charAt(i - 1) != '\\') {
					isInsideOfChar = (isInsideOfChar) ? false : true;
					sb.append(current);
					continue;
				}
			}
			if (isInsideOfChar || isInsideOfString) {
				continue;
			}
			// waiting for inside of the target class
			if (!isInsideOfTargetClass) {
				sb.append(current);
				// might be started class def
				if (i >= 6 && source.charAt(i - 6) == ' '
						&& source.charAt(i - 5) == 'c'
						&& source.charAt(i - 4) == 'l'
						&& source.charAt(i - 3) == 'a'
						&& source.charAt(i - 2) == 's'
						&& source.charAt(i - 1) == 's' && current == ' ') {
					isInsideOfTargetClass = true;
				}
				continue;
			}
			// waiting for inside of the first level brace
			if (!isInsideOfFirstLevel) {
				sb.append(current);
				if (current == '{') {
					isInsideOfFirstLevel = true;
				}
				continue;
			}
			// excluding inside of top brace
			// outer of top braced
			if (!isInsideOfSecondLevel) {
				sb.append(current);
			}
			// brace start
			if (current == '{') {
				isInsideOfSecondLevel = true;
				braceStack.push(current);
			}
			// brace end
			if (!braceStack.empty() && current == '}') {
				braceStack.pop();
				if (braceStack.empty()) {
					sb.append(current);
				}
			}
			// check the brace stack state
			if (braceStack.empty()) {
				isInsideOfSecondLevel = false;
			}
		}
		return sb.toString();
	}

	protected static boolean matchesPublic(String arg) {
		return arg.matches(searchTargetMethodRegExpPrefix + "public"
				+ searchTargetMethodRegExpPostfix);
	}

	protected static boolean matchesProtected(String arg) {
		return arg.matches(searchTargetMethodRegExpPrefix + "protected"
				+ searchTargetMethodRegExpPostfix);
	}

	protected static boolean matchesPackageLocal(String arg) {
		return !arg.matches(searchTargetMethodRegExpPrefix + "public"
				+ searchTargetMethodRegExpPostfix)
				&& !arg.matches(searchTargetMethodRegExpPrefix + "protected"
						+ searchTargetMethodRegExpPostfix)
				&& !arg.matches(searchTargetMethodRegExpPrefix + "private"
						+ searchTargetMethodRegExpPostfix);
	}

	protected static boolean matchesPrivate(String arg) {
		return arg.matches(searchTargetMethodRegExpPrefix + "private"
				+ searchTargetMethodRegExpPostfix);
	}

	private static String searchTargetMethodRegExpPrefix = "[\\{;\\}](\\s*.*\\s+)?";
	private static String searchTargetMethodRegExpPostfix = "\\s+?.*\\{";

	/**
	 * Get target constructors from source code string
	 * 
	 * @param source
	 * @return result
	 */
	public static List<String> getTargetConstructors(String className,
			String source, boolean publicRequired, boolean protectedRequired,
			boolean packageLocalRequired) {
		List<String> result = new ArrayList<String>();
		List<String> methods = getTargetMethods(source, publicRequired,
				protectedRequired, packageLocalRequired);
		for (String method : methods) {
			String matchesConstructors = RegExp.wsAsteriskMax + className
					+ "\\(([^\\)]*?)\\)" + RegExp.wsAsteriskMax
					+ "(throws .+)*.*?" + RegExp.wsAsteriskMax + "\\{.*";
			if (method.matches(matchesConstructors)) {
				result.add(StrConst.space + method);
			}
		}
		return result;
	}

	/**
	 * Get target methods from source code string
	 * 
	 * @param source
	 * @return result
	 */
	public static List<String> getTargetMethods(String source,
			boolean publicRequired, boolean protectedRequired,
			boolean packageLocalRequired) {
		source = source.replaceAll("\\s+?" + StrConst.comma, StrConst.comma)
				.replaceAll(StrConst.comma + "\\s+?", StrConst.comma)
				.replaceAll("<\\s+?", "<").replaceAll("\\s+?>", ">");
		List<String> result = new ArrayList<String>();
		// for method signature
		String regexp = "[\\{;\\}][^\\{;\\}]+?\\([^\\{;\\}]*?\\)[^\\{;\\}]+?\\{";
		Pattern pat = Pattern.compile(regexp);
		Matcher mat = pat.matcher(source);
		while (mat.find()) {
			String matched = mat.group(0)
					.replaceAll(StrConst.carriageReturn, StrConst.empty)
					.replaceAll(StrConst.lineFeed, StrConst.space);
			// skip public methods
			if (!publicRequired && matchesPublic(matched)) {
				continue;
			}
			// skip protected methods
			if (!protectedRequired && matchesProtected(matched)) {
				continue;
			}
			// skip package local methods
			if (!packageLocalRequired && matchesPackageLocal(matched)) {
				continue;
			}
			// skip private method by default
			if (matchesPrivate(matched)) {
				continue;
			}
			matched = matched
					.replaceAll(StrConst.tab, StrConst.space)
					.replaceAll(
							searchTargetMethodRegExpPrefix + "public" + "\\s",
							StrConst.empty)
					.replaceAll(
							searchTargetMethodRegExpPrefix + "protected"
									+ "\\s", StrConst.empty)
					.replaceAll(searchTargetMethodRegExpPrefix + "\\s+?",
							StrConst.empty)
					.replaceAll("\\sfinal\\s", StrConst.space);
			result.add(StrConst.space + matched);
		}
		return result;
	}

}