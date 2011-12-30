package org.junithelper.plugin.action;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.ConfigurationLoader;
import org.junithelper.core.config.extension.ExtConfiguration;
import org.junithelper.core.config.extension.ExtConfigurationLoader;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Dialog;
import org.junithelper.plugin.io.PropertiesLoader;
import org.junithelper.plugin.page.PreferenceLoader;
import org.junithelper.plugin.util.EclipseIFileUtil;
import org.junithelper.plugin.util.ResourcePathUtil;

public abstract class AbstractAction extends Action {

    public IPreferenceStore store = null;

    protected Configuration getConfiguration(IPreferenceStore store, ISelection selection) {

        Configuration config = null;

        // read from "junithelper-config.properties" if it exsits
        // at project root dir
        StructuredSelection structuredSelection = null;
        if (selection instanceof StructuredSelection) {
            // viewer
            structuredSelection = (StructuredSelection) selection;
        }
        if (!isNotSelected(structuredSelection) && !isSelectedSeveral(structuredSelection)) {
            String projectName = getProjectName(structuredSelection);
            String projectRootPath = getWorkspaceRootAbsolutePath(getIWorkspaceRoot())
                    + StringValue.DirectorySeparator.General + projectName + StringValue.DirectorySeparator.General;
            String configFilepath = projectRootPath + "junithelper-config.properties";
            File configProperites = new File(configFilepath);
            if (configProperites.exists()) {
                try {
                    config = new ConfigurationLoader().load(configFilepath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // load from Eclipse Preference
                config = new PreferenceLoader(store).getConfig();
            }
            String extConfigFilepath = projectRootPath + "junithelper-extension.xml";
            File extConfigXML = new File(extConfigFilepath);
            if (extConfigXML.exists()) {
                try {
                    ExtConfiguration extConfig = new ExtConfigurationLoader().load(extConfigFilepath);
                    config.isExtensionEnabled = true;
                    config.extConfiguration = extConfig;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return config;
    }

    protected PropertiesLoader getPropertiesLoader(String language) {
        return new PropertiesLoader(language);
    }

    // -------------------
    // String value
    protected String getTestClassNameFromClassName(String className) {
        return className + StringValue.JUnit.TestClassNameSuffix + StringValue.FileExtension.JavaFile;
    }

    protected String getDetectedEncodingFrom(IFile file, String defaultEncoding) {
        return EclipseIFileUtil.getDetectedEncodingFrom(file, defaultEncoding);
    }

    protected String getClassNameFromResourcePathForTargetClassFile(String resourcePathForTargetClassFile) {
        String[] splittedArray = resourcePathForTargetClassFile.split(StringValue.DirectorySeparator.General);
        return splittedArray[splittedArray.length - 1].split("\\.")[0];
    }

    protected String getProjectName(StructuredSelection structuredSelection) {
        String pathFromProjectRoot = getPathFromProjectRoot(structuredSelection);
        String[] dirArrFromProjectRoot = pathFromProjectRoot.split(StringValue.DirectorySeparator.General);
        return dirArrFromProjectRoot[1];
    }

    // -------------------
    // Path

    protected String getPathFromProjectRoot(StructuredSelection structuredSelection) {
        return ResourcePathUtil.getPathStartsFromProjectRoot(structuredSelection);
    }

    protected String getWorkspaceRootAbsolutePath(IWorkspaceRoot workspaceRoot) {
        return workspaceRoot.getLocation().toString();
    }

    protected String getResourcePathForTargetClassFile(StructuredSelection structuredSelection) {
        // path started from project root
        String pathFromProjectRoot = getPathFromProjectRoot(structuredSelection);
        // path started from project root
        // ex. /{projectName}/src/main/java/hoge/foo/var/TestTarget.java
        String[] dirArrFromProjectRoot = pathFromProjectRoot.split(StringValue.DirectorySeparator.General);
        // test case file create filesystem path
        String resourcePathForTargetClassFile = StringValue.Empty;
        int len = dirArrFromProjectRoot.length;
        for (int i = 2; i < len; i++) {
            resourcePathForTargetClassFile += dirArrFromProjectRoot[i] + StringValue.DirectorySeparator.General;
        }
        resourcePathForTargetClassFile = resourcePathForTargetClassFile.replaceAll(RegExp.CRLF, StringValue.Empty)
                .replaceFirst("\\.java.+", ".java").replace(
                        StringValue.JUnit.TestClassNameSuffix + StringValue.FileExtension.JavaFile,
                        StringValue.FileExtension.JavaFile);
        return resourcePathForTargetClassFile;
    }

    // -------------------
    // Eclipse SDK

    protected IPreferenceStore getIPreferenceStore() {
        if (store == null) {
            store = Activator.getDefault().getPreferenceStore();
        }
        return store;
    }

    protected IWorkspaceRoot getIWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    protected IWorkbenchPage getIWorkbenchPage() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }

    protected IProject getIProject(String projectName) {
        return getIWorkspaceRoot().getProject(projectName);
    }

    protected IFile getIFile(IProject project, String resourcePath) {
        return project.getFile(resourcePath);
    }

    protected IEditorPart getIEditorPart(IWorkbenchPage page, IFile file) throws Exception {
        String editorId = EclipseIFileUtil.getIEditorDescriptorFrom(file).getId();
        return IDE.openEditor(page, file, editorId);
    }

    // -------------------
    // selection

    protected boolean isNotSelected(StructuredSelection structuredSelection) {
        return structuredSelection != null && structuredSelection.size() == 0;
    }

    protected boolean isSelectedSeveral(StructuredSelection structuredSelection) {
        return structuredSelection != null && structuredSelection.size() > 1;
    }

    // -------------------
    // open dialog

    protected void openWarningForRequired(PropertiesLoader props) {
        MessageDialog.openWarning(new Shell(), props.get(Dialog.Common.title), props.get(Dialog.Common.required));
    }

    protected void openWarningForResourceRefreshError(PropertiesLoader props) {
        MessageDialog.openWarning(new Shell(), props.get(Dialog.Common.title), props
                .get(Dialog.Common.resourceRefreshError));
    }

    protected void openWarningForSelectOneOnly(PropertiesLoader props) {
        MessageDialog.openWarning(new Shell(), props.get(Dialog.Common.title), props.get(Dialog.Common.selectOneOnly));
    }

    protected void openWarningForSelectJavaFile(PropertiesLoader props) {
        MessageDialog.openWarning(new Shell(), props.get(Dialog.Common.title), props.get(Dialog.Common.selectJavaFile));
    }

    protected void openWarning(PropertiesLoader props, String message) {
        MessageDialog.openWarning(new Shell(), props.get(Dialog.Common.title), message);
    }

    protected boolean openConfirm(PropertiesLoader props, String message) {
        return MessageDialog.openConfirm(new Shell(), props.get(Dialog.Common.title), message);
    }

}
