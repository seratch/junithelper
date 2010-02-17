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
package org.junithelper.plugin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junithelper.plugin.exception.InvalidPreferenceException;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * FileResourceUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */

public final class FileResourceUtil {

	public static InputStream readFile(IFile file)
			throws InvalidPreferenceException {
		InputStream is = null;
		try {
			is = file.getContents();
		} catch (CoreException ignored) {
		} catch (NullPointerException e) {
			throw new InvalidPreferenceException();
		}
		return is;
	}

	/**
	 * Get detected encoding charset name
	 * 
	 * @param file
	 * @return encoding charset name
	 * @throws InvalidPreferenceException
	 */
	public static String detectEncoding(IFile file)
			throws InvalidPreferenceException {
		InputStream is = null;
		String encoding = null;
		try {
			is = FileResourceUtil.readFile(file);
			UniversalDetector detector = new UniversalDetector(null);
			byte[] buf = new byte[4096];
			int nread;
			while ((nread = is.read(buf)) > 0 && !detector.isDone())
				detector.handleData(buf, 0, nread);
			detector.dataEnd();
			encoding = detector.getDetectedCharset();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(is);
			if (encoding == null) {
				return Charset.defaultCharset().name();
			}
		}
		return encoding;
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param is
	 *            InputStream resource object
	 */
	public static void close(InputStream is) {
		try {
			if (is != null)
				is.close();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param isr
	 *            InputStreamReader resource object
	 */
	public static void close(InputStreamReader isr) {
		try {
			if (isr != null)
				isr.close();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param br
	 *            BufferedReader resource object
	 */
	public static void close(BufferedReader br) {
		try {
			if (br != null)
				br.close();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param bis
	 *            BufferedInputStream resource object
	 */
	public static void close(BufferedInputStream bis) {
		try {
			if (bis != null)
				bis.close();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param bis
	 *            FileOutputStream resource object
	 */
	public static void close(FileOutputStream fos) {
		try {
			if (fos != null)
				fos.close();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param osw
	 *            OutputStreamWriter resource object
	 */
	public static void close(OutputStreamWriter osw) {
		try {
			if (osw != null)
				osw.close();
		} catch (Exception ignore) {
		}
	}

}
