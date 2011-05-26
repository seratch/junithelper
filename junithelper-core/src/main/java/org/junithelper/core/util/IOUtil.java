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

import org.junithelper.core.config.Configulation;
import org.junithelper.core.constant.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class IOUtil {

	private static final String DEFAULT_OUTPUT_FILE_ENCODING = new Configulation().outputFileEncoding;

	private IOUtil() {
	}

	public static InputStream getResourceAsStream(String name) {
		return new IOUtil().getClass().getClassLoader()
				.getResourceAsStream(name);
	}

	public static final String readAsString(InputStream is, String encoding)
			throws IOException {
		BufferedReader br = null;
		try {
			if (encoding == null) {
				encoding = DEFAULT_OUTPUT_FILE_ENCODING;
			}
			br = new BufferedReader(new InputStreamReader(is, encoding));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(StringValue.CarriageReturn);
				sb.append(StringValue.LineFeed);
			}
			return sb.toString();
		} finally {
			IOUtil.close(br);
		}
	}

	public static final List<String> readAsLineList(InputStream is)
			throws IOException {
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
