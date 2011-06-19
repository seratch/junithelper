package org.junithelper.core.config.extension;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ExtConfigurationLoader {

	public static final String EXT_CONFIGURATION_XML = "junithelper-extension.xml";

	public ExtConfiguration load(String filepath) throws Exception {
		return load(new FileInputStream(new File(filepath)));
	}

	public ExtConfiguration load(InputStream inputStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ExtConfigurationParserHandler handler = new ExtConfigurationParserHandler();
		parser.parse(inputStream, handler);
		return handler.getExtConfiguration();
	}

}
