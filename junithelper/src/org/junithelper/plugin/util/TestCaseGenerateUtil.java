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

public class TestCaseGenerateUtil
{

	static IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

	static String RXP_WS = STR.RegExp.WHITE_AND_SPACE;
	static String RXP_NOT_S_RQ = STR.RegExp.NOT_S_REQUIRED;
	static String RXP_NOT_S_NRQ = STR.RegExp.NOT_S_NOREQUIRED;

	public static List<String> getUnimplementedTestMethodNames(IFile testTarget,
			IFile testCase) throws Exception
	{
		List<String> unimplementedMethodNames = new ArrayList<String>();
		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.ENABLE);
		if (enabled)
		{
			List<String> expectedList = getTestMethodsFromTarget(testTarget);
			List<String> actualList = getMethodNames(testCase);
			for (String expected : expectedList)
			{
				boolean exist = false;
				for (String actual : actualList)
				{
					String escapedExp = expected.replaceAll("\\$", "\\\\\\$");
					if (actual.matches(escapedExp + ".*"))
					{
						exist = true;
						break;
					}
				}
				if (!exist)
					unimplementedMethodNames.add(expected);
			}
		}
		return unimplementedMethodNames;
	}

	public static List<String> getAllSourceCodeLineList(IFile javaFile) throws Exception
	{
		List<String> lines = new ArrayList<String>();
		InputStream is = null;
		BufferedReader br = null;
		try
		{
			is = javaFile.getContents();
			br = new BufferedReader(new InputStreamReader(is));
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

	private static String RXP_SEARCH_METHOD = "";
	private static final String RXP_METHOD_MODIFIERS = "[<\\w+?>|static|final|\\s]*";
	static
	{
		// prefix white and space
		RXP_SEARCH_METHOD += RXP_WS;
		// method modifiers
		// no need to support abstract methods
		RXP_SEARCH_METHOD += RXP_METHOD_MODIFIERS;
		// return type
		RXP_SEARCH_METHOD += RXP_NOT_S_RQ + "\\s+";
		// method ex.doSomething(String arg)
		RXP_SEARCH_METHOD += RXP_NOT_S_RQ + RXP_WS;
		// method args
		RXP_SEARCH_METHOD += "\\(" + "[^\\)]*?" + "\\)";
		// throws exception
		RXP_SEARCH_METHOD += RXP_WS + ".*?" + RXP_WS;
		// method start and so on
		RXP_SEARCH_METHOD += "\\{.+";
	}
	private static String RXP_SEARCH_GROUP_METHOD = "";
	static
	{
		// prefix white and space
		RXP_SEARCH_GROUP_METHOD += RXP_WS;
		// method modifiers
		// TODO exclude abstract methods
		RXP_SEARCH_GROUP_METHOD += RXP_METHOD_MODIFIERS;
		// return type
		RXP_SEARCH_GROUP_METHOD += "(" + RXP_NOT_S_RQ + ")" + "\\s+";
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

	public static List<String> getMethodNames(IFile javaFile) throws Exception
	{
		List<String> methodNames = new ArrayList<String>();

		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.ENABLE);
		if (enabled)
		{
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try
			{
				is = javaFile.getContents();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					if (publicsEach.matches(RXP_SEARCH_METHOD))
					{
						Matcher matcher = PAT_SEARCH_GROUP_METHOD.matcher(publicsEach);
						if (matcher.find())
						{
							String methodReturnType = matcher.group(1);
							String methodName = matcher.group(2);
							if (methodReturnType.equals("static"))
								methodName = methodName.split("\\s")[1];
							methodNames.add(methodName);
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
		return methodNames;
	}

	public static List<String> getTestMethodsFromTarget(IFile javaFile) throws Exception
	{

		List<String> testMethods = new ArrayList<String>();

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// enable public method test
		boolean enabled = store.getBoolean(STR.Preference.TestMethodAutoGenerate.ENABLE);
		String delimiter = store
				.getString(STR.Preference.TestMethodAutoGenerate.DELIMITER);
		boolean enabledArgs = store
				.getBoolean(STR.Preference.TestMethodAutoGenerate.ARGS);
		String argsPrefix = store
				.getString(STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX);
		String argsDelimiter = store
				.getString(STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER);
		boolean enabledReturn = store
				.getBoolean(STR.Preference.TestMethodAutoGenerate.RETURN);
		String returnPrefix = store
				.getString(STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX);
		String returnDelimiter = store
				.getString(STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER);

		if (enabled)
		{
			InputStream is = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try
			{
				is = javaFile.getContents();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					// TODO ??inner class support
					if (publicsEach.matches(RXP_SEARCH_METHOD))
					{
						Matcher matcher = PAT_SEARCH_GROUP_METHOD.matcher(publicsEach);
						if (matcher.find())
						{
							String methodReturnType = matcher.group(1);
							String methodName = matcher.group(2);
							if (methodReturnType.equals("static"))
								methodName = methodName.split("\\s")[1];
							String args = matcher.group(3);
							String[] argArr = args.split(",");
							String argTypes = STR.EMPTY;

							if (enabledArgs)
							{
								int argArrLen = argArr.length;
								for (int i = 0; i < argArrLen; i++)
								{
									String arg = STR.EMPTY;
									arg = argArr[i].replaceAll("<.+?>", STR.EMPTY);
									arg = arg.replaceAll("\\.\\.\\.", "Array")
											.replaceAll("\\[\\]", "Array");
									arg = arg.trim().split("\\s+")[0];
									argTypes += argsDelimiter + arg;
								}
							}

							String testMethodName = "test" + delimiter + methodName;
							if (enabledReturn)
							{
								methodReturnType = methodReturnType.replaceAll("<.+?>",
										STR.EMPTY);
								testMethodName += delimiter + returnPrefix
										+ returnDelimiter + methodReturnType;
							}
							if (enabledArgs)
								testMethodName += delimiter + argsPrefix + argTypes;
							testMethods.add(testMethodName);
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
}
