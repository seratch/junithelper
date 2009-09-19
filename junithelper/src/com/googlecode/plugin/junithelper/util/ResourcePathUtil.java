package com.googlecode.plugin.junithelper.util;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.googlecode.plugin.junithelper.STR;

public class ResourcePathUtil
{

	@SuppressWarnings("restriction")
	public static String getPathStartsFromProjectRoot(StructuredSelection structuredSelection)
	{

		String pathFromProjectRoot = STR.EMPTY_STIRNG;
		if (structuredSelection == null)
		{
			// ----------------------------------------
			// java editor
			// ----------------------------------------
			IWorkbenchPage activePage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			pathFromProjectRoot = STR.DIR_SEPARATOR
					+ activePage.getActiveEditor().getTitleToolTip();

		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.core.internal.resources.File)
		{
			// ----------------------------------------
			// navigator
			// ----------------------------------------
			pathFromProjectRoot = structuredSelection.toString().replace("[L",
					STR.EMPTY_STIRNG).replace("]", STR.EMPTY_STIRNG);

		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit)
		{
			// ----------------------------------------
			// package explorer
			// ----------------------------------------
			// TODO better implementation
			String cuToString = structuredSelection.toString();
			String packagePath = cuToString.split("\\[in")[1].trim().replaceAll("\\.",
					STR.DIR_SEPARATOR);
			String projectName = cuToString.split("\\[in")[3].trim().split("]")[0];
			String testTargetClassname = cuToString.split(STR.RegExp.JAVA_EXP)[0]
					.replaceAll("(\\[|\\])", STR.EMPTY_STIRNG).replaceAll(
							"Working copy ", STR.EMPTY_STIRNG).trim();
			pathFromProjectRoot = STR.DIR_SEPARATOR + projectName + STR.DIR_SEPARATOR
					+ STR.SRC_MAIN_JAVA + STR.DIR_SEPARATOR + packagePath
					+ STR.DIR_SEPARATOR + testTargetClassname + STR.JAVA_EXP;

		}
		return pathFromProjectRoot;
	}

}
