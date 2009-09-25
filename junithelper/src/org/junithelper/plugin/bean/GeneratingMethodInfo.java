package org.junithelper.plugin.bean;

import java.util.ArrayList;
import java.util.List;

public class GeneratingMethodInfo
{

	public List<String> importList = new ArrayList<String>();

	public String returnTypeName;
	public String returnTypeNameInMethodName;

	public String methodName;
	public boolean isStatic = false;
	public String testMethodName;

	public List<String> argTypeNames = new ArrayList<String>();
	public List<String> argTypeNamesInMethodName = new ArrayList<String>();

}
