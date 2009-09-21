package org.junithelper.plugin.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import org.junithelper.plugin.STR;
import org.junithelper.plugin.util.ResourcePathUtil;
import org.junithelper.plugin.util.ResourceRefreshUtil;
import org.junithelper.plugin.util.TestCaseGenerateUtil;

public class CreateNewTestCaseAction extends Action implements IActionDelegate
{

	private ISelection selection = null;

	public void run(IAction action, ISelection selection)
	{
		this.selection = selection;
		this.run(action);
	}

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
				String[] dirArrFromProjectRoot = pathFromProjectRoot.split(STR.DIR_SEP);
				// test case file create filesystem path
				String selected = STR.EMPTY;
				int len = dirArrFromProjectRoot.length;
				for (int i = 2; i < len - 1; i++)
					selected += dirArrFromProjectRoot[i] + STR.DIR_SEP;
				selected += dirArrFromProjectRoot[len - 1];

				// current project name
				projectName = dirArrFromProjectRoot[1];

				// last element is test class file name
				String testTargetClassFilename = dirArrFromProjectRoot[dirArrFromProjectRoot.length - 1];
				testTargetClassname = testTargetClassFilename.replace(STR.JAVA_EXP,
						STR.EMPTY);

				// test class name to create
				testCaseClassname = testTargetClassname + STR.SUFFIX_OF_TESTCASE;
				// test case name to open
				testCaseFilename = testCaseClassname + STR.JAVA_EXP;

				// get workspace path on os file system
				String projectRootPath = workspaceRoot.getLocation() + STR.DIR_SEP
						+ projectName + STR.DIR_SEP;

				testCaseResource = selected.replace(STR.SRC_MAIN_JAVA, STR.SRC_TEST_JAVA)
						.replace(STR.JAVA_EXP, STR.SUFFIX_OF_TESTCASE + STR.JAVA_EXP);
				String[] selectedDirArr = selected.split(STR.DIR_SEP);
				testCaseDirResource = "";
				for (int i = 0; i < selectedDirArr.length - 1; i++)
				{
					testCaseDirResource += selectedDirArr[i] + STR.DIR_SEP;
				}
				testCaseDirResource = testCaseDirResource.replace(STR.SRC_MAIN_JAVA,
						STR.SRC_TEST_JAVA);
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;

				File testDir = new File(testCaseCreateDirpath);

				// check directory exist
				String[] dirArr = testCaseCreateDirpath.split(STR.DIR_SEP);
				String tmpDirPath = STR.EMPTY;
				String tmpResourceDirPath = STR.EMPTY;
				for (String each : dirArr)
				{
					tmpDirPath += STR.DIR_SEP + each;
					java.io.File tmpDir = new java.io.File(tmpDirPath);

					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length())
						continue;

					tmpResourceDirPath += STR.DIR_SEP + each;
					if (!tmpDir.exists())
					{
						if (!tmpDir.mkdir())
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						if (!ResourceRefreshUtil.refreshLocal(null, projectName
								+ STR.DIR_SEP + tmpResourceDirPath + "/.."))
						{
							String msg = STR.Dialog.Common.RESOURCE_REFRESH_ERROR;
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
				if (!ResourceRefreshUtil.refreshLocal(null, projectName + STR.DIR_SEP
						+ testCaseDirResource))
				{
					MessageDialog.openWarning(new Shell(), STR.Dialog.Common.TITLE,
							STR.Dialog.Common.RESOURCE_REFRESH_ERROR);
					System.err.println("access error - ResourceSynchronizer server");
				}

				try
				{
					// confirm if already exist
					File outputFile = new File(testCaseCreateDirpath + STR.DIR_SEP
							+ testCaseFilename);
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									STR.Dialog.Common.TITLE,
									STR.Dialog.Common.ALREADY_EXIST + " ("
											+ testCaseFilename + ")"))
					{

						// TODO
						// get public methods
						String targetClass = "/" + projectName + "/" + selected;
						IResource targetClassResource = workspaceRoot
								.findMember(targetClass);
						IFile file = (IFile) targetClassResource;
						List<String> testMethods = TestCaseGenerateUtil
								.getTestMethodsFromTarget(file);

						// generate test class
						testFileOSWriter = new OutputStreamWriter(new FileOutputStream(
								testCaseCreateDirpath + STR.DIR_SEP + testCaseFilename));

						StringBuffer sb = new StringBuffer();

						String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

						// get package
						String testPackageString = STR.EMPTY;
						String[] tmpDirArr = selected.split(STR.DIR_SEP);
						for (int i = 3; i < tmpDirArr.length - 2; i++)
							testPackageString += tmpDirArr[i] + ".";
						testPackageString += tmpDirArr[tmpDirArr.length - 2];

						sb.append("package " + testPackageString + ";" + CRLF);

						sb.append(STR.EMPTY + CRLF);
						sb.append("import junit.framework.TestCase;" + CRLF);
						sb.append(STR.EMPTY + CRLF);
						sb.append("public class " + testCaseClassname
								+ " extends TestCase {" + CRLF);
						sb.append(STR.EMPTY + CRLF);
						for (String testMethod : testMethods)
						{
							sb.append("\tpublic void " + testMethod
									+ "() throws Exception {" + CRLF);
							sb.append(STR.EMPTY + CRLF);
							sb.append("\t}" + CRLF);
							sb.append(STR.EMPTY + CRLF);
						}
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
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (testFileOSWriter != null)
					testFileOSWriter.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// access ResourceSynchronizer server
		if (refreshFlag && !ResourceRefreshUtil.refreshLocal(null,
		// projectName + "=INFINITE") ) {
				projectName + STR.DIR_SEP + testCaseDirResource + "/.."))
		{
			MessageDialog.openWarning(new Shell(), STR.Dialog.Common.TITLE,
					STR.Dialog.Common.RESOURCE_REFRESH_ERROR);
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
