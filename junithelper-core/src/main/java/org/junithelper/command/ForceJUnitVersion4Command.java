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
package org.junithelper.command;

import java.io.File;
import java.util.List;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.extractor.CurrentLineBreakDetector;
import org.junithelper.core.file.FileReader;
import org.junithelper.core.file.FileReaderFactory;
import org.junithelper.core.file.FileWriterFactory;
import org.junithelper.core.generator.LineBreakProvider;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.TestCaseGeneratorFactory;
import org.junithelper.core.meta.CurrentLineBreak;
import org.junithelper.core.util.Stdout;

public class ForceJUnitVersion4Command extends AbstractCommand {

    private ForceJUnitVersion4Command() {
    }

    public static Configuration config = new Configuration();

    public static void main(String[] args) throws Exception {

        config = getUpdatedConfig(config);

        config.junitVersion = JUnitVersion.version4;
        boolean hasFirstArg = (args != null && args.length > 0 && args[0] != null);
        String dirOrFile = hasFirstArg ? args[0] : config.directoryPathOfProductSourceCode;

        // Confirm input from stdin
        List<File> javaFiles = findTargets(config, dirOrFile);
        for (File javaFile : javaFiles) {
            Stdout.p("  Target: " + javaFile.getAbsolutePath());
        }
        if (confirmToExecute() > 0) {
            return;
        }

        // Execute re-writing tests
        FileReader fileReader = FileReaderFactory.create();
        for (File javaFile : javaFiles) {
            File testFile = null;
            String currentTestCaseSourceCode = null;
            try {
                String testFilePath = javaFile.getAbsolutePath().replaceAll("\\\\", "/").replaceFirst(
                        getDirectoryPathOfProductSourceCode(config), getDirectoryPathOfTestSourceCode(config))
                        .replaceFirst("\\.java", "Test.java");
                testFile = new File(testFilePath);
                currentTestCaseSourceCode = fileReader.readAsString(testFile);
            } catch (Exception e) {
            }
            CurrentLineBreak currentLineBreak = CurrentLineBreakDetector.detect(currentTestCaseSourceCode);
            LineBreakProvider lineBreakProvider = new LineBreakProvider(config, currentLineBreak);
            TestCaseGenerator testCaseGenerator = TestCaseGeneratorFactory.create(config, lineBreakProvider);

            String targetSourceCodeString = fileReader.readAsString(javaFile);
            testCaseGenerator.initialize(targetSourceCodeString);
            String testCodeString = null;
            if (currentTestCaseSourceCode != null) {
                testCodeString = testCaseGenerator.getUnifiedVersionTestCaseSourceCode(testCaseGenerator
                        .getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode), JUnitVersion.version4);
            } else {
                testCodeString = testCaseGenerator.getNewTestCaseSourceCode();
            }
            testCodeString = standardizeLineBreak(config, testCodeString);
            FileWriterFactory.create(testFile).writeText(testCodeString);
            Stdout.p("  Forced JUnit 4.x: " + testFile.getAbsolutePath());
        }

    }

}
