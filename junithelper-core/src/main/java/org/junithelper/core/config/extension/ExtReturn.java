package org.junithelper.core.config.extension;

import java.util.ArrayList;
import java.util.List;

public class ExtReturn {

	public ExtReturn(String canonicalClassName) {
		this.canonicalClassName = canonicalClassName;
	}

	public String canonicalClassName;

	public List<String> asserts = new ArrayList<String>();

}
