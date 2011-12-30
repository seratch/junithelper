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

import static org.junithelper.core.generator.GeneratorImplFunction.*;

import java.util.ArrayList;
import java.util.List;

import org.junithelper.core.config.Configuration;
import org.junithelper.core.config.extension.ExtInstantiation;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.util.PrimitiveTypeUtil;

class ConstructorGeneratorImpl implements ConstructorGenerator {

    private SourceCodeAppender appender;

    public ConstructorGeneratorImpl(Configuration config, LineBreakProvider lineBreakProvider) {
        IndentationProvider indentationProvider = new IndentationProvider(config);
        appender = new SourceCodeAppender(lineBreakProvider, indentationProvider);
    }

    @Override
    public List<String> getAllInstantiationSourceCodeList(Configuration config, ClassMeta classMeta) {
        List<String> dest = new ArrayList<String>();
        for (ConstructorMeta constructorMeta : classMeta.constructors) {
            dest.add(getInstantiationSourceCode(config, classMeta, constructorMeta));
        }
        return dest;
    }

    @Override
    public String getFirstInstantiationSourceCode(Configuration config, ClassMeta classMeta) {
        return getInstantiationSourceCode(config, classMeta, getFirstConstructor(classMeta));
    }

    @Override
    public String getInstantiationSourceCode(Configuration config, ClassMeta classMeta, ConstructorMeta constructorMeta) {
        // TODO better implementation
        StringBuilder buf = new StringBuilder();
        if (constructorMeta == null) {
            appender.appendTabs(buf, 2);
            buf.append(classMeta.name);
            buf.append(" target = null");
            buf.append(StringValue.Semicolon);
            appender.appendLineBreak(buf);
        } else {
            int len = constructorMeta.argTypes.size();
            for (int i = 0; i < len; i++) {
                String typeName = constructorMeta.argTypes.get(i).name;

                boolean isAssigned = false;
                if (config.isExtensionEnabled && config.extConfiguration.extInstantiations != null) {
                    for (ExtInstantiation ins : config.extConfiguration.extInstantiations) {
                        if (isCanonicalClassNameUsed(ins.canonicalClassName, typeName, classMeta)) {
                            // add import list
                            for (String newImport : ins.importList) {
                                classMeta.importedList.add(newImport);
                            }
                            // pre-assign
                            if (ins.preAssignCode != null && ins.preAssignCode.trim().length() > 0) {
                                appender.appendExtensionSourceCode(buf, ins.preAssignCode);
                            }
                            // assign
                            // \t\tBean bean =
                            appender.appendTabs(buf, 2);
                            buf.append(typeName);
                            buf.append(" ");
                            buf.append(constructorMeta.argNames.get(i));
                            buf.append(" = ");
                            buf.append(ins.assignCode.trim());
                            if (!ins.assignCode.endsWith(StringValue.Semicolon)) {
                                buf.append(StringValue.Semicolon);
                            }
                            appender.appendLineBreak(buf);
                            // post-assign
                            if (ins.postAssignCode != null && ins.postAssignCode.trim().length() > 0) {
                                appender.appendExtensionPostAssignSourceCode(buf, ins.postAssignCode,
                                        new String[] { "\\{instance\\}" }, constructorMeta.argNames.get(i));
                            }
                            isAssigned = true;
                        }
                    }
                }

                if (!isAssigned) {
                    appender.appendTabs(buf, 2);
                    buf.append(typeName);
                    buf.append(" ");
                    buf.append(constructorMeta.argNames.get(i));
                    buf.append(" = ");
                    if (PrimitiveTypeUtil.isPrimitive(typeName)) {
                        buf.append(PrimitiveTypeUtil.getTypeDefaultValue(typeName));
                    } else {
                        buf.append("null");
                    }
                    buf.append(StringValue.Semicolon);
                    appender.appendLineBreak(buf);
                }
            }
            appender.appendTabs(buf, 2);
            buf.append(classMeta.name);
            buf.append(" target = new ");
            buf.append(classMeta.name);
            buf.append("(");
            if (len > 0) {
                buf.append(constructorMeta.argNames.get(0));
            }
            if (len > 1) {
                for (int i = 1; i < len; i++) {
                    buf.append(StringValue.Comma);
                    buf.append(StringValue.Space);
                    buf.append(constructorMeta.argNames.get(i));
                }
            }
            buf.append(")");
            buf.append(StringValue.Semicolon);
            appender.appendLineBreak(buf);
        }
        return buf.toString();
    }

    ConstructorMeta getFirstConstructor(ClassMeta classMeta) {
        if (classMeta.constructors == null || classMeta.constructors.size() == 0) {
            return null;
        }
        for (ConstructorMeta constructor : classMeta.constructors) {
            if (constructor.accessModifier != AccessModifier.Private) {
                return constructor;
            }
        }
        return null;
    }

}
