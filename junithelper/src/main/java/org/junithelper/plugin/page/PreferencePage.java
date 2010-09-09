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
import org.junithelper.plugin.io.PropertiesLoader;

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
		PropertiesLoader props = new PropertiesLoader(getPreferenceStore()
				.getString(Preference.lang));
		{
			// parent group
			Composite parent = getFieldEditorParent();
			{
				GridData gd = new GridData(768);
				parent.setLayoutData(gd);
				{
					String en = Preference.Lang.English;
					String ja = Preference.Lang.Japanese;
					String[][] labelAndValues = new String[][] {
							{ Preference.Lang.EnglishLabel, en },
							{ Preference.Lang.JapaneseLabel, ja }, };
					langGroup = new RadioGroupFieldEditor(Preference.lang,
							"Settings for JUnit Helper.", 2, labelAndValues,
							parent);
					addField(langGroup);
				}
			}

			// common group
			Group commonParentGroup = new Group(parent, 0);
			{
				FillLayout parentLayout = new FillLayout(256);
				parentLayout.marginHeight = 2;
				parentLayout.marginWidth = 4;
				commonParentGroup.setLayout(parentLayout);
				commonParentGroup.setText(props
						.get(Preference.Common.groupName));
				GridData gd = new GridData(768);
				commonParentGroup.setLayoutData(gd);

				Composite grp = new Composite(commonParentGroup, 0);
				grp.setLayout(new GridLayout(2, false));
				Label label = new Label(grp, 0);
				label.setText(props.get(Preference.Common.description));
				gd = new GridData(768);
				gd.horizontalSpan = 2;
				label.setLayoutData(gd);
				// src/main/java
				addField(new StringFieldEditor(Preference.Common.srcMainPath,
						props.get(Preference.Common.srcMainPath), grp));
				// src/test/java
				addField(new StringFieldEditor(Preference.Common.srcTestPath,
						props.get(Preference.Common.srcTestPath), grp));
			}

			// generating test class group
			// enable extended class gen
			tcgEnable = new BooleanFieldEditor(Preference.TestClassGen.enabled,
					props.get(Preference.TestClassGen.enabled), parent);
			addField(tcgEnable);
			Group classConfigGroup = new Group(parent, 0);
			{
				FillLayout parentLayout = new FillLayout(256);
				parentLayout.marginHeight = 2;
				parentLayout.marginWidth = 4;
				classConfigGroup.setLayout(parentLayout);
				classConfigGroup.setText(props
						.get(Preference.TestClassGen.groupName));
				GridData gd = new GridData(768);
				classConfigGroup.setLayoutData(gd);

				tcgArea = new Composite(classConfigGroup, 0);
				tcgArea.setLayout(new GridLayout(2, false));
				{
					// JUnit version(3 or 4)
					String ver3 = Preference.TestClassGen.junitVersion3;
					String ver4 = Preference.TestClassGen.junitVersion4;
					String[][] labelAndValues = new String[][] {
							{ props.get(ver3), ver3 },
							{ props.get(ver4), ver4 }, };

					tcgRadioGroupVersions = new RadioGroupFieldEditor(
							Preference.TestClassGen.junitVersion,
							props.get(Preference.TestClassGen.description), 2,
							labelAndValues, tcgArea) {
						@Override
						protected void fireValueChanged(String p, Object o,
								Object n) {
							String ver3 = Preference.TestClassGen.junitVersion3;
							String ver4 = Preference.TestClassGen.junitVersion4;
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
									Preference.TestClassGen.junitVersion,
									String.valueOf(n));
						}
					};
					addField(tcgRadioGroupVersions);
				}
				{
					// using junit helper runtime lib or not
					tcgUsingJUnitHelperTestCase = new BooleanFieldEditor(
							Preference.TestClassGen.usingJunitHelperRuntimeLib,
							props.get(Preference.TestClassGen.usingJunitHelperRuntimeLib),
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
							Preference.TestClassGen.classToExtend,
							props.get(Preference.TestClassGen.classToExtend),
							tcgArea);
					addField(tcgClassToExtend);
				}
			}

			// generating test methods group
			// enable test methods gen
			tmgEnable = new BooleanFieldEditor(
					Preference.TestMethodGen.enabled,
					props.get(Preference.TestMethodGen.enabled), parent) {
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
				tmgParentGroup.setText(props
						.get(Preference.TestMethodGen.groupName));
				GridData gd = new GridData(768);
				tmgParentGroup.setLayoutData(gd);

				tmgArea = new Composite(tmgParentGroup, 0);
				GridLayout layout = new GridLayout(2, false);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				tmgArea.setLayout(layout);

				{
					String label = " "
							+ props.get(Preference.TestMethodGen.delimiter);
					// common delimiter setting
					tmgCommonDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.delimiter, label, 10,
							tmgArea);
					addField(tmgCommonDelimiter);
				}
			}
			{
				// enable args
				tmgEnableArgs = new BooleanFieldEditor(
						Preference.TestMethodGen.enabledArgs,
						props.get(Preference.TestMethodGen.enabledArgs),
						tmgParentGroup) {
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
				group.setText(props
						.get(Preference.TestMethodGen.subGroupNameForArgs));
				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);

				tmgEnableArgsArea = new Composite(group, 0);
				tmgEnableArgsArea.setLayout(new GridLayout(2, false));
				tmgArgsPrefix = new StringFieldEditor(
						Preference.TestMethodGen.argsPrefix,
						props.get(Preference.TestMethodGen.argsPrefix),
						tmgEnableArgsArea);
				addField(tmgArgsPrefix);
				tmgArgsDelimiter = new StringFieldEditor(
						Preference.TestMethodGen.argsDelimiter,
						props.get(Preference.TestMethodGen.argsDelimiter),
						tmgEnableArgsArea);
				addField(tmgArgsDelimiter);
			}
			{
				// enable return
				tmgEnableReturn = new BooleanFieldEditor(
						Preference.TestMethodGen.enabledReturn,
						props.get(Preference.TestMethodGen.enabledReturn),
						tmgParentGroup) {
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
				group.setText(props
						.get(Preference.TestMethodGen.subGroupNameForReturn));
				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);

				tmgReturnArea = new Composite(group, 0);
				tmgReturnArea.setLayout(new GridLayout(2, false));
				tmgReturnPrefix = new StringFieldEditor(
						Preference.TestMethodGen.returnPrefix,
						props.get(Preference.TestMethodGen.returnPrefix),
						tmgReturnArea);
				addField(tmgReturnPrefix);
				tmgReturnDelimiter = new StringFieldEditor(
						Preference.TestMethodGen.returnDelimiter,
						props.get(Preference.TestMethodGen.returnDelimiter),
						tmgReturnArea);
				addField(tmgReturnDelimiter);
			}

			{
				// enable exceptions
				tmgEnableException = new BooleanFieldEditor(
						Preference.TestMethodGen.enabledException,
						props.get(Preference.TestMethodGen.enabledException),
						tmgParentGroup) {
					@Override
					protected void valueChanged(boolean oldValue,
							boolean newValue) {
						super.valueChanged(oldValue, newValue);
						switchDisplayOfMethodExceptionsArea(newValue);
					}

					@Override
					protected void doLoad() {
						super.doLoad();
						switchDisplayOfMethodExceptionsArea(getBooleanValue());
					}

					@Override
					protected void doLoadDefault() {
						super.doLoadDefault();
						switchDisplayOfMethodExceptionsArea(getBooleanValue());
					}
				};
				addField(tmgEnableException);

				Group group = new Group(tmgParentGroup, 0);
				FillLayout layout = new FillLayout(256);
				layout.marginHeight = 4;
				layout.marginWidth = 4;
				group.setLayout(layout);
				group.setText(props
						.get(Preference.TestMethodGen.subGroupNameForException));
				GridData gd = new GridData(768);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);

				tmgExceptionArea = new Composite(group, 0);
				tmgExceptionArea.setLayout(new GridLayout(2, false));
				tmgExceptionPrefix = new StringFieldEditor(
						Preference.TestMethodGen.exceptionPrefix,
						props.get(Preference.TestMethodGen.exceptionPrefix),
						tmgExceptionArea);
				addField(tmgExceptionPrefix);
				tmgExceptionDelimiter = new StringFieldEditor(
						Preference.TestMethodGen.exceptionDelimiter,
						props.get(Preference.TestMethodGen.exceptionDelimiter),
						tmgExceptionArea);
				addField(tmgExceptionDelimiter);
			}

			{
				// public methods
				tmgCommonIncludePublicMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.includePublic,
						props.get(Preference.TestMethodGen.includePublic),
						tmgParentGroup);
				addField(tmgCommonIncludePublicMethods);
				// protected methods
				tmgCommonIncludeProtectdMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.includeProtected,
						props.get(Preference.TestMethodGen.includeProtected),
						tmgParentGroup);
				addField(tmgCommonIncludeProtectdMethods);
				// package local methods
				tmgCommonIncludePackageLocalMethods = new BooleanFieldEditor(
						Preference.TestMethodGen.includePackageLocal,
						props.get(Preference.TestMethodGen.includePackageLocal),
						tmgParentGroup);
				addField(tmgCommonIncludePackageLocalMethods);
			}
			{
				// enable excluding accessors
				tmgCommonExecludesAccessors = new BooleanFieldEditor(
						Preference.TestMethodGen.excludesAccessors,
						props.get(Preference.TestMethodGen.excludesAccessors),
						tmgParentGroup);
				addField(tmgCommonExecludesAccessors);
			}

			{
				// enable generate not blank methods
				tmgEnableGenerateSample = new BooleanFieldEditor(
						Preference.TestMethodGen.enabledTestMethodSampleImpl,
						props.get(Preference.TestMethodGen.enabledTestMethodSampleImpl),
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
				group.setText(props
						.get(Preference.TestMethodGen.subGroupNameForMock));
				GridData gd = new GridData(350);
				gd.horizontalSpan = 2;
				group.setLayoutData(gd);
				tmgSampleImplGenArea = new Composite(group, 0);
				tmgSampleImplGenArea.setLayout(new GridLayout(2, false));
				String none = Preference.TestMethodGen.usingMockNone;
				String easyMock = Preference.TestMethodGen.usingMockEasyMock;
				String jmock2 = Preference.TestMethodGen.usingMockJMock2;
				String mockito = Preference.TestMethodGen.usingMockMockito;
				String jmockit = Preference.TestMethodGen.usingMockJMockit;
				String[][] labelAndValues = new String[][] {
						{ props.get(none), none },
						{ props.get(easyMock), easyMock },
						{ props.get(jmock2), jmock2 },
						{ props.get(mockito), mockito },
						{ props.get(jmockit), jmockit } };
				tmgRadioGroupMocks = new RadioGroupFieldEditor(
						Preference.TestMethodGen.usingMock,
						props.get(Preference.TestMethodGen.descriptionForMock),
						4, labelAndValues, tmgSampleImplGenArea);
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
		String value = Activator.getDefault().getPreferenceStore()
				.getString(Preference.TestClassGen.junitVersion);
		if (value == null || value.equals("")
				|| value.equals(Preference.TestClassGen.junitVersion3)) {
			return true;
		} else {
			return false;
		}
	}

	private void switchDisplayOfClassToExtendArea(boolean value) {
		// enable/disable the area
		tcgClassToExtend.setEnabled(value, tcgArea);
	}

	private RadioGroupFieldEditor langGroup;

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

	private BooleanFieldEditor tmgEnableException;
	private StringFieldEditor tmgExceptionPrefix;
	private StringFieldEditor tmgExceptionDelimiter;
	private Composite tmgExceptionArea;

	private void switchDisplayOfMethodExceptionsArea(boolean value) {
		// enable/disable the area
		tmgExceptionDelimiter.setEnabled(value, tmgExceptionArea);
		tmgExceptionPrefix.setEnabled(value, tmgExceptionArea);
	}

	private BooleanFieldEditor tmgEnableGenerateSample;
	private RadioGroupFieldEditor tmgRadioGroupMocks;
	private Composite tmgSampleImplGenArea;

	private void switchDisplayOfSampleImplGenArea(boolean value) {
		// enable/disable the area
		tmgRadioGroupMocks.setEnabled(value, tmgSampleImplGenArea);
	}

}
