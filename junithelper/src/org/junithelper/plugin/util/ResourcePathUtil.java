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

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junithelper.plugin.STR;

/**
 * ResourcePathUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class ResourcePathUtil
{

	/**
	 * Get resource path that starts with project root path.
	 * 
	 * @param structuredSelection
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getPathStartsFromProjectRoot(
			StructuredSelection structuredSelection)
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
			pathFromProjectRoot = structuredSelection.toString().replace("[L", STR.EMPTY)
					.replace("]", STR.EMPTY);

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
					.replaceAll("(\\[|\\])", STR.EMPTY).replaceAll("Working copy ",
							STR.EMPTY).trim();
			pathFromProjectRoot = STR.DIR_SEP + projectName + STR.DIR_SEP
					+ STR.SRC_MAIN_JAVA + STR.DIR_SEP + packagePath + STR.DIR_SEP
					+ testTargetClassname + STR.JAVA_EXP;

		}
		return pathFromProjectRoot;
	}

}
