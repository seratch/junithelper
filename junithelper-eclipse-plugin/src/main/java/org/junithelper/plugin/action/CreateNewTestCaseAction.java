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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.generator.LineBreakProvider;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.TestCaseGeneratorFactory;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.ThreadUtil;
import org.junithelper.core.util.UniversalDetectorUtil;
import org.junithelper.plugin.constant.Dialog;
import org.junithelper.plugin.exception.InvalidPreferenceException;
import org.junithelper.plugin.io.PropertiesLoader;
import org.junithelper.plugin.util.EclipseIFileUtil;
import org.junithelper.plugin.util.ResourceRefreshUtil;

public class CreateNewTestCaseAction extends AbstractAction implements IActionDelegate, IEditorActionDelegate {

    ISelection selection;

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    }

    public void run(IAction action, ISelection selection) {
        this.selection = selection;
        this.run(action);
    }

    public void run(IAction action) {

        store = getIPreferenceStore();
        Configuration config = getConfiguration(store, selection);
        PropertiesLoader props = getPropertiesLoader(config.language);

        StructuredSelection structuredSelection = null;

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        OutputStreamWriter writer = null;

        String projectName = null;
        String testCaseFileName = null;
        String testCaseCreateFilePath = null;
        String resourcePathForTargetClassFile = null;
        String resourcePathForTestClassFile = null;

        try {

            // -------------------------
            // get selection
            if (selection instanceof StructuredSelection) {
                // viewer
                structuredSelection = (StructuredSelection) selection;
            }
            if (isNotSelected(structuredSelection)) {
                openWarningForRequired(props);
                return;
            } else if (isSelectedSeveral(structuredSelection)) {
                openWarningForSelectOneOnly(props);
                return;
            }

            // -------------------------
            // get project path, resource path
            projectName = getProjectName(structuredSelection);
            resourcePathForTargetClassFile = getResourcePathForTargetClassFile(structuredSelection);
            String projectRootAbsolutePath = getWorkspaceRootAbsolutePath(getIWorkspaceRoot())
                    + StringValue.DirectorySeparator.General + projectName + StringValue.DirectorySeparator.General;

            resourcePathForTestClassFile = resourcePathForTargetClassFile.replaceFirst(
                    config.directoryPathOfProductSourceCode, config.directoryPathOfTestSourceCode).replace(
                    StringValue.FileExtension.JavaFile,
                    StringValue.JUnit.TestClassNameSuffix + StringValue.FileExtension.JavaFile);
            testCaseCreateFilePath = projectRootAbsolutePath
                    + getResourcePathForTargetClassFile(structuredSelection).replace(
                            config.directoryPathOfProductSourceCode, config.directoryPathOfTestSourceCode).replace(
                            StringValue.FileExtension.JavaFile,
                            StringValue.JUnit.TestClassNameSuffix + StringValue.FileExtension.JavaFile);
            testCaseFileName = (getClassNameFromResourcePathForTargetClassFile(resourcePathForTargetClassFile) + StringValue.FileExtension.JavaFile)
                    .replace(StringValue.FileExtension.JavaFile, StringValue.JUnit.TestClassNameSuffix
                            + StringValue.FileExtension.JavaFile);

            // -------------------------
            // mkdir -p /abc/def/
            String testCaseCreateDirPath = testCaseCreateFilePath.replaceFirst("/[^/\\.]+\\.java$", "/");
            File testDir = new File(testCaseCreateDirPath);
            String[] dirArr = testCaseCreateDirPath.split(StringValue.DirectorySeparator.General);
            String tmpDirPath = StringValue.Empty;
            String tmpResourceDirPath = StringValue.Empty;
            for (String each : dirArr) {
                tmpDirPath += StringValue.DirectorySeparator.General + each;
                File tmpDir = new File(tmpDirPath);
                // skip until project root dir
                if (tmpDir.getPath().length() <= projectRootAbsolutePath.length()) {
                    continue;
                }
                tmpResourceDirPath += StringValue.DirectorySeparator.General + each;
                if (!tmpDir.exists()) {
                    if (!tmpDir.mkdir()) {
                        System.err.println("create directory error : " + tmpDir.getPath());
                    }
                    String parentPathOfCreatedDir = projectName + StringValue.DirectorySeparator.General
                            + tmpResourceDirPath + "/..";
                    if (!ResourceRefreshUtil.refreshLocal(null, parentPathOfCreatedDir)) {
                        System.err.println("Resource refresh error!" + parentPathOfCreatedDir);
                    }
                }
            }
            if (!testDir.mkdirs()) {
                System.err.println("test directory already exist");
            }

            // -------------------------
            // resource sync
            String pathOfTestCaseDir = projectName + StringValue.DirectorySeparator.General
                    + resourcePathForTestClassFile + "/..";
            if (!ResourceRefreshUtil.refreshLocal(null, pathOfTestCaseDir)) {
                openWarningForResourceRefreshError(props);
                return;
            }

            try {
                File outputIOFile = new File(testCaseCreateDirPath + StringValue.DirectorySeparator.General
                        + testCaseFileName);
                // ---------------
                // confirm if test case file already exists
                String msg = props.get(Dialog.Common.alreadyExist) + " (" + testCaseFileName + ")"
                        + StringValue.LineFeed + props.get(Dialog.Common.confirmToProceed);
                if (outputIOFile.exists() && !openConfirm(props, msg)) {
                    return;
                }
                // ---------------
                // get target class file
                IResource targetClassResource = getIWorkspaceRoot().findMember(
                        "/" + projectName + "/" + resourcePathForTargetClassFile);
                IFile targetClassFile = (IFile) targetClassResource;

                // -------------------------
                // overwrite config
                // http://code.google.com/p/junithelper/issues/detail?id=72
                String absolutePath = projectRootAbsolutePath + StringValue.DirectorySeparator.General;
                config.directoryPathOfProductSourceCode = absolutePath + config.directoryPathOfProductSourceCode;
                config.directoryPathOfTestSourceCode = absolutePath + config.directoryPathOfTestSourceCode;

                // ---------------
                // generate test case source code string
                String encoding = UniversalDetectorUtil.getDetectedEncoding(EclipseIFileUtil
                        .getInputStreamFrom(targetClassFile));
                InputStream targetInputStream = EclipseIFileUtil.getInputStreamFrom(targetClassFile);
                String sourceCodeString = IOUtil.readAsString(targetInputStream, encoding);
                LineBreakProvider lineBreakProvider = new LineBreakProvider(config, null);
                TestCaseGenerator generator = TestCaseGeneratorFactory.create(config, lineBreakProvider);
                generator.initialize(new ClassMetaExtractor(config).extract(sourceCodeString));

                // ---------------
                // write test case
                outputStream = new FileOutputStream(testCaseCreateDirPath + StringValue.DirectorySeparator.General
                        + testCaseFileName);
                writer = new OutputStreamWriter(outputStream, getDetectedEncodingFrom(targetClassFile,
                        config.outputFileEncoding));
                writer.write(generator.getNewTestCaseSourceCode());

            } catch (InvalidPreferenceException ipe) {
                ipe.printStackTrace();
                openWarning(props, props.get(Dialog.Common.invalidPreference));
                return;
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } finally {
                IOUtil.close(writer);
                IOUtil.close(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
            IOUtil.close(writer);
        }

        // ---------------
        // resource refresh
        if (!ResourceRefreshUtil.refreshLocal(null, projectName + StringValue.DirectorySeparator.General
                + resourcePathForTestClassFile + "/..")) {
            openWarningForResourceRefreshError(props);
            System.err.println("Resource refresh error!");
            return;
        }
        // ---------------
        // open test case
        int retryCount = 0;
        IEditorPart editorPart = null;
        ThreadUtil.sleep(1500);
        while (true) {
            try {
                IProject project = getIProject(projectName);
                IFile testCaseFile = getIFile(project, resourcePathForTestClassFile);
                IWorkbenchPage page = getIWorkbenchPage();
                editorPart = getIEditorPart(page, testCaseFile);
                if (editorPart == null) {
                    throw new NullPointerException();
                }
                break;
            } catch (Exception e) {
                retryCount++;
                if (retryCount > 3) {
                    break;
                }
                ThreadUtil.sleep(1500);
            }
        }
        editorPart.setFocus();
    }

}
