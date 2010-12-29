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

import java.io.InputStream;
import java.nio.charset.Charset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.ide.IDE;
import org.junithelper.core.util.IOUtil;
import org.junithelper.core.util.Stderr;
import org.junithelper.plugin.exception.InvalidPreferenceException;
import org.mozilla.universalchardet.UniversalDetector;

public final class EclipseIFileUtil {

	public static InputStream getInputStreamFrom(IFile file)
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

	public static String getDetectedEncodingFrom(IFile file,
			String defaultEncoding) {
		InputStream is = null;
		String encoding = defaultEncoding == null ? Charset.defaultCharset()
				.name() : defaultEncoding;
		try {
			is = EclipseIFileUtil.getInputStreamFrom(file);
			UniversalDetector detector = new UniversalDetector(null);
			byte[] buf = new byte[4096];
			int nread;
			while ((nread = is.read(buf)) > 0 && !detector.isDone())
				detector.handleData(buf, 0, nread);
			detector.dataEnd();
			encoding = detector.getDetectedCharset();
		} catch (Exception e) {
			Stderr.p("EclipseIFileUtil.getDetectedEncodingFrom(IFile): "
					+ e.getClass().getName() + "," + e.getLocalizedMessage());
		} finally {
			IOUtil.close(is);
			if (encoding == null) {
				return defaultEncoding;
			}
		}
		return encoding;
	}

	public static IEditorDescriptor getIEditorDescriptorFrom(IFile file)
			throws Exception {
		return IDE.getEditorDescriptor(file.getName());
	}

}
