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
package org.junithelper.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junithelper.core.config.Configuration;

public final class IOUtil {

    private static final String DEFAULT_OUTPUT_FILE_ENCODING = new Configuration().outputFileEncoding;

    private IOUtil() {
    }

    public static InputStream getResourceAsStream(String name) {
        Assertion.on("name").mustNotBeNull(name);
        return new IOUtil().getClass().getClassLoader().getResourceAsStream(name);
    }

    public static final String readAsString(InputStream is, String encoding) throws IOException {
        Assertion.on("is(InputStream)").mustNotBeNull(is);
        if (encoding == null) {
            encoding = DEFAULT_OUTPUT_FILE_ENCODING;
        }
        return IOUtils.toString(is);
    }

    public static final List<String> readAsLineList(InputStream is) throws IOException {
        List<String> dest = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                dest.add(line);
            }
            return dest;
        } finally {
            IOUtil.close(br);
        }
    }

    public static final void close(InputStream is) {
        try {
            if (is != null)
                is.close();
        } catch (Exception ignore) {
        }
    }

    public static final void close(InputStreamReader isr) {
        try {
            if (isr != null)
                isr.close();
        } catch (Exception ignore) {
        }
    }

    public static final void close(BufferedReader br) {
        try {
            if (br != null)
                br.close();
        } catch (Exception ignore) {
        }
    }

    public static final void close(BufferedInputStream bis) {
        try {
            if (bis != null)
                bis.close();
        } catch (Exception ignore) {
        }
    }

    public static final void close(FileOutputStream fos) {
        try {
            if (fos != null)
                fos.close();
        } catch (Exception ignore) {
        }
    }

    public static final void close(OutputStreamWriter osw) {
        try {
            if (osw != null)
                osw.close();
        } catch (Exception ignore) {
        }
    }

}
