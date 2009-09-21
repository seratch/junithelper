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

public class TestCaseGenerateUtil
{

	static IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

	public static List<String> getTestMethodsFromTarget(IFile javaFile) throws Exception
	{
		List<String> testMethods = new ArrayList<String>();
		InputStream is = javaFile.getContents();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer tmpsb = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null)
			tmpsb.append(line + " ");
		String allSrc = tmpsb.toString();
		String[] publics = allSrc.split("public");
		for (String publicsEach : publics)
		{
			// TODO ??inner class support
			if (publicsEach.matches("\\s*.+?\\s*.+?\\s*\\(.*?\\)\\s*\\{.+"))
			{
				Matcher matcher = Pattern.compile(
						"\\s*(.+?)\\s+(.+?)\\s*\\((.*?)\\)\\s*\\{.+")
						.matcher(publicsEach);
				if (matcher.find())
				{
					String methodReturnType = matcher.group(1);
					String methodName = matcher.group(2);
					String args = matcher.group(3);
					String[] argArr = args.split("\\s");
					String argTypes = "";
					for (int i = 0; i < argArr.length; i += 2)
					{
						argTypes += "$" + argArr[i];
					}
					String testMethodName = "test_" + methodName + "_RET$"
							+ methodReturnType + "_ARG" + argTypes;
					testMethods.add(testMethodName);
				}
			}
		}
		return testMethods;
	}

}
