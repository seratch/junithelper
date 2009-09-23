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
	public static final String SRC_MAIN_JAVA = Activator.getDefault()
			.getPreferenceStore().getString(STR.Preference.Common.SRC_MAIN_PATH);
	public static final String SRC_TEST_JAVA = Activator.getDefault()
			.getPreferenceStore().getString(STR.Preference.Common.SRC_TEST_PATH);

	public static class RegExp
	{

		public static final String DOT = "\\.";
		public static final String NOT_S_NOREQUIRED = "[^\\s]*?";
		public static final String NOT_S_REQUIRED = "[^\\s]+?";
		public static final String WHITE_AND_SPACE = "\\s*";
		public static final String JAVA_EXP = "\\.java";

	}

	public static class Dialog
	{
		public static class Common
		{
			public static final String TITLE = "JUnit Helper";
			public static final String EXCUTE_QUESTION = "Execute OK?";
			public static final String REQUIRED = "Please select target.";
			public static final String SELECT_ONLY_ONE = "Please select only one target.";
			public static final String ALREADY_EXIST = "Already exist.";
			public static final String NOT_EXIST = "Not exist.";
			public static final String CONFIRM_PROCEED = "Proceed OK?";
			public static final String COMFIRM_CREATE_NEW_FILE = "Create new test case file?";
			public static final String RESOURCE_REFRESH_ERROR = "Resource refresh error!";
		}

		public static class TestCase
		{
			public static final String SELECT_JAVA_FILE = "Please select java source file(*.java).";
			public static final String NOT_TEST_CLASS = "Please select test class file.";
		}

	}

	public static class Preference
	{

		public static final String LANG = "Language";

		public static class Common
		{
			public static final String DESCRIPTION = "JUnit Helper Prefernces.";
			public static final String SRC_MAIN_PATH = "Main Source Folder";
			public static final String SRC_TEST_PATH = "Test Source Folder";
		}

		public static class TestMethodAutoGenerate
		{
			public static final String ENABLE = "Test Method Auto Generate";
			public static final String DELIMITER = "Delimiter";

			public static final String ARGS = "Includes Method Args";
			public static final String ARGS_PREFIX = "Args Prefix";
			public static final String ARGS_DELIMITER = "Args Delimiter";

			public static final String RETURN = "Includes Method Return";
			public static final String RETURN_PREFIX = "Return Prefix";
			public static final String RETURN_DELIMITER = "Return Delimiter";
		}

	}

}
