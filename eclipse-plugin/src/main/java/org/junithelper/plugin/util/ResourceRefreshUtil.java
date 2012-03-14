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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

@SuppressWarnings("restriction")
public final class ResourceRefreshUtil {

    // get progress monitor
    private static IWorkbench workbench;
    private static WorkbenchWindow workbenchWindow;
    private static IActionBars bars;
    private static IStatusLineManager lineManager;
    private static IProgressMonitor monitor;

    static {
        try {
            workbench = PlatformUI.getWorkbench();
            workbenchWindow = (WorkbenchWindow) workbench.getActiveWorkbenchWindow();
            bars = workbenchWindow.getActionBars();
            lineManager = bars.getStatusLineManager();
            monitor = lineManager.getProgressMonitor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean refreshLocal(IWorkbenchWindow window, String param) {
        try {
            IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
            IResource resource = workspaceRoot.findMember(param);
            // refresh resource
            resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
