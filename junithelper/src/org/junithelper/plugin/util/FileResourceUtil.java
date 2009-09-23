/* 
 * Copyright 2009 Kazuhiro Sera. 
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * FileResourceUtil<br>
 * <br>
 * 
 * @author Kazuhiro Sera
 * @version 1.0
 */

public class FileResourceUtil
{

	/**
	 * Close resource safely.<br>
	 * 
	 * @param is
	 *            InputStream resource object
	 */
	public static void close(InputStream is)
	{
		try
		{
			if (is != null)
				is.close();
		} catch (Exception ignore)
		{
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param isr
	 *            InputStreamReader resource object
	 */
	public static void close(InputStreamReader isr)
	{
		try
		{
			if (isr != null)
				isr.close();
		} catch (Exception ignore)
		{
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param br
	 *            BufferedReader resource object
	 */
	public static void close(BufferedReader br)
	{
		try
		{
			if (br != null)
				br.close();
		} catch (Exception ignore)
		{
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param bis
	 *            BufferedInputStream resource object
	 */
	public static void close(BufferedInputStream bis)
	{
		try
		{
			if (bis != null)
				bis.close();
		} catch (Exception ignore)
		{
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param bis
	 *            FileOutputStream resource object
	 */
	public static void close(FileOutputStream fos)
	{
		try
		{
			if (fos != null)
				fos.close();
		} catch (Exception ignore)
		{
		}
	}

	/**
	 * Close resource safely.<br>
	 * 
	 * @param osw
	 *            OutputStreamWriter resource object
	 */
	public static void close(OutputStreamWriter osw)
	{
		try
		{
			if (osw != null)
				osw.close();
		} catch (Exception ignore)
		{
		}
	}

}