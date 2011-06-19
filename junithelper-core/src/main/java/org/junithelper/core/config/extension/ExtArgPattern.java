package org.junithelper.core.config.extension;

public class ExtArgPattern {

	public ExtArgPattern(ExtArg extArg, String name) {
		this.extArg = extArg;
		this.name = name;
	}

	public ExtArg extArg;

	public String getNameWhichFirstCharIsUpper() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public String getName() {
		return name;
	}

	private String name;

	public String preAssignCode;

	public String assignCode;

	public String postAssignCode;

}
