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
		OutputStreamWriter testDiconOSWriter = null;
		String projectName = null;
		boolean refreshFlag = true;
		String testTargetClassname = null;
		String testCaseFilename = null;
		String testCaseClassname = null;
		String testCaseCreateDirpath = null;
		String testDiconFilename = null;
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
				// 選択して下さい
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, IConstants.Dialog.Common.TITLE,
						IConstants.Dialog.Common.REQUIRED);
				refreshFlag = false;
			} else if (structuredSelection != null && structuredSelection.size() > 1)
			{
				// 一つしか選べません
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
					// TODO : learn more to get file path
					String testClassPath = activePage.getActiveEditor().getTitleToolTip();

					String[] dirArr = testClassPath.split(IConstants.DIR_SEPARATOR);
					testCaseFilename = dirArr[dirArr.length - 1].replace(
							IConstants.JAVA_EXP, "Test" + IConstants.JAVA_EXP);
					testDiconFilename = dirArr[dirArr.length - 1].replace(
							IConstants.JAVA_EXP, "Test" + IConstants.DICON_EXP);
					testTargetClassname = dirArr[dirArr.length - 1].replace(
							IConstants.JAVA_EXP, IConstants.EMPTY_STIRNG);
					testCaseClassname = testCaseFilename.replace(IConstants.JAVA_EXP,
							IConstants.EMPTY_STIRNG);
					// TODO: judge class path top
					for (int i = 2; i < dirArr.length - 1; i++)
					{
						selected += IConstants.DIR_SEPARATOR + dirArr[i];
					}
					selected += IConstants.DIR_SEPARATOR + "test";

				} else if (structuredSelection.getFirstElement() instanceof File)
				{
					// navigator

					// File javaFile = (File)resourceObject;
					// javaFileIStream = javaFile.getContents();
					// BufferedReader br = new BufferedReader(new
					// InputStreamReader(javaFileIStream));
					// String line = "";
					// while ( (line = br.readLine()) != null ) {
					// // package取得
					// }
					// br.close();

					String resourceStr = selection.toString().split("src")[1].replace(
							"]", IConstants.EMPTY_STIRNG);
					String[] dirSepArr = resourceStr.split(IConstants.DIR_SEPARATOR);
					testTargetClassname = dirSepArr[dirSepArr.length - 1].replace(
							IConstants.JAVA_EXP, IConstants.EMPTY_STIRNG);
					testCaseFilename = dirSepArr[dirSepArr.length - 1].replace(
							IConstants.JAVA_EXP, "Test" + IConstants.JAVA_EXP);
					testDiconFilename = dirSepArr[dirSepArr.length - 1].replace(
							IConstants.JAVA_EXP, "Test" + IConstants.DICON_EXP);
					testCaseClassname = testCaseFilename.replace(IConstants.JAVA_EXP,
							IConstants.EMPTY_STIRNG);
					dirSepArr[dirSepArr.length - 1] = "test";
					selected = IConstants.EMPTY_STIRNG;
					for (String each : dirSepArr)
					{
						if (each != null && !each.equals(IConstants.EMPTY_STIRNG))
							selected += IConstants.DIR_SEPARATOR + each;
					}

				} else if (structuredSelection.getFirstElement() instanceof CompilationUnit)
				{
					// package explorer

					// CompilationUnit unit = (CompilationUnit)resourceObject;
					// String source = unit.getSource();

					String classInfoStr = selection.toString();
					selected = classInfoStr.split("\\[in")[1].trim();
					selected = selected.replaceAll("\\.", IConstants.DIR_SEPARATOR);
					testTargetClassname = classInfoStr.split(IConstants.RegExp.JAVA_EXP)[0]
							.replaceAll("(\\[|\\])", IConstants.EMPTY_STIRNG).replaceAll(
									"Working copy ", IConstants.EMPTY_STIRNG).trim();
					testCaseFilename = testTargetClassname + "Test" + IConstants.JAVA_EXP;
					testDiconFilename = testTargetClassname + "Test"
							+ IConstants.DICON_EXP;
					testCaseClassname = testTargetClassname + "Test";
					selected = IConstants.DIR_SEPARATOR + selected
							+ IConstants.DIR_SEPARATOR + "test";

				}

				// TODO
				String baseDir = System.getProperty("user.dir");
				if (baseDir == IConstants.EMPTY_STIRNG)
				{
					Shell shell = new Shell();
					MessageDialog.openWarning(shell, IConstants.Dialog.Common.TITLE,
							IConstants.Dialog.TestCase.UNSETTING_UP_TEST_PJT_ROOT_DIR
									+ IConstants.LINE_FEED + IConstants.LINE_FEED
									+ IConstants.Dialog.Common.EXECUTE_AFTER_SETTING);

				} else
				{

					baseDir = baseDir.toString().replaceAll(
							IConstants.WINDOWS_DIR_SEPARATOR, IConstants.DIR_SEPARATOR);
					String[] tmpStrArr = baseDir.split(IConstants.DIR_SEPARATOR);
					projectName = tmpStrArr[tmpStrArr.length - 1];
					String rootDir = baseDir + IConstants.DIR_SEPARATOR + "src";

					testCaseResource = "src" + selected + IConstants.DIR_SEPARATOR
							+ testCaseFilename;
					testCaseDirResource = "src" + selected;
					testCaseCreateDirpath = rootDir + selected;

					java.io.File testDir = new java.io.File(testCaseCreateDirpath);

					// どの階層から既に存在するか確認
					String[] dirArr = testCaseCreateDirpath
							.split(IConstants.DIR_SEPARATOR);
					String tmpDirPath = "";
					String tmpResourceDirPath = "";
					for (String each : dirArr)
					{
						tmpDirPath += IConstants.DIR_SEPARATOR + each;
						java.io.File tmpDir = new java.io.File(tmpDirPath);

						// baseDirに届くまでは何もしない
						if (tmpDir.getPath().length() <= baseDir.length())
						{
							continue;
						}
						tmpResourceDirPath += IConstants.DIR_SEPARATOR + each;

						if (!tmpDir.exists())
						{
							if (!tmpDir.mkdir())
							{
								System.err.println("create directory error : "
										+ tmpDir.getPath());
							}
							if (!ResourceSynchronizerUtil.accessSynchronizeServer(null,
									projectName + IConstants.DIR_SEPARATOR
											+ tmpResourceDirPath + "/.." + "=INFINITE"))
							{
								MessageDialog
										.openWarning(
												new Shell(),
												IConstants.Dialog.Common.TITLE,
												IConstants.Dialog.EncodingJBossToolsHTMLEditor.RESOURCE_SYNC_SERVER_NOT_RUNNING);
								System.err
										.println("access error : ResourceSynchronizer server");
							}
						}
					}

					// ディレクトリ作成
					if (!testDir.mkdirs())
					{
						System.err
								.println("test directory create error or already exist");
					}
					// リソース更新
					if (!ResourceSynchronizerUtil.accessSynchronizeServer(null,
							projectName + IConstants.DIR_SEPARATOR + testCaseDirResource
									+ "=ONE"))
					{
						MessageDialog
								.openWarning(
										new Shell(),
										IConstants.Dialog.Common.TITLE,
										IConstants.Dialog.EncodingJBossToolsHTMLEditor.RESOURCE_SYNC_SERVER_NOT_RUNNING);
						System.err.println("access error - ResourceSynchronizer server");
					}

					try
					{
						// 既に存在する場合上書き確認
						java.io.File outputFile = new java.io.File(testCaseCreateDirpath
								+ IConstants.DIR_SEPARATOR + testCaseFilename);
						if (!outputFile.exists()
								|| MessageDialog.openConfirm(new Shell(),
										IConstants.Dialog.Common.TITLE, testCaseFilename
												+ IConstants.Dialog.Common.ALREADY_EXIST))
						{

							// test class generator
							testFileOSWriter = new OutputStreamWriter(
									new FileOutputStream(testCaseCreateDirpath
											+ IConstants.DIR_SEPARATOR + testCaseFilename));

							String testPackageString = selected.substring(1).replace(
									IConstants.DIR_SEPARATOR, ".");
							String targetClassPackageString = testPackageString.replace(
									"test", "");

							testFileOSWriter.write("package " + testPackageString + ";"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter
									.write("import static org.junit.Assert.assertNotNull;"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter
									.write("import junit.framework.JUnit4TestAdapter;"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter.write("import org.junit.runner.RunWith;"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter
									.write("import org.seasar.framework.unit.Seasar2;"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter.write("import " + targetClassPackageString
									+ testTargetClassname + ";"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter
									.write("import test.framework.AbstractBaseTest;"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("@RunWith(Seasar2.class)"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("public class " + testCaseClassname
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("	extends AbstractBaseTest {"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("	private " + testTargetClassname
									+ " testTarget;" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter
									.write("	public static junit.framework.Test suite() {"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter.write("		return new JUnit4TestAdapter("
									+ testCaseClassname + ".class);"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("	}" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("	// Excelシートの雛形を作成"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("	// src/test/resourcesの配下に出力されます"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter
									.write("	public void readyForTest() throws Exception {"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter
									.write("		assertNotNull(\"test target is null!!\",testTarget);"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testFileOSWriter.write("		this.readyForTestMain();"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("		return;"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testFileOSWriter.write("	}" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testFileOSWriter.write("}" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);

							// test dicon generator
							testDiconOSWriter = new OutputStreamWriter(
									new FileOutputStream(testCaseCreateDirpath
											+ IConstants.DIR_SEPARATOR
											+ testDiconFilename));
							testDiconOSWriter
									.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testDiconOSWriter
									.write("<!DOCTYPE components PUBLIC \"-//SEASAR//DTD S2Container 2.4//EN\""
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testDiconOSWriter
									.write("  \"http://www.seasar.org/dtd/components24.dtd\">"
											+ IConstants.CARRIAGE_RETURN
											+ IConstants.LINE_FEED);
							testDiconOSWriter.write("<components>"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("	<include path=\"common.dicon\"/>"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testDiconOSWriter.write("	<!-- page -->"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("	<component name=\"testTarget\""
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("		class=\""
									+ targetClassPackageString + testTargetClassname
									+ "\"" + IConstants.CARRIAGE_RETURN
									+ IConstants.LINE_FEED);
							testDiconOSWriter.write("		instance=\"prototype\">"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("	</component>"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
							testDiconOSWriter.write("</components>"
									+ IConstants.CARRIAGE_RETURN + IConstants.LINE_FEED);
						}

					} catch (FileNotFoundException fnfe)
					{
						fnfe.printStackTrace();
					}
				}
			}

			// } catch (CoreException cre) {
			// cre.printStackTrace();
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
				if (testDiconOSWriter != null)
					testDiconOSWriter.close();
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
			MessageDialog
					.openWarning(
							new Shell(),
							IConstants.Dialog.Common.TITLE,
							IConstants.Dialog.EncodingJBossToolsHTMLEditor.RESOURCE_SYNC_SERVER_NOT_RUNNING);
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
