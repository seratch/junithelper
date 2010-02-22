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
import org.junithelper.plugin.constant.StrConst;

/**
 * Source code parse util<br>
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
	 * Trim the line comment areas from source code string
	 * 
	 * @param source
	 * @return result without line comments
	 */
	public static String trimLineComments(String source) {
		return source.replaceAll("//.+?\n", StrConst.empty);
	}

	/**
	 * Trim the all comment areas from source code string
	 * 
	 * @param source
	 * @return result without line comments
	 */
	public static String trimAllComments(String source) {
		return trimLineComments(source
				.replaceAll("/\\*.+?\\*/", StrConst.empty));
	}

	/**
	 * trim inside of the second level braces.
	 * 
	 * @param source
	 * @return
	 */
	public static String trimInsideOfBraces(String source) {
		int len = source.length();
		boolean isInsideOfTargetClass = false;
		boolean isInsideOfFirstLevel = false;
		boolean isInsideOfSecondLevel = false;
		Stack<Character> braceStack = new Stack<Character>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			// waiting for inside of the target class
			if (!isInsideOfTargetClass) {
				sb.append(source.charAt(i));
				// might be started class def
				if (i >= 6 && source.charAt(i - 6) == ' '
						&& source.charAt(i - 5) == 'c'
						&& source.charAt(i - 4) == 'l'
						&& source.charAt(i - 3) == 'a'
						&& source.charAt(i - 2) == 's'
						&& source.charAt(i - 1) == 's'
						&& source.charAt(i) == ' ') {
					isInsideOfTargetClass = true;
				}
				continue;
			}
			// waiting for inside of the first level brace
			if (!isInsideOfFirstLevel) {
				sb.append(source.charAt(i));
				if (source.charAt(i) == '{') {
					isInsideOfFirstLevel = true;
				}
				continue;
			}
			// excluding inside of top brace
			// outer of top braced
			if (!isInsideOfSecondLevel) {
				sb.append(source.charAt(i));
			}
			// brace start
			if (source.charAt(i) == '{') {
				isInsideOfSecondLevel = true;
				braceStack.push(source.charAt(i));
			}
			// brace end
			if (!braceStack.empty() && source.charAt(i) == '}') {
				braceStack.pop();
				if (braceStack.empty()) {
					sb.append(source.charAt(i));
				}
			}
			// check the brace stack state
			if (braceStack.empty()) {
				isInsideOfSecondLevel = false;
			}
		}
		return sb.toString();
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
		String regexp = "[\\{;\\}][^\\{;\\}]+?\\([^\\{;\\}]*?\\)[^\\{;\\}]+?\\{";
		Pattern pat = Pattern.compile(regexp);
		Matcher mat = pat.matcher(source);
		while (mat.find()) {
			String matched = mat.group();
			String prefix = "[\\{;\\}]\\s+?";
			String postfix = "\\s.*";
			// skip public methods
			if (!publicRequired && matched.matches(prefix + "public" + postfix)) {
				continue;
			}
			// skip protected methods
			if (!protectedRequired
					&& matched.matches(prefix + "protected" + postfix)) {
				continue;
			}
			// skip package local methods
			if (!packageLocalRequired
					&& !matched.matches(prefix + "public" + postfix)
					&& !matched.matches(prefix + "protected" + postfix)) {
				continue;
			}
			matched = matched.replaceAll(StrConst.tab, StrConst.space)
					.replaceAll(prefix + "public" + "\\s", StrConst.empty)
					.replaceAll(prefix + "protected" + "\\s", StrConst.empty)
					.replaceAll(prefix + "\\s+?", StrConst.empty).replaceAll(
							"\\sfinal\\s", StrConst.space);
			result.add(StrConst.space + matched);
		}
		return result;
	}
}