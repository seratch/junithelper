package com.googlecode.plugin.junithelper.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.googlecode.plugin.junithelper.IConstants;
import com.googlecode.plugin.junithelper.util.ResourceSynchronizerUtil;

public class CreateNewTestCaseAction extends Action implements IActionDelegate
{

	private ISelection selection = null;

	public void run(IAction action, ISelection selection)
	{
		this.selection = selection;
		this.run(action);
	}

	@SuppressWarnings("restriction")
	public void run(IAction action)
	{

		InputStream javaFileIStream = null;
		OutputStreamWriter testFileOSWriter = null;
		String projectName = null;
		boolean refreshFlag = true;
		String testTargetClassname = null;
		String testCaseFilename = null;
		String testCaseClassname = null;
		String testCaseCreateDirpath = null;
		String testCaseResource = null;
		String testCaseDirResource = null;

		try
		{

			StructuredSelection structuredSelection = null;
			if (selection instanceof StructuredSelection)
			{
				// viewer
				structuredSelection = (StructuredSelection) selection;
			}

			if (structuredSelection != null && structuredSelection.size() == 0)
			{
				// required select
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, IConstants.Dialog.Common.TITLE,
						IConstants.Dialog.Common.REQUIRED);
				refreshFlag = false;
			} else if (structuredSelection != null && structuredSelection.size() > 1)
			{
				// select one only
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, IConstants.Dialog.Common.TITLE,
						IConstants.Dialog.Common.SELECT_ONLY_ONE);
				refreshFlag = false;
			} else
			{

				// get test case file create filesystem path
				String selected = "";

				if (structuredSelection == null)
				{
					// java editor
					IWorkbenchPage activePage = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					selected = activePage.getActiveEditor().getTitleToolTip();
					String[] dirArr = selected.split(IConstants.DIR_SEPARATOR);
					selected = "";
					for (int i = 1; i < dirArr.length; i++)
					{
						selected += dirArr[i] + IConstants.DIR_SEPARATOR;
					}
					selected = selected.replace(".java/", ".java");
					testTargetClassname = dirArr[dirArr.length - 1].replace(".java",
							IConstants.EMPTY_STIRNG);
					testCaseFilename = testTargetClassname + "Test" + IConstants.JAVA_EXP;
					testCaseClassname = testCaseFilename.replace(IConstants.JAVA_EXP,
							IConstants.EMPTY_STIRNG);

				} else if (structuredSelection.getFirstElement() instanceof File)
				{
					// navigator
					String resourceStr = selection.toString().split("src")[1].replace(
							"]", IConstants.EMPTY_STIRNG);
					String[] dirSepArr = resourceStr.split(IConstants.DIR_SEPARATOR);
					testTargetClassname = dirSepArr[dirSepArr.length - 1].replace(
							IConstants.JAVA_EXP, IConstants.EMPTY_STIRNG);
					testCaseFilename = dirSepArr[dirSepArr.length - 1].replace(
							IConstants.JAVA_EXP, "Test" + IConstants.JAVA_EXP);
					testCaseClassname = testCaseFilename.replace(IConstants.JAVA_EXP,
							IConstants.EMPTY_STIRNG);

					selected = "src";
					for (String each : dirSepArr)
					{
						if (each != null && !each.equals(IConstants.EMPTY_STIRNG))
							selected += IConstants.DIR_SEPARATOR + each;
					}

				} else if (structuredSelection.getFirstElement() instanceof CompilationUnit)
				{

					// package explorer
					String classInfoStr = selection.toString();
					selected = classInfoStr.split("\\[in")[1].trim();
					selected = selected.replaceAll("\\.", IConstants.DIR_SEPARATOR);
					testTargetClassname = classInfoStr.split(IConstants.RegExp.JAVA_EXP)[0]
							.replaceAll("(\\[|\\])", IConstants.EMPTY_STIRNG).replaceAll(
									"Working copy ", IConstants.EMPTY_STIRNG).trim();
					testCaseFilename = testTargetClassname + "Test" + IConstants.JAVA_EXP;
					testCaseClassname = testCaseFilename.replace(IConstants.JAVA_EXP,
							IConstants.EMPTY_STIRNG);

					selected = "src/main/java/" + selected + IConstants.DIR_SEPARATOR
							+ testTargetClassname + ".java";

				}

				// get workspace path on os file system
				String projectRootPath = System.getProperty("user.dir");
				// for develpment
				if (projectRootPath.matches(".*eclipse$"))
				{
					String baseDirDev = System.getProperty("osgi.logfile");
					// C:\works\galileo_plugin\runtime-EclipseApplication\.metadata\.log
					baseDirDev = baseDirDev.replaceAll(IConstants.WINDOWS_DIR_SEPARATOR,
							IConstants.DIR_SEPARATOR);
					String dirArr[] = baseDirDev.split(IConstants.DIR_SEPARATOR);
					projectRootPath = "";
					for (int i = 0; i < dirArr.length; i++)
					{
						if (dirArr[i].equals(".metadata"))
							break;
						projectRootPath += dirArr[i] + IConstants.DIR_SEPARATOR;
					}
					projectRootPath += "sample" + IConstants.DIR_SEPARATOR;
				}

				String[] tmpStrArr = projectRootPath.split(IConstants.DIR_SEPARATOR);
				projectName = tmpStrArr[tmpStrArr.length - 1];

				testCaseResource = selected.replace("src/main/java", "src/test/java")
						.replace(".java", "Test.java");
				String[] selectedDirArr = selected.split(IConstants.DIR_SEPARATOR);
				testCaseDirResource = "";
				for (int i = 0; i < selectedDirArr.length - 1; i++)
				{
					testCaseDirResource += selectedDirArr[i] + IConstants.DIR_SEPARATOR;
				}
				testCaseDirResource = testCaseDirResource.replace("src/main/java",
						"src/test/java");
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;

				java.io.File testDir = new java.io.File(testCaseCreateDirpath);

				// check directory exist
				String[] dirArr = testCaseCreateDirpath.split(IConstants.DIR_SEPARATOR);
				String tmpDirPath = "";
				String tmpResourceDirPath = "";
				for (String each : dirArr)
				{
					tmpDirPath += IConstants.DIR_SEPARATOR + each;
					java.io.File tmpDir = new java.io.File(tmpDirPath);

					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length())
						continue;

					tmpResourceDirPath += IConstants.DIR_SEPARATOR + each;
					if (!tmpDir.exists())
					{
						if (!tmpDir.mkdir())
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						if (!ResourceSynchronizerUtil.accessSynchronizeServer(null,
								projectName + IConstants.DIR_SEPARATOR
										+ tmpResourceDirPath + "/.." + "=INFINITE"))
						{
							String msg = IConstants.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING;
							MessageDialog.openWarning(new Shell(),
									IConstants.Dialog.Common.TITLE, msg);
							System.err
									.println("access error : ResourceSynchronizer server");
						}
					}
				}

				// execute create directory
				if (!testDir.mkdirs())
					System.err.println("test directory create error or already exist");

				// resource sync
				if (!ResourceSynchronizerUtil.accessSynchronizeServer(null, projectName
						+ IConstants.DIR_SEPARATOR + testCaseDirResource + "=ONE"))
				{
					MessageDialog.openWarning(new Shell(),
							IConstants.Dialog.Common.TITLE,
							IConstants.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING);
					System.err.println("access error - ResourceSynchronizer server");
				}

				try
				{
					// 既に存在する場合上書き確認
					java.io.File outputFile = new java.io.File(testCaseCreateDirpath
							+ IConstants.DIR_SEPARATOR + testCaseFilename);
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									IConstants.Dialog.Common.TITLE,
									IConstants.Dialog.Common.ALREADY_EXIST + " ("
											+ testCaseFilename + ")"))
					{

						// test class generator
						testFileOSWriter = new OutputStreamWriter(new FileOutputStream(
								testCaseCreateDirpath + IConstants.DIR_SEPARATOR
										+ testCaseFilename));

						StringBuffer sb = new StringBuffer();

						String CRLF = IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED;

						// get package
						String testPackageString = IConstants.EMPTY_STIRNG;
						String[] tmpDirArr = selected.split(IConstants.DIR_SEPARATOR);
						for (int i = 3; i < tmpDirArr.length - 2; i++)
							testPackageString += tmpDirArr[i] + ".";
						testPackageString += tmpDirArr[tmpDirArr.length - 2];

						sb.append("package " + testPackageString + ";" + CRLF);

						sb.append("" + CRLF);
						sb.append("import junit.framework.TestCase;" + CRLF);
						sb.append("" + CRLF);
						sb.append("public class " + testCaseClassname
								+ " extends TestCase {" + CRLF);
						sb.append("" + CRLF);
						sb.append("" + CRLF);
						sb.append("" + CRLF);
						sb.append("}" + CRLF);

						testFileOSWriter.write(sb.toString());
					}

				} catch (FileNotFoundException fnfe)
				{
					fnfe.printStackTrace();
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (javaFileIStream != null)
					javaFileIStream.close();
				if (testFileOSWriter != null)
					testFileOSWriter.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// access ResourceSynchronizer server
		if (refreshFlag
				&& !ResourceSynchronizerUtil.accessSynchronizeServer(null,
				// projectName + "=INFINITE") ) {
						projectName + IConstants.DIR_SEPARATOR + testCaseDirResource
								+ "/.." + "=INFINITE"))
		{
			MessageDialog.openWarning(new Shell(), IConstants.Dialog.Common.TITLE,
					IConstants.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING);
			System.err.println("access error - ResourceSynchronizer server");

		} else
		{

			// open test case
			int retryCount = 0;
			IEditorPart editorPart = null;
			try
			{
				Thread.sleep(1500);
			} catch (InterruptedException irte)
			{
			}
			while (true)
			{
				try
				{
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IWorkspaceRoot root = workspace.getRoot();
					IProject project = root.getProject(projectName);
					IFile testCaseFile = project.getFile(testCaseResource);
					String editorId = IDE.getEditorDescriptor(testCaseFile.getName())
							.getId();
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					editorPart = IDE.openEditor(page, testCaseFile, editorId);
					if (editorPart == null)
						throw new NullPointerException();
					break;
				} catch (Exception e)
				{
					retryCount++;
					if (retryCount > 3)
						break;
					try
					{
						Thread.sleep(1500);
					} catch (InterruptedException irte)
					{
					}
				}
			}
			editorPart.setFocus();
		}
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
		this.selection = selection;
		// TODO : popup menu enable/disable
	}

}
