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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junithelper.core.util.Assertion;
import org.junithelper.core.util.IOUtil;
import org.mozilla.universalchardet.UniversalDetector;

class FileReaderCommonsIOImpl implements FileReader {

    @Override
    public InputStream getResourceAsStream(String name) {
        Assertion.on("name").mustNotBeNull(name);
        return FileReaderCommonsIOImpl.class.getClassLoader().getResourceAsStream(name);
    }

    @Override
    public String readAsString(File file) throws IOException {
        String readResult = FileUtils.readFileToString(file, getDetectedEncoding(file));
        return readResult;
    }

    @Override
    public String getDetectedEncoding(File file) {
        InputStream is = null;
        String encoding = null;
        try {
            is = new FileInputStream(file);
            UniversalDetector detector = new UniversalDetector(null);
            byte[] buf = new byte[4096];
            int nread;
            while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            encoding = detector.getDetectedCharset();
        } catch (IOException e) {
            // nothing to do
        } finally {
            IOUtil.close(is);
            if (encoding == null) {
                return Charset.defaultCharset().name();
            }
        }
        return encoding;
    }

}
