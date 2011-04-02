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
package org.junithelper.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.junithelper.command.MakeTestCommand;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.MockObjectFramework;

/**
 * Making all tests
 *
 * @goal makeall
 * @phase process-sources
 */
public class MakeallMojo extends AbstractMojo {

    /**
     * @parameter
     */
    private String language = "en";
    /**
     * @parameter
     */
    private String outputFileEncoding = "UTF-8";

    /**
     * @parameter
     */
    private String directoryPathOfProductSourceCode = "src/main/java";
    /**
     * @parameter
     */
    private String directoryPathOfTestSourceCode = "src/test/java";

    /**
     * @parameter
     */
    private String junitVersion = "version4";
    /**
     * @parameter
     */
    private String testCaseClassNameToExtend = "junit.framework.TestCase";

    /**
     * @parameter
     */
    private boolean isTemplateImplementationRequired = true;

    /**
     * @parameter expression="${target.isAccessorExcluded}"
     */
    private boolean target_isAccessorExcluded = true;
    /**
     * @parameter expression="${target.isExceptionPatternRequired}"
     */
    private boolean target_isExceptionPatternRequired = false;
    /**
     * @parameter expression="${target.isPackageLocalMethodRequired}"
     */
    private boolean target_isPackageLocalMethodRequired = true;
    /**
     * @parameter expression="${target.isProtectedMethodRequired}"
     */
    private boolean target_isProtectedMethodRequired = true;
    /**
     * @parameter expression="${target.isPublicMethodRequired}"
     */
    private boolean target_isPublicMethodRequired = true;

    /**
     * @parameter expression="${testMethodName.isArgsRequired}"
     */
    private boolean testMethodName_isArgsRequired = true;
    /**
     * @parameter expression="${testMethodName.isReturnRequired}"
     */
    private boolean testMethodName_isReturnRequired = false;

    /**
     * @parameter expression="${testMethodName.basicDelimiter}"
     */
    private String testMethodName_basicDelimiter = "_";
    /**
     * @parameter expression="${testMethodName.argsAreaPrefix}"
     */
    private String testMethodName_argsAreaPrefix = "A";
    /**
     * @parameter expression="${testMethodName.argsAreaDelimiter}"
     */
    private String testMethodName_argsAreaDelimiter = "$";
    /**
     * @parameter expression="${testMethodName.returnAreaPrefix}"
     */
    private String testMethodName_returnAreaPrefix = "R";
    /**
     * @parameter expression="${testMethodName.returnAreaDelimiter}"
     */
    private String testMethodName_returnAreaDelimiter = "$";
    /**
     * @parameter expression="${testMethodName.exceptionAreaPrefix}"
     */
    private String testMethodName_exceptionAreaPrefix = "T";
    /**
     * @parameter expression="${testMethodName.exceptionAreaDelimiter}"
     */
    private String testMethodName_exceptionAreaDelimiter = "$";

    /**
     * @parameter
     */
    private String mockObjectFramework = "";

    @Override
    public void execute() throws MojoExecutionException {

        try {
            Configulation config = new Configulation();
            config.language = language;
            config.outputFileEncoding = outputFileEncoding;
            config.directoryPathOfProductSourceCode = directoryPathOfProductSourceCode;
            config.directoryPathOfTestSourceCode = directoryPathOfTestSourceCode;
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
                config.mockObjectFramework = MockObjectFramework.valueOf(mockObjectFramework);
            } catch (Exception e) {
                config.mockObjectFramework = null;
            }
            MakeTestCommand.config = config;
            MakeTestCommand.main(new String[]{});
        } catch (Exception e) {
            throw new MojoExecutionException("junithelper make error!", e);
        }
    }
}
