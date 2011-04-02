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
package org.junithelper.core.generator.impl;

import org.junithelper.core.constant.StringValue;
import org.junithelper.core.generator.ConstructorGenerator;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.util.PrimitiveTypeUtil;

import java.util.ArrayList;
import java.util.List;

public class DefaultConstructorGenerator implements ConstructorGenerator {

    @Override
    public List<String> getAllInstantiationSourceCodeList(ClassMeta classMeta) {
        List<String> dest = new ArrayList<String>();
        for (ConstructorMeta constructorMeta : classMeta.constructors) {
            dest.add(getInstantiationSourceCode(classMeta, constructorMeta));
        }
        return dest;
    }

    @Override
    public String getFirstInstantiationSourceCode(ClassMeta classMeta) {
        return getInstantiationSourceCode(classMeta,
                getFirstConstructor(classMeta));
    }

    @Override
    public String getInstantiationSourceCode(ClassMeta classMeta, ConstructorMeta constructorMeta) {
        StringBuilder buf = new StringBuilder();
        if (constructorMeta == null) {
            buf.append(StringValue.Tab);
            buf.append(StringValue.Tab);
            buf.append(classMeta.name);
            buf.append(" target = null");
            buf.append(StringValue.Semicolon);
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        } else {
            int len = constructorMeta.argTypes.size();
            for (int i = 0; i < len; i++) {
                buf.append(StringValue.Tab);
                buf.append(StringValue.Tab);
                String typeName = constructorMeta.argTypes.get(i).name;
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
                buf.append(StringValue.CarriageReturn);
                buf.append(StringValue.LineFeed);
            }
            buf.append(StringValue.Tab);
            buf.append(StringValue.Tab);
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
            buf.append(StringValue.CarriageReturn);
            buf.append(StringValue.LineFeed);
        }
        return buf.toString();
    }

    ConstructorMeta getFirstConstructor(ClassMeta classMeta) {
        if (classMeta.constructors == null
                || classMeta.constructors.size() == 0) {
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
