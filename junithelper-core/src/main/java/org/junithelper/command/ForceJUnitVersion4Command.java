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
import org.junithelper.core.file.FileReader;
import org.junithelper.core.file.FileReaderFactory;
import org.junithelper.core.file.FileWriterFactory;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.TestCaseGeneratorFactory;
import org.junithelper.core.util.Stdout;

public class ForceJUnitVersion4Command extends AbstractCommand {

    private ForceJUnitVersion4Command() {
    }

    public static Configuration config = new Configuration();

    public static void main(String[] args) throws Exception {
        config = overrideConfiguration(config);
        config.junitVersion = JUnitVersion.version4;
        String dirOrFile = (args != null && args.length > 0 && args[0] != null) ? args[0]
                : config.directoryPathOfProductSourceCode;
        List<File> javaFiles = findTargets(config, dirOrFile);
        for (File javaFile : javaFiles) {
            Stdout.p("  Target: " + javaFile.getAbsolutePath());
        }
        // confirm input from stdin
        if (confirmToExecute() > 0) {
            return;
        }
        TestCaseGenerator testCaseGenerator = TestCaseGeneratorFactory.create(config);
        FileReader fileReader = FileReaderFactory.create();
        for (File javaFile : javaFiles) {
            File testFile = null;
            String currentTestCaseSourceCode = null;
            try {
                testFile = new File(javaFile.getAbsolutePath().replaceAll("\\\\", "/").replaceFirst(
                        getDirectoryPathOfProductSourceCode(config), getDirectoryPathOfTestSourceCode(config))
                        .replaceFirst("\\.java", "Test.java"));
                currentTestCaseSourceCode = fileReader.readAsString(testFile);
            } catch (Exception e) {
            }
            testCaseGenerator.initialize(fileReader.readAsString(javaFile));
            String testCodeString = null;
            if (currentTestCaseSourceCode != null) {
                testCodeString = testCaseGenerator.getUnifiedVersionTestCaseSourceCode(testCaseGenerator
                        .getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode), JUnitVersion.version4);
            } else {
                testCodeString = testCaseGenerator.getNewTestCaseSourceCode();
            }
            FileWriterFactory.create(testFile).writeText(testCodeString);
            Stdout.p("  Forced JUnit 4.x: " + testFile.getAbsolutePath());
        }
    }

}
