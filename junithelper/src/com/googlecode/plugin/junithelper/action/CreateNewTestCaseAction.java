package com.googlecode.plugin.junithelper.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
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

import com.googlecode.plugin.junithelper.STR;
import com.googlecode.plugin.junithelper.util.ResourcePathUtil;
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
		boolean refreshFlag = true;
		String projectName = null;
		String testCaseDirResource = null;
		String testCaseResource = null;

		try
		{
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

			String testTargetClassname = null;
			String testCaseFilename = null;
			String testCaseClassname = null;
			String testCaseCreateDirpath = null;

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
				MessageDialog.openWarning(shell, STR.Dialog.Common.TITLE,
						STR.Dialog.Common.REQUIRED);
				refreshFlag = false;
			} else if (structuredSelection != null && structuredSelection.size() > 1)
			{
				// select one only
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, STR.Dialog.Common.TITLE,
						STR.Dialog.Common.SELECT_ONLY_ONE);
				refreshFlag = false;
			} else
			{

				// path started from project root
				String pathFromProjectRoot = ResourcePathUtil
						.getPathStartsFromProjectRoot(structuredSelection);

				// path started from project root
				// ex. /{projectName}/src/main/java/hoge/foo/var/TestTarget.java
				String[] dirArrFromProjectRoot = pathFromProjectRoot
						.split(STR.DIR_SEPARATOR);
				// test case file create filesystem path
				String selected = STR.EMPTY_STIRNG;
				int len = dirArrFromProjectRoot.length;
				for (int i = 2; i < len; i++)
					selected += dirArrFromProjectRoot[i] + STR.DIR_SEPARATOR;

				// current project name
				projectName = dirArrFromProjectRoot[1];

				// last element is test class file name
				String testTargetClassFilename = dirArrFromProjectRoot[dirArrFromProjectRoot.length - 1];
				testTargetClassname = testTargetClassFilename.replace(STR.JAVA_EXP,
						STR.EMPTY_STIRNG);

				// test class name to create
				testCaseClassname = testTargetClassname + STR.SUFFIX_OF_TESTCASE;
				// test case name to open
				testCaseFilename = testCaseClassname + STR.JAVA_EXP;

				// get workspace path on os file system
				String projectRootPath = workspaceRoot.getLocation() + STR.DIR_SEPARATOR
						+ projectName + STR.DIR_SEPARATOR;

				testCaseResource = selected.replace(STR.SRC_MAIN_JAVA, STR.SRC_TEST_JAVA)
						.replace(STR.JAVA_EXP, STR.SUFFIX_OF_TESTCASE + STR.JAVA_EXP);
				String[] selectedDirArr = selected.split(STR.DIR_SEPARATOR);
				testCaseDirResource = "";
				for (int i = 0; i < selectedDirArr.length - 1; i++)
				{
					testCaseDirResource += selectedDirArr[i] + STR.DIR_SEPARATOR;
				}
				testCaseDirResource = testCaseDirResource.replace(STR.SRC_MAIN_JAVA,
						STR.SRC_TEST_JAVA);
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;

				File testDir = new File(testCaseCreateDirpath);

				// check directory exist
				String[] dirArr = testCaseCreateDirpath.split(STR.DIR_SEPARATOR);
				String tmpDirPath = STR.EMPTY_STIRNG;
				String tmpResourceDirPath = STR.EMPTY_STIRNG;
				for (String each : dirArr)
				{
					tmpDirPath += STR.DIR_SEPARATOR + each;
					java.io.File tmpDir = new java.io.File(tmpDirPath);

					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length())
						continue;

					tmpResourceDirPath += STR.DIR_SEPARATOR + each;
					if (!tmpDir.exists())
					{
						if (!tmpDir.mkdir())
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						if (!ResourceSynchronizerUtil.accessSynchronizeServer(null,
								projectName + STR.DIR_SEPARATOR + tmpResourceDirPath
										+ "/.." + "=INFINITE"))
						{
							String msg = STR.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING;
							MessageDialog.openWarning(new Shell(),
									STR.Dialog.Common.TITLE, msg);
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
						+ STR.DIR_SEPARATOR + testCaseDirResource + "=ONE"))
				{
					MessageDialog.openWarning(new Shell(), STR.Dialog.Common.TITLE,
							STR.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING);
					System.err.println("access error - ResourceSynchronizer server");
				}

				try
				{
					// 既に存在する場合上書き確認
					java.io.File outputFile = new java.io.File(testCaseCreateDirpath
							+ STR.DIR_SEPARATOR + testCaseFilename);
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									STR.Dialog.Common.TITLE,
									STR.Dialog.Common.ALREADY_EXIST + " ("
											+ testCaseFilename + ")"))
					{

						// test class generator
						testFileOSWriter = new OutputStreamWriter(new FileOutputStream(
								testCaseCreateDirpath + STR.DIR_SEPARATOR
										+ testCaseFilename));

						StringBuffer sb = new StringBuffer();

						String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

						// get package
						String testPackageString = STR.EMPTY_STIRNG;
						String[] tmpDirArr = selected.split(STR.DIR_SEPARATOR);
						for (int i = 3; i < tmpDirArr.length - 2; i++)
							testPackageString += tmpDirArr[i] + ".";
						testPackageString += tmpDirArr[tmpDirArr.length - 2];

						sb.append("package " + testPackageString + ";" + CRLF);

						sb.append(STR.EMPTY_STIRNG + CRLF);
						sb.append("import junit.framework.TestCase;" + CRLF);
						sb.append(STR.EMPTY_STIRNG + CRLF);
						sb.append("public class " + testCaseClassname
								+ " extends TestCase {" + CRLF);
						sb.append(STR.EMPTY_STIRNG + CRLF);
						sb.append(STR.EMPTY_STIRNG + CRLF);
						sb.append(STR.EMPTY_STIRNG + CRLF);
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
						projectName + STR.DIR_SEPARATOR + testCaseDirResource + "/.."
								+ "=INFINITE"))
		{
			MessageDialog.openWarning(new Shell(), STR.Dialog.Common.TITLE,
					STR.Dialog.Common.RESOURCE_SYNC_SERVER_NOT_RUNNING);
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
	}

}
