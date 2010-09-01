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
import org.junithelper.plugin.constant.StrConst;
import org.junithelper.plugin.exception.InvalidPreferenceException;
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
			if (selection instanceof StructuredSelection) {
				// viewer
				structuredSelection = (StructuredSelection) selection;
			}
			if (structuredSelection != null && structuredSelection.size() == 0) {
				// required select
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, Dialog.Common.title,
						Dialog.Common.required);
				refreshFlag = false;
			} else if (structuredSelection != null
					&& structuredSelection.size() > 1) {
				// select one only
				Shell shell = new Shell();
				MessageDialog.openWarning(shell, Dialog.Common.title,
						Dialog.Common.selectOneOnly);
				refreshFlag = false;
			} else {
				// path started from project root
				String pathFromProjectRoot = ResourcePathUtil
						.getPathStartsFromProjectRoot(structuredSelection);
				// path started from project root
				// ex. /{projectName}/src/main/java/hoge/foo/var/TestTarget.java
				String[] dirArrFromProjectRoot = pathFromProjectRoot
						.split(StrConst.dirSep);
				// test case file create filesystem path
				String selected = StrConst.empty;
				int len = dirArrFromProjectRoot.length;
				for (int i = 2; i < len - 1; i++)
					selected += dirArrFromProjectRoot[i] + StrConst.dirSep;
				selected += dirArrFromProjectRoot[len - 1];
				// current project name
				projectName = dirArrFromProjectRoot[1];
				// last element is test class file name
				String testTargetClassFilename = dirArrFromProjectRoot[dirArrFromProjectRoot.length - 1];
				testTargetClassname = testTargetClassFilename.replace(
						StrConst.javaFileExp, StrConst.empty);
				// test class name to create
				testCaseClassname = testTargetClassname
						+ StrConst.suffixOfTestcase;
				// test case name to open
				testCaseFilename = testCaseClassname + StrConst.javaFileExp;

				// get workspace path on os file system
				String projectRootPath = workspaceRoot.getLocation()
						+ StrConst.dirSep + projectName + StrConst.dirSep;

				testCaseResource = selected.replace(pref.commonSrcMainJavaDir,
						pref.commonTestMainJavaDir).replace(
						StrConst.javaFileExp,
						StrConst.suffixOfTestcase + StrConst.javaFileExp);
				String[] selectedDirArr = selected.split(StrConst.dirSep);
				testCaseDirResource = "";
				int selectedDirArrLength = selectedDirArr.length;
				for (int i = 0; i < selectedDirArrLength - 1; i++)
					testCaseDirResource += selectedDirArr[i] + StrConst.dirSep;
				testCaseDirResource = testCaseDirResource.replace(
						pref.commonSrcMainJavaDir, pref.commonTestMainJavaDir);
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;
				File testDir = new File(testCaseCreateDirpath);
				// check directory exist
				String[] dirArr = testCaseCreateDirpath.split(StrConst.dirSep);
				String tmpDirPath = StrConst.empty;
				String tmpResourceDirPath = StrConst.empty;
				for (String each : dirArr) {
					tmpDirPath += StrConst.dirSep + each;
					File tmpDir = new File(tmpDirPath);
					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length()) {
						continue;
					}
					tmpResourceDirPath += StrConst.dirSep + each;
					if (!tmpDir.exists()) {
						if (!tmpDir.mkdir()) {
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						}
						if (!ResourceRefreshUtil.refreshLocal(null, projectName
								+ StrConst.dirSep + tmpResourceDirPath + "/..")) {
							String msg = Dialog.Common.resourceRefreshError;
							MessageDialog.openWarning(new Shell(),
									Dialog.Common.title, msg);
							System.err.println("Resource refresh error!");
						}
					}
				}
				// execute create directory
				if (!testDir.mkdirs()) {
					System.err.println("test directory already exist");
				}
				// resource sync
				if (!ResourceRefreshUtil.refreshLocal(null, projectName
						+ StrConst.dirSep + testCaseDirResource)) {
					MessageDialog.openWarning(new Shell(), Dialog.Common.title,
							Dialog.Common.resourceRefreshError);
					System.err.println("Resource refresh error!");
				}
				try {
					// confirm if already exist
					File outputFile = new File(testCaseCreateDirpath
							+ StrConst.dirSep + testCaseFilename);
					String msg = Dialog.Common.alreadyExist + " ("
							+ testCaseFilename + ")" + StrConst.lineFeed
							+ Dialog.Common.confirmToProceed;
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									Dialog.Common.title, msg)) {
						// get public methods
						String targetClass = "/" + projectName + "/" + selected;
						IResource targetClassResource = workspaceRoot
								.findMember(targetClass);
						IFile file = (IFile) targetClassResource;
						ClassInfo testClassInfo = TestCaseGenerateUtil
								.getTestClassInfoFromTargetClass(
										testTargetClassname, file);
						List<MethodInfo> testMethods = testClassInfo.methods;
						// generate test class
						String writeEncoding = FileResourceUtil
								.detectEncoding(file);
						fos = new FileOutputStream(testCaseCreateDirpath
								+ StrConst.dirSep + testCaseFilename);
						testFileOSWriter = new OutputStreamWriter(fos,
								writeEncoding);
						StringBuilder sb = new StringBuilder();
						String CRLF = StrConst.carriageReturn
								+ StrConst.lineFeed;
						// get package
						String testPackageString = StrConst.empty;
						String[] tmpDirArr = selected.split(StrConst.dirSep);
						StringBuilder dirSb = new StringBuilder();
						int packageArrLen = tmpDirArr.length - 2;
						int mainJavaLen = pref.commonSrcMainJavaDir.split("/").length;
						// if not default package
						if (mainJavaLen != tmpDirArr.length - 1) {
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
						}
						// get class to extend
						String testCase = pref.isUsingJUnitHelperRuntime ? RuntimeLibrary.testcase
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
								importedPackage = importedPackage.trim();
								if (importedPackage != null
										&& importedPackage.length() != 0) {
									sb.append("import ");
									sb.append(importedPackage);
									sb.append(";");
									sb.append(CRLF);
								}
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
												.equals(StrConst.empty)) {
									continue;
								}
								if (pref.isTestMethodGenEnabledSupportJMockit) {
									sb.append(TestCaseGenerateUtil
											.getRequiredInstanceFieldsForJMockitTestMethod(
													testMethod, testClassInfo,
													testTargetClassname));
								}
								if (pref.isJUnitVersion4) {
									sb.append("\t@Test");
									sb.append(CRLF);
								}
								sb.append("\tpublic void ");
								sb.append(testMethod.testMethodName);
								sb.append("() throws Exception {");
								sb.append(CRLF);
								sb.append("\t\t//");
								sb.append(StrConst.autoGenTodoMessage);
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
				} catch (InvalidPreferenceException ipe) {
					MessageDialog.openWarning(new Shell(), Dialog.Common.title,
							Dialog.Common.invalidPreference);
					return;
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
						+ StrConst.dirSep + testCaseDirResource + "/..")) {
			MessageDialog.openWarning(new Shell(), Dialog.Common.title,
					Dialog.Common.resourceRefreshError);
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
