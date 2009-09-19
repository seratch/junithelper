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

		String pathFromProjectRoot = STR.EMPTY;
		if (structuredSelection == null)
		{
			// ----------------------------------------
			// java editor
			// ----------------------------------------
			IWorkbenchPage activePage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			pathFromProjectRoot = STR.DIR_SEP
					+ activePage.getActiveEditor().getTitleToolTip();

		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.core.internal.resources.File)
		{
			// ----------------------------------------
			// navigator
			// ----------------------------------------
			pathFromProjectRoot = structuredSelection.toString().replace("[L",
					STR.EMPTY).replace("]", STR.EMPTY);

		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit)
		{
			// ----------------------------------------
			// package explorer
			// ----------------------------------------
			// TODO better implementation
			String cuToString = structuredSelection.toString();
			String packagePath = cuToString.split("\\[in")[1].trim().replaceAll("\\.",
					STR.DIR_SEP);
			String projectName = cuToString.split("\\[in")[3].trim().split("]")[0];
			String testTargetClassname = cuToString.split(STR.RegExp.JAVA_EXP)[0]
					.replaceAll("(\\[|\\])", STR.EMPTY).replaceAll(
							"Working copy ", STR.EMPTY).trim();
			pathFromProjectRoot = STR.DIR_SEP + projectName + STR.DIR_SEP
					+ STR.SRC_MAIN_JAVA + STR.DIR_SEP + packagePath
					+ STR.DIR_SEP + testTargetClassname + STR.JAVA_EXP;

		}
		return pathFromProjectRoot;
	}

}
