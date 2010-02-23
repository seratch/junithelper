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
public final class Dialog {

	public static class Common {
		/**
		 * Title
		 */
		public static final String title = "JUnit Helper";

		/**
		 * Invalid preference error
		 */
		public static final String invalidPreference = "Maybe invalid preference, please check again!";

		/**
		 * Confirm to execute
		 */
		public static final String confirmToExecute = "Execute OK?";
		/**
		 * Select required
		 */
		public static final String required = "Please select target.";
		/**
		 * Select only one
		 */
		public static final String selectOneOnly = "Please select only one target.";

		/**
		 * Already file exists
		 */
		public static final String alreadyExist = "Already exist.";

		/**
		 * File does not exist
		 */
		public static final String notExist = "Not exist.";

		/**
		 * Confirm to proceed
		 */
		public static final String confirmToProceed = "Proceed OK?";

		/**
		 * Setting not to create new file
		 */
		public static final String notToCreateNewFilePreference = "Test case file does not exist."
				+ StrConst.lineFeed
				+ "If you want to generate, please alter preferences.";

		/**
		 * Confirm to create new file
		 */
		public static final String confirmToCreateNewFile = "Create new test case file?";

		/**
		 * Error occurred when refreshing resource
		 */
		public static final String resourceRefreshError = "Resource refresh error!";

		/**
		 * Select java extension file
		 */
		public static final String selectJavaFile = "Please select java source file(*.java).";

		/**
		 * Select Test.java extension file
		 */
		public static final String notTestClass = "Please select test class file.";

	}

}
