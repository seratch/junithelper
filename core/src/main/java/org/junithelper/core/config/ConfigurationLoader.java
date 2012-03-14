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
package org.junithelper.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

import org.junithelper.core.util.Assertion;

public class ConfigurationLoader {

    public Configuration load(String filepath) throws Exception {
        Assertion.on("filepath").mustNotBeNull(filepath);
        return load(new FileInputStream(new File(filepath)));
    }

    public Configuration load(InputStream is) throws Exception {
        Assertion.on("inputStream").mustNotBeNull(is);
        Properties props = new Properties();
        props.load(is);
        Configuration config = new Configuration();
        Set<Object> keys = props.keySet();
        for (Object key : keys) {
            String[] splitted = String.valueOf(key).split("\\.");
            if (splitted.length == 1) {
                Field f = Configuration.class.getDeclaredField(String.valueOf(key));
                if (f.getType().isEnum()) {
                    try {
                        Method m = f.getType().getDeclaredMethod("valueOf", String.class);
                        Object value = m.invoke(f.getType(), props.get(key));
                        f.set(config, value);
                    } catch (Exception e) {
                    }
                } else {
                    if (f.getType() == boolean.class) {
                        f.set(config, Boolean.valueOf(String.valueOf(props.get(key))));
                    } else if (f.getType() == int.class) {
                        f.set(config, Integer.valueOf(String.valueOf(props.get(key))));
                    } else {
                        f.set(config, props.get(key));
                    }
                }
            } else if (splitted.length == 2) {
                Field f = Configuration.class.getDeclaredField(String.valueOf(splitted[0]));
                Field target = f.getType().getDeclaredField(splitted[1]);
                if (target.getType() == boolean.class) {
                    target.set(f.get(config), Boolean.valueOf(String.valueOf(props.get(key))));
                } else {
                    target.set(f.get(config), props.get(key));
                }
            } else {
                throw new IllegalArgumentException("Invalid configuration - " + key);
            }
        }
        return config;
    }
}
