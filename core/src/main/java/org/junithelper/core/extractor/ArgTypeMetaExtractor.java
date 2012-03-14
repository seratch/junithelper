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
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.util.Assertion;

public class ArgTypeMetaExtractor {

    private Configuration config;
    private ClassMeta classMeta;
    private List<ArgTypeMeta> extractedMetaList = new ArrayList<ArgTypeMeta>();
    private List<String> extractedNameList = new ArrayList<String>();

    public ArgTypeMetaExtractor(Configuration config) {
        this.config = config;
    }

    public ArgTypeMetaExtractor initialize(String sourceCodeString) {
        return initialize(null, sourceCodeString);
    }

    public ArgTypeMetaExtractor initialize(ClassMeta classMeta) {
        this.classMeta = classMeta;
        return this;
    }

    public ArgTypeMetaExtractor initialize(ClassMeta classMeta, String sourceCodeString) {
        if (classMeta == null) {
            this.classMeta = new ClassMetaExtractor(config).extract(sourceCodeString);
        } else {
            this.classMeta = classMeta;
        }
        return this;
    }

    public List<ArgTypeMeta> getExtractedMetaList() {
        return extractedMetaList;
    }

    public List<String> getExtractedNameList() {
        return extractedNameList;
    }

    public void doExtract(String argsAreaString) {

        Assertion.on("classMeta").mustNotBeNull(classMeta);

        TypeNameConverter typeNameConverter = new TypeNameConverter(config);
        // -----------------
        // args
        String[] argArr = ArgExtractorHelper.getArgListFromArgsDefAreaString(argsAreaString).toArray(new String[0]);
        int argArrLen = argArr.length;
        for (int i = 0; i < argArrLen; i++) {
            ArgTypeMeta argTypeMeta = new ArgTypeMeta();
            String argTypeFull = argArr[i];
            // -----------------
            // generics of arg
            Matcher toGenericsMatcherForArg = Pattern.compile(RegExp.Generics_Group).matcher(argTypeFull);
            while (toGenericsMatcherForArg.find()) {
                String[] generics = toGenericsMatcherForArg.group().replaceAll("<", StringValue.Empty).replaceAll(">",
                        StringValue.Empty).split(StringValue.Comma);
                // convert to java.lang.Object if self class is included
                for (String generic : generics) {
                    generic = typeNameConverter
                            .toCompilableType(generic, classMeta.importedList, classMeta.packageName);
                    argTypeMeta.generics.add(generic);
                }
            }
            // -----------------
            // arg type
            String argTypeName = argTypeFull.replaceAll("final ", StringValue.Empty).replaceAll(RegExp.Generics,
                    StringValue.Empty).split("\\s+")[0].trim();
            if (argTypeName != null && !"".equals(argTypeName)) {
                argTypeMeta.name = typeNameConverter.toCompilableType(argTypeName, argTypeMeta.generics,
                        classMeta.importedList, classMeta.packageName);
                argTypeMeta.nameInMethodName = typeNameConverter.toAvailableInMethodName(argTypeMeta.name);
                extractedMetaList.add(argTypeMeta);
            }
            // -----------------
            // arg name string
            Matcher argNameMatcher = RegExp.PatternObject.MethodArg_Group.matcher(argTypeFull);
            if (argNameMatcher.find()) {
                String argName = argNameMatcher.group(1);
                if (argName.matches(".+\\[\\s*\\]")) {
                    // ex. String strArr[] = null;
                    String arrayPart = "";
                    Matcher mat = Pattern.compile("\\[\\s*\\]").matcher(argName);
                    while (mat.find()) {
                        arrayPart += "[]";
                    }
                    argName = argName.replaceAll("\\[\\s*\\]", StringValue.Empty);
                    argTypeMeta.name = argTypeMeta.name + arrayPart;
                    argTypeMeta.nameInMethodName = typeNameConverter.toAvailableInMethodName(argTypeMeta.name);
                }
                extractedNameList.add(argName);
            } else if (extractedMetaList.size() > 0) {
                extractedNameList.add("arg" + i);
            }
        }
    }

}
