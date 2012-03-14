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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.junithelper.command.ForceJUnitVersion4Command;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.util.ThreadUtil;
import org.junithelper.plugin.constant.Dialog;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.io.PropertiesLoader;
import org.junithelper.plugin.util.ResourceRefreshUtil;

public class Force4TestCaseAction extends AbstractAction implements IActionDelegate, IEditorActionDelegate {

    ISelection selection = null;

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    }

    public void run(IAction action) {

        store = getIPreferenceStore();
        Configuration config = getConfiguration(store, selection);
        PropertiesLoader props = getPropertiesLoader(store.getString(Preference.lang));

        StructuredSelection structuredSelection = null;

        try {

            if (selection instanceof StructuredSelection) {
                // viewer
                structuredSelection = (StructuredSelection) selection;
            }
            if (isNotSelected(structuredSelection)) {
                // required selecttion
                openWarningForRequired(props);
                return;
            } else if (isSelectedSeveral(structuredSelection)) {
                // select only one
                openWarningForSelectOneOnly(props);
                return;
            }

            // ----------------------------------------
            // get project path, resource path
            String resourcePathForTargetClassFile = getResourcePathForTargetClassFile(structuredSelection)
                    .replaceFirst(config.directoryPathOfTestSourceCode, config.directoryPathOfProductSourceCode);
            String resourcePathForTestCaseFile = resourcePathForTargetClassFile.replaceFirst(
                    config.directoryPathOfProductSourceCode, config.directoryPathOfTestSourceCode).replaceFirst(
                    "[^(Test)]\\.java$", StringValue.JUnit.TestClassNameSuffix + StringValue.FileExtension.JavaFile);
            String projectName = getProjectName(structuredSelection);
            String projectRootAbsolutePath = getWorkspaceRootAbsolutePath(getIWorkspaceRoot())
                    + StringValue.DirectorySeparator.General + projectName + StringValue.DirectorySeparator.General;

            // ----------------------------------------
            // check selection
            if (!resourcePathForTestCaseFile.matches(".*" + RegExp.FileExtension.JavaFile)) {
                openWarningForSelectJavaFile(props);
                return;
            }

            // ----------------------------------------
            // confirm to execute
            String targetClassName = getClassNameFromResourcePathForTargetClassFile(resourcePathForTargetClassFile);
            String testCaseFilename = getTestClassNameFromClassName(targetClassName);
            String msg = props.get(Dialog.Common.confirmToChangeToJUnitVersion4) + " (" + testCaseFilename + ")";
            if (testCaseFilename == null || !openConfirm(props, msg)) {
                return;
            }

            // ----------------------------------------
            // force version 4.x
            System.setProperty("junithelper.skipConfirming", "true");
            ForceJUnitVersion4Command.main(new String[] { projectRootAbsolutePath + resourcePathForTargetClassFile });

            // ----------------------------------------
            // open test case
            ThreadUtil.sleep(200);
            int retryCount = 0;
            while (true) {
                try {
                    // ----------------------------------------
                    // resource refresh
                    if (!ResourceRefreshUtil.refreshLocal(null, projectName + StringValue.DirectorySeparator.General
                            + resourcePathForTestCaseFile + "/..")) {
                        openWarningForResourceRefreshError(props);
                        System.err.println("Resource refresh error!");
                        return;
                    }

                    // ----------------------------------------
                    // open test case file
                    retryCount = 0;
                    ThreadUtil.sleep(1500);
                } catch (Exception e) {
                    retryCount++;
                    if (retryCount > 10) {
                        break;
                    }
                    e.printStackTrace();
                    ThreadUtil.sleep(1500);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
