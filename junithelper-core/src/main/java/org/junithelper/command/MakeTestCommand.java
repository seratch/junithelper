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

import org.junithelper.core.config.Configulation;
import org.junithelper.core.file.FileReader;
import org.junithelper.core.file.impl.CommonsIOFileReader;
import org.junithelper.core.file.impl.CommonsIOFileWriter;
import org.junithelper.core.generator.TestCaseGenerator;
import org.junithelper.core.generator.impl.DefaultTestCaseGenerator;
import org.junithelper.core.util.Stdout;

import java.io.File;
import java.util.List;

public class MakeTestCommand extends AbstractCommand {

    private MakeTestCommand() {
    }

    public static Configulation config = new Configulation();

    public static void main(String[] args) throws Exception {
        config = overrideConfigulation(config);
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
        FileReader fileReader = new CommonsIOFileReader();
        TestCaseGenerator testCaseGenerator = new DefaultTestCaseGenerator(
                config);
        for (File javaFile : javaFiles) {
            File testFile = null;
            String currentTestCaseSourceCode = null;
            try {
                testFile = new File(javaFile
                        .getAbsolutePath()
                        .replaceAll("\\\\", "/")
                        .replaceFirst(
                                getDirectoryPathOfProductSourceCode(config),
                                getDirectoryPathOfTestSourceCode(config))
                        .replaceFirst("\\.java", "Test.java"));
                currentTestCaseSourceCode = fileReader.readAsString(testFile);
            } catch (Exception e) {
            }
            testCaseGenerator.initialize(fileReader.readAsString(javaFile));
            String testCodeString = null;
            if (currentTestCaseSourceCode != null) {
                testCodeString = testCaseGenerator
                        .getTestCaseSourceCodeWithLackingTestMethod(currentTestCaseSourceCode);
                if (!testCodeString.equals(currentTestCaseSourceCode)) {
                    Stdout.p("  Modified: " + testFile.getAbsolutePath());
                    new CommonsIOFileWriter(testFile).writeText(testCodeString);
                }
            } else {
                testCodeString = testCaseGenerator.getNewTestCaseSourceCode();
                Stdout.p("  Created: " + testFile.getAbsolutePath());
                new CommonsIOFileWriter(testFile).writeText(testCodeString);
            }
        }
    }

}
