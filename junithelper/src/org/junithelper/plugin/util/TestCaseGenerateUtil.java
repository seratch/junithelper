/* 
 * Copyright 2009 junithelper.org. 
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;
import org.junithelper.plugin.bean.GeneratingMethodInfo;

/**
 * TestCaseGenerateUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class TestCaseGenerateUtil
{

	static IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

	static String RXP_WS = STR.RegExp.WHITE_AND_SPACE;
	static String RXP_NOT_S_RQ = STR.RegExp.NOT_S_REQUIRED;
	static String RXP_NOT_S_NRQ = STR.RegExp.NOT_S_NOREQUIRED;

	private static final String RXP_METHOD_MODIFIERS = "[<\\w+?>|static|final|\\s]*";
	private static final String RXP_METHOD_RETURN_TYPE = "[a-zA-Z1-9\\[\\]_,\\$<>]+?";

	private static final String RXP_GENERICS_PART = "<[a-zA-Z0-9,\\$_]+?>";

	private static final String RXP_METHOD_STATIC_MODIFIERS = "static";
	private static final String RXP_METHOD_MODIFIERS_EXCLUDES_STATIC = "[<\\w+?>|final|\\s]*";

	/**
	 * Regular expression to search method syntax
	 */
	private static String RXP_SEARCH_METHOD = "";
	static
	{
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
	static
	{
		// prefix white and space
		RXP_SEARCH_STATIC_METHOD += RXP_WS;
		// method modifiers
		// no need to support abstract methods
		RXP_SEARCH_STATIC_METHOD += RXP_METHOD_MODIFIERS_EXCLUDES_STATIC
				+ RXP_METHOD_STATIC_MODIFIERS + RXP_METHOD_MODIFIERS_EXCLUDES_STATIC;
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
	static
	{
		// prefix white and space
		RXP_SEARCH_GROUP_METHOD += RXP_WS;
		// method modifiers
		// TODO exclude abstract methods
		RXP_SEARCH_GROUP_METHOD += RXP_METHOD_MODIFIERS;
		// return type
		RXP_SEARCH_GROUP_METHOD += "\\s+(" + RXP_METHOD_RETURN_TYPE + ")" + "\\s+";
		// method ex.doSomething
		RXP_SEARCH_GROUP_METHOD += "(" + RXP_NOT_S_RQ + ")" + RXP_WS;
		// method args
		RXP_SEARCH_GROUP_METHOD += "\\((" + "[^\\)]*?" + ")\\)";
		// throws exception
		RXP_SEARCH_GROUP_METHOD += RXP_WS + ".*?" + RXP_WS;
		// method start and so on
		RXP_SEARCH_GROUP_METHOD += "\\{.+";
	}
	static Pattern PAT_SEARCH_GROUP_METHOD = Pattern.compile(RXP_SEARCH_GROUP_METHOD);

	/**
	 * Get the information on the unimplemented test methods.
	 * 
	 * @param testTarget
	 * @param testCase
	 * @return the information on the unimplemented test methods
	 * @throws Exception
	 */
	public static List<GeneratingMethodInfo> getUnimplementedTestMethodNames(
			IFile testTarget, IFile testCase) throws Exception
	{
		List<GeneratingMethodInfo> unimplementedMethodNames = new ArrayList<GeneratingMethodInfo>();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// enable public method test
		boolean enabled = store.getBoolean(STR.Preference.TestMethodGen.ENABLE);
		boolean enabledNotBlankMethods = store
				.getBoolean(STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION);
		// enable public method test
		if (enabled)
		{
			List<GeneratingMethodInfo> expectedList = getTestMethodsFromTarget(testTarget);
			List<GeneratingMethodInfo> actualList = getMethodNames(testCase);
			for (GeneratingMethodInfo expected : expectedList)
			{
				boolean exist = false;
				for (GeneratingMethodInfo actual : actualList)
				{
					String escapedExp = expected.testMethodName.replaceAll("\\$",
							"\\\\\\$");
					if (actual.testMethodName.matches(escapedExp + ".*"))
					{
						exist = true;
						break;
					}
				}
				if (!exist)
					unimplementedMethodNames.add(expected);
			}
			// imported types
			if (enabledNotBlankMethods)
			{
				List<String> notImportedList = new ArrayList<String>();
				List<String> expImportedList = expectedList.get(0).importList;
				List<String> actImportedList = actualList.get(0).importList;
				for (String expImported : expImportedList)
				{
					boolean found = false;
					for (String actImported : actImportedList)
					{
						if (expImported.equals(actImported))
						{
							found = true;
							break;
						}
					}
					if (!found)
						notImportedList.add(expImported);
				}
				unimplementedMethodNames.get(0).importList = notImportedList;
			}
		}
		return unimplementedMethodNames;
	}

	/**
	 * Get source code lines.
	 * 
	 * @param javaFile
	 * @return source code lines
	 * @throws Exception
	 */
	public static List<String> getAllSourceCodeLineList(IFile javaFile) throws Exception
	{
		List<String> lines = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try
		{
			// detect charset
			is = javaFile.getContents();
			String encoding = FileResourceUtil.detectEncoding(javaFile);

			// read file
			is = javaFile.getContents();
			br = new BufferedReader(new InputStreamReader(is, encoding));
			String line = null;
			while ((line = br.readLine()) != null)
				lines.add(line.replace("\r", STR.EMPTY));
		} finally
		{
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
	public static List<GeneratingMethodInfo> getMethodNames(IFile javaFile)
			throws Exception
	{
		List<GeneratingMethodInfo> methodStringInfos = new ArrayList<GeneratingMethodInfo>();

		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodGen.ENABLE);
		boolean enabledNotBlankMethods = Activator.getDefault().getPreferenceStore()
				.getBoolean(STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION);
		if (enabled)
		{
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try
			{
				is = javaFile.getContents();

				// detect charset
				String encoding = FileResourceUtil.detectEncoding(javaFile);

				is = javaFile.getContents();
				isr = new InputStreamReader(is, encoding);
				br = new BufferedReader(isr);
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					publicsEach = publicsEach.replaceAll("\\s+?" + STR.COMMA, STR.COMMA)
							.replaceAll(STR.COMMA + "\\s+?", STR.COMMA);
					publicsEach = publicsEach.replaceAll("<\\s+?", "<").replaceAll(
							"\\s+?>", ">");
					if (publicsEach.matches(RXP_SEARCH_METHOD))
					{
						Matcher matcher = PAT_SEARCH_GROUP_METHOD.matcher(publicsEach);
						if (matcher.find())
						{
							GeneratingMethodInfo each = new GeneratingMethodInfo();
							// return type
							each.returnTypeName = getType(matcher.group(1));
							each.returnTypeNameInMethodName = getTypeAvailableInMethodName(matcher
									.group(1));
							// method name
							each.methodName = matcher.group(2);
							each.testMethodName = each.methodName;
							// arg types
							String args = matcher.group(3);
							String[] argArr = args.split(",");
							int argArrLen = argArr.length;
							for (int i = 0; i < argArrLen; i++)
							{
								each.argTypeNames.add(getType(argArr[i]));
								each.argTypeNamesInMethodName
										.add(getTypeAvailableInMethodName(argArr[i]));
							}
							methodStringInfos.add(each);
						}
					}
				}
				// imported types
				if (enabledNotBlankMethods)
				{
					if (methodStringInfos.size() <= 0 || methodStringInfos.get(0) == null)
						methodStringInfos.add(new GeneratingMethodInfo());
					String[] importStartLines = allSrc.split("import\\s+");
					for (String importStartLine : importStartLines)
					{
						// not package or not comment
						if (!importStartLine
								.matches("^\\s*package.*?|^\\s*/\\*.*?|^\\s*//.*?"))
						{
							String importedPackage = importStartLine.split(";")[0];
							methodStringInfos.get(0).importList.add(importedPackage);
						}
					}
				}
			} finally
			{
				FileResourceUtil.close(br);
				FileResourceUtil.close(isr);
				FileResourceUtil.close(is);
			}

		}
		return methodStringInfos;
	}

	/**
	 * Get the information on the test methods corresponded the developing
	 * public methods.
	 * 
	 * @param javaFile
	 * @return the information on the test methods
	 * @throws Exception
	 */
	public static List<GeneratingMethodInfo> getTestMethodsFromTarget(IFile javaFile)
			throws Exception
	{

		List<GeneratingMethodInfo> testMethods = new ArrayList<GeneratingMethodInfo>();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// enable public method test
		boolean enabled = store.getBoolean(STR.Preference.TestMethodGen.ENABLE);

		boolean enabledArgs = store.getBoolean(STR.Preference.TestMethodGen.ARGS);
		boolean enabledReturn = store.getBoolean(STR.Preference.TestMethodGen.RETURN);
		boolean enabledNotBlankMethods = store
				.getBoolean(STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION);
		boolean enableExcludesAccessors = store
				.getBoolean(STR.Preference.TestMethodGen.EXLCUDES_ACCESSORS);

		String delimiter = store.getString(STR.Preference.TestMethodGen.DELIMITER);
		String argsPrefix = store.getString(STR.Preference.TestMethodGen.ARGS_PREFIX);
		String argsDelimiter = store
				.getString(STR.Preference.TestMethodGen.ARGS_DELIMITER);
		String returnPrefix = store.getString(STR.Preference.TestMethodGen.RETURN_PREFIX);
		String returnDelimiter = store
				.getString(STR.Preference.TestMethodGen.RETURN_DELIMITER);

		if (enabled)
		{
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try
			{
				// detect charset
				String encoding = FileResourceUtil.detectEncoding(javaFile);

				is = javaFile.getContents();
				isr = new InputStreamReader(is, encoding);
				br = new BufferedReader(isr);
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					publicsEach = publicsEach.replaceAll("\\s+?" + STR.COMMA, STR.COMMA)
							.replaceAll(STR.COMMA + "\\s+?", STR.COMMA);
					publicsEach = publicsEach.replaceAll("<\\s+?", "<").replaceAll(
							"\\s+?>", ">");
					// TODO inner class support
					if (publicsEach.matches(RXP_SEARCH_METHOD))
					{
						Matcher matcher = PAT_SEARCH_GROUP_METHOD.matcher(publicsEach);
						if (matcher.find())
						{
							GeneratingMethodInfo each = new GeneratingMethodInfo();
							// return type
							if (enabledNotBlankMethods || enabledReturn)
							{
								each.returnTypeName = getType(matcher.group(1));
								each.returnTypeNameInMethodName = getTypeAvailableInMethodName(each.returnTypeName);
							}
							// method name
							each.methodName = matcher.group(2);
							// arg types
							String args = matcher.group(3);
							args = args.replaceAll(RXP_GENERICS_PART, STR.EMPTY);
							String[] argArr = args.split(",");
							if (enabledNotBlankMethods || enabledArgs)
							{
								int argArrLen = argArr.length;
								for (int i = 0; i < argArrLen; i++)
								{
									each.argTypeNames.add(getType(argArr[i]));
									each.argTypeNamesInMethodName
											.add(getTypeAvailableInMethodName(argArr[i]));
								}
							}
							// exlucdes accessors
							if (enableExcludesAccessors)
							{
								String fieldName = null;
								String fieldType = null;
								if (each.methodName.matches("^set.+"))
								{
									// target field name
									fieldName = each.methodName.substring(3);
									fieldName = fieldName.substring(0, 1).toLowerCase()
											+ fieldName.substring(1);
									fieldType = each.argTypeNames.get(0);
								} else if (each.methodName.matches("^get.+"))
								{
									// target field name
									fieldName = each.methodName.substring(3);
									fieldName = fieldName.substring(0, 1).toLowerCase()
											+ fieldName.substring(1);
									fieldType = each.returnTypeName;
								} else if (each.methodName.matches("^is.+"))
								{
									// target field name
									fieldName = each.methodName.substring(2);
									fieldName = fieldName.substring(0, 1).toLowerCase()
											+ fieldName.substring(1);
									fieldType = each.returnTypeName;
								}

								if (fieldName != null)
								{
									String searchRegexp = ".+?private\\s+" + fieldType
											+ "\\s+" + fieldName + ".+?";
									if (allSrc.matches(searchRegexp))
										continue;
								}
							}
							each.testMethodName = "test_" + each.methodName;
							// add arg types
							if (enabledArgs)
							{
								each.testMethodName += delimiter + argsPrefix;
								if (each.argTypeNames.size() == 0)
									each.testMethodName += argsDelimiter;
								for (String argType : each.argTypeNamesInMethodName)
									each.testMethodName += argsDelimiter + argType;
							}
							// add return type
							if (enabledReturn)
								each.testMethodName += delimiter + returnPrefix
										+ returnDelimiter
										+ each.returnTypeNameInMethodName;
							// static or instance method
							if (publicsEach.matches(RXP_SEARCH_STATIC_METHOD))
								each.isStatic = true;
							testMethods.add(each);
						}
					}
				}
				// imported types
				if (enabledNotBlankMethods)
				{
					if (testMethods.size() <= 0 || testMethods.get(0) == null)
						testMethods.add(new GeneratingMethodInfo());
					String[] importStartLines = allSrc.split("import\\s+");
					for (String importStartLine : importStartLines)
					{
						// not package or not comment
						if (!importStartLine
								.matches("^\\s*package.*?|^\\s*/\\*.*?|^\\s*//.*?"))
						{
							String importedPackage = importStartLine.split(";")[0];
							testMethods.get(0).importList.add(importedPackage);
						}
					}
				}
			} finally
			{
				FileResourceUtil.close(br);
				FileResourceUtil.close(isr);
				FileResourceUtil.close(is);
			}

		}
		return testMethods;
	}

	/**
	 * Get sample implementation source code of the test methods.
	 * 
	 * @param testMethod
	 * @param testMethods
	 * @param testTargetClassname
	 * @return sample implementation source code
	 */
	public static String getNotBlankTestMethodSource(GeneratingMethodInfo testMethod,
			List<GeneratingMethodInfo> testMethods, String testTargetClassname)
	{
		StringBuffer sb = new StringBuffer();
		String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

		sb.append("\t\t");

		String returnType = testMethod.returnTypeName;
		Object returnDefaultValue = null;
		if (!returnType.equals("void"))
		{
			returnType = returnType.replaceAll(RXP_GENERICS_PART, STR.EMPTY);
			String returnTypeToCheck = returnType.replaceAll("\\[\\]", STR.EMPTY);
			boolean returnTypeFound = false;
			try
			{
				if (PrimitiveTypeUtil.isPrimitive(returnTypeToCheck))
				{
					returnTypeFound = true;
					if (!returnType.matches(".+?\\[\\]$"))
						returnDefaultValue = PrimitiveTypeUtil
								.getPrimitiveDefaultValue(returnTypeToCheck);
				} else
				{
					try
					{
						Class.forName("java.lang." + returnTypeToCheck);
						returnTypeFound = true;
					} catch (Exception ignore)
					{
					}
					if (!returnTypeFound)
						Class.forName(returnTypeToCheck);
				}
			} catch (Exception e)
			{
				// class not found
				if (returnTypeToCheck.equals(testTargetClassname))
					returnTypeFound = true;
				for (String importedPackage : testMethods.get(0).importList)
				{
					if (importedPackage.matches(".+?\\." + returnTypeToCheck + "$"))
					{
						returnTypeFound = true;
						break;
					}
				}
			}
			if (!returnTypeFound)
				returnType = returnType.replaceAll(returnTypeToCheck, "Object");
		}

		// instance method
		// ex. TestTarget target = new TestTarget();
		if (!testMethod.isStatic)
		{
			sb.append(testTargetClassname);
			sb.append(" target = new ");
			sb.append(testTargetClassname);
			sb.append("();");
			sb.append(CRLF);
			sb.append("\t\t");
		}

		if (!returnType.equals("void"))
		{
			sb.append(returnType);
			sb.append(" expected = ");
			sb.append(returnDefaultValue);
			sb.append(";");
			sb.append(CRLF);
			sb.append("\t\t");
		}
		// args define
		// ex. String arg0 = null;
		// int arg1 = 0;
		List<String> argTypes = testMethod.argTypeNames;
		List<String> args = new ArrayList<String>();
		int argTypesLen = argTypes.size();
		if (argTypesLen > 0 && argTypes.get(0) != null
				&& !argTypes.get(0).equals(STR.EMPTY))
		{
			for (int i = 0; i < argTypesLen; i++)
			{
				// flexible length args
				if (argTypes.get(i).matches(".+\\.\\.\\."))
					argTypes.set(i, argTypes.get(i).replaceAll("\\.\\.\\.", "[]"));
				sb.append(argTypes.get(i));
				sb.append(" arg");
				sb.append(i);
				sb.append(" = ");
				if (PrimitiveTypeUtil.isPrimitive(argTypes.get(i)))
				{
					Object primitiveDefault = PrimitiveTypeUtil
							.getPrimitiveDefaultValue(argTypes.get(i));
					sb.append(primitiveDefault);
				} else
					sb.append("null");
				sb.append(";");
				sb.append(CRLF);
				sb.append("\t\t");
				args.add("arg" + i);
			}
		}

		// execute target method
		// ex. SampleUtil.doSomething(null, null);
		// ex. String expected = null;
		// String actual = target.doSomething();
		if (!returnType.equals("void"))
		{
			sb.append(returnType);
			sb.append(" actual = ");
		}
		if (testMethod.isStatic)
			sb.append(testTargetClassname);
		else
			sb.append("target");

		sb.append(".");
		sb.append(testMethod.methodName);
		sb.append("(");
		if (argTypesLen > 0 && argTypes.get(0) != null
				&& !argTypes.get(0).equals(STR.EMPTY))
			sb.append(args.get(0));
		for (int i = 1; i < argTypes.size(); i++)
		{
			sb.append(", ");
			sb.append(args.get(i));
		}
		sb.append(");");
		sb.append(CRLF);

		// assert return value
		// ex. assertEquals(expected, actual);
		if (!returnType.equals("void"))
		{
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
	private static String getType(String arg)
	{
		arg = arg.trim().split("\\s+")[0];
		return arg;
	}

	/**
	 * Get type converted.
	 * 
	 * @param arg
	 * @return
	 */
	private static String getTypeAvailableInMethodName(String arg)
	{
		arg = arg.replaceAll("<.+?>", STR.EMPTY);
		arg = arg.replaceAll("final ", STR.EMPTY);
		arg = arg.replaceAll("\\.\\.\\.", "Array").replaceAll("\\[\\]", "Array");
		arg = arg.trim().split("\\s+")[0];
		return arg;
	}

}
