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

public class Preference {

    public static final String lang = "Preference.lang";

    public static class Lang {

        public static final String English = "en";

        public static final String EnglishLabel = "English";

        public static final String Japanese = "ja";

        public static final String JapaneseLabel = "Japanese";
    }

    public static class Common {

        public static final String description = "Preference.Common.description";

        public static final String outputFileEncoding = "Preference.Common.outputFileEncoding";

        public static final String srcMainPath = "Preference.Common.srcMainPath";

        public static final String srcTestPath = "Preference.Common.srcTestPath";

        public static final String lineBreakPolicy = "Preference.Common.lineBreakPolicy";

        public static final String useSoftTabs = "Preference.Common.useSoftTabs";

        public static final String softTabSize = "Preference.Common.softTabSize";

    }

    public static class LineBreakPolicy {

        public static final String description = "Preference.Common.lineBreakPolicy.description";

        public static final String forceCRLF = "Preference.Common.lineBreakPolicy.forceCRLF";

        public static final String forceLF = "Preference.Common.lineBreakPolicy.forceLF";

        public static final String forceNewFileCRLF = "Preference.Common.lineBreakPolicy.forceNewFileCRLF";

        public static final String forceNewFileLF = "Preference.Common.lineBreakPolicy.forceNewFileLF";

    }

    public static class TestClassGen {

        public static final String description = "Preference.TestClassGen.description";

        public static final String junitVersion = "Preference.TestClassGen.junitVersion";

        public static final String junitVersion3 = "Preference.TestClassGen.junitVersion3";

        public static final String junitVersion4 = "Preference.TestClassGen.junitVersion4";

        public static final String classToExtend = "Preference.TestClassGen.classToExtend";

    }

    public static class TestMethodGen {

        public static final String delimiter = "Preference.TestMethodGen.delimiter";

        public static final String enabledArgs = "Preference.TestMethodGen.enabledArgs";

        public static final String argsPrefix = "Preference.TestMethodGen.argsPrefix";

        public static final String argsDelimiter = "Preference.TestMethodGen.argsDelimiter";

        public static final String enabledReturn = "Preference.TestMethodGen.enabledReturn";

        public static final String returnPrefix = "Preference.TestMethodGen.returnPrefix";

        public static final String returnDelimiter = "Preference.TestMethodGen.returnDelimiter";

        public static final String enabledException = "Preference.TestMethodGen.enabledException";

        public static final String exceptionPrefix = "Preference.TestMethodGen.exceptionPrefix";

        public static final String exceptionDelimiter = "Preference.TestMethodGen.exceptionDelimiter";

        public static final String includePublic = "Preference.TestMethodGen.includePublic";

        public static final String includeProtected = "Preference.TestMethodGen.includeProtected";

        public static final String includePackageLocal = "Preference.TestMethodGen.includePackageLocal";

        public static final String excludesAccessors = "Preference.TestMethodGen.excludesAccessors";

        public static final String enabledTestMethodSampleImpl = "Preference.TestMethodGen.enabledTestMethodSampleImpl";

        public static final String usingMock = "Preference.TestMethodGen.usingMock";

        public static final String descriptionForMock = "Preference.TestMethodGen.descriptionForMock";

        public static final String usingMockNone = "Preference.TestMethodGen.usingMockNone";

        public static final String usingMockEasyMock = "Preference.TestMethodGen.usingMockEasyMock";

        public static final String usingMockJMock2 = "Preference.TestMethodGen.usingMockJMock2";

        public static final String usingMockMockito = "Preference.TestMethodGen.usingMockMockito";

        public static final String usingMockJMockit = "Preference.TestMethodGen.usingMockJMockit";

        public static final String usingTestingPatternComments = "Preference.TestMethodGen.usingTestingPatternComments";

        public static final String descriptionForTestingPatternComments = "Preference.TestMethodGen.descriptionForTestingPatternComments";

        public static final String commentsNone = "Preference.TestMethodGen.commentsNone";

        public static final String commentsArrangeActAssert = "Preference.TestMethodGen.commentsArrangeActAssert";

        public static final String commentsGivenWhenThen = "Preference.TestMethodGen.commentsGivenWhenThen";

    }

}
