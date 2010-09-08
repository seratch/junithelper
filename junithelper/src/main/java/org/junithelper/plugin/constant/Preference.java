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
package org.junithelper.plugin.constant;

/**
 * Constant for preferences<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class Preference {

	/**
	 * Select language
	 */
	public static final String lang = "Preference.lang";

	/**
	 * Lang
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class Lang {

		public static final String English = "en";

		public static final String EnglishLabel = "English";

		public static final String Japanese = "ja";

		public static final String JapaneseLabel = "日本語";
	}

	/**
	 * Common
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class Common {

		/**
		 * Description
		 */
		public static final String description = "TODO";
		/**
		 * Main Source Folder
		 */
		public static final String srcMainPath = "Preference.Common.srcMainPath";
		/**
		 * Test Source Folder
		 */
		public static final String srcTestPath = "Preference.Common.srcTestPath";
	}

	public static class TestClassGen {

		/**
		 * Enable test methods gen
		 */
		public static final String enabled = "Preference.TestClassGen.enabled";
		/**
		 * JUnit versions
		 */
		public static final String junitVersion = "Preference.TestClassGen.junitVersion";
		/**
		 * JUnit version 3.x
		 */
		public static final String junitVersion3 = "Preference.TestClassGen.junitVersion3";
		/**
		 * JUnit version 4.x
		 */
		public static final String junitVersion4 = "Preference.TestClassGen.junitVersion4";
		/**
		 * Class To Extend
		 */
		public static final String classToExtend = "Preference.TestClassGen.classToExtend";
		/**
		 * Using JUnit Helper runtime library
		 */
		public static final String usingJunitHelperRuntimeLib = "Preference.TestClassGen.usingJunitHelperRuntimeLib";
	}

	public static class TestMethodGen {
		/**
		 * Enable test methods gen
		 */
		public static final String enabled = "Preference.TestMethodGen.enabled";
		/**
		 * Delimiter to test method name
		 */
		public static final String delimiter = "Preference.TestMethodGen.delimiter";

		/**
		 * Enable includes method args in test method name
		 */
		public static final String enabledArgs = "Preference.TestMethodGen.enabledArgs";
		/**
		 * Args prefix
		 */
		public static final String argsPrefix = "Preference.TestMethodGen.argsPrefix";
		/**
		 * Args delimiter
		 */
		public static final String argsDelimiter = "Preference.TestMethodGen.argsDelimiter";

		/**
		 * Enable includes method return in test method name
		 */
		public static final String enabledReturn = "Preference.TestMethodGen.enabledReturn";
		/**
		 * Return prefix
		 */
		public static final String returnPrefix = "Preference.TestMethodGen.returnPrefix";
		/**
		 * Return delimiter
		 */
		public static final String returnDelimiter = "Preference.TestMethodGen.returnDelimiter";

		/**
		 * Enable includes exceptions that the method throws
		 */
		public static final String enabledException = "Preference.TestMethodGen.enabledException";
		/**
		 * Return prefix
		 */
		public static final String exceptionPrefix = "Preference.TestMethodGen.exceptionPrefix";
		/**
		 * Return delimiter
		 */
		public static final String exceptionDelimiter = "Preference.TestMethodGen.exceptionDelimiter";

		/**
		 * Public methods
		 */
		public static final String includePublic = "Preference.TestMethodGen.includePublic";

		/**
		 * Protected methods
		 */
		public static final String includeProtected = "Preference.TestMethodGen.includeProtected";

		/**
		 * Package local methods
		 */
		public static final String includePackageLocal = "Preference.TestMethodGen.includePackageLocal";

		/**
		 * Exclude accessors
		 */
		public static final String excludesAccessors = "Preference.TestMethodGen.excludesAccessors";

		/**
		 * Generate sample implementation to test methods
		 */
		public static final String enabledTestMethodSampleImpl = "Preference.TestMethodGen.enabledTestMethodSampleImpl";

		/**
		 * Using mock object framework
		 */
		public static final String usingMock = "Preference.TestMethodGen.usingMock";
		/**
		 * None
		 */
		public static final String usingMockNone = "Preference.TestMethodGen.usingMockNone";
		/**
		 * EasyMock
		 */
		public static final String usingMockEasyMock = "Preference.TestMethodGen.usingMockEasyMock";
		/**
		 * JMock2
		 */
		public static final String usingMockJMock2 = "Preference.TestMethodGen.usingMockJMock2";
		/**
		 * Mockito
		 */
		public static final String usingMockMockito = "Preference.TestMethodGen.usingMockMockito";
		/**
		 * JMockit
		 */
		public static final String usingMockJMockit = "Preference.TestMethodGen.usingMockJMockit";

	}

}
