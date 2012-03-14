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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.filter.TrimFilterUtil;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.util.AccessModifierDetector;
import org.junithelper.core.util.Assertion;

public class MethodMetaExtractor {

    private Configuration config;
    private ClassMeta classMeta;

    public MethodMetaExtractor(Configuration config) {
        this.config = config;
    }

    public MethodMetaExtractor initialize(String sourceCodeString) {
        return initialize(null, sourceCodeString);
    }

    public MethodMetaExtractor initialize(ClassMeta classMeta) {
        this.classMeta = classMeta;
        return this;
    }

    public MethodMetaExtractor initialize(ClassMeta classMeta, String sourceCodeString) {
        if (classMeta == null) {
            this.classMeta = new ClassMetaExtractor(config).extract(sourceCodeString);
        } else {
            this.classMeta = classMeta;
        }
        return this;
    }

    public List<MethodMeta> extract(String sourceCodeString) {

        Assertion.on("sourceCodeString").mustNotBeNull(sourceCodeString);

        List<MethodMeta> dest = new ArrayList<MethodMeta>();

        TypeNameConverter typeNameConverter = new TypeNameConverter(config);

        sourceCodeString = TrimFilterUtil.doAllFilters(sourceCodeString);

        // -----------------
        // for method signature
        Matcher mat = RegExp.PatternObject.MethodSignatureArea.matcher(sourceCodeString);
        while (mat.find()) {
            MethodMeta meta = new MethodMeta();
            String methodSignatureArea = mat.group(0).replaceAll(StringValue.CarriageReturn, StringValue.Empty)
                    .replaceAll(StringValue.LineFeed, StringValue.Space);

            // -----------------
            // skip constructors
            if (methodSignatureArea.matches(RegExp.Anything_ZeroOrMore_Min
                    + RegExp.WhiteSpace.Consecutive_OneOrMore_Max + classMeta.name + "\\("
                    + RegExp.Anything_ZeroOrMore_Min + "\\)" + RegExp.Anything_ZeroOrMore_Min)) {
                continue;
            }

            // -----------------
            // skip not method signature
            String trimmedMethodSignatureArea = methodSignatureArea.replaceAll("\\s*,\\s*", ",").replaceAll(
                    "\\s*<\\s*", "<").replaceAll("\\s*>", ">");
            String methodSignatureAreaWithoutAccessModifier = trimAccessModifierFromMethodSignatureArea(trimmedMethodSignatureArea);
            Matcher matcherGrouping = RegExp.PatternObject.MethodSignatureWithoutAccessModifier_Group
                    .matcher(StringValue.Space + methodSignatureAreaWithoutAccessModifier);
            if (!matcherGrouping.find()) {
                continue;
            }

            // -----------------
            // is static method
            if (methodSignatureArea.matches(RegExp.Anything_ZeroOrMore_Min
                    + RegExp.WhiteSpace.Consecutive_OneOrMore_Max + "static"
                    + RegExp.WhiteSpace.Consecutive_OneOrMore_Max + RegExp.Anything_ZeroOrMore_Min)) {
                meta.isStatic = true;
            }
            // -----------------
            // access modifier
            meta.accessModifier = getAccessModifier(methodSignatureArea);

            // -----------------
            // return type
            String grouped = matcherGrouping.group(1);
            String returnTypeFull = grouped.replaceAll("final ", StringValue.Empty).split("\\s+")[0].trim();
            // generics

            // remove generics if nested
            returnTypeFull = trimGenericsIfNested(returnTypeFull);
            Matcher toGenericsMatcherForReturn = Pattern.compile(RegExp.Generics_Group).matcher(returnTypeFull);
            while (toGenericsMatcherForReturn.find()) {
                String[] generics = toGenericsMatcherForReturn.group().replaceAll("<", StringValue.Empty).replaceAll(
                        ">", StringValue.Empty).split(StringValue.Comma);
                for (String generic : generics) {
                    generic = typeNameConverter
                            .toCompilableType(generic, classMeta.importedList, classMeta.packageName).trim();
                    meta.returnType.generics.add(generic);
                }
            }
            String returnTypeName = returnTypeFull.replace(RegExp.Generics, StringValue.Empty);
            if (!returnTypeName.equals("void")) {
                meta.returnType.name = typeNameConverter.toCompilableType(returnTypeName, meta.returnType.generics,
                        classMeta.importedList, classMeta.packageName).trim();
                meta.returnType.nameInMethodName = typeNameConverter.toAvailableInMethodName(meta.returnType.name);
            }
            // -----------------
            // method name
            meta.name = matcherGrouping.group(2);
            // -----------------
            // args
            String argsAreaString = matcherGrouping.group(3);
            ArgTypeMetaExtractor argTypeMetaExtractor = new ArgTypeMetaExtractor(config);
            argTypeMetaExtractor.initialize(classMeta).doExtract(argsAreaString);
            meta.argNames = argTypeMetaExtractor.getExtractedNameList();
            meta.argTypes = argTypeMetaExtractor.getExtractedMetaList();
            // -----------------
            // is accessor method or not
            String fieldName = null;
            String fieldType = null;
            if (meta.name.matches("^set.+")) {
                // target field name
                fieldName = meta.name.substring(3);
                if (meta.argTypes.size() > 0) {
                    fieldType = meta.argTypes.get(0).name;
                }
            } else if (meta.name.matches("^get.+")) {
                // target field name
                fieldName = meta.name.substring(3);
                fieldType = meta.returnType.name;
            } else if (meta.name.matches("^is.+")) {
                // target field name
                fieldName = meta.name.substring(2);
                fieldType = meta.returnType.name;
            }
            if (fieldName != null && fieldType != null) {
                meta.isAccessor = isPrivateFieldExists(fieldType, fieldName, sourceCodeString);
            }
            // -----------------
            // throws exception
            String throwsExceptions = matcherGrouping.group(4);
            if (throwsExceptions != null) {
                String[] exceptions = throwsExceptions.replaceAll(
                        "throws" + RegExp.WhiteSpace.Consecutive_OneOrMore_Max, StringValue.Empty).split(
                        StringValue.Comma);
                for (String exception : exceptions) {
                    exception = exception.trim();
                    ExceptionMeta exceptionMeta = new ExceptionMeta();
                    exceptionMeta.name = exception;
                    exceptionMeta.nameInMethodName = typeNameConverter.toAvailableInMethodName(exception);
                    meta.throwsExceptions.add(exceptionMeta);
                }
            }
            dest.add(meta);
        }
        return dest;
    }

    boolean isPrivateFieldExists(String fieldType, String fieldName, String sourceCodeString) {

        Assertion.on("fieldType").mustNotBeEmpty(fieldType);
        Assertion.on("fieldName").mustNotBeEmpty(fieldName);
        Assertion.on("sourceCodeString").mustNotBeNull(sourceCodeString);

        // field name
        String regExpForFieldNameArea = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        // field type
        // considering array, generics comma
        String regExpForFieldTypeArea = fieldType.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]").replaceAll(",",
                "\\\\s*,\\\\s*");
        String regExpForPrivateFieldThatHasAccessors = ".*?private\\s+" + regExpForFieldTypeArea + "("
                + RegExp.Generics + ")*" + RegExp.WhiteSpace.Consecutive_OneOrMore_Max + regExpForFieldNameArea + ".+";
        return sourceCodeString.replaceAll(RegExp.CRLF, StringValue.Empty).matches(
                regExpForPrivateFieldThatHasAccessors);
    }

    AccessModifier getAccessModifier(String methodSignatureArea) {
        if (AccessModifierDetector.isPublic(methodSignatureArea)) {
            return AccessModifier.Public;
        } else if (AccessModifierDetector.isProtected(methodSignatureArea)) {
            return AccessModifier.Protected;
        } else if (AccessModifierDetector.isPackageLocal(methodSignatureArea)) {
            return AccessModifier.PackageLocal;
        } else if (AccessModifierDetector.isPrivate(methodSignatureArea)) {
            return AccessModifier.Private;
        } else {
            return AccessModifier.Public;
        }
    }

    static String trimAccessModifierFromMethodSignatureArea(String methodSignatureArea) {

        Assertion.on("methodSignatureArea").mustNotBeNull(methodSignatureArea);

        String regExpForAccessModifier_public = AccessModifierDetector.RegExp.Prefix + "public" + "\\s+";
        String regExpForAccessModifier_protected = AccessModifierDetector.RegExp.Prefix + "protected" + "\\s+";
        String methodSignatureAreaWithoutAccessModifier = methodSignatureArea.replaceAll(StringValue.Tab,
                StringValue.Space).replaceAll(regExpForAccessModifier_public, StringValue.Space).replaceAll(
                regExpForAccessModifier_protected, StringValue.Space).replaceAll("\\sfinal\\s", StringValue.Space);
        return methodSignatureAreaWithoutAccessModifier;
    }

    static String trimGenericsIfNested(String returnTypeDef) {
        if (returnTypeDef == null) {
            return null;
        }
        boolean isInsideOfGeneric = false;
        boolean hasNestedGenerics = false;
        int len = returnTypeDef.length();
        for (int i = 0; i < len; i++) {
            char c = returnTypeDef.charAt(i);
            if (isInsideOfGeneric) {
                if (c == '<') {
                    hasNestedGenerics = true;
                    break;
                }
                if (c == '>') {
                    isInsideOfGeneric = false;
                }
            } else {
                if (c == '<') {
                    isInsideOfGeneric = true;
                }
                if (c == '>') {
                    isInsideOfGeneric = false;
                }
            }
        }
        if (hasNestedGenerics) {
            return returnTypeDef.replaceFirst(RegExp.Generics, StringValue.Empty).replaceAll("<", StringValue.Empty)
                    .replaceAll(">", StringValue.Empty);
        }
        return returnTypeDef;
    }

}
