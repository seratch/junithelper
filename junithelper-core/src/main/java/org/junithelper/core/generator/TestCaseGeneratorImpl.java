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
package org.junithelper.core.generator;

import static org.junithelper.core.generator.GeneratorImplFunction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.MessageValue;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.config.extension.ExtArg;
import org.junithelper.core.config.extension.ExtArgPattern;
import org.junithelper.core.config.extension.ExtReturn;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.extractor.ClassMetaExtractor;
import org.junithelper.core.filter.TrimFilterUtil;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestCaseMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.util.Assertion;
import org.junithelper.core.util.ObjectUtil;
import org.junithelper.core.util.Stderr;

class TestCaseGeneratorImpl implements TestCaseGenerator {

    private SourceCodeAppender appender;
    private boolean isAlreadyInitialized = false;

    private Configuration config;
    private ClassMeta targetClassMeta;
    private MessageValue messageValue = new MessageValue();
    private TestMethodGenerator testMethodGenerator;

    public TestCaseGeneratorImpl(Configuration config, LineBreakProvider lineBreakProvider) {
        this.config = config;
        appender = new SourceCodeAppender(lineBreakProvider);
        testMethodGenerator = new TestMethodGeneratorImpl(config, lineBreakProvider);
    }

    @Override
    public TestCaseGeneratorImpl initialize(String targetSourceCodeString) {
        if (isAlreadyInitialized) {
            throw new IllegalStateException("Cannnot reuse this instance..");
        }
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        this.targetClassMeta = classMetaExtractor.extract(targetSourceCodeString);
        this.testMethodGenerator.initialize(targetClassMeta);
        this.messageValue.initialize(config.language);
        this.isAlreadyInitialized = true;
        return this;
    }

    @Override
    public TestCaseGeneratorImpl initialize(ClassMeta targetClassMeta) {
        this.targetClassMeta = targetClassMeta;
        this.testMethodGenerator.initialize(targetClassMeta);
        this.messageValue.initialize(config.language);
        return this;
    }

    @Override
    public TestCaseMeta getNewTestCaseMeta() {
        TestCaseMeta testCaseMeta = new TestCaseMeta();
        testCaseMeta.target = targetClassMeta;
        for (MethodMeta targetMethodMeta : testCaseMeta.target.methods) {
            testCaseMeta.tests.add(testMethodGenerator.getTestMethodMeta(targetMethodMeta));
        }
        return testCaseMeta;
    }

    @Override
    public List<TestMethodMeta> getLackingTestMethodMetaList(String currentTestCaseSourceCode) {

        Assertion.on("currentTestCaseSourceCode").mustNotBeNull(currentTestCaseSourceCode);
        Assertion.on("targetClassMeta").mustNotBeNull(targetClassMeta);

        List<TestMethodMeta> dest = new ArrayList<TestMethodMeta>();
        String checkTargetSourceCode = TrimFilterUtil.doAllFilters(currentTestCaseSourceCode);

        // is testing type safe required
        if (!checkTargetSourceCode.matches(RegExp.Anything_ZeroOrMore_Min + "public\\s+void\\s+[^\\s]*type\\("
                + RegExp.Anything_ZeroOrMore_Min)) {
            TestMethodMeta meta = new TestMethodMeta();
            meta.classMeta = targetClassMeta;
            meta.isTypeTest = true;
            addTestMethodMetaToListIfNotExists(dest, meta);
        }
        // is testing instantiation required
        if (!targetClassMeta.isEnum && targetClassMeta.constructors.size() > 0) {
            ConstructorMeta notPrivateConstructor = null;
            for (ConstructorMeta constructor : targetClassMeta.constructors) {
                if (constructor.accessModifier != AccessModifier.Private) {
                    notPrivateConstructor = constructor;
                    break;
                }
            }
            // instantiation test
            if (notPrivateConstructor != null) {
                if (!checkTargetSourceCode.matches(RegExp.Anything_ZeroOrMore_Min
                        + "public\\s+void\\s+[^\\s]*instantiation\\(" + RegExp.Anything_ZeroOrMore_Min)) {
                    TestMethodMeta meta = new TestMethodMeta();
                    meta.classMeta = targetClassMeta;
                    meta.isInstantiationTest = true;
                    addTestMethodMetaToListIfNotExists(dest, meta);
                }
            }
        }
        // test methods
        for (MethodMeta methodMeta : targetClassMeta.methods) {

            try {
                // exclude accessors
                if (config.target.isAccessorExcluded && methodMeta.isAccessor) {
                    continue;
                }
                // testing target access modifier
                if (!isPublicMethodAndTestingRequired(methodMeta, config.target)
                        && !isProtectedMethodAndTestingRequired(methodMeta, config.target)
                        && !isPackageLocalMethodAndTestingRequired(methodMeta, config.target)) {
                    continue;
                }
                // -----------
                // at least one test method for the target
                // the test method is not already exist
                StringBuilder IS_ALREADY_EXISTS = new StringBuilder();
                IS_ALREADY_EXISTS.append(RegExp.Anything_ZeroOrMore_Min);
                // test method signature prefix
                TestMethodMeta testMethodMeta = new TestMethodMeta();
                testMethodMeta.methodMeta = methodMeta;
                String testMethodNamePrefix = testMethodGenerator.getTestMethodNamePrefix(testMethodMeta);
                IS_ALREADY_EXISTS.append(testMethodNamePrefix);
                IS_ALREADY_EXISTS.append("[");
                IS_ALREADY_EXISTS.append(config.testMethodName.basicDelimiter);
                IS_ALREADY_EXISTS.append("\\(");
                IS_ALREADY_EXISTS.append("]");
                IS_ALREADY_EXISTS.append(RegExp.Anything_ZeroOrMore_Min);
                if (!checkTargetSourceCode.matches(Matcher.quoteReplacement(IS_ALREADY_EXISTS.toString()))) {
                    // testing normal pattern
                    TestMethodMeta meta = testMethodGenerator.getTestMethodMeta(methodMeta);
                    // extension assertions
                    meta = appendIfExtensionAssertionsExist(meta, config);
                    addTestMethodMetaToListIfNotExists(dest, meta);
                    // testing exception patterns
                    if (config.target.isExceptionPatternRequired) {
                        for (ExceptionMeta exceptionMeta : methodMeta.throwsExceptions) {
                            TestMethodMeta metaEx = testMethodGenerator.getTestMethodMeta(methodMeta, exceptionMeta);
                            // extension assertions
                            metaEx = appendIfExtensionAssertionsExist(metaEx, config);
                            addTestMethodMetaToListIfNotExists(dest, metaEx);
                        }
                    }
                }
                // -----------
                // Extension
                if (config.isExtensionEnabled) {
                    // extension arg patterns
                    List<ExtArg> extArgs = config.extConfiguration.extArgs;
                    for (ExtArg extArg : extArgs) {
                        // import and className
                        for (ArgTypeMeta argType : methodMeta.argTypes) {
                            if (isCanonicalClassNameUsed(extArg.canonicalClassName, argType.name, targetClassMeta)) {
                                for (ExtArgPattern pattern : extArg.patterns) {
                                    // extension pattern is not matched
                                    // e.g.
                                    // .*?doSomething_A$String_StringIsNull.*?
                                    String IS_ALREADY_EXISTS_FOR_PATTERN = RegExp.Anything_ZeroOrMore_Min
                                            + testMethodNamePrefix + config.testMethodName.basicDelimiter
                                            + extArg.getCanonicalClassNameInMethodName() + "Is"
                                            + pattern.getNameWhichFirstCharIsUpper() + RegExp.Anything_ZeroOrMore_Min;
                                    IS_ALREADY_EXISTS_FOR_PATTERN = Matcher
                                            .quoteReplacement(IS_ALREADY_EXISTS_FOR_PATTERN);
                                    if (!checkTargetSourceCode.matches(IS_ALREADY_EXISTS_FOR_PATTERN)) {
                                        // testing target access modifier
                                        TestMethodMeta meta = testMethodGenerator.getTestMethodMeta(methodMeta, null);
                                        meta.extArgPattern = pattern;
                                        // extension assertions
                                        meta = appendIfExtensionAssertionsExist(meta, config);
                                        addTestMethodMetaToListIfNotExists(dest, meta);
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                String errorMessage = "  I'm sorry, \"" + methodMeta.name + "\" is skipped because of internal error("
                        + e.getClass().getName() + "," + e.getLocalizedMessage() + ").";
                Stderr.p(errorMessage);
            }
        }
        return dest;
    }

    static void addTestMethodMetaToListIfNotExists(List<TestMethodMeta> dest, TestMethodMeta meta) {
        for (TestMethodMeta each : dest) {
            if (each.methodMeta != null && meta.methodMeta != null && each.methodMeta.name.equals(meta.methodMeta.name)) {
                if (each.testingTargetException != null && meta.testingTargetException != null
                        && each.testingTargetException.name.equals(meta.testingTargetException.name)) {
                    return;
                }
                if (each.extArgPattern != null
                        && meta.extArgPattern != null
                        && each.extArgPattern.getNameWhichFirstCharIsUpper().equals(
                                meta.extArgPattern.getNameWhichFirstCharIsUpper())) {
                    return;
                }
            } else {
                if (each.isTypeTest == true && meta.isTypeTest == true) {
                    return;
                }
                if (each.isInstantiationTest == true && meta.isInstantiationTest == true) {
                    return;
                }
            }
        }
        dest.add(meta);
    }

    static TestMethodMeta appendIfExtensionAssertionsExist(TestMethodMeta testMethodMeta, Configuration config) {
        if (testMethodMeta.methodMeta != null && testMethodMeta.methodMeta.returnType != null
                && testMethodMeta.methodMeta.returnType.name != null) {
            // -----------
            // Extension
            if (config.isExtensionEnabled) {
                List<ExtReturn> extReturns = config.extConfiguration.extReturns;
                if (extReturns != null && extReturns.size() > 0) {
                    for (ExtReturn extReturn : extReturns) {
                        // The return type matches ext return type
                        if (isCanonicalClassNameUsed(extReturn.canonicalClassName,
                                testMethodMeta.methodMeta.returnType.name, testMethodMeta.classMeta)) {
                            testMethodMeta.extReturn = extReturn;
                            break;
                        }
                    }
                }
            }
        }
        return testMethodMeta;
    }

    @Override
    public String getNewTestCaseSourceCode() {
        StringBuilder buf = new StringBuilder();
        if (targetClassMeta.packageName != null && targetClassMeta.packageName.trim().length() > 0) {
            buf.append("package ");
            buf.append(targetClassMeta.packageName);
            buf.append(";");
            appender.appendLineBreak(buf);
            appender.appendLineBreak(buf);
        }
        for (String imported : targetClassMeta.importedList) {
            if (imported != null && imported.trim().length() > 0) {
                buf.append("import ");
                buf.append(imported);
                buf.append(";");
                appender.appendLineBreak(buf);
            }
        }
        // JUnit 3.x or specified super class
        if (config.testCaseClassNameToExtend != null && config.testCaseClassNameToExtend.trim().length() > 0) {
            if (config.junitVersion == JUnitVersion.version3
                    || !config.testCaseClassNameToExtend.equals("junit.framework.TestCase")) {
                buf.append("import ");
                buf.append(config.testCaseClassNameToExtend);
                buf.append(";");
                appender.appendLineBreak(buf);
                appender.appendLineBreak(buf);
            }
        } else {
            appender.appendLineBreak(buf);
        }
        buf.append("public class ");
        buf.append(targetClassMeta.name);
        buf.append("Test ");
        // JUnit 3.x or specified super class
        if (config.testCaseClassNameToExtend != null && config.testCaseClassNameToExtend.trim().length() > 0) {
            if (config.junitVersion == JUnitVersion.version3
                    || !config.testCaseClassNameToExtend.equals("junit.framework.TestCase")) {
                buf.append("extends ");
                String[] splittedArray = config.testCaseClassNameToExtend.split("\\.");
                buf.append(splittedArray[splittedArray.length - 1]);
                buf.append(" ");
            }
        }
        buf.append("{");
        appender.appendLineBreak(buf);
        appender.appendLineBreak(buf);
        buf.append("}");
        appender.appendLineBreak(buf);
        return getTestCaseSourceCodeWithLackingTestMethod(buf.toString());
    }

    @Override
    public String getTestCaseSourceCodeWithLackingTestMethod(String currentTestCaseSourceCode) {
        String dest = currentTestCaseSourceCode;
        // lacking test methods
        StringBuilder buf = new StringBuilder();
        List<TestMethodMeta> lackingTestMethodMetaList = getLackingTestMethodMetaList(currentTestCaseSourceCode);
        if (lackingTestMethodMetaList.size() == 0) {
            // not modified
            return dest;
        }
        for (TestMethodMeta testMethodMeta : lackingTestMethodMetaList) {
            // method signature
            buf.append(testMethodGenerator.getTestMethodSourceCode(testMethodMeta));
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
            // append import if defined
            if (testMethodMeta.extArgPattern != null) {
                for (String newImport : testMethodMeta.extArgPattern.extArg.importList) {
                    targetClassMeta.importedList.add(newImport);
                }
            }
            if (testMethodMeta.extReturn != null) {
                for (String newImport : testMethodMeta.extReturn.importList) {
                    targetClassMeta.importedList.add(newImport);
                }
            }
        }
        dest = dest.replaceFirst("}[^}]*$", "");
        String lackingSourceCode = buf.toString();
        dest += lackingSourceCode + "}\r\n";
        dest = appendRequiredImportListToSourceCode(dest, targetClassMeta, config);
        return dest;
    }

    @Override
    public String getUnifiedVersionTestCaseSourceCode(String currentTestCaseSourceCode, JUnitVersion version) {
        String dest = currentTestCaseSourceCode;
        ClassMeta classMeta = new ClassMetaExtractor(config).extract(currentTestCaseSourceCode);
        Configuration config = ObjectUtil.deepCopy(this.config);
        if (version == JUnitVersion.version3) {
            dest = dest.replaceAll("@Test[\\s\r\n]*public void ", "public void test"
                    + config.testMethodName.basicDelimiter);
            String[] splittedArray = config.testCaseClassNameToExtend.split("\\.");
            String testCaseName = splittedArray[splittedArray.length - 1];
            dest = dest.replaceFirst(classMeta.name + "\\s*\\{", classMeta.name + " extends " + testCaseName + " {");
            config.junitVersion = JUnitVersion.version3;
            dest = appendRequiredImportListToSourceCode(dest, targetClassMeta, config);
        } else if (version == JUnitVersion.version4) {
            dest = dest.replaceAll("public void test" + config.testMethodName.basicDelimiter,
                    "@Test \r\n\tpublic void ");
            // When it is changed to JUnit 4.x style,
            // "junit.framework.TestCase" inheritance should be removed.
            String REGEXP_FOR_SUPER_CLASS = ".+\\s+extends\\s+([^{]+)\\s*\\{.+";
            String REGEXP_FOR_IMPORT_TEST_CASE = ".+import\\s+junit.framework.TestCase;.+";
            String TEST_CASE_CLASS_WITH_PACAKGE = "junit.framework.TestCase";
            String TEST_CASE_CLASS = "TestCase";
            String destWithoutCRLF = dest.replaceAll("\r", "").replaceAll("\n", "");
            if (destWithoutCRLF.matches(REGEXP_FOR_SUPER_CLASS)) {
                Matcher matcher = Pattern.compile(REGEXP_FOR_SUPER_CLASS).matcher(destWithoutCRLF);
                if (matcher.matches()) {
                    String matched = matcher.group(1);
                    if (matched.trim().equals(TEST_CASE_CLASS_WITH_PACAKGE)) {
                        dest = dest.replaceFirst(classMeta.name + "\\s+extends\\s+.+\\s*\\{", classMeta.name + " {");
                    } else if (matched.trim().equals(TEST_CASE_CLASS)
                            && destWithoutCRLF.matches(REGEXP_FOR_IMPORT_TEST_CASE)) {
                        dest = dest.replaceFirst(classMeta.name + "\\s+extends\\s+.+\\s*\\{", classMeta.name + " {")
                                .replaceAll("import\\s+junit.framework.TestCase;", "");
                    }
                }
            }
            config.junitVersion = JUnitVersion.version4;
            dest = appendRequiredImportListToSourceCode(dest, targetClassMeta, config);
        }
        return dest;
    }

    String appendRequiredImportListToSourceCode(String sourceCode, ClassMeta targetClassMeta, Configuration config) {

        Assertion.on("targetClassMeta").mustNotBeNull(targetClassMeta);

        String dest = sourceCode;
        String oneline = TrimFilterUtil.doAllFilters(sourceCode);
        StringBuilder importedListBuf = new StringBuilder();

        // to uniq collection
        List<String> uniqImportedList = new ArrayList<String>();
        for (String imported : targetClassMeta.importedList) {
            if (!uniqImportedList.contains(imported.trim())) {
                uniqImportedList.add(imported.trim());
            }
        }
        for (String imported : uniqImportedList) {
            String newOne = "import " + imported + ";";
            if (!oneline.matches(RegExp.Anything_ZeroOrMore_Min + newOne + RegExp.Anything_ZeroOrMore_Min)) {
                importedListBuf.append(newOne);
                importedListBuf.append(StringValue.CarriageReturn);
                importedListBuf.append(StringValue.LineFeed);
            }
        }
        // Inner classes of test target class
        String prefix = targetClassMeta.packageName == null ? "" : targetClassMeta.packageName + ".";
        appender.appendIfNotExists(importedListBuf, oneline, "import " + prefix + targetClassMeta.name + ".*;");
        // JUnit
        if (config.junitVersion == JUnitVersion.version3) {
            appender.appendIfNotExists(importedListBuf, oneline, "import " + config.testCaseClassNameToExtend + ";");
        } else if (config.junitVersion == JUnitVersion.version4) {
            if (!sourceCode.contains("org.hamcrest.Matchers.*;")
                    && !uniqImportedList.contains("org.hamcrest.Matchers.*;")) {
                appender.appendIfNotExists(importedListBuf, oneline, "import static org.hamcrest.CoreMatchers.*;");
            }
            appender.appendIfNotExists(importedListBuf, oneline, "import static org.junit.Assert.*;");
            appender.appendIfNotExists(importedListBuf, oneline, "import org.junit.Test;");
        }
        // Mock object framework
        if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
            appender.appendIfNotExists(importedListBuf, oneline, "import org.easymock.classextension.EasyMock;");
            appender.appendIfNotExists(importedListBuf, oneline, "import org.easymock.classextension.IMocksControl;");
        } else if (config.mockObjectFramework == MockObjectFramework.JMock2) {
            appender.appendIfNotExists(importedListBuf, oneline, "import org.jmock.Mockery;");
            appender.appendIfNotExists(importedListBuf, oneline, "import org.jmock.Expectations;");
            appender.appendIfNotExists(importedListBuf, oneline, "import org.jmock.lib.legacy.ClassImposteriser;");
        } else if (config.mockObjectFramework == MockObjectFramework.JMockit) {
            appender.appendIfNotExists(importedListBuf, oneline, "import mockit.Mocked;");
            appender.appendIfNotExists(importedListBuf, oneline, "import mockit.Expectations;");
        } else if (config.mockObjectFramework == MockObjectFramework.Mockito) {
            appender.appendIfNotExists(importedListBuf, oneline, "import static org.mockito.BDDMockito.*;");
        }
        if (importedListBuf.length() > 0) {
            Matcher matcher = RegExp.PatternObject.PackageDefArea_Group.matcher(sourceCode.replaceAll(RegExp.CRLF,
                    StringValue.Space));
            if (matcher.find()) {
                String packageDef = matcher.group(1);
                String CRLF = StringValue.CarriageReturn + StringValue.LineFeed;
                String replacement = packageDef + CRLF + CRLF
                        + importedListBuf.toString().replaceAll("\r\n*$", StringValue.Empty);
                dest = dest.replaceFirst(packageDef, replacement);
            } else {
                dest = importedListBuf.toString() + dest;
            }
        }
        return dest;
    }

}
