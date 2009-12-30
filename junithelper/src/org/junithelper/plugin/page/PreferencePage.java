/* 
 * Copyright 2009 junithelper.org. 
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
package org.junithelper.plugin.page;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.STR;

/**
 * PreferencePage<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class PreferencePage extends FieldEditorPreferencePage implements
	IWorkbenchPreferencePage {

    /**
     * Constructor
     */
    public PreferencePage() {

	super(FieldEditorPreferencePage.GRID);
	setDescription(STR.Preference.Common.DESCRIPTION);
	setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    /**
     * Init
     */
    public void init(IWorkbench workbench) {
    }

    /**
     * Create field editors
     */
    @Override
    protected void createFieldEditors() {
	{
	    // parent group
	    Composite parent = getFieldEditorParent();

	    // common group
	    Group commonPrentGroup = new Group(parent, 0);
	    {
		FillLayout parentLayout = new FillLayout(256);
		parentLayout.marginHeight = 2;
		parentLayout.marginWidth = 4;
		commonPrentGroup.setLayout(parentLayout);
		commonPrentGroup.setText("Common");

		GridData gd = new GridData(768);
		commonPrentGroup.setLayoutData(gd);
		Composite grp = new Composite(commonPrentGroup, 0);
		grp.setLayout(new GridLayout(2, false));

		Label label = new Label(grp, 0);
		label
			.setText("If you changed this section, please restart Eclipse.");
		gd = new GridData(768);
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		// src/main/java
		addField(new StringFieldEditor(
			STR.Preference.Common.SRC_MAIN_PATH,
			STR.Preference.Common.SRC_MAIN_PATH, grp));
		// src/test/java
		addField(new StringFieldEditor(
			STR.Preference.Common.SRC_TEST_PATH,
			STR.Preference.Common.SRC_TEST_PATH, grp));
	    }

	    // generating test class group
	    // enable extended class gen
	    BooleanFieldEditor classExtendsEnable = new BooleanFieldEditor(
		    STR.Preference.TestClassGen.ENABLE,
		    STR.Preference.TestClassGen.ENABLE, parent);
	    addField(classExtendsEnable);
	    Group classConfigGroup = new Group(parent, 0);
	    {
		FillLayout parentLayout = new FillLayout(256);
		parentLayout.marginHeight = 2;
		parentLayout.marginWidth = 4;
		classConfigGroup.setLayout(parentLayout);
		classConfigGroup.setText("Test Class");

		GridData gd = new GridData(768);
		classConfigGroup.setLayoutData(gd);
		Composite grp = new Composite(classConfigGroup, 0);
		grp.setLayout(new GridLayout(2, false));

		// extends
		addField(new StringFieldEditor(
			STR.Preference.TestClassGen.CLASS_TO_EXTEND,
			STR.Preference.TestClassGen.CLASS_TO_EXTEND, grp));
	    }

	    // generating test methods group
	    // enable test methods gen
	    BooleanFieldEditor testMethodsGenEnable = new BooleanFieldEditor(
		    STR.Preference.TestMethodGen.ENABLE,
		    STR.Preference.TestMethodGen.ENABLE, parent);
	    addField(testMethodsGenEnable);
	    Group genTestMethodsParentGroup = new Group(parent, 0);
	    {
		FillLayout parentLayout = new FillLayout(256);
		parentLayout.marginHeight = 2;
		parentLayout.marginWidth = 4;
		genTestMethodsParentGroup.setLayout(parentLayout);
		genTestMethodsParentGroup.setText("Test Method Auto Generate");

		GridData gd = new GridData(768);
		genTestMethodsParentGroup.setLayoutData(gd);
		Composite grp = new Composite(genTestMethodsParentGroup, 0);
		grp.setLayout(new GridLayout(2, false));

	    }

	    {
		// delimiter
		Group group = new Group(genTestMethodsParentGroup, 0);

		FillLayout layout = new FillLayout(256);
		layout.marginHeight = 4;
		layout.marginWidth = 4;
		group.setLayout(layout);
		group.setText("Delimiter");

		GridData gd = new GridData(768);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		Composite grp = new Composite(group, 0);
		grp.setLayout(new GridLayout(2, false));
		// delimiter
		addField(new StringFieldEditor(
			STR.Preference.TestMethodGen.DELIMITER,
			STR.Preference.TestMethodGen.DELIMITER, grp));
	    }

	    {
		// enable args
		BooleanFieldEditor enableArgs = new BooleanFieldEditor(
			STR.Preference.TestMethodGen.ARGS,
			STR.Preference.TestMethodGen.ARGS,
			genTestMethodsParentGroup);

		addField(enableArgs);

		Group group = new Group(genTestMethodsParentGroup, 0);

		FillLayout layout = new FillLayout(256);
		layout.marginHeight = 4;
		layout.marginWidth = 4;
		group.setLayout(layout);
		group.setText("Method Args");

		GridData gd = new GridData(768);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		Composite grp = new Composite(group, 0);
		grp.setLayout(new GridLayout(2, false));
		StringFieldEditor argsPrefix = new StringFieldEditor(
			STR.Preference.TestMethodGen.ARGS_PREFIX,
			STR.Preference.TestMethodGen.ARGS_PREFIX, grp);
		StringFieldEditor argsDelimiter = new StringFieldEditor(
			STR.Preference.TestMethodGen.ARGS_DELIMITER,
			STR.Preference.TestMethodGen.ARGS_DELIMITER, grp);
		addField(argsPrefix);
		addField(argsDelimiter);
	    }
	    {
		// enable return
		addField(new BooleanFieldEditor(
			STR.Preference.TestMethodGen.RETURN,
			STR.Preference.TestMethodGen.RETURN,
			genTestMethodsParentGroup));

		Group group = new Group(genTestMethodsParentGroup, 0);

		FillLayout layout = new FillLayout(256);
		layout.marginHeight = 4;
		layout.marginWidth = 4;
		group.setLayout(layout);
		group.setText("Return");

		GridData gd = new GridData(768);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		Composite grp = new Composite(group, 0);
		grp.setLayout(new GridLayout(2, false));
		StringFieldEditor argsPrefix = new StringFieldEditor(
			STR.Preference.TestMethodGen.RETURN_PREFIX,
			STR.Preference.TestMethodGen.RETURN_PREFIX, grp);
		StringFieldEditor argsDelimiter = new StringFieldEditor(
			STR.Preference.TestMethodGen.RETURN_DELIMITER,
			STR.Preference.TestMethodGen.RETURN_DELIMITER, grp);
		addField(argsPrefix);
		addField(argsDelimiter);
	    }
	    {
		// enable excluding accessors
		BooleanFieldEditor execludesAccessors = new BooleanFieldEditor(
			STR.Preference.TestMethodGen.EXLCUDES_ACCESSORS,
			STR.Preference.TestMethodGen.EXLCUDES_ACCESSORS,
			genTestMethodsParentGroup);

		addField(execludesAccessors);
	    }
	    {
		// enable generate not blank methods
		BooleanFieldEditor enableGenerateSample = new BooleanFieldEditor(
			STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION,
			STR.Preference.TestMethodGen.METHOD_SAMPLE_IMPLEMENTATION,
			genTestMethodsParentGroup);
		addField(enableGenerateSample);

		Group group = new Group(genTestMethodsParentGroup, 0);
		FillLayout layout = new FillLayout(768);
		layout.marginHeight = 4;
		layout.marginWidth = 4;
		group.setLayout(layout);
		group.setText("Mock Object Frameworks");
		GridData gd = new GridData(350);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		final Composite grp = new Composite(group, 0);
		grp.setLayout(new GridLayout(2, false));

		// enable EasyMock supported test methods
		final BooleanFieldEditor enableSupportEasyMock = new BooleanFieldEditor(
			STR.Preference.TestMethodGen.USING_EASYMOCK,
			STR.Preference.TestMethodGen.USING_EASYMOCK, grp);
		addField(enableSupportEasyMock);

		// enable JMock2 supported test methods
		final BooleanFieldEditor enableSupportJMock2 = new BooleanFieldEditor(
			STR.Preference.TestMethodGen.USING_JMOCK2,
			STR.Preference.TestMethodGen.USING_JMOCK2, grp);
		addField(enableSupportJMock2);

		// TODO trap property changed event
		// and enable/disable the checkboxes
	    }
	}
    }

}
