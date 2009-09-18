package com.googlecode.plugin.junithelper.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.ui.IWorkbenchWindow;

public class ResourceSynchronizerUtil
{

	public static boolean accessSynchronizeServer(IWorkbenchWindow window,
			String param)
	{

		// access ResourceSynchronizer refresh server
		String refreshUrl = "http://localhost:8386/refresh?";
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(refreshUrl + param);

		try
		{
			int status = httpClient.executeMethod(getMethod);
			if (status != 200)
			{
				return false;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;

	}

}
