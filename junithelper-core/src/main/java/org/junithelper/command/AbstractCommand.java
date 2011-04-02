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
package org.junithelper.command;

import org.junithelper.core.config.Configulation;
import org.junithelper.core.config.ConfigulationLoader;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.file.FileSearcher;
import org.junithelper.core.file.impl.CommonsIOFileSearcher;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.extractor.ClassMetaExtractor;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.Stdout;
import org.junithelper.core.util.UniversalDetectorUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand {

    protected static Configulation overrideConfigulation(Configulation config)
            throws Exception {
        String configFile = System.getProperty("junithelper.configProperties");
        if (configFile != null) {
            config = new ConfigulationLoader().load(configFile);
        }
        return config;
    }

    protected static boolean skipConfirming() {
        String value = System.getProperty("junithelper.skipConfirming");
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            return false;
        }
    }

    protected static int confirmToExecute() {
        if (skipConfirming()) {
            return 0;
        }
        Stdout.p("");
        BufferedReader reader = null;
        try {
            while (true) {
                reader = new BufferedReader(new InputStreamReader(System.in), 1);
                Stdout.p("Are you sure?(y/n)");
                String input = reader.readLine();
                if (input.equals("y")) {
                    break;
                } else if (input.equals("n")) {
                    Stdout.p("Canceled.");
                    return 1;
                }
            }
        } catch (Exception e) {
        } finally {
            IOUtil.close(reader);
        }
        return 0;
    }

    protected static List<File> findTargets(Configulation config,
                                            String dirOrFile) throws Exception {
        dirOrFile = dirOrFile.replaceAll("\\\\", "/");
        List<File> dest = new ArrayList<File>();
        ClassMetaExtractor extractor = new ClassMetaExtractor(config);
        if (dirOrFile.matches(".+\\.java$")) {
            File file = new File(dirOrFile);
            String encoding = UniversalDetectorUtil.getDetectedEncoding(file);
            ClassMeta classMeta = extractor.extract(IOUtil.readAsString(
                    new FileInputStream(file), encoding));
            if (!classMeta.isAbstract) {
                dest.add(file);
            }
        } else {
            List<File> javaFiles = new ArrayList<File>();
            FileSearcher fileSearcher = new CommonsIOFileSearcher();
            javaFiles = fileSearcher.searchFilesRecursivelyByName(dirOrFile,
                    RegExp.FileExtension.JavaFile);
            for (File file : javaFiles) {
                String encoding = UniversalDetectorUtil
                        .getDetectedEncoding(file);
                ClassMeta classMeta = extractor.extract(IOUtil.readAsString(
                        new FileInputStream(file), encoding));
                if (!classMeta.name.matches(".*Test$") && !classMeta.isAbstract) {
                    dest.add(file);
                }
            }
        }
        return dest;
    }

    protected static String getDirectoryPathOfProductSourceCode(
            Configulation config) {
        return "/"
                + config.directoryPathOfProductSourceCode
                .replaceFirst("^/", "").replaceFirst("/$", "") + "/";
    }

    protected static String getDirectoryPathOfTestSourceCode(
            Configulation config) {
        return "/"
                + config.directoryPathOfTestSourceCode.replaceFirst("^/", "")
                .replaceFirst("/$", "") + "/";
    }

}
