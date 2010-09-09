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

	private static final String common = "Dialog.Common.";

	public static class Common {
		/**
		 * Title
		 */
		public static final String title = common + "title";

		/**
		 * Invalid preference error
		 */
		public static final String invalidPreference = common
				+ "invalidPreference";

		/**
		 * Confirm to execute
		 */
		public static final String confirmToExecute = common
				+ "confirmToExecute";
		/**
		 * Select required
		 */
		public static final String required = common + "required";
		/**
		 * Select only one
		 */
		public static final String selectOneOnly = common + "selectOneOnly";

		/**
		 * Already file exists
		 */
		public static final String alreadyExist = common + "alreadyExist";

		/**
		 * File does not exist
		 */
		public static final String notExist = common + "notExist";

		/**
		 * Confirm to proceed
		 */
		public static final String confirmToProceed = common
				+ "confirmToProceed";

		/**
		 * Setting not to create new file
		 */
		public static final String notToCreateNewFilePreference = common
				+ "notToCreateNewFilePreference";

		/**
		 * Confirm to create new file
		 */
		public static final String confirmToCreateNewFile = common
				+ "confirmToCreateNewFile";

		/**
		 * Error occurred when refreshing resource
		 */
		public static final String resourceRefreshError = common
				+ "resourceRefreshError";

		/**
		 * Select java extension file
		 */
		public static final String selectJavaFile = common + "selectJavaFile";

		/**
		 * Select Test.java extension file
		 */
		public static final String notTestClass = common + "notTestClass";

	}

}
