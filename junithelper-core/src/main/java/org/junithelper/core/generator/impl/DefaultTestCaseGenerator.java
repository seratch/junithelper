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
package org.junithelper.core.generator.impl;

import org.junithelper.core.config.*;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.filter.TrimFilterUtil;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.TestMethodGenerator;
import org.junithelper.core.meta.*;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.util.ObjectUtil;
import org.junithelper.core.util.Stderr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class DefaultTestCaseGenerator implements TestCaseGenerator {

    private Configulation config;
    private ClassMeta targetClassMeta;
    private MessageValue messageValue = new MessageValue();
    private TestMethodGenerator testMethodGenerator;

    public DefaultTestCaseGenerator(Configulation config) {
        this.config = config;
        testMethodGenerator = new DefaultTestMethodGenerator(config);
    }

    @Override
    public DefaultTestCaseGenerator initialize(String targetSourceCodeString) {
        ClassMetaExtractor classMetaExtractor = new ClassMetaExtractor(config);
        this.targetClassMeta = classMetaExtractor
                .extract(targetSourceCodeString);
        this.testMethodGenerator.initialize(targetClassMeta);
        this.messageValue.initialize(config.language);
        return this;
    }

    @Override
    public DefaultTestCaseGenerator initialize(ClassMeta targetClassMeta) {
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
            testCaseMeta.tests.add(testMethodGenerator
                    .getTestMethodMeta(targetMethodMeta));
        }
        return testCaseMeta;
    }

    @Override
    public List<TestMethodMeta> getLackingTestMethodMetaList(String currentTestCaseSourceCode) {

        List<TestMethodMeta> dest = new ArrayList<TestMethodMeta>();
        String checkTargetSourceCode = TrimFilterUtil
                .doAllFilters(currentTestCaseSourceCode);

        // is testing type safe required
        if (!checkTargetSourceCode.matches(RegExp.Anything_ZeroOrMore_Min
                + "public\\s+void\\s+[^\\s]*type\\("
                + RegExp.Anything_ZeroOrMore_Min)) {
            TestMethodMeta testMethod = new TestMethodMeta();
            testMethod.classMeta = targetClassMeta;
            testMethod.isTypeTest = true;
            dest.add(testMethod);
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
                if (!checkTargetSourceCode
                        .matches(RegExp.Anything_ZeroOrMore_Min
                                + "public\\s+void\\s+[^\\s]*instantiation\\("
                                + RegExp.Anything_ZeroOrMore_Min)) {
                    TestMethodMeta testMethod = new TestMethodMeta();
                    testMethod.classMeta = targetClassMeta;
                    testMethod.isInstantiationTest = true;
                    dest.add(testMethod);
                }
            }
        }
        // test methods
        for (MethodMeta methodMeta : targetClassMeta.methods) {
            TestMethodMeta testMethodMeta = new TestMethodMeta();
            testMethodMeta.methodMeta = methodMeta;
            String testMethodNamePrefix = testMethodGenerator
                    .getTestMethodNamePrefix(testMethodMeta);
            // the test method is not already exist
            String regExpForDiscriminateOverloadMethods = "";
            if (!config.testMethodName.isReturnRequired) {
                String argDelimiter = Matcher
                        .quoteReplacement(config.testMethodName.argsAreaDelimiter);
                regExpForDiscriminateOverloadMethods = "[^" + argDelimiter
                        + "]";
            }
            String regExpForCheckAlreadyExists = RegExp.Anything_ZeroOrMore_Min
                    + testMethodNamePrefix
                    + regExpForDiscriminateOverloadMethods
                    + RegExp.Anything_ZeroOrMore_Min;
            regExpForCheckAlreadyExists = Matcher
                    .quoteReplacement(regExpForCheckAlreadyExists);
            try {
                if (!checkTargetSourceCode.matches(regExpForCheckAlreadyExists)) {
                    // exclude accessors
                    if (config.target.isAccessorExcluded
                            && methodMeta.isAccessor) {
                        continue;
                    }
                    // testing target access modifier
                    if (isPublicMethodAndTestingRequired(methodMeta, config.target)
                            || isProtectedMethodAndTestingRequired(methodMeta, config.target)
                            || isPackageLocalMethodAndTestingRequired(methodMeta, config.target)) {
                        dest.add(testMethodGenerator.getTestMethodMeta(methodMeta));
                        if (config.target.isExceptionPatternRequired) {
                            // testing exception patterns
                            for (ExceptionMeta exceptionMeta : methodMeta.throwsExceptions) {
                                TestMethodMeta newOne =
                                        testMethodGenerator.getTestMethodMeta(methodMeta, exceptionMeta);
                                dest.add(newOne);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                String errorMessage = "  I'm sorry, \"" + methodMeta.name
                        + "\" is skipped because of internal error("
                        + e.getClass().getName() + ","
                        + e.getLocalizedMessage() + ").";
                Stderr.p(errorMessage);
            }
        }
        return dest;
    }

    boolean isPublicMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.Public
                && target.isPublicMethodRequired;
    }

    boolean isProtectedMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.Protected
                && target.isProtectedMethodRequired;
    }

    boolean isPackageLocalMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.PackageLocal
                && target.isPackageLocalMethodRequired;
    }

    @Override
    public String getNewTestCaseSourceCode() {
        StringBuilder buf = new StringBuilder();
        if (targetClassMeta.packageName != null) {
            buf.append("package ");
            buf.append(targetClassMeta.packageName);
            buf.append(";");
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }
        for (String imported : targetClassMeta.importedList) {
            buf.append("import ");
            buf.append(imported);
            buf.append(";");
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }
        if (config.junitVersion == JUnitVersion.version3) {
            buf.append("import ");
            buf.append(config.testCaseClassNameToExtend);
            buf.append(";");
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }
        buf.append(StringValue.CarriageReturn);
        buf.append(StringValue.LineFeed);
        buf.append("public class ");
        buf.append(targetClassMeta.name);
        buf.append("Test ");
        if (config.junitVersion == JUnitVersion.version3) {
            buf.append("extends ");
            String[] splittedArray = config.testCaseClassNameToExtend.split("\\.");
            buf.append(splittedArray[splittedArray.length - 1]);
            buf.append(" ");
        }
        buf.append("{");
        buf.append(StringValue.CarriageReturn);
        buf.append(StringValue.LineFeed);
        buf.append(StringValue.CarriageReturn);
        buf.append(StringValue.LineFeed);
        buf.append("}");
        buf.append(StringValue.CarriageReturn);
        buf.append(StringValue.LineFeed);
        return getTestCaseSourceCodeWithLackingTestMethod(buf.toString());
    }

    @Override
    public String getTestCaseSourceCodeWithLackingTestMethod(
            String currentTestCaseSourceCode) {
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
        }
        dest = dest.replaceFirst("}[^}]*$", "");
        String lackingSourceCode = buf.toString();
        dest += lackingSourceCode + "}\r\n";
        dest = addRequiredImportList(dest);
        return dest;
    }

    @Override
    public String getUnifiedVersionTestCaseSourceCode(
            String currentTestCaseSourceCode, JUnitVersion version) {
        String dest = currentTestCaseSourceCode;
        ClassMeta classMeta = new ClassMetaExtractor(config).extract(currentTestCaseSourceCode);
        Configulation config = ObjectUtil.deepCopy(this.config);
        if (version == JUnitVersion.version3) {
            dest = dest.replaceAll("@Test[\\s\r\n]*public void ",
                    "public void test" + config.testMethodName.basicDelimiter);
            String[] splittedArray = config.testCaseClassNameToExtend.split("\\.");
            String testCaseName = splittedArray[splittedArray.length - 1];
            dest = dest.replaceFirst(classMeta.name + "\\s*\\{",
                    classMeta.name + " extends " + testCaseName + " {");
            config.junitVersion = JUnitVersion.version3;
            dest = addRequiredImportList(dest, config);
        } else if (version == JUnitVersion.version4) {
            dest = dest.replaceAll("public void test" + config.testMethodName.basicDelimiter,
                    "@Test \r\n\tpublic void ");
            dest = dest.replaceFirst(classMeta.name + "\\s+extends\\s+.+\\s*\\{",
                    classMeta.name + " {");
            config.junitVersion = JUnitVersion.version4;
            dest = addRequiredImportList(dest, config);
        }
        return dest;
    }

    String addRequiredImportList(String src) {
        return addRequiredImportList(src, config);
    }

    String addRequiredImportList(String src, Configulation config) {
        String dest = src;
        String oneline = TrimFilterUtil.doAllFilters(src);
        StringBuilder importedListBuf = new StringBuilder();
        for (String imported : targetClassMeta.importedList) {
            String newOne = "import " + imported + ";";
            if (!oneline.matches(
                    RegExp.Anything_ZeroOrMore_Min + newOne + RegExp.Anything_ZeroOrMore_Min)) {
                importedListBuf.append(newOne);
                importedListBuf.append(StringValue.CarriageReturn);
                importedListBuf.append(StringValue.LineFeed);
            }
        }
        // Inner classes of test target class
        appendIfNotExists(importedListBuf, oneline,
                "import " + targetClassMeta.packageName + "." + targetClassMeta.name + ".*;");
        // JUnit
        if (config.junitVersion == JUnitVersion.version3) {
            appendIfNotExists(importedListBuf, oneline,
                    "import " + config.testCaseClassNameToExtend + ";");
        } else if (config.junitVersion == JUnitVersion.version4) {
            appendIfNotExists(importedListBuf, oneline, "import static org.junit.Assert.*;");
            appendIfNotExists(importedListBuf, oneline, "import org.junit.Test;");
        }
        // Mock object framework
        if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
            appendIfNotExists(importedListBuf, oneline, "import org.easymock.classextension.EasyMock;");
            appendIfNotExists(importedListBuf, oneline, "import org.easymock.classextension.IMocksControl;");
        } else if (config.mockObjectFramework == MockObjectFramework.JMock2) {
            appendIfNotExists(importedListBuf, oneline, "import org.jmock.Mockery;");
            appendIfNotExists(importedListBuf, oneline, "import org.jmock.Expectations;");
            appendIfNotExists(importedListBuf, oneline, "import org.jmock.lib.legacy.ClassImposteriser;");
        } else if (config.mockObjectFramework == MockObjectFramework.JMockit) {
            appendIfNotExists(importedListBuf, oneline, "import mockit.Mocked;");
            appendIfNotExists(importedListBuf, oneline, "import mockit.Expectations;");
        } else if (config.mockObjectFramework == MockObjectFramework.Mockito) {
            appendIfNotExists(importedListBuf, oneline, "import static org.mockito.BDDMockito.*;");
        }
        if (importedListBuf.length() > 0) {
            Matcher matcher = RegExp.PatternObject.PackageDefArea_Group
                    .matcher(src.replaceAll(RegExp.CRLF, StringValue.Space));
            if (matcher.find()) {
                String packageDef = matcher.group(1);
                String CRLF = StringValue.CarriageReturn + StringValue.LineFeed;
                String replacement = packageDef
                        + CRLF
                        + CRLF
                        + importedListBuf.toString().replaceAll("\r\n*$",
                        StringValue.Empty);
                dest = dest.replaceFirst(packageDef, replacement);
            } else {
                dest = importedListBuf.toString() + dest;
            }
        }
        return dest;

    }

    void appendIfNotExists(StringBuilder buf, String src, String importLine) {
        String oneline = src.replaceAll(RegExp.CRLF, StringValue.Space);
        importLine = importLine.replace(StringValue.CarriageReturn
                + StringValue.LineFeed, StringValue.Empty);
        String importLineRegExp = importLine.replaceAll("\\s+", "\\\\s+")
                .replaceAll("\\.", "\\\\.").replaceAll("\\*", "\\\\*");
        if (!oneline.matches(
                RegExp.Anything_ZeroOrMore_Min + importLineRegExp + RegExp.Anything_ZeroOrMore_Min)) {
            buf.append(importLine);
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }
    }

}
