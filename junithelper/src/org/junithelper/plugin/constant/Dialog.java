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
 * Constant for dialogs<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class Dialog {

	public static class Common {
		/**
		 * Title
		 */
		public static final String TITLE = "JUnit Helper";

		/**
		 * Invalid preference error
		 */
		public static final String INVALID_PREFERENCE = "Maybe invalid preference, please check again!";

		/**
		 * Confirm to execute
		 */
		public static final String EXCUTE_QUESTION = "Execute OK?";
		/**
		 * Select required
		 */
		public static final String REQUIRED = "Please select target.";
		/**
		 * Select only one
		 */
		public static final String SELECT_ONLY_ONE = "Please select only one target.";

		/**
		 * Already file exists
		 */
		public static final String ALREADY_EXIST = "Already exist.";

		/**
		 * File does not exist
		 */
		public static final String NOT_EXIST = "Not exist.";

		/**
		 * Confirm to proceed
		 */
		public static final String CONFIRM_PROCEED = "Proceed OK?";

		/**
		 * Setting not to create new file
		 */
		public static final String NOT_CREATE_NEW_FILE = "Test case file does not exist."
				+ STR.LINE_FEED
				+ "If you want to generate, please alter preferences.";

		/**
		 * Confirm to create new file
		 */
		public static final String COMFIRM_CREATE_NEW_FILE = "Create new test case file?";

		/**
		 * Error occurred when refreshing resource
		 */
		public static final String RESOURCE_REFRESH_ERROR = "Resource refresh error!";

		/**
		 * Select java extension file
		 */
		public static final String SELECT_JAVA_FILE = "Please select java source file(*.java).";

		/**
		 * Select Test.java extension file
		 */
		public static final String NOT_TEST_CLASS = "Please select test class file.";

	}

}
