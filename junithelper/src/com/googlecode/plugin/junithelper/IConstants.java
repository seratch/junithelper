package com.googlecode.plugin.junithelper;

public interface IConstants
{
	public static final String ENCODING = "encoding";
	public static final String WINDOWS_DIR_SEPARATOR = "\\\\";
	public static final String DIR_SEPARATOR = "/";
	public static final String EMPTY_STIRNG = "";
	public static final String COMMA_STIRNG = ",";
	public static final String CARRIAGE_RETURN = "\r";
	public static final String LINE_FEED = "\n";
	public static final String ENCODING_UTF_8 = "UTF-8";
	public static final String JAVA_EXP = ".java";
	public static final String PROPERTIES_EXP = ".properties";

	public static class RegExp
	{

		public static final String JAVA_EXP = "\\.java";

	}

	public static class Dialog
	{
		public static class Common
		{
			public static final String TITLE = "JUnit Helper";
			public static final String EXCUTE_QUESTION = "実行してもよろしいですか？";
			public static final String EXECUTE_AFTER_SETTING = "設定後に再度、実行して下さい。";
			public static final String NORMAL_END = "正常に完了しました。";
			public static final String ABNORMAL_END = "異常終了しました。";
			public static final String REQUIRED = "対象を選択して下さい。";
			public static final String SELECT_ONLY_ONE = "選択できる対象は1件のみです。";
			public static final String ALREADY_EXIST = "は既に存在しています。";
			public static final String NOT_EXIST = "は存在しません。";
			public static final String COMFIRM_CREATE_NEW_FILE = "新しく作成しますか？";
			public static final String RESOURCE_SYNC_SERVER_NOT_RUNNING = "ResourceSynchronizerがインストールされていない、もしくは同期サーバが起動していません。";
		}

		public static class TestCase
		{
			public static final String SELECT_JAVA_FILE = "このコマンドはJavaファイルを選択してから実行して下さい。";
			public static final String NOT_TEST_CLASS = "このコマンドはテストクラスを選択して実行して下さい。";
			public static final String ALREAY_OPEN_TEST_CLASS = "既にテストクラスを開いています。";
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
