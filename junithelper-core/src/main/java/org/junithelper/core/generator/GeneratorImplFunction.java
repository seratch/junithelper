/* 
 * Copyright 2009-2011 junithelper.org. 
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

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.util.Assertion;

class GeneratorImplFunction {

    private GeneratorImplFunction() {
    }

    static boolean isPublicMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.Public && target.isPublicMethodRequired;
    }

    static boolean isProtectedMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.Protected && target.isProtectedMethodRequired;
    }

    static boolean isPackageLocalMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
        return methodMeta.accessModifier == AccessModifier.PackageLocal && target.isPackageLocalMethodRequired;
    }

    static boolean isCanonicalClassNameUsed(String expectedCanonicalClassName, String usedClassName,
            ClassMeta targetClassMeta) {

        Assertion.on("expectedCanonicalClassName").mustNotBeNull(expectedCanonicalClassName);
        Assertion.on("usedClassName").mustNotBeNull(usedClassName);
        Assertion.on("targetClassMeta").mustNotBeNull(targetClassMeta);

        if (usedClassName.equals(expectedCanonicalClassName)
                || usedClassName.equals(expectedCanonicalClassName.replace("java.lang.", ""))) {
            // canonical class name
            // e.g.
            // "com.example.ArgBean"
            return true;
        } else {
            // imported type
            // e.g.
            // (same package)
            // import com.example.*;
            // import com.example.ArgBean;
            // "ArgBean"
            String[] extSplitted = expectedCanonicalClassName.split("\\.");
            String extClassName = extSplitted[extSplitted.length - 1];
            if (usedClassName.equals(extClassName)) {
                String extInSamplePackage = targetClassMeta.packageName + "." + extClassName;
                if (extInSamplePackage.equals(expectedCanonicalClassName)) {
                    return true;
                } else {
                    for (String imported : targetClassMeta.importedList) {
                        String target = expectedCanonicalClassName.replaceFirst(extClassName, "");
                        if (imported.matches(expectedCanonicalClassName) || imported.matches(target + ".+")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static String getInstantiationSourceCode(Configuration config, SourceCodeAppender appender,
            TestMethodMeta testMethodMeta) {

        Assertion.on("config").mustNotBeNull(config);
        Assertion.on("testMethodMeta").mustNotBeNull(testMethodMeta);

        // -----------
        // Extension
        if (config.isExtensionEnabled && config.extConfiguration.extInstantiations != null) {
            for (ExtInstantiation ins : config.extConfiguration.extInstantiations) {
                if (isCanonicalClassNameUsed(ins.canonicalClassName, testMethodMeta.classMeta.name,
                        testMethodMeta.classMeta)) {
                    // add import list
                    for (String newImport : ins.importList) {
                        testMethodMeta.classMeta.importedList.add(newImport);
                    }
                    // instantiation code
                    // e.g. Sample target = new Sample();
                    StringBuilder buf = new StringBuilder();
                    if (ins.preAssignCode != null && ins.preAssignCode.trim().length() > 0) {
                        appender.appendExtensionSourceCode(buf, ins.preAssignCode);
                    }
                    appender.appendTabs(buf, 2);
                    buf.append(testMethodMeta.classMeta.name);
                    buf.append(" target = ");
                    buf.append(ins.assignCode.trim());
                    if (!ins.assignCode.trim().endsWith(StringValue.Semicolon)) {
                        buf.append(StringValue.Semicolon);
                    }
                    appender.appendLineBreak(buf);
                    if (ins.postAssignCode != null && ins.postAssignCode.trim().length() > 0) {
                        appender.appendExtensionPostAssignSourceCode(buf, ins.postAssignCode,
                                new String[] { "\\{instance\\}" }, "target");
                    }
                    return buf.toString();
                }
            }
        }
        // TODO better implementation
        ConstructorGenerator constructorGenerator = ConstructorGeneratorFactory.create(config, appender
                .getLineBreakProvider());
        return constructorGenerator.getFirstInstantiationSourceCode(config, testMethodMeta.classMeta);
    }

}
