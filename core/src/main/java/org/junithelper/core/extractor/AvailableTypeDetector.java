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
import java.lang.reflect.Modifier;
import java.util.List;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.file.FileSearcherFactory;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.util.PrimitiveTypeUtil;
import org.junithelper.core.util.Stderr;

public class AvailableTypeDetector {

    private ClassMeta classMeta;

    public AvailableTypeDetector(ClassMeta classMeta) {
        this.classMeta = classMeta;
    }

    public boolean isPrimitive(String typeName) {
        return typeName != null && PrimitiveTypeUtil.isPrimitive(typeName);
    }

    public boolean isArray(String typeName) {
        return typeName != null && typeName.matches(".+?\\[\\]$");
    }

    public boolean isJavaLangPackageType(String typeName) {
        if (typeName == null) {
            return false;
        }
        if (typeName.matches("java\\.lang\\.[^\\.]+$")) {
            return true;
        }
        try {
            Class.forName("java.lang." + typeName);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isAvailableType(String typeName, Configuration config) {
        if (typeName == null) {
            return false;
        }
        boolean isTypeAvailable = false;
        String[] packageArr = typeName.split("\\.");
        String packageName = null;
        if (packageArr.length > 1) {
            packageName = typeName.replaceFirst("\\.[^\\.]+$", StringValue.Empty);
        }
        for (String imported : classMeta.importedList) {
            if (packageName != null && imported.equals(typeName)) {
                return true;
            } else if (imported.matches(".+\\." + typeName + "$")) {
                return true;
            }
        }
        try {
            Class.forName("java.lang." + typeName);
            isTypeAvailable = true;
        } catch (Exception ignore) {
            if (config != null && packageName != null) {
                // check same package class
                String searchPath = config.directoryPathOfProductSourceCode + "/" + packageName.replaceAll("\\.", "/");
                try {
                    List<File> files = FileSearcherFactory.create().searchFilesRecursivelyByName(searchPath,
                            typeName + RegExp.FileExtension.JavaFile);
                    if (files != null && files.size() > 0) {
                        isTypeAvailable = true;
                    }
                } catch (Exception e) {
                    Stderr.p(e.getLocalizedMessage() + " - " + searchPath);
                }
            }
        }
        return isTypeAvailable;
    }

    public boolean isJMockitMockableType(String typeName) {
        if (typeName == null) {
            return false;
        }
        if (PrimitiveTypeUtil.isPrimitive(typeName) || typeName.matches(".+?\\[\\]$")) {
            return false;
        }
        try {
            // java.lang class name
            Class<?> clazz = Class.forName("java.lang." + typeName);
            return (Modifier.isFinal(clazz.getModifiers())) ? false : true;
        } catch (Exception ignore) {
            // imported class name
            for (String importedPackage : classMeta.importedList) {
                importedPackage = importedPackage.replaceAll("//", StringValue.Empty);
                if (importedPackage.matches(".+?\\." + typeName + "$")) {
                    return true;
                }
            }
            // full package class name
            if (typeName.matches(".+?\\..+")) {
                try {
                    Class<?> clazz = Class.forName(typeName);
                    return !(Modifier.isFinal(clazz.getModifiers()));
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

}
