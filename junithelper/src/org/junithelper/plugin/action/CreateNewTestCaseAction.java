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
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;
import org.junithelper.plugin.bean.GeneratingMethodInfo;
import org.junithelper.plugin.util.FileResourceUtil;
import org.junithelper.plugin.util.ResourcePathUtil;
import org.junithelper.plugin.util.ResourceRefreshUtil;
import org.junithelper.plugin.util.TestCaseGenerateUtil;
import org.junithelper.plugin.util.ThreadUtil;

public class CreateNewTestCaseAction extends Action implements IActionDelegate,
		IEditorActionDelegate
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
		FileOutputStream fos = null;
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
				// viewer
				structuredSelection = (StructuredSelection) selection;

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
				int selectedDirArrLength = selectedDirArr.length;
				for (int i = 0; i < selectedDirArrLength - 1; i++)
					testCaseDirResource += selectedDirArr[i] + STR.DIR_SEP;
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
					File tmpDir = new File(tmpDirPath);

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
							System.err.println("Resource refresh error!");
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
					System.err.println("Resource refresh error!");
				}

				try
				{
					// confirm if already exist
					File outputFile = new File(testCaseCreateDirpath + STR.DIR_SEP
							+ testCaseFilename);
					String msg = STR.Dialog.Common.ALREADY_EXIST + " ("
							+ testCaseFilename + ")" + STR.LINE_FEED
							+ STR.Dialog.Common.CONFIRM_PROCEED;
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									STR.Dialog.Common.TITLE, msg))
					{
						// get public methods
						String targetClass = "/" + projectName + "/" + selected;
						IResource targetClassResource = workspaceRoot
								.findMember(targetClass);
						IFile file = (IFile) targetClassResource;
						List<GeneratingMethodInfo> testMethods = TestCaseGenerateUtil
								.getTestMethodsFromTarget(file);

						// generate test class
						fos = new FileOutputStream(testCaseCreateDirpath + STR.DIR_SEP
								+ testCaseFilename);
						testFileOSWriter = new OutputStreamWriter(fos);

						StringBuffer sb = new StringBuffer();

						String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

						// get package
						String testPackageString = STR.EMPTY;
						String[] tmpDirArr = selected.split(STR.DIR_SEP);
						StringBuffer dirSb = new StringBuffer();
						int packageArrLen = tmpDirArr.length - 2;
						for (int i = 3; i < packageArrLen; i++)
						{
							dirSb.append(tmpDirArr[i]);
							dirSb.append(".");
						}
						dirSb.append(tmpDirArr[packageArrLen]);
						testPackageString = dirSb.toString();

						sb.append("package ");
						sb.append(testPackageString);
						sb.append(";");
						sb.append(CRLF);

						sb.append(CRLF);

						sb.append("import junit.framework.TestCase;");
						sb.append(CRLF);
						boolean enabledTestMethodsGen = Activator.getDefault()
								.getPreferenceStore().getBoolean(
										STR.Preference.TestMethodGen.ENABLE);
						boolean enabledNotBlankMethods = Activator
								.getDefault()
								.getPreferenceStore()
								.getBoolean(
										STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION);
						if (enabledTestMethodsGen && enabledNotBlankMethods)
						{
							sb.append(CRLF);
							List<String> importedPackageList = testMethods.get(0).importList;
							for (String importedPackage : importedPackageList)
							{
								sb.append("import ");
								sb.append(importedPackage);
								sb.append(";");
								sb.append(CRLF);
							}

						}
						sb.append(CRLF);

						sb.append("public class ");
						sb.append(testCaseClassname);
						sb.append(" extends TestCase {");
						sb.append(CRLF);

						sb.append(CRLF);

						if (enabledTestMethodsGen)
						{
							for (GeneratingMethodInfo testMethod : testMethods)
							{
								sb.append("\tpublic void ");
								sb.append(testMethod.testMethodName);
								sb.append("() throws Exception {");
								sb.append(CRLF);

								sb.append("\t\t//");
								sb.append(STR.AUTO_GEN_MSG_TODO);
								sb.append(CRLF);

								if (enabledNotBlankMethods)
								{
									String notBlankSourceCode = TestCaseGenerateUtil
											.getNotBlankTestMethodSource(testMethod,
													testMethods, testTargetClassname);
									sb.append(notBlankSourceCode);
								}

								sb.append("\t}");
								sb.append(CRLF);

								sb.append(CRLF);
							}
						}
						sb.append("}");
						sb.append(CRLF);

						testFileOSWriter.write(sb.toString());
					}

				} catch (FileNotFoundException fnfe)
				{
					fnfe.printStackTrace();
				} finally
				{
					FileResourceUtil.close(testFileOSWriter);
					FileResourceUtil.close(fos);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			FileResourceUtil.close(javaFileIStream);
			FileResourceUtil.close(testFileOSWriter);
		}

		// resource refresh
		if (refreshFlag
				&& !ResourceRefreshUtil.refreshLocal(null, projectName + STR.DIR_SEP
						+ testCaseDirResource + "/.."))
		{
			MessageDialog.openWarning(new Shell(), STR.Dialog.Common.TITLE,
					STR.Dialog.Common.RESOURCE_REFRESH_ERROR);
			System.err.println("Resource refresh error!");

		} else
		{

			// open test case
			int retryCount = 0;
			IEditorPart editorPart = null;
			ThreadUtil.sleep(1500);
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
					ThreadUtil.sleep(1500);
				}
			}
			editorPart.setFocus();
		}
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
		this.selection = selection;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor)
	{
		// TODO Auto-generated method stub

	}

}
