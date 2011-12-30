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
package org.junithelper.core.extractor;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.file.FileSearcherFactory;
import org.junithelper.core.util.Assertion;
import org.junithelper.core.util.PrimitiveTypeUtil;

public class TypeNameConverter {

    private Configuration config;

    public TypeNameConverter(Configuration config) {
        this.config = config;
    }

    public String toAvailableInMethodName(String typeName) {
        Assertion.on("typeName").mustNotBeEmpty(typeName);
        typeName = typeName.replaceAll(RegExp.Generics, StringValue.Empty);
        typeName = typeName.replaceAll("final ", StringValue.Empty);
        typeName = typeName.replaceAll("\\.\\.\\.", "Array").replaceAll("\\[\\]", "Array");
        // sample name classes imported or full package class defined
        // ex. java.util.Date, java.sql.Date
        typeName = typeName.replaceAll("\\.", StringValue.Empty);
        typeName = typeName.trim().split(RegExp.WhiteSpace.Consecutive_OneOrMore_Max)[0];
        return typeName;
    }

    public String toCompilableType(String typeName, List<String> importedList, String callerClassPackageName) {
        return toCompilableType(typeName, null, importedList, callerClassPackageName);
    }

    public String toCompilableType(String typeName, List<String> generics, List<String> importedList,
            String callerClassPackageName) {
        if (typeName == null) {
            return typeName;
        }
        typeName = typeName.replaceAll("\\.\\.\\.", "[]");
        // defined class with full package
        if (typeName.matches(".+?\\..+")) {
            return typeName;
        }
        // array object
        boolean isArray = false;
        String arrayPart = "";
        if (typeName.matches(".+?\\[\\s*\\]")) {
            isArray = true;
            Matcher mat = Pattern.compile("\\[\\s*\\]").matcher(typeName);
            while (mat.find()) {
                arrayPart += "[]";
            }
            typeName = typeName.replaceAll("\\[\\]", "");
        }
        // remove generics
        if (typeName.matches(RegExp.Anything_ZeroOrMore_Min + RegExp.Generics + RegExp.Anything_ZeroOrMore_Min)) {
            typeName = typeName.replaceAll(RegExp.Generics, StringValue.Empty);
        }
        boolean isTypeAvailable = false;
        String destTypeName = "Object";
        try {
            if (PrimitiveTypeUtil.isPrimitive(typeName)) {
                isTypeAvailable = true;
                if (!destTypeName.matches(".+?\\[\\]$"))
                    destTypeName = PrimitiveTypeUtil.getTypeDefaultValue(typeName);
            } else {
                try {
                    Class.forName("java.lang." + typeName);
                    isTypeAvailable = true;
                } catch (Exception ignore) {
                    // check same package class
                    List<File> files = FileSearcherFactory.create().searchFilesRecursivelyByName(
                            config.directoryPathOfProductSourceCode + "/"
                                    + callerClassPackageName.replaceAll("\\.", "/"),
                            typeName + RegExp.FileExtension.JavaFile);
                    if (files != null && files.size() > 0) {
                        isTypeAvailable = true;
                    }
                }
                if (!isTypeAvailable) {
                    Class.forName(typeName);
                }
            }
        } catch (Exception e) {
            // class not found
            for (String importedPackage : importedList) {
                importedPackage = importedPackage.replaceAll("//", StringValue.Empty).trim();
                try {
                    String regexp = ".+?\\." + typeName.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]") + "$";
                    if (importedPackage.matches(regexp)) {
                        isTypeAvailable = true;
                        break;
                    }
                    // wildcard import
                    if (!importedPackage.contains("static ") && importedPackage.endsWith("*")) {
                        try {
                            Class.forName(importedPackage.replace("*", "") + typeName);
                            isTypeAvailable = true;
                        } catch (Exception ignore) {
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (generics != null) {
            StringBuilder buf = new StringBuilder();
            if (generics.size() > 0) {
                buf.append("<");
                buf.append(generics.get(0));
                if (generics.size() > 1) {
                    for (int i = 1; i < generics.size(); i++) {
                        buf.append(", ");
                        buf.append(generics.get(i));
                    }
                }
                buf.append(">");
            }
            typeName += buf.toString();
        }
        if (typeName == null || typeName.equals(StringValue.Empty)) {
            typeName = "Object";
        }
        if (destTypeName == null || destTypeName.equals(StringValue.Empty)) {
            destTypeName = "Object";
        }
        if (arrayPart == null || arrayPart.length() == 0) {
            arrayPart = "[]";
        }
        if (isTypeAvailable) {
            return isArray ? typeName + arrayPart : typeName;
        } else {
            return isArray ? destTypeName + arrayPart : destTypeName;
        }
    }

}
