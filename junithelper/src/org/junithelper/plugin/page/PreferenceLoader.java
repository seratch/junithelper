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

import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;
import org.junithelper.plugin.util.MockGenUtil;

/**
 * PreferenceLoader<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class PreferenceLoader {

	public String commonSrcMainJavaDir;
	public String commonTestMainJavaDir;

	public boolean isJUnitVersion3;
	public boolean isJUnitVersion4;
	public boolean isUsingJUnitHelperRuntime;

	public boolean isTestClassGenEnabled;

	public boolean isTestMethodGenEnabled;
	public boolean isTestMethodGenArgsEnabled;
	public boolean isTestMethodGenReturnEnabled;
	public boolean isTestMethodGenNotBlankEnabled;
	public boolean isTestMethodGenEnabledSupportJMock2;
	public boolean isTestMethodGenEnabledSupportEasyMock;
	public boolean isTestMethodGenExecludeAccessors;

	public String testMethodDelimiter;
	public String testMethodArgsPrefix;
	public String testMethodArgsDelimiter;
	public String testMethodReturnPrefix;
	public String testMethodReturnDelimiter;

	public String classToExtend;

	public PreferenceLoader() {

		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		commonSrcMainJavaDir = store.getString(Preference.Common.SRC_MAIN_PATH);
		commonTestMainJavaDir = store
				.getString(Preference.Common.SRC_TEST_PATH);

		isTestClassGenEnabled = store
				.getBoolean(Preference.TestClassGen.ENABLE);

		isTestMethodGenEnabled = store
				.getBoolean(Preference.TestMethodGen.ENABLE);
		isTestMethodGenArgsEnabled = store
				.getBoolean(Preference.TestMethodGen.ARGS);
		isTestMethodGenReturnEnabled = store
				.getBoolean(Preference.TestMethodGen.RETURN);
		isTestMethodGenNotBlankEnabled = store
				.getBoolean(Preference.TestMethodGen.METHOD_SAMPLE_IMPL);
		isTestMethodGenExecludeAccessors = store
				.getBoolean(Preference.TestMethodGen.EXLCUDES_ACCESSORS);
		isTestMethodGenEnabledSupportJMock2 = MockGenUtil.isUsingJMock2(store);
		isTestMethodGenEnabledSupportEasyMock = MockGenUtil
				.isUsingEasyMock(store);

		String version = store.getString(Preference.TestClassGen.JUNIT_VERSION);
		isJUnitVersion3 = version
				.equals(Preference.TestClassGen.JUNIT_VERSION_3);
		isJUnitVersion4 = version
				.equals(Preference.TestClassGen.JUNIT_VERSION_4);
		isUsingJUnitHelperRuntime = store
				.getBoolean(Preference.TestClassGen.USING_JUNIT_HELPER_RUNTIME_LIB);

		testMethodDelimiter = store
				.getString(Preference.TestMethodGen.DELIMITER);
		testMethodArgsPrefix = store
				.getString(Preference.TestMethodGen.ARGS_PREFIX);
		testMethodArgsDelimiter = store
				.getString(Preference.TestMethodGen.ARGS_DELIMITER);
		testMethodReturnPrefix = store
				.getString(Preference.TestMethodGen.RETURN_PREFIX);
		testMethodReturnDelimiter = store
				.getString(Preference.TestMethodGen.RETURN_DELIMITER);

		classToExtend = store
				.getString(Preference.TestClassGen.CLASS_TO_EXTEND);

	}
}
