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
package org.junithelper.core.parser.convert;

import java.io.File;
import java.util.List;

import org.junithelper.core.config.Configulation;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.file.impl.CommonsIOFileSearcher;
import org.junithelper.core.util.PrimitiveTypeUtil;

public class TypeNameConverter {

	private Configulation config;

	public TypeNameConverter(Configulation config) {
		this.config = config;
	}

	public String toAvailableInMethodName(String arg) {
		arg = arg.replaceAll(RegExp.Generics, StringValue.Empty);
		arg = arg.replaceAll("final ", StringValue.Empty);
		arg = arg.replaceAll("\\.\\.\\.", "Array")
				.replaceAll("\\[\\]", "Array");
		// sample name classes imported or full package class defined
		// ex. java.util.Date, java.sql.Date
		arg = arg.replaceAll("\\.", StringValue.Empty);
		arg = arg.trim().split(RegExp.WhiteSpace.Consecutive_OneOrMore_Max)[0];
		return arg;
	}

	public String toCompilableType(String packageName, String className,
			List<String> importedList) {
		return toCompilableType(packageName, className, null, importedList);
	}

	public String toCompilableType(String packageName, String className,
			List<String> generics, List<String> importedList) {
		if (className == null) {
			return className;
		}
		className = className.replaceAll("\\.\\.\\.", "[]");
		// defined class with full package
		if (className.matches(".+?\\..+")) {
			return className;
		}
		// array object
		boolean isArray = false;
		if (className.matches(".+?\\[\\]")) {
			isArray = true;
			className = className.replaceAll("\\[\\]", "");
		}
		// remove generics
		if (className.matches(RegExp.Anything_ZeroOrMore_Min + RegExp.Generics
				+ RegExp.Anything_ZeroOrMore_Min)) {
			className = className
					.replaceAll(RegExp.Generics, StringValue.Empty);
		}
		boolean isTypeAvailable = false;
		String destTypeName = "Object";
		try {
			if (PrimitiveTypeUtil.isPrimitive(className)) {
				isTypeAvailable = true;
				if (!destTypeName.matches(".+?\\[\\]$"))
					destTypeName = PrimitiveTypeUtil
							.getTypeDefaultValue(className);
			} else {
				try {
					Class.forName("java.lang." + className);
					isTypeAvailable = true;
				} catch (Exception ignore) {
					// check same package class
					List<File> files = new CommonsIOFileSearcher()
							.searchFilesRecursivelyByName(
									config.directoryPathOfProductSourceCode
											+ "/"
											+ packageName
													.replaceAll("\\.", "/"),
									className + RegExp.FileExtension.JavaFile);
					if (files != null && files.size() > 0) {
						isTypeAvailable = true;
					}
				}
				if (!isTypeAvailable)
					Class.forName(className);
			}
		} catch (Exception e) {
			// class not found
			for (String importedPackage : importedList) {
				importedPackage = importedPackage.replaceAll("//",
						StringValue.Empty);
				try {
					String regexp = ".+?\\."
							+ className.replaceAll("\\[", "\\\\[").replaceAll(
									"\\]", "\\\\]") + "$";
					if (importedPackage.matches(regexp)) {
						isTypeAvailable = true;
						break;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		if (generics != null) {
			StringBuilder buf = new StringBuilder();
			if (generics.size() > 0) {
				buf.append("<");
				buf.append(generics.get(0));
				if (generics.size() > 1) {
					for (int i = 1; i < generics.size(); i++) {
						buf.append(", ");
						buf.append(generics.get(i));
					}
				}
				buf.append(">");
			}
			className += buf.toString();
		}
		if (className == null || className.equals(StringValue.Empty)) {
			className = "Object";
		}
		if (destTypeName == null || destTypeName.equals(StringValue.Empty)) {
			destTypeName = "Object";
		}
		if (isTypeAvailable) {
			return isArray ? className + "[]" : className;
		} else {
			return isArray ? destTypeName + "[]" : destTypeName;
		}
	}

}
