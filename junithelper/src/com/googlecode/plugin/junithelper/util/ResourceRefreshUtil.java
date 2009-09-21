package com.googlecode.plugin.junithelper.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

@SuppressWarnings("restriction")
public class ResourceRefreshUtil
{

	// get progress monitor
	private static IWorkbench workbench = PlatformUI.getWorkbench();
	private static WorkbenchWindow workbenchWindow = (WorkbenchWindow) workbench
			.getActiveWorkbenchWindow();
	private static IActionBars bars = workbenchWindow.getActionBars();
	private static IStatusLineManager lineManager = bars.getStatusLineManager();
	private static IProgressMonitor monitor = lineManager.getProgressMonitor();

	public static boolean refreshLocal(IWorkbenchWindow window, String param)
	{
		try
		{
			// get resource
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = workspaceRoot.findMember(param);
			// refresh resource
			resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
