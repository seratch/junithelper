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
 * Information about test target developingd punlic methods<br>
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
	 * Return type converted
	 */
	public ReturnType returnType = new ReturnType();

	/**
	 * Return type
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class ReturnType
	{
		public String name;
		public String nameInMethodName;
		public List<String> generics;
	}

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
	 * Test target method arg types
	 */
	public List<ArgType> argTypes = new ArrayList<ArgType>();

	/**
	 * Arg type
	 * 
	 * @author Kazuhiro Sera
	 */
	public static class ArgType
	{
		public String name;
		public String nameInMethodName;
		public List<String> generics;
	}

}