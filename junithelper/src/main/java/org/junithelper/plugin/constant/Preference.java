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
public final class Preference {

	/**
	 * Select language
	 */
	// TODO
	public static final String lang = "Language";

	/**
	 * Common
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class Common {
		/**
		 * Description
		 */
		public static final String description = "Settings for JUnit Helper plugin.";
		/**
		 * Main Source Folder
		 */
		public static final String srcMainPath = "Main Source Folder";
		/**
		 * Test Source Folder
		 */
		public static final String srcTestPath = "Test Source Folder";
	}

	public static class TestClassGen {

		/**
		 * Enable test methods gen
		 */
		public static final String enabled = "Test Class Auto Generate";
		/**
		 * JUnit versions
		 */
		public static final String junitVersion = "JUnit Version";
		/**
		 * JUnit version 3.x
		 */
		public static final String junitVersion3 = "JUnit 3.x";
		/**
		 * JUnit version 4.x
		 */
		public static final String junitVersion4 = "JUnit 4.x";
		/**
		 * Class To Extend
		 */
		public static final String classToExtend = "Class To Extend";
		/**
		 * Using JUnit Helper runtime library
		 */
		public static final String usingJunitHelperRuntimeLib = "Using JUnit Helper Runtime Library.";
	}

	public static class TestMethodGen {
		/**
		 * Enable test methods gen
		 */
		public static final String enabled = "Test Method Auto Generate";
		/**
		 * Delimiter to test method name
		 */
		public static final String delimiter = " Method Name Delimiter";

		/**
		 * Enable includes method args in test method name
		 */
		public static final String enabledArgs = " Includes Method Args";
		/**
		 * Args prefix
		 */
		public static final String argsPrefix = "Args Prefix";
		/**
		 * Args delimiter
		 */
		public static final String argsDelimiter = "Args Delimiter";

		/**
		 * Enable includes method return in test method name
		 */
		public static final String enabledReturn = " Includes Method Return";
		/**
		 * Return prefix
		 */
		public static final String returnPrefix = "Return Prefix";
		/**
		 * Return delimiter
		 */
		public static final String returnDelimiter = "Return Delimiter";

		/**
		 * Enable includes exceptions that the method throws
		 */
		public static final String enabledException = " Includes Exceptions Thrown";
		/**
		 * Return prefix
		 */
		public static final String exceptionPrefix = "Exception Prefix";
		/**
		 * Return delimiter
		 */
		public static final String exceptionDelimiter = "Exception Delimiter";

		/**
		 * Public methods
		 */
		public static final String includePublic = " Includes \"public\"";

		/**
		 * Protected methods
		 */
		public static final String includeProtected = " Includes \"protected\"";

		/**
		 * Package local methods
		 */
		public static final String includePackageLocal = " Includes \"package local\"";

		/**
		 * Exclude accessors
		 */
		public static final String excludesAccessors = " Exculdes Accessors(setter/getter)";

		/**
		 * Generate sample implementation to test methods
		 */
		public static final String enabledTestMethodSampleImpl = " Generate Sample Implementation";

		/**
		 * Using mock object framework
		 */
		public static final String usingMock = "Mock";
		/**
		 * None
		 */
		public static final String usingMockNone = "None";
		/**
		 * EasyMock
		 */
		public static final String usingMockEasyMock = "EasyMock";
		/**
		 * JMock2
		 */
		public static final String usingMockJMock2 = "JMock2";
		/**
		 * Mockito
		 */
		public static final String usingMockMockito = "Mockito(BDD)";
		/**
		 * JMockit
		 */
		public static final String usingMockJMockit = "JMockit";

	}

}
