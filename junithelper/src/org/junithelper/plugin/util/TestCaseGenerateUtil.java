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
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.bean.ClassInfo;
import org.junithelper.plugin.bean.MethodInfo;
import org.junithelper.plugin.bean.MethodInfo.ArgType;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.constant.STR;

/**
 * TestCaseGenerateUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public final class TestCaseGenerateUtil {

	static IWorkspace workspace = ResourcesPlugin.getWorkspace();
	static IWorkspaceRoot workspaceRoot = workspace.getRoot();

	static String RXP_WS = STR.RegExp.WHITE_AND_SPACE;
	static String RXP_NOT_S_RQ = STR.RegExp.NOT_S_REQUIRED;
	static String RXP_NOT_S_NRQ = STR.RegExp.NOT_S_NOREQUIRED;

	private static final String RXP_METHOD_MODIFIERS = "[<\\w+?>|static|final|\\s]*";
	private static final String RXP_METHOD_RETURN_TYPE = "[a-zA-Z1-9\\[\\]_,\\$<>\\.]+?";

	private static final String RXP_GENERICS_PART = "<[a-zA-Z0-9,\\$_\\?]+?>";
	private static final String RXP_GENERICS_PART_GROUP = "<([a-zA-Z0-9,\\$_\\?]+?)>";

	private static final String RXP_METHOD_STATIC_MODIFIERS = "static";
	private static final String RXP_METHOD_MODIFIERS_EXCLUDES_STATIC = "[<\\w+?>|final|\\s]*";

	/**
	 * Regular expression to search method syntax
	 */
	private static String RXP_SEARCH_METHOD = "";
	static {
		// prefix white and space
		RXP_SEARCH_METHOD += RXP_WS;
		// method modifiers
		// no need to support abstract methods
		RXP_SEARCH_METHOD += RXP_METHOD_MODIFIERS;
		// return type
		RXP_SEARCH_METHOD += "\\s+" + RXP_METHOD_RETURN_TYPE + "\\s+";
		// method ex.doSomething(String arg)
		RXP_SEARCH_METHOD += RXP_NOT_S_RQ + RXP_WS;
		// method args
		RXP_SEARCH_METHOD += "\\(" + "[^\\)]*?" + "\\)";
		// throws exception
		RXP_SEARCH_METHOD += RXP_WS + ".*?" + RXP_WS;
		// method start and so on
		RXP_SEARCH_METHOD += "\\{.+";
	}

	/**
	 * Regular expression to search static method syntax
	 */
	private static String RXP_SEARCH_STATIC_METHOD = "";
	static {
		// prefix white and space
		RXP_SEARCH_STATIC_METHOD += RXP_WS;
		// method modifiers
		// no need to support abstract methods
		RXP_SEARCH_STATIC_METHOD += RXP_METHOD_MODIFIERS_EXCLUDES_STATIC
				+ RXP_METHOD_STATIC_MODIFIERS
				+ RXP_METHOD_MODIFIERS_EXCLUDES_STATIC;
		// return type
		RXP_SEARCH_STATIC_METHOD += RXP_NOT_S_RQ + "\\s+";
		// method ex.doSomething(String arg)
		RXP_SEARCH_STATIC_METHOD += RXP_NOT_S_RQ + RXP_WS;
		// method args
		RXP_SEARCH_STATIC_METHOD += "\\(" + "[^\\)]*?" + "\\)";
		// throws exception
		RXP_SEARCH_STATIC_METHOD += RXP_WS + ".*?" + RXP_WS;
		// method start and so on
		RXP_SEARCH_STATIC_METHOD += "\\{.+";
	}

	/**
	 * Regular expression to search method syntax grouped<br>
	 * $1 : return value <br>
	 * $2 : method name <br>
	 * $3 : args<br>
	 */
	private static String RXP_SEARCH_GROUP_METHOD = "";
	static {
		// prefix white and space
		RXP_SEARCH_GROUP_METHOD += RXP_WS;
		// method modifiers
		// TODO exclude abstract methods
		RXP_SEARCH_GROUP_METHOD += RXP_METHOD_MODIFIERS;
		// return type
		RXP_SEARCH_GROUP_METHOD += "\\s+(" + RXP_METHOD_RETURN_TYPE + ")"
				+ "\\s+";
		// method ex.doSomething
		RXP_SEARCH_GROUP_METHOD += "(" + RXP_NOT_S_RQ + ")" + RXP_WS;
		// method args
		RXP_SEARCH_GROUP_METHOD += "\\((" + "[^\\)]*?" + ")\\)";
		// throws exception
		RXP_SEARCH_GROUP_METHOD += RXP_WS + ".*?" + RXP_WS;
		// method start and so on
		RXP_SEARCH_GROUP_METHOD += "\\{.+";
	}
	static Pattern PAT_SEARCH_GROUP_METHOD = Pattern
			.compile(RXP_SEARCH_GROUP_METHOD);

	/**
	 * Get the information on the unimplemented test methods.
	 * 
	 * @param testTarget
	 * @param testCase
	 * @return the information on test class with the unimplemented test methods
	 * @throws Exception
	 */
	public static ClassInfo getClassInfoWithUnimplementedTestMethods(
			IFile testTarget, IFile testCase) throws Exception {
		ClassInfo classInfo = new ClassInfo();
		List<MethodInfo> unimplementedMethodNames = new ArrayList<MethodInfo>();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// enable public method test
		boolean enabled = store.getBoolean(Preference.TestMethodGen.ENABLE);
		boolean enabledNotBlankMethods = store
				.getBoolean(Preference.TestMethodGen.METHOD_SAMPLE_IMPL);
		boolean enabledSupportJMock2 = MockGenUtil.isUsingJMock2(store);
		boolean enabledSupportEasyMock = MockGenUtil.isUsingEasyMock(store);
		// enable public method test
		if (enabled) {
			ClassInfo expectedClassInfo = getTestClassInfoFromTargetClass(testTarget);
			List<MethodInfo> expectedMethods = expectedClassInfo.methods;
			ClassInfo actualClassInfo = getMethodNamesAlreadyExists(testCase);
			List<MethodInfo> actualMethods = actualClassInfo.methods;
			for (MethodInfo expected : expectedMethods) {
				boolean exist = false;
				for (MethodInfo actual : actualMethods) {
					String escapedExp = expected.testMethodName.replaceAll(
							"\\$", "\\\\\\$");
					if (actual.testMethodName.matches(escapedExp + ".*")) {
						exist = true;
						break;
					}
				}
				if (!exist)
					unimplementedMethodNames.add(expected);
			}
			// imported types
			if (enabledNotBlankMethods) {
				List<String> notImportedList = new ArrayList<String>();
				List<String> expImportedList = expectedClassInfo.importList;
				List<String> actImportedList = actualClassInfo.importList;
				for (String expImported : expImportedList) {
					boolean found = false;
					for (String actImported : actImportedList) {
						if (expImported.equals(actImported)) {
							found = true;
							break;
						}
					}
					if (!found)
						notImportedList.add(expImported);
					else
						notImportedList.add("//" + expImported);
				}
				classInfo.importList = notImportedList;
				if (enabledSupportJMock2) {
					classInfo.importList.add("org.jmock.Mockery");
					classInfo.importList.add("org.jmock.Expectations");
					classInfo.importList
							.add("org.jmock.lib.legacy.ClassImposteriser");
				}
				if (enabledSupportEasyMock) {
					classInfo.importList
							.add("org.easymock.classextension.EasyMock");
					classInfo.importList
							.add("org.easymock.classextension.IMocksControl");
				}
			}
		}
		classInfo.methods = unimplementedMethodNames;
		return classInfo;
	}

	/**
	 * Get source code lines.
	 * 
	 * @param javaFile
	 * @return source code lines
	 * @throws Exception
	 */
	public static List<String> getAllSourceCodeLineList(IFile javaFile)
			throws Exception {
		List<String> lines = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try {
			// detect charset
			is = javaFile.getContents();
			String encoding = FileResourceUtil.detectEncoding(javaFile);

			// read file
			is = javaFile.getContents();
			br = new BufferedReader(new InputStreamReader(is, encoding));
			String line = null;
			while ((line = br.readLine()) != null)
				lines.add(line.replace("\r", STR.EMPTY));
		} finally {
			FileResourceUtil.close(is);
			FileResourceUtil.close(br);
		}
		return lines;
	}

	/**
	 * Get the information on the methods.
	 * 
	 * @param javaFile
	 * @return the information on the methods
	 * @throws Exception
	 */
	public static ClassInfo getMethodNamesAlreadyExists(IFile javaFile)
			throws Exception {
		ClassInfo classInfo = new ClassInfo();
		List<MethodInfo> methodStringInfos = new ArrayList<MethodInfo>();

		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore()
				.getBoolean(Preference.TestMethodGen.ENABLE);
		boolean enabledNotBlankMethods = Activator.getDefault()
				.getPreferenceStore().getBoolean(
						Preference.TestMethodGen.METHOD_SAMPLE_IMPL);
		if (enabled) {
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				is = javaFile.getContents();
				// detect charset
				String encoding = FileResourceUtil.detectEncoding(javaFile);

				is = javaFile.getContents();
				isr = new InputStreamReader(is, encoding);
				br = new BufferedReader(isr);

				StringBuilder tmpsb = new StringBuilder();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String targetClassSourceStr = tmpsb.toString();
				String[] targets = targetClassSourceStr.split("public");
				for (String target : targets) {
					target = target.replaceAll("\\s+?" + STR.COMMA, STR.COMMA)
							.replaceAll(STR.COMMA + "\\s+?", STR.COMMA);
					target = target.replaceAll("<\\s+?", "<").replaceAll(
							"\\s+?>", ">");
					if (target.matches(RXP_SEARCH_METHOD)) {
						Matcher matcher = PAT_SEARCH_GROUP_METHOD
								.matcher(target);
						if (matcher.find()) {
							MethodInfo each = new MethodInfo();
							// return type
							each.returnType.name = getType(matcher.group(1));
							each.returnType.nameInMethodName = getTypeAvailableInMethodName(matcher
									.group(1));
							// method name
							each.methodName = matcher.group(2);
							each.testMethodName = each.methodName;
							// arg types
							String args = matcher.group(3);
							String[] argArr = args.split(",");
							int argArrLen = argArr.length;
							for (int i = 0; i < argArrLen; i++) {
								ArgType argType = new ArgType();
								argType.name = getType(argArr[i]);
								argType.nameInMethodName = getTypeAvailableInMethodName(argArr[i]);
								each.argTypes.add(argType);
							}
							methodStringInfos.add(each);
						}
					}
				}
				// imported types
				if (enabledNotBlankMethods) {
					if (methodStringInfos.size() <= 0
							|| methodStringInfos.get(0) == null)
						methodStringInfos.add(new MethodInfo());
					String[] importStartLines = targetClassSourceStr
							.split("import\\s+");
					for (String importStartLine : importStartLines) {
						// not package or not comment
						if (!importStartLine
								.matches("^\\s*package.*?|^\\s*/\\*.*?|^\\s*//.*?")) {
							String importedPackage = importStartLine.split(";")[0];
							classInfo.importList.add(importedPackage);
						}
					}
				}
			} finally {
				FileResourceUtil.close(br);
				FileResourceUtil.close(isr);
				FileResourceUtil.close(is);
			}
		}
		classInfo.methods = methodStringInfos;
		return classInfo;
	}

	/**
	 * Get the information on the test methods corresponded the developing
	 * public methods.
	 * 
	 * @param javaFile
	 * @return the information on the test methods
	 * @throws Exception
	 */
	public static ClassInfo getTestClassInfoFromTargetClass(IFile javaFile)
			throws Exception {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		ClassInfo classInfo = new ClassInfo();
		List<MethodInfo> testMethods = new ArrayList<MethodInfo>();
		// enable public method test
		boolean enabled = store.getBoolean(Preference.TestMethodGen.ENABLE);
		if (!enabled) {
			return classInfo;
		}
		boolean enabledArgs = store.getBoolean(Preference.TestMethodGen.ARGS);
		boolean enabledReturn = store
				.getBoolean(Preference.TestMethodGen.RETURN);
		boolean enabledNotBlankMethods = store
				.getBoolean(Preference.TestMethodGen.METHOD_SAMPLE_IMPL);
		boolean enableExcludesAccessors = store
				.getBoolean(Preference.TestMethodGen.EXLCUDES_ACCESSORS);
		boolean enabledSupportJMock2 = MockGenUtil.isUsingJMock2(store);
		boolean enabledSupportEasyMock = MockGenUtil.isUsingEasyMock(store);

		String delimiter = store.getString(Preference.TestMethodGen.DELIMITER);
		String argsPrefix = store
				.getString(Preference.TestMethodGen.ARGS_PREFIX);
		String argsDelimiter = store
				.getString(Preference.TestMethodGen.ARGS_DELIMITER);
		String returnPrefix = store
				.getString(Preference.TestMethodGen.RETURN_PREFIX);
		String returnDelimiter = store
				.getString(Preference.TestMethodGen.RETURN_DELIMITER);

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			// detect charset
			String encoding = FileResourceUtil.detectEncoding(javaFile);
			// read test target class source code string
			is = javaFile.getContents();
			isr = new InputStreamReader(is, encoding);
			br = new BufferedReader(isr);
			StringBuilder tmpsb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				tmpsb.append(trimLineComments(line) + " ");
			}
			// source code string
			// (inner class methods are excluded)
			String targetClassSourceStr = trimInsideOfBraces(trimAllComments(tmpsb
					.toString()));

			// get imported types
			if (enabledNotBlankMethods) {
				if (testMethods.size() <= 0 || testMethods.get(0) == null)
					testMethods.add(new MethodInfo());
				String[] importStartLines = targetClassSourceStr
						.split("import\\s+");
				for (String importStartLine : importStartLines) {
					// not package or not comment
					if (!importStartLine
							.matches("^\\s*package.*?|^\\s*/\\*.*?|^\\s*//.*?")) {
						String importedPackage = importStartLine.split(";")[0];
						classInfo.importList.add(importedPackage);
					}
				}
				if (enabledSupportJMock2) {
					classInfo.importList.add("org.jmock.Mockery");
					classInfo.importList.add("org.jmock.Expectations");
					classInfo.importList
							.add("org.jmock.lib.legacy.ClassImposteriser");
				}
				if (enabledSupportEasyMock) {
					classInfo.importList
							.add("org.easymock.classextension.EasyMock");
					classInfo.importList
							.add("org.easymock.classextension.IMocksControl");
				}
			}

			String[] targets = targetClassSourceStr.split("public|protected");
			for (String target : targets) {
				target = target.replaceAll("\\s+?" + STR.COMMA, STR.COMMA)
						.replaceAll(STR.COMMA + "\\s+?", STR.COMMA).replaceAll(
								"<\\s+?", "<").replaceAll("\\s+?>", ">");
				if (target.matches(RXP_SEARCH_METHOD)) {
					Matcher matcher = PAT_SEARCH_GROUP_METHOD.matcher(target);
					if (matcher.find()) {
						MethodInfo each = new MethodInfo();
						// return type
						if (enabledNotBlankMethods || enabledReturn) {
							String returnTypeFull = getType(matcher.group(1));
							// get generics
							Matcher toGenericsMatcher = Pattern.compile(
									RXP_GENERICS_PART_GROUP).matcher(
									returnTypeFull);
							while (toGenericsMatcher.find()) {
								String[] generics = toGenericsMatcher.group()
										.replaceAll("<", STR.EMPTY).replaceAll(
												">", STR.EMPTY).split(",");
								// convert to java.lang.Object if self
								// class is included
								for (String generic : generics) {
									generic = getClassInSourceCode(generic,
											STR.EMPTY, new ArrayList<String>());
									each.returnType.generics.add(generic);
								}
							}
							each.returnType.name = returnTypeFull.replace(
									RXP_GENERICS_PART, STR.EMPTY);
							each.returnType.nameInMethodName = getTypeAvailableInMethodName(each.returnType.name);
						}
						// method name
						each.methodName = matcher.group(2);
						// arg types
						String args = matcher.group(3);
						// prepare to get generics
						String[] tmpArr = args.split(",");
						int tmpArrLen = tmpArr.length;
						List<String> tmpArrList = new ArrayList<String>();
						String buf = STR.EMPTY;
						for (int i = 0; i < tmpArrLen; i++) {
							String element = tmpArr[i].trim();
							// ex. List<String>
							if (element.matches(".+?<.+?>.+")) {
								tmpArrList.add(element);
								continue;
							}
							// ex. Map<String
							if (element.matches(".+?<.+")) {
								buf += element;
								continue;
							}
							// ex. (Map<String,) Object>
							if (element.matches(".+?>.+")) {
								String result = buf + STR.COMMA + element;
								tmpArrList.add(result);
								buf = STR.EMPTY;
								continue;
							}
							if (!buf.equals(STR.EMPTY)) {
								buf += STR.COMMA + element;
								continue;
							}
							tmpArrList.add(element);
						}
						String[] argArr = tmpArrList.toArray(new String[0]);
						if (enabledNotBlankMethods || enabledArgs) {
							int argArrLen = argArr.length;
							for (int i = 0; i < argArrLen; i++) {
								ArgType argType = new ArgType();
								String argTypeFull = argArr[i];
								Matcher toGenericsMatcher = Pattern.compile(
										RXP_GENERICS_PART_GROUP).matcher(
										argTypeFull);
								while (toGenericsMatcher.find()) {
									String[] generics = toGenericsMatcher
											.group().replaceAll("<", STR.EMPTY)
											.replaceAll(">", STR.EMPTY).split(
													",");
									// convert to java.lang.Object if self
									// class is included
									for (String generic : generics) {
										generic = getClassInSourceCode(generic,
												STR.EMPTY,
												new ArrayList<String>());
										argType.generics.add(generic);
									}
								}
								String argTypeStr = argTypeFull.replaceAll(
										RXP_GENERICS_PART, STR.EMPTY);
								argType.name = getType(argTypeStr);
								argType.nameInMethodName = getTypeAvailableInMethodName(argTypeStr);
								each.argTypes.add(argType);
							}
						}
						// exlucdes accessors
						if (enableExcludesAccessors) {
							String fieldName = null;
							String fieldType = null;
							if (each.methodName.matches("^set.+")) {
								// target field name
								fieldName = each.methodName.substring(3);
								if (each.argTypes.size() > 0) {
									fieldType = each.argTypes.get(0).name;
								}
							} else if (each.methodName.matches("^get.+")) {
								// target field name
								fieldName = each.methodName.substring(3);
								fieldType = each.returnType.name;
							} else if (each.methodName.matches("^is.+")) {
								// target field name
								fieldName = each.methodName.substring(2);
								fieldType = each.returnType.name;
							}

							if (fieldName != null) {
								fieldName = fieldName.substring(0, 1)
										.toLowerCase()
										+ fieldName.substring(1);
								fieldType = fieldType
										.replaceAll("\\[", "\\\\[").replaceAll(
												"\\]", "\\\\]");
								String searchRegexp = ".+?private\\s+"
										+ fieldType + "\\s+" + fieldName + ".+";
								if (targetClassSourceStr.matches(searchRegexp))
									continue;
							}
						}
						each.testMethodName = "test_" + each.methodName;
						// add arg types
						if (enabledArgs) {
							each.testMethodName += delimiter + argsPrefix;
							if (each.argTypes.size() == 0)
								each.testMethodName += argsDelimiter;
							for (ArgType argType : each.argTypes)
								each.testMethodName += argsDelimiter
										+ argType.nameInMethodName;
						}
						// add return type
						if (enabledReturn)
							each.testMethodName += delimiter + returnPrefix
									+ returnDelimiter
									+ each.returnType.nameInMethodName;
						// static or instance method
						if (target.matches(RXP_SEARCH_STATIC_METHOD))
							each.isStatic = true;
						testMethods.add(each);
					}
				}
			}
		} finally {
			FileResourceUtil.close(br);
			FileResourceUtil.close(isr);
			FileResourceUtil.close(is);
		}
		classInfo.methods = testMethods;
		return classInfo;
	}

	/**
	 * Get sample implementation source code of the test methods.
	 * 
	 * @param testMethod
	 * @param testMethods
	 * @param testTargetClassname
	 * @return sample implementation source code
	 */
	public static String getNotBlankTestMethodSource(MethodInfo testMethod,
			ClassInfo testClassinfo, String testTargetClassname) {
		StringBuilder sb = new StringBuilder();
		String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

		// mocked args using JMock2
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean usingJUnitHelperRuntime = store
				.getBoolean(Preference.TestClassGen.USING_JUNIT_HELPER_RUNTIME_LIB);
		boolean enabledSupportJMock2 = MockGenUtil.isUsingJMock2(store);
		boolean enabledSupportEasyMock = MockGenUtil.isUsingEasyMock(store);

		if (enabledSupportJMock2 && !usingJUnitHelperRuntime) {
			sb.append("\t\t");
			sb.append("Mockery context = new Mockery(){{");
			sb.append(CRLF);
			sb.append("\t\t\t");
			sb.append("setImposteriser(ClassImposteriser.INSTANCE);");
			sb.append(CRLF);
			sb.append("\t\t");
			sb.append("}};");
			sb.append(CRLF);
		}
		if (enabledSupportEasyMock) {
			sb.append("\t\t");
			sb.append("IMocksControl mocks = EasyMock.createControl();");
			sb.append(CRLF);
		}

		sb.append("\t\t");

		String returnTypeName = testMethod.returnType.name;
		Object returnDefaultValue = null;
		if (!returnTypeName.equals("void")) {
			returnTypeName = returnTypeName.replaceAll(RXP_GENERICS_PART,
					STR.EMPTY);
			returnTypeName = getClassInSourceCode(returnTypeName,
					testTargetClassname, testClassinfo.importList);
			List<String> generics = testMethod.returnType.generics;
			int genericsLen = generics.size();
			if (genericsLen > 0) {
				returnTypeName += "<" + generics.get(0);
				for (int i = 1; i < genericsLen; i++)
					returnTypeName += "," + generics.get(i);
				returnTypeName += ">";
			}
			if (PrimitiveTypeUtil.isPrimitive(returnTypeName)) {
				returnDefaultValue = PrimitiveTypeUtil
						.getPrimitiveDefaultValue(returnTypeName);
			}
		}

		// instance method
		// ex. TestTarget target = new TestTarget();
		if (!testMethod.isStatic) {
			sb.append(testTargetClassname);
			sb.append(" target = new ");
			sb.append(testTargetClassname);
			sb.append("();");
			sb.append(CRLF);
			sb.append("\t\t");
		}

		if (!returnTypeName.equals("void")) {
			sb.append(returnTypeName);
			sb.append(" expected = ");
			sb.append(returnDefaultValue);
			sb.append(";");
			sb.append(CRLF);
			sb.append("\t\t");
		}

		// args define
		// ex. String arg0 = null;
		// int arg1 = 0;
		List<ArgType> argTypes = testMethod.argTypes;
		List<String> args = new ArrayList<String>();
		int argTypesLen = argTypes.size();
		if (argTypesLen > 0 && argTypes.get(0).name != null
				&& !argTypes.get(0).name.equals(STR.EMPTY)) {
			for (int i = 0; i < argTypesLen; i++) {
				ArgType argType = argTypes.get(i);
				// flexible length args
				if (argType.name.matches(".+\\.\\.\\."))
					argType.name = argType.name.replaceAll("\\.\\.\\.", "[]");
				String argTypeName = getClassInSourceCode(argType.name,
						testTargetClassname, testClassinfo.importList);
				boolean isJMock2 = enabledSupportJMock2
						&& MockGenUtil.isMockableClassName(argTypeName,
								testClassinfo.importList);
				boolean isEasyMock = enabledSupportEasyMock
						&& MockGenUtil.isMockableClassName(argTypeName,
								testClassinfo.importList);
				if (isJMock2) {
					sb.append("final ");
				}
				sb.append(argTypeName);
				// add generics
				if (argType.generics.size() > 0) {
					sb.append("<");
					sb.append(argType.generics.get(0));
					if (argType.generics.size() > 1) {
						for (int j = 1; j < argType.generics.size(); j++) {
							sb.append(",");
							sb.append(argType.generics.get(j));
						}
					}
					sb.append(">");
				}
				sb.append(" arg");
				sb.append(i);
				sb.append(" = ");
				if (PrimitiveTypeUtil.isPrimitive(argType.name)) {
					Object primitiveDefault = PrimitiveTypeUtil
							.getPrimitiveDefaultValue(argType.name);
					sb.append(primitiveDefault);
				} else {
					if (isJMock2) {
						sb.append(usingJUnitHelperRuntime ? "jmock2"
								: "context");
						sb.append(".mock(");
						sb.append(argTypeName);
						sb.append(".class)");
					} else if (isEasyMock) {
						sb.append("mocks.createMock(");
						sb.append(argTypeName);
						sb.append(".class)");
					} else {
						sb.append("null");
					}

				}
				sb.append(";");
				sb.append(CRLF);
				sb.append("\t\t");
				args.add("arg" + i);
			}
		}

		// JMock2 expectations
		if (enabledSupportJMock2) {
			sb.append(usingJUnitHelperRuntime ? "jmock2" : "context");
			sb.append(".checking(new Expectations(){{");
			sb.append(CRLF);
			sb.append("\t\t\t// TODO JMock2 Expectations");
			sb.append(CRLF);
			sb.append("\t\t}});");
			sb.append(CRLF);
			sb.append("\t\t");
		}
		if (enabledSupportEasyMock) {
			sb.append("// TODO EasyMock Expectations");
			sb.append(CRLF);
			sb.append("\t\tmocks.replay();");
			sb.append(CRLF);
			sb.append("\t\t");
		}

		// execute target method
		// ex. SampleUtil.doSomething(null, null);
		// ex. String expected = null;
		// String actual = target.doSomething();
		if (!returnTypeName.equals("void")) {
			sb.append(returnTypeName);
			sb.append(" actual = ");
		}
		if (testMethod.isStatic)
			sb.append(testTargetClassname);
		else
			sb.append("target");

		sb.append(".");
		sb.append(testMethod.methodName);
		sb.append("(");
		if (args.size() > 0 && argTypesLen > 0 && argTypes.get(0).name != null
				&& !argTypes.get(0).name.equals(STR.EMPTY))
			sb.append(args.get(0));
		for (int i = 1; i < argTypes.size(); i++) {
			sb.append(", ");
			sb.append(args.get(i));
		}
		sb.append(");");
		sb.append(CRLF);

		if (enabledSupportEasyMock) {
			sb.append("\t\tmocks.verify();");
			sb.append(CRLF);
		}

		// assert return value
		// ex. assertEquals(expected, actual);
		if (!returnTypeName.equals("void")) {
			sb.append("\t\t");
			sb.append("assertEquals(expected, actual);");
			sb.append(CRLF);
		}
		return sb.toString();
	}

	/**
	 * Get type.
	 * 
	 * @param arg
	 * @return
	 */
	protected static String getType(String arg) {
		arg = arg.trim().replaceAll("final ", STR.EMPTY).split("\\s+")[0];
		return arg;
	}

	/**
	 * Get type converted.
	 * 
	 * @param arg
	 * @return
	 */
	protected static String getTypeAvailableInMethodName(String arg) {
		arg = arg.replaceAll(RXP_GENERICS_PART, STR.EMPTY);
		arg = arg.replaceAll("final ", STR.EMPTY);
		arg = arg.replaceAll("\\.\\.\\.", "Array")
				.replaceAll("\\[\\]", "Array");
		// sample name classes imported or full package class defined
		// ex. java.util.Date, java.sql.Date
		arg = arg.replaceAll("\\.", STR.EMPTY);
		arg = arg.trim().split("\\s+")[0];
		return arg;
	}

	protected static String getClassInSourceCode(String returnTypeToCheck,
			String testTargetClassname, List<String> importList) {
		// defined class with full package
		if (returnTypeToCheck.matches(".+?\\..+"))
			return returnTypeToCheck;
		// array object
		boolean isArray = false;
		if (returnTypeToCheck.matches(".+?\\[\\]")) {
			isArray = true;
			returnTypeToCheck = returnTypeToCheck.replaceAll("\\[\\]", "");
		}
		String returnTypeName = "Object";
		boolean returnTypeFound = false;
		try {
			if (PrimitiveTypeUtil.isPrimitive(returnTypeToCheck)) {
				returnTypeFound = true;
				if (!returnTypeName.matches(".+?\\[\\]$"))
					returnTypeName = PrimitiveTypeUtil
							.getPrimitiveDefaultValue(returnTypeToCheck);
			} else {
				try {
					Class.forName("java.lang." + returnTypeToCheck);
					returnTypeFound = true;
				} catch (Exception ignore) {
				}
				if (!returnTypeFound)
					Class.forName(returnTypeToCheck);
			}
		} catch (Exception e) {
			// class not found
			if (returnTypeToCheck.equals(testTargetClassname))
				returnTypeFound = true;
			for (String importedPackage : importList) {
				importedPackage = importedPackage.replaceAll("//", STR.EMPTY);
				if (importedPackage.matches(".+?\\." + returnTypeToCheck + "$")) {
					returnTypeFound = true;
					break;
				}
			}
		}
		if (!returnTypeFound) {
			if (isArray)
				returnTypeName += "[]";
			return returnTypeName;
		} else {
			if (isArray)
				returnTypeToCheck += "[]";
			return returnTypeToCheck;
		}
	}

	protected static String trimLineComments(String source) {
		return source.replaceAll("//.+?\n", STR.EMPTY);
	}

	protected static String trimAllComments(String source) {
		return trimLineComments(source.replaceAll("/\\*.+?\\*/", STR.EMPTY));
	}

	/**
	 * trim inside of the second level braces.
	 * 
	 * @param source
	 * @return
	 */
	protected static String trimInsideOfBraces(String source) {
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
			}
			// check the brace stack state
			if (braceStack.empty()) {
				isInsideOfSecondLevel = false;
			}
		}
		return sb.toString();
	}

}
