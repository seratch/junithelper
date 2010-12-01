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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.io.PropertiesLoader;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	RadioGroupFieldEditor editor_Language;

	StringFieldEditor editor_outputFileEncoding;

	RadioGroupFieldEditor editor_JUnitVersion;

	StringFieldEditor editor_ClassToExtend;

	StringFieldEditor editor_TestMethodNameBasicDelimiter;

	BooleanFieldEditor editor_isTestMethodNameArgsRequired;
	StringFieldEditor editor_TestMethodNameArgsPrefix;
	StringFieldEditor editor_TestMethodNameArgsDelimiter;

	BooleanFieldEditor editor_isTestMethodNameReturnTypeRequired;
	StringFieldEditor editor_TestMethodNameReturnPrefix;
	StringFieldEditor editor_TestMethodNameRetrnDelimiter;

	BooleanFieldEditor editor_isExceptionThrownRequired;
	StringFieldEditor editor_TestMethodNameExceptionPrefix;
	StringFieldEditor editor_TestMethodNameExceptionDelimiter;

	BooleanFieldEditor editor_isPublicRequired;
	BooleanFieldEditor editor_isProtectedRequired;
	BooleanFieldEditor editor_isPackageLocalRequired;
	BooleanFieldEditor editor_isAccessorsExcluded;

	RadioGroupFieldEditor editor_MockObjectFramework;

	public PreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
	}

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
					editor_Language = new RadioGroupFieldEditor(
							Preference.lang, "Select your language.", 2,
							labelAndValues, parent);
					addField(editor_Language);

					// output fileencoding
					addField(new StringFieldEditor(
							Preference.Common.outputFileEncoding,
							props.get(Preference.Common.outputFileEncoding),
							parent));

					// src/main/java
					addField(new StringFieldEditor(
							Preference.Common.srcMainPath,
							props.get(Preference.Common.srcMainPath), parent));
					// src/test/java
					addField(new StringFieldEditor(
							Preference.Common.srcTestPath,
							props.get(Preference.Common.srcTestPath), parent));

					// JUnit version(3 or 4)
					String ver3 = Preference.TestClassGen.junitVersion3;
					String ver4 = Preference.TestClassGen.junitVersion4;
					String[][] junitVersionsLabelAndValues = new String[][] {
							{ props.get(ver3), ver3 },
							{ props.get(ver4), ver4 }, };

					editor_JUnitVersion = new RadioGroupFieldEditor(
							Preference.TestClassGen.junitVersion,
							props.get(Preference.TestClassGen.description), 2,
							junitVersionsLabelAndValues, parent) {
						@Override
						protected void fireValueChanged(String p, Object o,
								Object n) {
							super.fireValueChanged(p, o, n);
							IPreferenceStore store = Activator.getDefault()
									.getPreferenceStore();
							store.setValue(
									Preference.TestClassGen.junitVersion,
									String.valueOf(n));
						}
					};
					addField(editor_JUnitVersion);

					// class to extend
					editor_ClassToExtend = new StringFieldEditor(
							Preference.TestClassGen.classToExtend,
							props.get(Preference.TestClassGen.classToExtend),
							parent);
					addField(editor_ClassToExtend);

					// common delimiter setting
					editor_TestMethodNameBasicDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.delimiter,
							props.get(Preference.TestMethodGen.delimiter), 10,
							parent);
					addField(editor_TestMethodNameBasicDelimiter);

					editor_isTestMethodNameArgsRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.enabledArgs,
							props.get(Preference.TestMethodGen.enabledArgs),
							parent);
					addField(editor_isTestMethodNameArgsRequired);
					editor_TestMethodNameArgsPrefix = new StringFieldEditor(
							Preference.TestMethodGen.argsPrefix,
							props.get(Preference.TestMethodGen.argsPrefix),
							parent);
					addField(editor_TestMethodNameArgsPrefix);
					editor_TestMethodNameArgsDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.argsDelimiter,
							props.get(Preference.TestMethodGen.argsDelimiter),
							parent);
					addField(editor_TestMethodNameArgsDelimiter);

					editor_isTestMethodNameReturnTypeRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.enabledReturn,
							props.get(Preference.TestMethodGen.enabledReturn),
							parent);
					addField(editor_isTestMethodNameReturnTypeRequired);
					editor_TestMethodNameReturnPrefix = new StringFieldEditor(
							Preference.TestMethodGen.returnPrefix,
							props.get(Preference.TestMethodGen.returnPrefix),
							parent);
					addField(editor_TestMethodNameReturnPrefix);
					editor_TestMethodNameRetrnDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.returnDelimiter,
							props.get(Preference.TestMethodGen.returnDelimiter),
							parent);
					addField(editor_TestMethodNameRetrnDelimiter);

					// enable exception thrown
					editor_isExceptionThrownRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.enabledException,
							props.get(Preference.TestMethodGen.enabledException),
							parent);
					addField(editor_isExceptionThrownRequired);
					editor_TestMethodNameExceptionPrefix = new StringFieldEditor(
							Preference.TestMethodGen.exceptionPrefix,
							props.get(Preference.TestMethodGen.exceptionPrefix),
							parent);
					addField(editor_TestMethodNameExceptionPrefix);
					editor_TestMethodNameExceptionDelimiter = new StringFieldEditor(
							Preference.TestMethodGen.exceptionDelimiter,
							props.get(Preference.TestMethodGen.exceptionDelimiter),
							parent);
					addField(editor_TestMethodNameExceptionDelimiter);

					// public methods
					editor_isPublicRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.includePublic,
							props.get(Preference.TestMethodGen.includePublic),
							parent);
					addField(editor_isPublicRequired);
					// protected methods
					editor_isProtectedRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.includeProtected,
							props.get(Preference.TestMethodGen.includeProtected),
							parent);
					addField(editor_isProtectedRequired);
					// package local methods
					editor_isPackageLocalRequired = new BooleanFieldEditor(
							Preference.TestMethodGen.includePackageLocal,
							props.get(Preference.TestMethodGen.includePackageLocal),
							parent);
					addField(editor_isPackageLocalRequired);

					// enable excluding accessors
					editor_isAccessorsExcluded = new BooleanFieldEditor(
							Preference.TestMethodGen.excludesAccessors,
							props.get(Preference.TestMethodGen.excludesAccessors),
							parent);
					addField(editor_isAccessorsExcluded);

					String none = Preference.TestMethodGen.usingMockNone;
					String easyMock = Preference.TestMethodGen.usingMockEasyMock;
					String jmock2 = Preference.TestMethodGen.usingMockJMock2;
					String mockito = Preference.TestMethodGen.usingMockMockito;
					String jmockit = Preference.TestMethodGen.usingMockJMockit;
					String[][] mockLabelAndValues = new String[][] {
							{ props.get(none), none },
							{ props.get(easyMock), easyMock },
							{ props.get(jmock2), jmock2 },
							{ props.get(mockito), mockito },
							{ props.get(jmockit), jmockit } };
					editor_MockObjectFramework = new RadioGroupFieldEditor(
							Preference.TestMethodGen.usingMock,
							props.get(Preference.TestMethodGen.descriptionForMock),
							8, mockLabelAndValues, parent);
					addField(editor_MockObjectFramework);

				}
			}
		}
	}

}
