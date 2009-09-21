package org.junithelper.plugin;

public interface STR
{

	public static final String WINDOWS_DIR_SEP = "\\\\";

	public static final String DIR_SEP = "/";
	public static final String EMPTY = "";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String ASTERISK = "*";

	public static final String CARRIAGE_RETURN = "\r";
	public static final String LINE_FEED = "\n";

	public static final String JAVA_EXP = ".java";
	public static final String PROPERTIES_EXP = ".properties";

	public static final String SUFFIX_OF_TESTCASE = "Test";
	public static final String SRC_MAIN_JAVA = "src/main/java";
	public static final String SRC_TEST_JAVA = "src/test/java";

	public static class RegExp
	{

		public static final String DOT = "\\.";
		public static final String JAVA_EXP = "\\.java";

	}

	public static class Dialog
	{
		public static class Common
		{
			public static final String TITLE = "JUnit Helper";
			public static final String EXCUTE_QUESTION = "Execute OK?";
			public static final String EXECUTE_AFTER_SETTING = "Execute after setting.";
			public static final String NORMAL_END = "Normal end.";
			public static final String ABNORMAL_END = "Error end.";
			public static final String REQUIRED = "Please select command target.";
			public static final String SELECT_ONLY_ONE = "Please select only one target.";
			public static final String ALREADY_EXIST = "Already exist";
			public static final String NOT_EXIST = "Not exist.";
			public static final String COMFIRM_CREATE_NEW_FILE = "New file create?";
			public static final String RESOURCE_REFRESH_ERROR = "Resource refresh error!";
		}

		public static class TestCase
		{
			public static final String SELECT_JAVA_FILE = "Please select *.java file.";
			public static final String NOT_TEST_CLASS = "Please select test class file.";
			public static final String ALREAY_OPEN_TEST_CLASS = "This is a test class.";
		}

	}

	public static class Preference
	{

		public static final String LANG = "lang";

		public static class Common
		{
			public static final String DESCRIPTION = "JUnit Helper Prefernce";
		}
	}

}
