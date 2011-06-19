package org.junithelper.core.config.extension;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ExtConfigurationParserHandler extends DefaultHandler {

	private ExtConfiguration config;

	private ExtArg currentArg;

	private ExtArgPattern currentArgPattern;

	private ExtReturn currentReturn;

	private String tempValue;

	public ExtConfiguration getExtConfiguration() {
		return this.config;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempValue = new String(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (name.equals("generator-ext")) {
			config = new ExtConfiguration();
		} else if (name.equals("arg")) {
			if (attributes.getValue("class") != null) {
				String className = attributes.getValue("class");
				currentArg = new ExtArg(className);
				return;
			}
		} else if (name.equals("pattern")) {
			if (attributes.getValue("name") != null) {
				String patternName = attributes.getValue("name");
				currentArgPattern = new ExtArgPattern(currentArg, patternName);
				return;
			}
		} else if (name.equals("return")) {
			if (attributes.getValue("class") != null) {
				String className = attributes.getValue("class");
				currentReturn = new ExtReturn(className);
				return;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		if (name.equals("arg")) {
			config.extArgs.add(currentArg);
			currentArg = null;
		} else if (name.equals("pattern")) {
			currentArg.patterns.add(currentArgPattern);
			currentArgPattern = null;
		} else if (name.equals("return")) {
			config.extReturns.add(currentReturn);
			currentReturn = null;
		} else if (name.equals("import")) {
			currentArg.importList.add(tempValue);
		} else if (name.equals("pre-assign")) {
			currentArgPattern.preAssignCode = tempValue;
		} else if (name.equals("assign")) {
			currentArgPattern.assignCode = tempValue;
		} else if (name.equals("post-assign")) {
			currentArgPattern.postAssignCode = tempValue;
		} else if (name.equals("assert")) {
			currentReturn.asserts.add(tempValue);
		}
	}

}
