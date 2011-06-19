package org.junithelper.core.config.extension;

import java.util.ArrayList;
import java.util.List;

public class ExtArg {

	public ExtArg(String canonicalClassName) {
		this.canonicalClassName = canonicalClassName;
	}

	public String getCanonicalClassNameInMethodName() {
		String[] splitted = canonicalClassName.replaceFirst("java\\.lang\\.", "").split("\\.");
		StringBuilder buf = new StringBuilder();
		if (splitted.length == 1) {
			buf.append(splitted[0]);
		} else {
			for (int i = 0; i < (splitted.length - 1); i++) {
				buf.append(splitted[i].subSequence(0, 1));
			}
			buf.append(splitted[splitted.length - 1]);
		}
		return buf.toString();
	}

	public String canonicalClassName;

	public List<String> importList = new ArrayList<String>();

	public List<ExtArgPattern> patterns = new ArrayList<ExtArgPattern>();

}
