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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.impl.DefaultTestCaseGenerator;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.ThreadUtil;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Dialog;
import org.junithelper.plugin.exception.InvalidPreferenceException;
import org.junithelper.plugin.io.PropertiesLoader;
import org.junithelper.plugin.page.PreferenceLoader;
import org.junithelper.plugin.util.EclipseIFileUtil;
import org.junithelper.plugin.util.ResourcePathUtil;
import org.junithelper.plugin.util.ResourceRefreshUtil;

public class CreateNewTestCaseAction extends Action implements IActionDelegate,
		IEditorActionDelegate {

	private ISelection selection = null;

	public IPreferenceStore store = null;

	public void run(IAction action, ISelection selection) {
		this.selection = selection;
		this.run(action);
	}

	public void run(IAction action) {
		if (store == null) {
			store = Activator.getDefault().getPreferenceStore();
		}
		Configulation config = new PreferenceLoader(store).getConfig();
		PropertiesLoader props = new PropertiesLoader(config.language);

		InputStream inputStream = null;
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = null;
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
				MessageDialog.openWarning(shell,
						props.get(Dialog.Common.title),
						props.get(Dialog.Common.required));
				refreshFlag = false;
			} else if (structuredSelection != null
					&& structuredSelection.size() > 1) {
				// select one only
				Shell shell = new Shell();
				MessageDialog.openWarning(shell,
						props.get(Dialog.Common.title),
						props.get(Dialog.Common.selectOneOnly));
				refreshFlag = false;
			} else {
				// path started from project root
				String pathFromProjectRoot = ResourcePathUtil
						.getPathStartsFromProjectRoot(structuredSelection);
				// path started from project root
				// ex. /{projectName}/src/main/java/hoge/foo/var/TestTarget.java
				String[] dirArrFromProjectRoot = pathFromProjectRoot
						.split(StringValue.DirectorySeparator.General);
				// test case file create filesystem path
				// TODO
				String selected = StringValue.Empty;
				int len = dirArrFromProjectRoot.length;
				for (int i = 2; i < len; i++) {
					selected += dirArrFromProjectRoot[i]
							+ StringValue.DirectorySeparator.General;
				}
				selected = selected
						.replaceAll(RegExp.CRLF, StringValue.Empty)
						.replaceFirst("\\.java.+", ".java")
						.replace(
								StringValue.JUnit.TestClassNameSuffix
										+ StringValue.FileExtension.JavaFile,
								StringValue.FileExtension.JavaFile);
				// current project name
				projectName = dirArrFromProjectRoot[1];
				// last element is test class file name
				String[] selectedSplittedArray = selected.split("/");
				String testTargetClassFilename = selectedSplittedArray[selectedSplittedArray.length - 1]
						.split("\\.")[0];
				testTargetClassname = testTargetClassFilename.replace(
						StringValue.FileExtension.JavaFile, StringValue.Empty);
				// test class name to create
				testCaseClassname = testTargetClassname
						+ StringValue.JUnit.TestClassNameSuffix;
				// test case name to open
				testCaseFilename = testCaseClassname
						+ StringValue.FileExtension.JavaFile;

				// get workspace path on os file system
				String projectRootPath = workspaceRoot.getLocation()
						+ StringValue.DirectorySeparator.General + projectName
						+ StringValue.DirectorySeparator.General;

				testCaseResource = selected.replace(
						config.directoryPathOfProductSourceCode,
						config.directoryPathOfTestSourceCode).replace(
						StringValue.FileExtension.JavaFile,
						StringValue.JUnit.TestClassNameSuffix
								+ StringValue.FileExtension.JavaFile);
				String[] selectedDirArr = selected
						.split(StringValue.DirectorySeparator.General);
				testCaseDirResource = "";
				int selectedDirArrLength = selectedDirArr.length;
				for (int i = 0; i < selectedDirArrLength - 1; i++)
					testCaseDirResource += selectedDirArr[i]
							+ StringValue.DirectorySeparator.General;
				testCaseDirResource = testCaseDirResource.replace(
						config.directoryPathOfProductSourceCode,
						config.directoryPathOfTestSourceCode);
				testCaseCreateDirpath = projectRootPath + testCaseDirResource;
				File testDir = new File(testCaseCreateDirpath);
				// check directory exist
				String[] dirArr = testCaseCreateDirpath
						.split(StringValue.DirectorySeparator.General);
				String tmpDirPath = StringValue.Empty;
				String tmpResourceDirPath = StringValue.Empty;
				for (String each : dirArr) {
					tmpDirPath += StringValue.DirectorySeparator.General + each;
					File tmpDir = new File(tmpDirPath);
					// skip until project root dir
					if (tmpDir.getPath().length() <= projectRootPath.length()) {
						continue;
					}
					tmpResourceDirPath += StringValue.DirectorySeparator.General
							+ each;
					if (!tmpDir.exists()) {
						if (!tmpDir.mkdir()) {
							System.err.println("create directory error : "
									+ tmpDir.getPath());
						}
						if (!ResourceRefreshUtil.refreshLocal(null, projectName
								+ StringValue.DirectorySeparator.General
								+ tmpResourceDirPath + "/..")) {
							String msg = props
									.get(Dialog.Common.resourceRefreshError);
							MessageDialog.openWarning(new Shell(),
									props.get(Dialog.Common.title), msg);
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
						+ StringValue.DirectorySeparator.General
						+ testCaseDirResource)) {
					MessageDialog.openWarning(new Shell(),
							props.get(Dialog.Common.title),
							props.get(Dialog.Common.resourceRefreshError));
					System.err.println("Resource refresh error!");
				}
				try {
					// confirm if already exist
					File outputFile = new File(testCaseCreateDirpath
							+ StringValue.DirectorySeparator.General
							+ testCaseFilename);
					String msg = props.get(Dialog.Common.alreadyExist) + " ("
							+ testCaseFilename + ")" + StringValue.LineFeed
							+ props.get(Dialog.Common.confirmToProceed);
					if (!outputFile.exists()
							|| MessageDialog.openConfirm(new Shell(),
									props.get(Dialog.Common.title), msg)) {
						// get public methods
						String targetClass = "/" + projectName + "/" + selected;
						IResource targetClassResource = workspaceRoot
								.findMember(targetClass);
						IFile file = (IFile) targetClassResource;
						outputStream = new FileOutputStream(
								testCaseCreateDirpath
										+ StringValue.DirectorySeparator.General
										+ testCaseFilename);
						TestCaseGenerator generator = new DefaultTestCaseGenerator(
								config);
						generator.initialize(new ClassMetaExtractor(config)
								.extract(IOUtil.readAsString(EclipseIFileUtil
										.getInputStreamFrom(file))));
						writer = new OutputStreamWriter(outputStream,
								EclipseIFileUtil.getDetectedEncodingFrom(file));
						writer.write(generator.getNewTestCaseSourceCode());
					}
				} catch (InvalidPreferenceException ipe) {
					ipe.printStackTrace();
					MessageDialog.openWarning(new Shell(),
							props.get(Dialog.Common.title),
							props.get(Dialog.Common.invalidPreference));
					return;
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} finally {
					IOUtil.close(writer);
					IOUtil.close(outputStream);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(inputStream);
			IOUtil.close(writer);
		}

		// resource refresh
		if (refreshFlag
				&& !ResourceRefreshUtil.refreshLocal(null, projectName
						+ StringValue.DirectorySeparator.General
						+ testCaseDirResource + "/..")) {
			MessageDialog.openWarning(new Shell(),
					props.get(Dialog.Common.title),
					props.get(Dialog.Common.resourceRefreshError));
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

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

}
