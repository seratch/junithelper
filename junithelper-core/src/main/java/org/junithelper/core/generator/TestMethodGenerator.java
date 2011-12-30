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

import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;

public interface TestMethodGenerator {

    void initialize(ClassMeta targetClassMeta);

    TestMethodMeta getTestMethodMeta(MethodMeta targetMethodMeta);

    TestMethodMeta getTestMethodMeta(MethodMeta targetMethodMeta, ExceptionMeta exception);

    String getTestMethodNamePrefix(TestMethodMeta testMethodMeta);

    String getTestMethodNamePrefix(TestMethodMeta testMethodMeta, ExceptionMeta exception);

    String getTestMethodSourceCode(TestMethodMeta testMethodMeta);

}
