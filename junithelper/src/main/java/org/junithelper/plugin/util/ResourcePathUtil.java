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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.junithelper.plugin.constant.RegExp;
import org.junithelper.plugin.constant.StrConst;
import org.junithelper.plugin.page.PreferenceLoader;

/**
 * ResourcePathUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public final class ResourcePathUtil {

	public static IPreferenceStore store = null;

	/**
	 * Get resource path that starts with project root path.
	 * 
	 * @param structuredSelection
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getPathStartsFromProjectRoot(
			StructuredSelection structuredSelection) {
		PreferenceLoader pref = new PreferenceLoader(store);
		String pathFromProjectRoot = StrConst.empty;
		if (structuredSelection == null) {
			// ----------------------------------------
			// java editor
			// ----------------------------------------
			IWorkbenchPage activePage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			pathFromProjectRoot = StrConst.dirSep
					+ activePage.getActiveEditor().getTitleToolTip();
		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.core.internal.resources.File) {
			// ----------------------------------------
			// navigator
			// ----------------------------------------
			pathFromProjectRoot = structuredSelection.toString()
					.replace("[L", StrConst.empty).replace("]", StrConst.empty);
		} else if (structuredSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit) {
			// ----------------------------------------
			// package explorer
			// ----------------------------------------
			// TODO better implementation
			String cuToString = structuredSelection.toString();
			String packagePath = cuToString.split("\\[in")[1].trim()
					.replaceAll("\\.", StrConst.dirSep);
			String projectName = cuToString.split("\\[in")[3].trim().split("]")[0];
			String testTargetClassname = cuToString.split(RegExp.javaFileExp)[0]
					.replaceAll("(\\[|\\])", StrConst.empty)
					.replaceAll("Working copy ", StrConst.empty).trim();
			pathFromProjectRoot = StrConst.dirSep + projectName
					+ StrConst.dirSep + pref.commonSrcMainJavaDir
					+ StrConst.dirSep + packagePath + StrConst.dirSep
					+ testTargetClassname + StrConst.javaFileExp;
		}
		return pathFromProjectRoot;
	}

}
