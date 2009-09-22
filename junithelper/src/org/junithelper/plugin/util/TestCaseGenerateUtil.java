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
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;

public class TestCaseGenerateUtil
{

	static IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

	static String RXP_WS = STR.RegExp.WHITE_AND_SPACE;
	static String RXP_ANY_RQ = STR.RegExp.ANY_WORDS_REQUIRED;
	static String RXP_ANY_NRQ = STR.RegExp.ANY_WORDS_NOT_REQUIRED;

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
					if (actual.matches(".*" + escapedExp + ".*"))
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
			if (is != null)
				try
				{
					is.close();
				} catch (Exception ignore)
				{
				}
			if (br != null)
				try
				{
					br.close();
				} catch (Exception ignore)
				{
				}
		}
		return lines;
	}

	public static List<String> getMethodNames(IFile javaFile) throws Exception
	{
		List<String> methodNames = new ArrayList<String>();

		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.ENABLE);
		if (enabled)
		{
			InputStream is = null;
			BufferedReader br = null;
			try
			{
				is = javaFile.getContents();
				br = new BufferedReader(new InputStreamReader(is));
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					if (publicsEach.matches(RXP_WS + RXP_ANY_RQ + "\\s+" + RXP_ANY_RQ
							+ RXP_WS + "\\(" + RXP_ANY_NRQ + "\\)" + RXP_WS + RXP_ANY_NRQ
							+ RXP_WS + RXP_ANY_NRQ + "\\{.+"))
					{
						Matcher matcher = Pattern.compile(
								RXP_WS + "(" + RXP_ANY_RQ + ")\\s+(" + RXP_ANY_RQ + ")"
										+ RXP_WS + "\\((" + RXP_ANY_NRQ + ")\\)" + RXP_WS
										+ RXP_ANY_NRQ + RXP_WS + RXP_ANY_NRQ + "\\{.+")
								.matcher(publicsEach);
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
				if (is != null)
					try
					{
						is.close();
					} catch (Exception ignore)
					{
					}
				if (br != null)
					try
					{
						br.close();
					} catch (Exception ignore)
					{
					}
			}

		}
		return methodNames;
	}

	public static List<String> getTestMethodsFromTarget(IFile javaFile) throws Exception
	{

		List<String> testMethods = new ArrayList<String>();

		// enable public method test
		boolean enabled = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.ENABLE);
		String delimiter = Activator.getDefault().getPreferenceStore().getString(
				STR.Preference.TestMethodAutoGenerate.DELIMITER);
		boolean enabledArgs = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.ARGS);
		String argsPrefix = Activator.getDefault().getPreferenceStore().getString(
				STR.Preference.TestMethodAutoGenerate.ARGS_PREFIX);
		String argsDelimiter = Activator.getDefault().getPreferenceStore().getString(
				STR.Preference.TestMethodAutoGenerate.ARGS_DELIMITER);
		boolean enabledReturn = Activator.getDefault().getPreferenceStore().getBoolean(
				STR.Preference.TestMethodAutoGenerate.RETURN);
		String returnPrefix = Activator.getDefault().getPreferenceStore().getString(
				STR.Preference.TestMethodAutoGenerate.RETURN_PREFIX);
		String returnDelimiter = Activator.getDefault().getPreferenceStore().getString(
				STR.Preference.TestMethodAutoGenerate.RETURN_DELIMITER);

		if (enabled)
		{
			InputStream is = null;
			BufferedReader br = null;
			try
			{
				is = javaFile.getContents();
				br = new BufferedReader(new InputStreamReader(is));
				StringBuffer tmpsb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null)
					tmpsb.append(line + " ");
				String allSrc = tmpsb.toString();
				String[] publics = allSrc.split("public");
				for (String publicsEach : publics)
				{
					// TODO ??inner class support
					if (publicsEach.matches(RXP_WS + RXP_ANY_RQ + "\\s+" + RXP_ANY_RQ
							+ RXP_WS + "\\(" + RXP_ANY_NRQ + "\\)" + RXP_WS + RXP_ANY_NRQ
							+ RXP_WS + RXP_ANY_NRQ + "\\{.+"))
					{
						Matcher matcher = Pattern.compile(
								RXP_WS + "(" + RXP_ANY_RQ + ")\\s+(" + RXP_ANY_RQ + ")"
										+ RXP_WS + "\\((" + RXP_ANY_NRQ + ")\\)" + RXP_WS
										+ RXP_ANY_NRQ + RXP_WS + RXP_ANY_NRQ + "\\{.+")
								.matcher(publicsEach);
						if (matcher.find())
						{
							String methodReturnType = matcher.group(1);
							String methodName = matcher.group(2);
							if (methodReturnType.equals("static"))
								methodName = methodName.split("\\s")[1];
							String args = matcher.group(3);
							String[] argArr = args.split(",");
							String argTypes = STR.EMPTY;
							for (int i = 0; i < argArr.length; i++)
							{
								String arg = STR.EMPTY;
								arg = argArr[i].replaceAll("<.+?>", STR.EMPTY);
								arg = arg.trim();
								arg = arg.replaceAll("\\.\\.\\.", "Array").replaceAll(
										"\\[\\]", "Array");
								arg = arg.split("\\s+")[0];
								argTypes += argsDelimiter + arg;
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
				if (is != null)
					try
					{
						is.close();
					} catch (Exception ignore)
					{
					}
				if (br != null)
					try
					{
						br.close();
					} catch (Exception ignore)
					{
					}
			}

		}
		return testMethods;
	}
}
