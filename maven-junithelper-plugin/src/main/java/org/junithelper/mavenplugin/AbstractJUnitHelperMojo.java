package org.junithelper.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.junithelper.core.Version;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.config.TestingPatternExplicitComment;
import org.junithelper.core.util.Stdout;

public abstract class AbstractJUnitHelperMojo extends AbstractMojo {

	/**
	 * @parameter
	 */
	protected String language = "en";
	/**
	 * @parameter
	 */
	protected String outputFileEncoding = "UTF-8";

	/**
	 * @parameter
	 */
	protected String directoryPathOfProductSourceCode = "src/main/java";
	/**
	 * @parameter
	 */
	protected String directoryPathOfTestSourceCode = "src/test/java";

	/**
	 * @parameter
	 */
	protected String lineBreakPolicy = "forceNewFileCRLF";

	/**
	 * @parameter
	 */
	protected boolean useSoftTabs = false;

	/**
	 * @parameter
	 */
	protected String softTabSize = "4";

	/**
	 * @parameter
	 */
	protected String junitVersion = "version4";
	/**
	 * @parameter
	 */
	protected String testCaseClassNameToExtend = "junit.framework.TestCase";

	/**
	 * @parameter
	 */
	protected boolean isTemplateImplementationRequired = true;

	/**
	 * @parameter
	 */
	protected boolean target_isAccessorExcluded = true;
	/**
	 * @parameter
	 */
	protected boolean target_isExceptionPatternRequired = false;
	/**
	 * @parameter
	 */
	protected boolean target_isPackageLocalMethodRequired = true;
	/**
	 * @parameter
	 */
	protected boolean target_isProtectedMethodRequired = true;
	/**
	 * @parameter
	 */
	protected boolean target_isPublicMethodRequired = true;
	/**
	 * @parameter
	 */
	protected String target_regexpCsvForExclusion = "";

	/**
	 * @parameter
	 */
	protected boolean testMethodName_isArgsRequired = true;
	/**
	 * @parameter
	 */
	protected boolean testMethodName_isReturnRequired = false;

	/**
	 * @parameter
	 */
	protected String testMethodName_basicDelimiter = "_";
	/**
	 * @parameter
	 */
	protected String testMethodName_argsAreaPrefix = "A";
	/**
	 * @parameter
	 */
	protected String testMethodName_argsAreaDelimiter = "$";
	/**
	 * @parameter
	 */
	protected String testMethodName_returnAreaPrefix = "R";
	/**
	 * @parameter
	 */
	protected String testMethodName_returnAreaDelimiter = "$";
	/**
	 * @parameter
	 */
	protected String testMethodName_exceptionAreaPrefix = "T";
	/**
	 * @parameter
	 */
	protected String testMethodName_exceptionAreaDelimiter = "$";

	/**
	 * @parameter
	 */
	protected String mockObjectFramework = "";

	/**
	 * @parameter
	 */
	protected String testingPatternExplicitComment = "";

	/**
	 * @parameter
	 */
	protected boolean isExtensionEnabled = false;

	/**
	 * @parameter
	 */
	protected String extensionConfigXML = "junithelper-extension.xml";

	protected Configuration loadConfig() {
		Configuration config = new Configuration();
		config.language = language;
		config.outputFileEncoding = outputFileEncoding;
		config.directoryPathOfProductSourceCode = directoryPathOfProductSourceCode;
		config.directoryPathOfTestSourceCode = directoryPathOfTestSourceCode;
		try {
			config.lineBreakPolicy = LineBreakPolicy.valueOf(lineBreakPolicy);
		} catch (Exception e) {
			config.lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;
		}
		config.useSoftTabs = useSoftTabs;
		config.softTabSize = Integer.valueOf(softTabSize);
		try {
			config.junitVersion = JUnitVersion.valueOf(junitVersion);
		} catch (Exception e) {
			config.junitVersion = JUnitVersion.version4;
		}
		config.testCaseClassNameToExtend = testCaseClassNameToExtend;
		config.isTemplateImplementationRequired = isTemplateImplementationRequired;
		config.target.isAccessorExcluded = target_isAccessorExcluded;
		config.target.isExceptionPatternRequired = target_isExceptionPatternRequired;
		config.target.isPackageLocalMethodRequired = target_isPackageLocalMethodRequired;
		config.target.isProtectedMethodRequired = target_isProtectedMethodRequired;
		config.target.isPublicMethodRequired = target_isPublicMethodRequired;
		config.target.regexpCsvForExclusion = target_regexpCsvForExclusion;
		config.testMethodName.isArgsRequired = testMethodName_isArgsRequired;
		config.testMethodName.isReturnRequired = testMethodName_isReturnRequired;
		config.testMethodName.basicDelimiter = testMethodName_basicDelimiter;
		config.testMethodName.argsAreaPrefix = testMethodName_argsAreaPrefix;
		config.testMethodName.argsAreaDelimiter = testMethodName_argsAreaDelimiter;
		config.testMethodName.returnAreaPrefix = testMethodName_returnAreaPrefix;
		config.testMethodName.returnAreaDelimiter = testMethodName_returnAreaDelimiter;
		config.testMethodName.exceptionAreaPrefix = testMethodName_exceptionAreaPrefix;
		config.testMethodName.exceptionAreaDelimiter = testMethodName_exceptionAreaDelimiter;
		try {
			config.mockObjectFramework = MockObjectFramework
					.valueOf(mockObjectFramework);
		} catch (Exception e) {
			config.mockObjectFramework = null;
		}
		try {
			config.testingPatternExplicitComment = TestingPatternExplicitComment
					.valueOf(testingPatternExplicitComment);
		} catch (Exception e) {
			config.testingPatternExplicitComment = TestingPatternExplicitComment.None;
		}
		config.isExtensionEnabled = isExtensionEnabled;
		config.extensionConfigXML = extensionConfigXML;
		return config;
	}

	protected static void printLogoAndVersion() {
		Stdout.p("  _                         ");
		Stdout.p("   /   _  ._/_/_/_  /_  _  _");
		Stdout.p("(_//_// // / / //_'//_//_'/ ");
		Stdout.p("                   /        ");
		Stdout.p("JUnit Helper version " + Version.get());
		Stdout.p("");
	}

}
