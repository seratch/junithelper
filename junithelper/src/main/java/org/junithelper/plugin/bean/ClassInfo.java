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
package org.junithelper.plugin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassInfo<br>
 * <br>
 * Information about test target class<br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class ClassInfo {

	/**
	 * Class name
	 */
	public String name;

	/**
	 * Import types
	 */
	public List<String> importList = new ArrayList<String>();

	/**
	 * Public methods info
	 */
	public List<MethodInfo> methods = new ArrayList<MethodInfo>();

}
