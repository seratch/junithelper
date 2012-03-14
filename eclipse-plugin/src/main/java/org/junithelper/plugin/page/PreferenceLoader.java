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
package org.junithelper.plugin.page;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.LineBreakPolicy;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.config.TestingPatternExplicitComment;
import org.junithelper.plugin.Activator;
import org.junithelper.plugin.constant.Preference;

public class PreferenceLoader {

    public String directoryPathOfProductSourceCode;
    public String directoryPathOfTestSourceCode;

    public String outputFileEncoding;

    public String lineBreakPolicy;

    public boolean useSoftTabs;
    public int softTabSize;

    public boolean isJUnitVersion3;
    public boolean isJUnitVersion4;

    public boolean isTemplateImplementationRequired;

    public boolean isAccessorExcluded;
    public boolean isExceptionPatternRequired;

    public boolean isTestMethodNameArgsRequired;
    public boolean isTestMethodNameReturnRequired;

    public boolean isMockJMock2;
    public boolean isMockEasyMock;
    public boolean isMockMockito;
    public boolean isMockJMockit;

    public boolean isArrangeActAssertCommentsRequired;
    public boolean isGivenWhenThenCommentsRequired;

    public boolean isPublicRequired;
    public boolean isProtectedRequired;
    public boolean isPackageLocalRequired;

    public String testMethodDelimiter;
    public String testMethodArgsPrefix;
    public String testMethodArgsDelimiter;
    public String testMethodReturnPrefix;
    public String testMethodReturnDelimiter;
    public String testMethodExceptionPrefix;
    public String testMethodExceptionDelimiter;

    public String classToExtend;

    private Configuration config = new Configuration();

    public Configuration getConfig() {
        config.language = Activator.getDefault().getPreferenceStore().getString(Preference.lang);
        config.outputFileEncoding = outputFileEncoding;
        {
            String[] arr = lineBreakPolicy.split(".");
            if (arr != null && arr.length > 0) {
                config.lineBreakPolicy = LineBreakPolicy.valueOf(arr[arr.length - 1]);
            } else {
                config.lineBreakPolicy = LineBreakPolicy.forceNewFileCRLF;
            }
        }
        config.useSoftTabs = useSoftTabs;
        config.softTabSize = softTabSize;
        config.directoryPathOfProductSourceCode = directoryPathOfProductSourceCode;
        config.directoryPathOfTestSourceCode = directoryPathOfTestSourceCode;
        config.isTemplateImplementationRequired = isTemplateImplementationRequired;
        config.junitVersion = isJUnitVersion3 ? JUnitVersion.version3 : JUnitVersion.version4;
        if (isMockEasyMock) {
            config.mockObjectFramework = MockObjectFramework.EasyMock;
        }
        if (isMockJMock2) {
            config.mockObjectFramework = MockObjectFramework.JMock2;
        }
        if (isMockJMockit) {
            config.mockObjectFramework = MockObjectFramework.JMockit;
        }
        if (isMockMockito) {
            config.mockObjectFramework = MockObjectFramework.Mockito;
        }
        config.target.isAccessorExcluded = isAccessorExcluded;
        config.target.isExceptionPatternRequired = isExceptionPatternRequired;
        config.target.isPackageLocalMethodRequired = isPackageLocalRequired;
        config.target.isProtectedMethodRequired = isProtectedRequired;
        config.target.isPublicMethodRequired = isPublicRequired;
        config.testCaseClassNameToExtend = classToExtend;
        config.testMethodName.argsAreaDelimiter = testMethodArgsDelimiter;
        config.testMethodName.argsAreaPrefix = testMethodArgsPrefix;
        config.testMethodName.basicDelimiter = testMethodDelimiter;
        config.testMethodName.exceptionAreaDelimiter = testMethodExceptionDelimiter;
        config.testMethodName.exceptionAreaPrefix = testMethodExceptionPrefix;
        config.testMethodName.isArgsRequired = isTestMethodNameArgsRequired;
        config.testMethodName.isReturnRequired = isTestMethodNameReturnRequired;
        config.testMethodName.returnAreaDelimiter = testMethodReturnDelimiter;
        config.testMethodName.returnAreaPrefix = testMethodReturnPrefix;
        if (isArrangeActAssertCommentsRequired) {
            config.testingPatternExplicitComment = TestingPatternExplicitComment.ArrangeActAssert;
        }
        if (isGivenWhenThenCommentsRequired) {
            config.testingPatternExplicitComment = TestingPatternExplicitComment.GivenWhenThen;
        }
        return config;
    }

    public PreferenceLoader(IPreferenceStore store) {
        initialize(store);
    }

    public void initialize(IPreferenceStore store) {

        // loading preference store
        if (store == null) {
            store = Activator.getDefault().getPreferenceStore();
        }

        // source code directory
        directoryPathOfProductSourceCode = store.getString(Preference.Common.srcMainPath);
        directoryPathOfTestSourceCode = store.getString(Preference.Common.srcTestPath);
        outputFileEncoding = store.getString(Preference.Common.outputFileEncoding);

        lineBreakPolicy = store.getString(Preference.Common.lineBreakPolicy);
        useSoftTabs = store.getBoolean(Preference.Common.useSoftTabs);
        softTabSize = store.getInt(Preference.Common.softTabSize);

        isTestMethodNameArgsRequired = store.getBoolean(Preference.TestMethodGen.enabledArgs);
        isTestMethodNameReturnRequired = store.getBoolean(Preference.TestMethodGen.enabledReturn);
        isExceptionPatternRequired = store.getBoolean(Preference.TestMethodGen.enabledException);

        // test method template implementation gen
        isTemplateImplementationRequired = store.getBoolean(Preference.TestMethodGen.enabledTestMethodSampleImpl);

        // access modifier
        isPublicRequired = store.getBoolean(Preference.TestMethodGen.includePublic);
        isProtectedRequired = store.getBoolean(Preference.TestMethodGen.includeProtected);
        isPackageLocalRequired = store.getBoolean(Preference.TestMethodGen.includePackageLocal);

        // accessors
        isAccessorExcluded = store.getBoolean(Preference.TestMethodGen.excludesAccessors);

        // mock object frameworks
        isMockJMock2 = isUsingJMock2(store);
        isMockEasyMock = isUsingEasyMock(store);
        isMockMockito = isUsingMockito(store);
        isMockJMockit = isUsingJMockit(store);

        // testing pattern comments
        isArrangeActAssertCommentsRequired = isCommentsArrangeActAssert(store);
        isGivenWhenThenCommentsRequired = isCommentsGivenWhenThen(store);

        // junit version
        String version = store.getString(Preference.TestClassGen.junitVersion);
        isJUnitVersion3 = version.equals(Preference.TestClassGen.junitVersion3);
        isJUnitVersion4 = version.equals(Preference.TestClassGen.junitVersion4);

        // test method signature
        testMethodDelimiter = store.getString(Preference.TestMethodGen.delimiter);
        testMethodArgsPrefix = store.getString(Preference.TestMethodGen.argsPrefix);
        testMethodArgsDelimiter = store.getString(Preference.TestMethodGen.argsDelimiter);
        testMethodReturnPrefix = store.getString(Preference.TestMethodGen.returnPrefix);
        testMethodReturnDelimiter = store.getString(Preference.TestMethodGen.returnDelimiter);
        testMethodExceptionPrefix = store.getString(Preference.TestMethodGen.exceptionPrefix);
        testMethodExceptionDelimiter = store.getString(Preference.TestMethodGen.exceptionDelimiter);

        // class to extend
        classToExtend = store.getString(Preference.TestClassGen.classToExtend);

    }

    static final boolean isUsingEasyMock(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingMock);
        return Preference.TestMethodGen.usingMockEasyMock.equals(setting);
    }

    static final boolean isUsingJMock2(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingMock);
        return Preference.TestMethodGen.usingMockJMock2.equals(setting);
    }

    static final boolean isUsingMockito(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingMock);
        return Preference.TestMethodGen.usingMockMockito.equals(setting);
    }

    static final boolean isUsingJMockit(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingMock);
        return Preference.TestMethodGen.usingMockJMockit.equals(setting);
    }

    static final boolean isCommentsArrangeActAssert(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingTestingPatternComments);
        return Preference.TestMethodGen.commentsArrangeActAssert.equals(setting);
    }

    static final boolean isCommentsGivenWhenThen(IPreferenceStore store) {
        String setting = store.getString(Preference.TestMethodGen.usingTestingPatternComments);
        return Preference.TestMethodGen.commentsGivenWhenThen.equals(setting);
    }

}
