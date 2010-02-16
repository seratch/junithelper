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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junithelper.plugin.constant.STR;

/**
 * Source code parse util<br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class SourceCodeParseUtil {

	public static List<String> getTargetMethods(String source) {
		source = source.replaceAll("\\s+?" + STR.COMMA, STR.COMMA).replaceAll(
				STR.COMMA + "\\s+?", STR.COMMA).replaceAll("<\\s+?", "<")
				.replaceAll("\\s+?>", ">");
		List<String> result = new ArrayList<String>();
		String regexp = "[\\{;\\}][^\\{;\\}]+?\\([^\\{;\\}]*?\\)[^\\{;\\}]+?\\{";
		Pattern pat = Pattern.compile(regexp);
		Matcher mat = pat.matcher(source);
		while (mat.find()) {
			String matched = mat.group().replaceAll("\t", " ").replaceAll(
					"[\\{;\\}]\\s+?public\\s", "").replaceAll(
					"[\\{;\\}]\\s+?protected\\s", "").replaceAll(
					"[\\{;\\}]\\s+?", "").replaceAll("\\sfinal\\s", " ");
			result.add(matched);
		}
		return result;
	}
}