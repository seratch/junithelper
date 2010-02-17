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
	public static final String LANG = "Language";

	/**
	 * Common
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class Common {
		/**
		 * Description
		 */
		public static final String DESCRIPTION = "Settings for JUnit Helper plugin.";
		/**
		 * Main Source Folder
		 */
		public static final String SRC_MAIN_PATH = "Main Source Folder";
		/**
		 * Test Source Folder
		 */
		public static final String SRC_TEST_PATH = "Test Source Folder";
	}

	public static class TestClassGen {

		/**
		 * Enable test methods gen
		 */
		public static final String ENABLE = "Test Class Auto Generate";
		/**
		 * JUnit versions
		 */
		public static final String JUNIT_VERSION = "JUnit Version";
		/**
		 * JUnit version 3.x
		 */
		public static final String JUNIT_VERSION_3 = "JUnit 3.x";
		/**
		 * JUnit version 4.x
		 */
		public static final String JUNIT_VERSION_4 = "JUnit 4.x";
		/**
		 * Class To Extend
		 */
		public static final String CLASS_TO_EXTEND = "Class To Extend";
		/**
		 * Using JUnit Helper runtime library
		 */
		public static final String USING_JUNIT_HELPER_RUNTIME_LIB = "Using JUnit Helper Runtime Library.";
	}

	public static class TestMethodGen {
		/**
		 * Enable test methods gen
		 */
		public static final String ENABLE = "Test Method Auto Generate";
		/**
		 * Delimiter to test method name
		 */
		public static final String DELIMITER = " Method Name Delimiter";

		/**
		 * Enable includes method args in test method name
		 */
		public static final String ARGS = " Includes Method Args";
		/**
		 * Args prefix
		 */
		public static final String ARGS_PREFIX = "Args Prefix";
		/**
		 * Args delimiter
		 */
		public static final String ARGS_DELIMITER = "Args Delimiter";

		/**
		 * Enable includes method return in test method name
		 */
		public static final String RETURN = " Includes Method Return";
		/**
		 * Return prefix
		 */
		public static final String RETURN_PREFIX = "Return Prefix";
		/**
		 * Return delimiter
		 */
		public static final String RETURN_DELIMITER = "Return Delimiter";

		/**
		 * Public methods
		 */
		public static final String INCLUDE_PUBLIC = " Includes \"public\"";

		/**
		 * Protected methods
		 */
		public static final String INCLUDE_PROTECTED = " Includes \"protected\"";

		/**
		 * Package local methods
		 */
		public static final String INCLUDE_PACKAGE_LOCAL = " Includes \"package local\"";

		/**
		 * Exclude accessors
		 */
		public static final String EXLCUDES_ACCESSORS = " Exculdes Accessors(setter/getter)";

		/**
		 * Generate sample implementation to test methods
		 */
		public static final String METHOD_SAMPLE_IMPL = " Generate Sample Implementation";

		/**
		 * Using mock object framework
		 */
		public static final String USING_MOCK = "Mock";
		public static final String USING_MOCK_NONE = "None";
		public static final String USING_MOCK_EASYMOCK = "EasyMock";
		public static final String USING_MOCK_JMOCK2 = "JMock2";

	}

}
