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
package org.junithelper.core.parser.impl;

import org.junithelper.core.config.Configulation;
import org.junithelper.core.filter.TrimFilterUtil;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.meta.extractor.ConstructorMetaExtractor;
import org.junithelper.core.meta.extractor.MethodMetaExtractor;
import org.junithelper.core.parser.SourceCodeParser;
import org.junithelper.core.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DefaultSourceCodeParser implements SourceCodeParser {

    private ClassMetaExtractor classMetaExtractor;
    private MethodMetaExtractor methodMetaExtractor;
    private ConstructorMetaExtractor constructorMetaExtractor;

    public DefaultSourceCodeParser(Configulation config) {
        classMetaExtractor = new ClassMetaExtractor(config);
        methodMetaExtractor = new MethodMetaExtractor(config);
        constructorMetaExtractor = new ConstructorMetaExtractor(config);
    }

    @Override
    public ClassMeta parse(InputStream is, String encoding) throws IOException {
        return parse(IOUtil.readAsString(is, encoding));
    }

    @Override
    public ClassMeta parse(String sourceCodeString) {
        sourceCodeString = TrimFilterUtil.doAllFilters(sourceCodeString);
        ClassMeta classMeta = classMetaExtractor.extract(sourceCodeString);
        classMeta.constructors = getConstructors(classMeta, sourceCodeString);
        classMeta.methods = getMethods(classMeta, sourceCodeString);
        return classMeta;
    }

    List<MethodMeta> getMethods(ClassMeta classMeta, String sourceCodeString) {
        methodMetaExtractor.initialize(classMeta, sourceCodeString);
        return methodMetaExtractor.extract(sourceCodeString);
    }

    List<ConstructorMeta> getConstructors(ClassMeta classMeta, String sourceCodeString) {
        methodMetaExtractor.initialize(classMeta, sourceCodeString);
        constructorMetaExtractor.initialize(classMeta, sourceCodeString);
        List<ConstructorMeta> constructorMetaList
                = constructorMetaExtractor.extract(sourceCodeString);
        if (constructorMetaList.size() == 0) {
            ConstructorMeta defaultConstructor = new ConstructorMeta();
            constructorMetaList.add(defaultConstructor);
        }
        return constructorMetaList;
    }

}
