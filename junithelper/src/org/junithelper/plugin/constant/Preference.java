package org.junithelper.plugin.constant;

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
		 * Class To Extend
		 */
		public static final String CLASS_TO_EXTEND = "Class To Extend";
	}

	public static class TestMethodGen {
		/**
		 * Enable test methods gen
		 */
		public static final String ENABLE = "Test Method Auto Generate";
		/**
		 * Delimiter to test method name
		 */
		public static final String DELIMITER = "Method Name Delimiter";

		/**
		 * Enable includes method args in test method name
		 */
		public static final String ARGS = "Includes Method Args";
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
		public static final String RETURN = "Includes Method Return";
		/**
		 * Return prefix
		 */
		public static final String RETURN_PREFIX = "Return Prefix";
		/**
		 * Return delimiter
		 */
		public static final String RETURN_DELIMITER = "Return Delimiter";

		/**
		 * Exclude accessors
		 */
		public static final String EXLCUDES_ACCESSORS = "Exculdes Accessors(setter/getter)";

		/**
		 * Generate sample implementation to test methods
		 */
		public static final String METHOD_SAMPLE_IMPLEMENTATION = "Generate Sample Implementation";

		/**
		 * Using mock object framework
		 */
		public static final String USING_MOCK = "Mock";
		public static final String USING_MOCK_NONE = "None";
		public static final String USING_MOCK_EASYMOCK = "EasyMock";
		public static final String USING_MOCK_JMOCK2 = "JMock2";

	}

}
