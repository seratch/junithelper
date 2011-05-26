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
package org.junithelper.core.file.impl;

import org.apache.commons.io.FileUtils;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.file.FileWriter;

import java.io.File;
import java.io.IOException;

public class CommonsIOFileWriter implements FileWriter {

	private File file;
	private String encoding = new Configulation().outputFileEncoding;

	public CommonsIOFileWriter(File file) {
		setWriteTarget(file);
	}

	@Override
	public FileWriter setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	@Override
	public FileWriter setWriteTarget(File file) {
		this.file = file;
		return this;
	}

	@Override
	public void writeText(String text) throws IOException {
		if (encoding != null) {
			FileUtils.writeStringToFile(file, text, encoding);
		} else {
			FileUtils.writeStringToFile(file, text);
		}
	}

	@Override
	public void writeText(String text, String encoding) throws IOException {
		FileUtils.writeStringToFile(file, text, encoding);
	}

}
