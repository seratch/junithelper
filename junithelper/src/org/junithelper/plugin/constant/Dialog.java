package org.junithelper.plugin.constant;

public class Dialog {

	public static class Common {
		/**
		 * Title
		 */
		public static final String TITLE = "JUnit Helper";

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
