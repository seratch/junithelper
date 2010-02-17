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
package org.junithelper.plugin.page;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
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
import org.junithelper.plugin.constant.Preference;

/**
 * PreferencePage<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Constructor
	 */
	public PreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setDescription(Preference.Common.DESCRIPTION);
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
				label.setText("Specify the source folders.");
				gd = new GridData(768);
				gd.horizontalSpan = 2;
				label.setLayoutData(gd);
				// src/main/java
				addField(new StringFieldEditor(Preference.Common.SRC_MAIN_PATH,
						Preference.Common.SRC_MAIN_PATH, grp));
				// src/test/java
				addField(new StringFieldEditor(Preference.Common.SRC_TEST_PATH,
						Preference.Common.SRC_TEST_PATH, grp));
			}

			// generating test class group
			// enable extended class gen
			tcgEnable = new BooleanFieldEditor(Preference.TestClassGen.ENABLE,
					Preference.TestClassGen.ENABLE, parent);
			addField(tcgEnable);
			Group classConfigGroup = new Group(parent, 0);
			{
				FillLayout parentLayout = new FillLayout(256);
				parentLayout.marginHeight = 2;
				parentLayout.marginWidth = 4;
				classConfigGroup.setLayout(parentLayout);
				classConfigGroup.setText("Test Class");
				GridData gd = new GridData(768);
				classConfigGroup.setLayoutData(gd);

				tcgArea = new Composite(classConfigGroup, 0);
				tcgArea.setLayout(new GridLayout(2, false));
				{
					// JUnit version(3 or 4)
					String ver3 = Preference.TestClassGen.JUNIT_VERSION_3;
					String ver4 = Preference.TestClassGen.JUNIT_VERSION_4;
					String[][] labelAndValues = new String[][] {
							{ ver3, ver3 }, { ver4, ver4 }, };
					tcgRadioGroupVersions = new RadioGroupFieldEditor(
							Preference.TestClassGen.JUNIT_VERSION,
							"Select JUnit Version.", 2, labelAndValues, tcgArea) {
						@Override
						protected void fireValueChanged(String p, Object o,
								Object n) {
							String ver3 = Preference.TestClassGen.JUNIT_VERSION_3;
							String ver4 = Preference.TestClassGen.JUNIT_VERSION_4;
							boolean usingRuntimeLib = tcgUsingJUnitHelperTestCase
									.getBooleanValue();
							if (n.equals(ver4)) {
								switchDisplayOfClassToExtendArea(false);
							} else if (n.equals(ver3) && !usingRuntimeLib) {
								switchDisplayOfClassToExtendArea(true);
							}
							super.fireValueChanged(p, o, n);
							IPreferenceStore store = Activator.getDefault()
									.getPreferenceStore();
							store.setValue(
									Preference.TestClassGen.JUNIT_VERSION,
									String.valueOf(n));
						}
					};
					addField(tcgRadioGroupVersions);
				}
				{
					// using junit helper runtime lib or not
					tcgUsingJUnitHelperTestCase = new BooleanFieldEditor(
							Preference.TestClassGen.USING_JUNIT_HELPER_RUNTIME_LIB,
							Preference.TestClassGen.USING_JUNIT_HELPER_RUNTIME_LIB,
							tcgArea) {
						@Override
						protected void valueChanged(boolean oldValue,
								boolean newValue) {
							super.valueChanged(oldValue, newValue);
							if (isJUnitVersion3()) {
								switchDisplayOfClassToExtendArea(newValue ? false
										: true);
							} else {
								switchDisplayOfClassToExtendArea(false);
							}
						}

						@Override
						protected void doLoad() {
							super.doLoad();
							if (isJUnitVersion3()) {
								switchDisplayOfClassToExtendArea(getBooleanValue() ? false
										: true);
							} else {
								switchDisplayOfClassToExtendArea(false);
							}
						}

						@Override
						protected void doLoadDefault() {
							super.doLoadDefault();
							if (isJUnitVersion3()) {
								switchDisplayOfClassToExtendArea(getBooleanValue() ? false
										: true);
							} else {
								switchDisplayOfClassToExtendArea(false);
							}
						}
					};
					addField(tcgUsingJUnitHelperTestCase);
				}
				{
					// class to extend
					tcgClassToExtend = new StringFieldEditor(
							Preference.TestClassGen.CLASS_TO_EXTEND,
							Preference.TestClassGen.CLASS_TO_EXTEND, tcgArea);
					addField(tcgClassToExtend);
				}
			}

			// generating test methods group
			// enable test methods gen
			tmgEnable = new BooleanFieldEditor(Preference.TestMethodGen.ENABLE,
					Preference.TestMethodGen.ENABLE, parent) {
				@Override
				protected void valueChanged(boolean oldValue, boolean newValue) {
					super.valueChanged(oldValue, newValue);
					switchDisplayOfTestMethodsGenArea(newValue);
				}

				@Override
				protected void doLoad() {
					super.doLoad();
					switchDisplayOfTestMethodsGenArea(getBooleanValue());
				}

				@Override
				protected void doLoadDefault() {
					super.doLoadDefault();
					switchDisplayOfTestMethodsGenArea(getBooleanValue());
				}
			};
			addField(tmgEnable);
			tmgParentGroup = new Group(parent, 0);
			{
				FillLayout parentLayout = new FillLayout(256);
				parentLayout.marginHeight = 4;
				parentLayout.marginWidth = 4;
				tmgParentGroup.setLayout(parentLayout);
				tmgParentGroup.setText("Test Method Auto Generate");
				GridData gd = new GridData(768);
				tmgParentGroup.setLayoutData(gd);

				tmgArea = new Composite(tmgParentGroup, 0);
				GridLayout layout = new GridLayout(2, false);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				tmgArea.setLayout(layout);

				{
					// common delimiter setting
					tmgCommonDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.DELIMITER,
							Preference.TestMethodGen.DELIMITER, 10, tmgArea);
					addField(tmgCommonDelimiter);
				}
			}
			{
				// enable args
				tmgEnableArgs = new BooleanFieldEditor(
						Preference.TestMethodGen.ARGS,
						Preference.TestMethodGen.ARGS, tmgParentGroup) {
					@Override
					protected void valueChanged(boolean oldValue,
							boolean newValue) {
						super.valueChanged(oldValue, newValue);
						switchDisplayOfMethodArgsArea(newValue);
					}

					@Override
					protected void doLoad() {
						super.doLoad();
						switchDisplayOfMethodArgsArea(getBooleanValue());
					}

					@Override
					protected void doLoadDefault() {
						super.doLoadDefault();
						switchDisplayOfMethodArgsArea(getBooleanValue());
					}
				};
				addField(tmgEnableArgs);

				Group group = new Group(tmgParentGroup, 0);
				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Args");
				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);

				tmgEnableArgsArea = new Composite(group, 0);
				tmgEnableArgsArea.setLayout(new GridLayout(2, false));
				tmgArgsPrefix = new StringFieldEditor(
						Preference.TestMethodGen.ARGS_PREFIX,
						Preference.TestMethodGen.ARGS_PREFIX, tmgEnableArgsArea);
				addField(tmgArgsPrefix);
				tmgArgsDelimiter = new StringFieldEditor(
						Preference.TestMethodGen.ARGS_DELIMITER,
						Preference.TestMethodGen.ARGS_DELIMITER,
						tmgEnableArgsArea);
				addField(tmgArgsDelimiter);
			}
			{
				// enable return
				tmgEnableReturn = new BooleanFieldEditor(
						Preference.TestMethodGen.RETURN,
						Preference.TestMethodGen.RETURN, tmgParentGroup) {
					@Override
					protected void valueChanged(boolean oldValue,
							boolean newValue) {
						super.valueChanged(oldValue, newValue);
						switchDisplayOfMethodReturnArea(newValue);
					}

					@Override
					protected void doLoad() {
						super.doLoad();
						switchDisplayOfMethodReturnArea(getBooleanValue());
					}

					@Override
					protected void doLoadDefault() {
						super.doLoadDefault();
						switchDisplayOfMethodReturnArea(getBooleanValue());
					}
				};
				addField(tmgEnableReturn);

				Group group = new Group(tmgParentGroup, 0);
				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Return");
				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);

				tmgReturnArea = new Composite(group, 0);
				tmgReturnArea.setLayout(new GridLayout(2, false));
				tmgReturnPrefix = new StringFieldEditor(
						Preference.TestMethodGen.RETURN_PREFIX,
						Preference.TestMethodGen.RETURN_PREFIX, tmgReturnArea);
				addField(tmgReturnPrefix);
				tmgReturnDelimiter = new StringFieldEditor(
						Preference.TestMethodGen.RETURN_DELIMITER,
						Preference.TestMethodGen.RETURN_DELIMITER,
						tmgReturnArea);
				addField(tmgReturnDelimiter);
			}

			{
				// public methods
				tmgCommonIncludePublicMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.INCLUDE_PUBLIC,
						Preference.TestMethodGen.INCLUDE_PUBLIC, tmgParentGroup);
				addField(tmgCommonIncludePublicMethods);
				// protected methods
				tmgCommonIncludeProtectdMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.INCLUDE_PROTECTED,
						Preference.TestMethodGen.INCLUDE_PROTECTED,
						tmgParentGroup);
				addField(tmgCommonIncludeProtectdMethods);
				// package local methods
				tmgCommonIncludePackageLocalMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.INCLUDE_PACKAGE_LOCAL,
						Preference.TestMethodGen.INCLUDE_PACKAGE_LOCAL,
						tmgParentGroup);
				addField(tmgCommonIncludePackageLocalMethods);
			}
			{
				// enable excluding accessors
				tmgCommonExecludesAccessors = new BooleanFieldEditor(
						Preference.TestMethodGen.EXLCUDES_ACCESSORS,
						Preference.TestMethodGen.EXLCUDES_ACCESSORS,
						tmgParentGroup);
				addField(tmgCommonExecludesAccessors);
			}

			{
				// enable generate not blank methods
				tmgEnableGenerateSample = new BooleanFieldEditor(
						Preference.TestMethodGen.METHOD_SAMPLE_IMPL,
						Preference.TestMethodGen.METHOD_SAMPLE_IMPL,
						tmgParentGroup) {
					@Override
					protected void valueChanged(boolean oldValue,
							boolean newValue) {
						super.valueChanged(oldValue, newValue);
						switchDisplayOfSampleImplGenArea(newValue);
					}

					@Override
					protected void doLoad() {
						super.doLoad();
						switchDisplayOfSampleImplGenArea(getBooleanValue());
					}

					@Override
					protected void doLoadDefault() {
						super.doLoadDefault();
						switchDisplayOfSampleImplGenArea(getBooleanValue());
					}
				};
				addField(tmgEnableGenerateSample);

				Group group = new Group(tmgParentGroup, 0);
				FillLayout layout = new FillLayout(768);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText("Mock Object Frameworks");
				GridData gd = new GridData(350);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);
				tmgSampleImplGenArea = new Composite(group, 0);
				tmgSampleImplGenArea.setLayout(new GridLayout(2, false));
				String none = Preference.TestMethodGen.USING_MOCK_NONE;
				String easyMock = Preference.TestMethodGen.USING_MOCK_EASYMOCK;
				String jmock2 = Preference.TestMethodGen.USING_MOCK_JMOCK2;
				String[][] labelAndValues = new String[][] { { none, none },
						{ easyMock, easyMock }, { jmock2, jmock2 }, };
				tmgRadioGroupMocks = new RadioGroupFieldEditor(
						Preference.TestMethodGen.USING_MOCK,
						"Select your favorite framework.", 3, labelAndValues,
						tmgSampleImplGenArea);
				addField(tmgRadioGroupMocks);
			}
		}
	}

	private void switchDisplayOfTestMethodsGenArea(boolean value) {
	}

	private BooleanFieldEditor tcgEnable;
	private RadioGroupFieldEditor tcgRadioGroupVersions;
	private StringFieldEditor tcgClassToExtend;
	private BooleanFieldEditor tcgUsingJUnitHelperTestCase;
	private Composite tcgArea;

	private boolean isJUnitVersion3() {
		String value = Activator.getDefault().getPreferenceStore().getString(
				Preference.TestClassGen.JUNIT_VERSION);
		if (value == null || value.equals("")
				|| value.equals(Preference.TestClassGen.JUNIT_VERSION_3)) {
			return true;
		} else {
			return false;
		}
	}

	private void switchDisplayOfClassToExtendArea(boolean value) {
		// enable/disable the area
		tcgClassToExtend.setEnabled(value, tcgArea);
	}

	private BooleanFieldEditor tmgEnable;
	private StringFieldEditor tmgCommonDelimiter;
	private BooleanFieldEditor tmgCommonIncludePublicMethods;
	private BooleanFieldEditor tmgCommonIncludeProtectdMethods;
	private BooleanFieldEditor tmgCommonIncludePackageLocalMethods;
	private BooleanFieldEditor tmgCommonExecludesAccessors;
	private Composite tmgArea;
	private Group tmgParentGroup;

	private BooleanFieldEditor tmgEnableArgs;
	private StringFieldEditor tmgArgsPrefix;
	private StringFieldEditor tmgArgsDelimiter;
	private Composite tmgEnableArgsArea;

	private void switchDisplayOfMethodArgsArea(boolean value) {
		// enable/disable the area
		tmgArgsDelimiter.setEnabled(value, tmgEnableArgsArea);
		tmgArgsPrefix.setEnabled(value, tmgEnableArgsArea);
	}

	private BooleanFieldEditor tmgEnableReturn;
	private StringFieldEditor tmgReturnPrefix;
	private StringFieldEditor tmgReturnDelimiter;
	private Composite tmgReturnArea;

	private void switchDisplayOfMethodReturnArea(boolean value) {
		// enable/disable the area
		tmgReturnDelimiter.setEnabled(value, tmgReturnArea);
		tmgReturnPrefix.setEnabled(value, tmgReturnArea);
	}

	private BooleanFieldEditor tmgEnableGenerateSample;
	private RadioGroupFieldEditor tmgRadioGroupMocks;
	private Composite tmgSampleImplGenArea;

	private void switchDisplayOfSampleImplGenArea(boolean value) {
		// enable/disable the area
		tmgRadioGroupMocks.setEnabled(value, tmgSampleImplGenArea);
	}

}
