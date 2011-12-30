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

import java.util.List;

import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.TestCaseMeta;
import org.junithelper.core.meta.TestMethodMeta;

public interface TestCaseGenerator {

    TestCaseGenerator initialize(String targetSourceCodeString);

    TestCaseGenerator initialize(ClassMeta targetClassMeta);

    TestCaseMeta getNewTestCaseMeta();

    List<TestMethodMeta> getLackingTestMethodMetaList(String currentTestCaseSourceCode);

    String getNewTestCaseSourceCode();

    String getTestCaseSourceCodeWithLackingTestMethod(String currentTestCaseSourceCode);

    String getUnifiedVersionTestCaseSourceCode(String currentTestCaseSourceCode, JUnitVersion version);

}
