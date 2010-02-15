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
import org.junithelper.plugin.bean.ClassInfo;
import org.junithelper.plugin.bean.MethodInfo;
import org.junithelper.plugin.constant.Dialog;
import org.junithelper.plugin.constant.RuntimeLibrary;
import org.junithelper.plugin.constant.STR;
import org.junithelper.plugin.page.PreferenceLoader;
import org.junithelper.plugin.util.FileResourceUtil;
import org.junithelper.plugin.util.ResourcePathUtil;
import org.junithelper.plugin.util.ResourceRefreshUtil;
import org.junithelper.plugin.util.TestCaseGenerateUtil;
import org.junithelper.plugin.util.ThreadUtil;

/**
 * CreateNewTestCaseAction<br>
 * <br>
 * Create new test case file.<br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class CreateNewTestCaseAction extends Action implements IActionDelegate,
		IEditorActionDelegate {

	private ISelection selection = null;

	/**
	 * Run method to invoked.
	 * 
	 * @param action
	 * @param selection
	 */
	public void run(IAction action, ISelection selection) {
		this.selection = selection;
		this.run(action);
	}

	/**
	 * Run method to invoked.
	 * 
	 * @param action
	 */
	public void run(IAction action) {

		PreferenceLoader pref = new PreferenceLoader();

		InputStream javaFileIStream = null;
		OutputStreamWriter testFileOSWriter = null;
		FileOutputStream fos = null;
		boolean refreshFlag = true;
		String projectName = null;
		String testCaseDirResource = null;
		String testCaseResource = null;

		try {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();

			String testTargetClassname = null;
			String testCaseFilename = null;
			String testCaseClassname = null;
			String testCaseCreateDirpath = null;

			StructuredSelection structuredSelection = null;
			if (selection instanceof StructuredSelection)
				// viewer
				structuredSelection = (StructuredSelection) selection;

			if (structuredSelection != null && structuredSelection.size() == 0) {
				// required select
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, Dialog.Common.TITLE,
						Dialog.Common.REQUIRED);
				refreshFlag = false;
			} else if (structuredSelection != null
					&& structuredSelection.size() > 1) {
				// select one only
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, Dialog.Common.TITLE,
						Dialog.Common.SELECT_ONLY_ONE);
				refreshFlag = false;
			} else {

				// path started from project root
				String pathFromProjectRoot = ResourcePathUtil
						.getPathStartsFromProjectRoot(structuredSelection);

				// path started from project root
				// ex. /{projectName}/src/main/java/hoge/foo/var/TestTarget.java
				String[] dirArrFromProjectRoot = pathFromProjectRoot
						.split(STR.DIR_SEP);
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
				testTargetClassname = testTargetClassFilename.replace(
						STR.JAVA_EXP, STR.EMPTY);

				// test class name to create
				testCaseClassname = testTargetClassname
						+ STR.SUFFIX_OF_TESTCASE;
				// test case name to open
				testCaseFilename = testCaseClassname + STR.JAVA_EXP;

				// get workspace path on os file system
				String projectRootPath = workspaceRoot.getLocation()
						+ STR.DIR_SEP + projectName + STR.DIR_SEP;

				testCaseResource = selected.replace(STR.SRC_MAIN_JAVA,
						STR.SRC_TEST_JAVA).replace(STR.JAVA_EXP,
						STR.SUFFIX_OF_TESTCASE + STR.JAVA_EXP);
				String[] selectedDirArr = selected.split(STR.DIR_SEP);
				testCaseDirResource = "";
				int selectedDirArrLength = selectedDirArr.length;
				for (int i = 0; i < selectedDirArrLength - 1; i++)
					testCaseDirResource += selectedDirArr[i] + STR.DIR_SEP;
				testCaseDirResource = testCaseDirResource.replace(
						STR.SRC_MAIN_JAVA, STR.SRC_TEST_JAVA);
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;

				File testDir = new File(testCaseCreateDirpath);

				// check directory exist
				String[] dirArr = testCaseCreateDirpath.split(STR.DIR_SEP);
				String tmpDirPath = STR.EMPTY;
				String tmpResourceDirPath = STR.EMPTY;
				for (String each : dirArr) {
					tmpDirPath += STR.DIR_SEP + each;
					File tmpDir = new File(tmpDirPath);

					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length())
						continue;

					tmpResourceDirPath += STR.DIR_SEP + each;
					if (!tmpDir.exists()) {
						if (!tmpDir.mkdir())
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						if (!ResourceRefreshUtil.refreshLocal(null, projectName
								+ STR.DIR_SEP + tmpResourceDirPath + "/..")) {
							String msg = Dialog.Common.RESOURCE_REFRESH_ERROR;
							MessageDialog.openWarning(new Shell(),
									Dialog.Common.TITLE, msg);
							System.err.println("Resource refresh error!");
						}
					}
				}

				// execute create directory
				if (!testDir.mkdirs())
					System.err
							.println("test directory create error or already exist");

				// resource sync
				if (!ResourceRefreshUtil.refreshLocal(null, projectName
						+ STR.DIR_SEP + testCaseDirResource)) {
					MessageDialog.openWarning(new Shell(), Dialog.Common.TITLE,
							Dialog.Common.RESOURCE_REFRESH_ERROR);
					System.err.println("Resource refresh error!");
				}

				try {
					// confirm if already exist
					File outputFile = new File(testCaseCreateDirpath
							+ STR.DIR_SEP + testCaseFilename);
					String msg = Dialog.Common.ALREADY_EXIST + " ("
							+ testCaseFilename + ")" + STR.LINE_FEED
							+ Dialog.Common.CONFIRM_PROCEED;
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									Dialog.Common.TITLE, msg)) {
						// get public methods
						String targetClass = "/" + projectName + "/" + selected;
						IResource targetClassResource = workspaceRoot
								.findMember(targetClass);
						IFile file = (IFile) targetClassResource;
						ClassInfo testClassInfo = TestCaseGenerateUtil
								.getTestClassInfoFromTargetClass(file);
						List<MethodInfo> testMethods = testClassInfo.methods;
						// generate test class
						String writeEncoding = FileResourceUtil
								.detectEncoding(file);
						fos = new FileOutputStream(testCaseCreateDirpath
								+ STR.DIR_SEP + testCaseFilename);
						testFileOSWriter = new OutputStreamWriter(fos,
								writeEncoding);

						StringBuilder sb = new StringBuilder();

						String CRLF = STR.CARRIAGE_RETURN + STR.LINE_FEED;

						// get package
						String testPackageString = STR.EMPTY;
						String[] tmpDirArr = selected.split(STR.DIR_SEP);
						StringBuilder dirSb = new StringBuilder();
						int packageArrLen = tmpDirArr.length - 2;
						int mainJavaLen = STR.SRC_MAIN_JAVA.split("/").length;
						for (int i = mainJavaLen; i < packageArrLen; i++) {
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

						// get class to extend
						String testCase = pref.isUsingJUnitHelperRuntime ? RuntimeLibrary.TEST_CASE
								: pref.classToExtend;
						String[] tmpTestCaseArr = testCase.split("\\.");
						String testCaseName = tmpTestCaseArr[tmpTestCaseArr.length - 1];
						sb.append("import ");
						sb.append(testCase);
						sb.append(";");
						sb.append(CRLF);

						if (pref.isTestMethodGenEnabled
								&& pref.isTestMethodGenNotBlankEnabled) {
							List<String> importedPackageList = testClassInfo.importList;
							for (String importedPackage : importedPackageList) {
								sb.append("import ");
								sb.append(importedPackage);
								sb.append(";");
								sb.append(CRLF);
							}
						}
						sb.append(CRLF);

						sb.append("public class ");
						sb.append(testCaseClassname);
						if (pref.isJUnitVersion3) {
							sb.append(" extends ");
							sb.append(testCaseName);
						}
						sb.append(" {");
						sb.append(CRLF);

						sb.append(CRLF);

						if (pref.isTestMethodGenEnabled) {
							for (MethodInfo testMethod : testMethods) {
								if (testMethod.testMethodName == null
										|| testMethod.testMethodName
												.equals(STR.EMPTY))
									continue;

								if (pref.isJUnitVersion4) {
									sb.append("\t@Test");
									sb.append(CRLF);
								}
								sb.append("\tpublic void ");
								sb.append(testMethod.testMethodName);
								sb.append("() throws Exception {");
								sb.append(CRLF);

								sb.append("\t\t//");
								sb.append(STR.AUTO_GEN_MSG_TODO);
								sb.append(CRLF);

								if (pref.isTestMethodGenNotBlankEnabled) {
									String notBlankSourceCode = TestCaseGenerateUtil
											.getNotBlankTestMethodSource(
													testMethod, testClassInfo,
													testTargetClassname);
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

				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} finally {
					FileResourceUtil.close(testFileOSWriter);
					FileResourceUtil.close(fos);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileResourceUtil.close(javaFileIStream);
			FileResourceUtil.close(testFileOSWriter);
		}

		// resource refresh
		if (refreshFlag
				&& !ResourceRefreshUtil.refreshLocal(null, projectName
						+ STR.DIR_SEP + testCaseDirResource + "/..")) {
			MessageDialog.openWarning(new Shell(), Dialog.Common.TITLE,
					Dialog.Common.RESOURCE_REFRESH_ERROR);
			System.err.println("Resource refresh error!");

		} else {

			// open test case
			int retryCount = 0;
			IEditorPart editorPart = null;
			ThreadUtil.sleep(1500);
			while (true) {
				try {
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IWorkspaceRoot root = workspace.getRoot();
					IProject project = root.getProject(projectName);
					IFile testCaseFile = project.getFile(testCaseResource);
					String editorId = IDE.getEditorDescriptor(
							testCaseFile.getName()).getId();
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					editorPart = IDE.openEditor(page, testCaseFile, editorId);
					if (editorPart == null)
						throw new NullPointerException();
					break;
				} catch (Exception e) {
					retryCount++;
					if (retryCount > 3)
						break;
					ThreadUtil.sleep(1500);
				}
			}
			editorPart.setFocus();
		}
	}

	/**
	 * Method to catch the event selection has been changed.
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Required in IEditorActionDelegate(ex. Java editor)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

}
