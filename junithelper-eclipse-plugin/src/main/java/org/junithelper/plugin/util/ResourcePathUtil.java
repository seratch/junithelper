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
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.plugin.page.PreferenceLoader;

public final class ResourcePathUtil {

    public static IPreferenceStore store = null;

    @SuppressWarnings("restriction")
    public static String getPathStartsFromProjectRoot(StructuredSelection structuredSelection) {
        PreferenceLoader pref = new PreferenceLoader(store);
        String pathFromProjectRoot = StringValue.Empty;
        if (structuredSelection == null) {
            // ----------------------------------------
            // java editor
            // ----------------------------------------
            IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
            pathFromProjectRoot = StringValue.DirectorySeparator.General
                    + activePage.getActiveEditor().getTitleToolTip();
        } else if (structuredSelection.getFirstElement() instanceof org.eclipse.core.internal.resources.File) {
            // ----------------------------------------
            // navigator
            // ----------------------------------------
            pathFromProjectRoot = structuredSelection.toString().replace("[L", StringValue.Empty).replace("]",
                    StringValue.Empty);
        } else if (structuredSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit) {
            // ----------------------------------------
            // package explorer
            // ----------------------------------------
            // TODO better implementation
            String cuToString = structuredSelection.toString();
            String packagePath = cuToString.split("\\[in")[1].trim().replaceAll("\\.",
                    StringValue.DirectorySeparator.General);
            String projectName = cuToString.split("\\[in")[3].trim().split("]")[0];
            String testTargetClassname = cuToString.split(RegExp.FileExtension.JavaFile)[0].replaceAll("(\\[|\\])",
                    StringValue.Empty).replaceAll("Working copy ", StringValue.Empty).trim();
            pathFromProjectRoot = StringValue.DirectorySeparator.General + projectName
                    + StringValue.DirectorySeparator.General + pref.directoryPathOfProductSourceCode
                    + StringValue.DirectorySeparator.General + packagePath + StringValue.DirectorySeparator.General
                    + testTargetClassname + StringValue.DirectorySeparator.General;
        }
        return pathFromProjectRoot;
    }

}
