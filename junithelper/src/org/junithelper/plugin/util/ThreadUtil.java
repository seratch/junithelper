package org.junithelper.plugin.util;

public final class ThreadUtil
{
	public static final void sleep(long millisec)
	{
		try
		{
			Thread.sleep(millisec);
		} catch (InterruptedException ignore)
		{
		}
	}

}
