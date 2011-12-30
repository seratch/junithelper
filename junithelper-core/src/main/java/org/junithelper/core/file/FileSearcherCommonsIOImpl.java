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
package org.junithelper.core.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.util.Assertion;

class FileSearcherCommonsIOImpl implements FileSearcher {

    @Override
    public List<File> searchFilesRecursivelyByName(String baseAbsoluteDir, String regexp) {
        Assertion.on("baseAbsoluteDir").mustNotBeEmpty(baseAbsoluteDir);
        File dir = new File(baseAbsoluteDir);
        IOFileFilter fileFilter = new RegexFileFilter(RegExp.Anything_ZeroOrMore_Min + regexp
                + RegExp.Anything_ZeroOrMore_Min);
        IOFileFilter dirFilter = new RegexFileFilter("[^(/\\.)]*");
        Collection<File> files = FileUtils.listFiles(dir, fileFilter, dirFilter);
        List<File> dest = new ArrayList<File>();
        for (File file : files) {
            dest.add(file);
        }
        return dest;
    }

}
