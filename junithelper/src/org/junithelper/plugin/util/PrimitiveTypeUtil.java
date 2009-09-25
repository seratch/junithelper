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
package org.junithelper.plugin.util;

/**
 * PrimitiveTypeUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */
public class PrimitiveTypeUtil
{

	/**
	 * Judge the arg type name is primitive type or not.
	 * 
	 * @param typeName
	 * @return
	 */
	public static boolean isPrimitive(String typeName)
	{
		if (typeName.equals("byte"))
			return true;
		if (typeName.equals("short"))
			return true;
		if (typeName.equals("int"))
			return true;
		if (typeName.equals("long"))
			return true;
		if (typeName.equals("char"))
			return true;
		if (typeName.equals("float"))
			return true;
		if (typeName.equals("double"))
			return true;
		if (typeName.equals("boolean"))
			return true;
		if (typeName.equals("void"))
			return true;
		return false;
	}

	/**
	 * Get primitive class object from type name.
	 * 
	 * @param typeName
	 * @return
	 */
	public static Class<?> getPrimitiveClass(String typeName)
	{
		if (typeName.equals("byte"))
			return byte.class;
		if (typeName.equals("short"))
			return short.class;
		if (typeName.equals("int"))
			return int.class;
		if (typeName.equals("long"))
			return long.class;
		if (typeName.equals("char"))
			return char.class;
		if (typeName.equals("float"))
			return float.class;
		if (typeName.equals("double"))
			return double.class;
		if (typeName.equals("boolean"))
			return boolean.class;
		if (typeName.equals("void"))
			return void.class;
		throw new IllegalArgumentException("Not primitive type : " + typeName);
	}

	/**
	 * Get primitive type default value from type name.
	 * 
	 * @param typeName
	 * @return
	 */
	public static String getPrimitiveDefaultValue(String typeName)
	{
		if (typeName.equals("byte"))
			return "0";
		if (typeName.equals("short"))
			return "0";
		if (typeName.equals("int"))
			return "0";
		if (typeName.equals("long"))
			return "0L";
		if (typeName.equals("char"))
			return "'\u0000'";
		if (typeName.equals("float"))
			return "0.0F";
		if (typeName.equals("double"))
			return "0.0";
		if (typeName.equals("boolean"))
			return "false";
		if (typeName.equals("void"))
			return "void";
		throw new IllegalArgumentException("Not primitive type : " + typeName);
	}
}
