/* 
 * Copyright 2009 junithelper.org. 
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
package org.junithelper.plugin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * GeneratingMethodInfo<br>
 * <br>
 * Information about test target developing punlic methods<br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class GeneratingMethodInfo
{

	/**
	 * Import types
	 */
	public List<String> importList = new ArrayList<String>();

	/**
	 * Return type name
	 */
	public String returnTypeName = "";

	/**
	 * Return type name converted
	 */
	public String returnTypeNameInMethodName = "";

	/**
	 * Test target method name
	 */
	public String methodName = "";

	/**
	 * Test target method is static or not
	 */
	public boolean isStatic = false;

	/**
	 * Test method name
	 */
	public String testMethodName = "";

	/**
	 * Test target method arg type names
	 */
	public List<String> argTypeNames = new ArrayList<String>();

	/**
	 * Test target method arg type names converted
	 */
	public List<String> argTypeNamesInMethodName = new ArrayList<String>();

}
