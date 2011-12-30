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
package org.junithelper.core.meta;

import org.junithelper.core.config.extension.ExtArgPattern;
import org.junithelper.core.config.extension.ExtReturn;

public class TestMethodMeta {

    public boolean isTypeTest;

    public boolean isInstantiationTest;

    public ClassMeta classMeta;

    public MethodMeta methodMeta;

    public ExtArgPattern extArgPattern;

    public ExtReturn extReturn;

    public ExceptionMeta testingTargetException;

}
