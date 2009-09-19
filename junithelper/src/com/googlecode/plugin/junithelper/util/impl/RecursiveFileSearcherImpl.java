package com.googlecode.plugin.junithelper.util.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import com.googlecode.plugin.junithelper.STR;
import com.googlecode.plugin.junithelper.util.RecursiveFileSearcher;

public class RecursiveFileSearcherImpl implements RecursiveFileSearcher
{

	public static final int TYPE_FILE_OR_DIR = 1;
	public static final int TYPE_FILE = 2;
	public static final int TYPE_DIR = 3;

	public File[] listFiles(String directoryPath, String fileName)
	{
		// replace * to wildcard
		if (fileName != null)
		{
			fileName = fileName.replace(STR.DOT, STR.RegExp.DOT);
			fileName = fileName.replace(STR.ASTERISK, STR.DOT + STR.ASTERISK);
		}
		return listFiles(directoryPath, fileName, TYPE_FILE, true, 0);
	}

	public File[] listFiles(String directoryPath, String fileNamePattern, int type,
			boolean isRecursive, int period)
	{

		File dir = new File(directoryPath);
		if (!dir.isDirectory())
		{
			throw new IllegalArgumentException("Not directory : " + dir.getAbsolutePath());
		}
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			File file = files[i];
			addFile(type, fileNamePattern, set, file, period);
			// recursive search
			// add to list if target is directory
			if (isRecursive && file.isDirectory())
			{
				listFiles(file.getAbsolutePath(), fileNamePattern, type, isRecursive,
						period);
			}
		}
		return (File[]) set.toArray(new File[set.size()]);
	}

	@SuppressWarnings("unchecked")
	private void addFile(int type, String match, TreeSet set, File file, int period)
	{
		switch (type)
		{
		case TYPE_FILE:
			if (!file.isFile())
			{
				return;
			}
			break;
		case TYPE_DIR:
			if (!file.isDirectory())
			{
				return;
			}
			break;
		}

		if (match != null && !file.getName().matches(match))
			return;

		// if specified modified date
		if (period != 0)
		{
			// modified date
			Date lastModifiedDate = new Date(file.lastModified());
			String lastModifiedDateStr = new SimpleDateFormat("yyyyMMdd")
					.format(lastModifiedDate);

			// specified date (milli sec)
			long oneDayTime = 24L * 60L * 60L * 1000L;
			long periodTime = oneDayTime * Math.abs(period);
			Date designatedDate = new Date(System.currentTimeMillis() - periodTime);
			String designatedDateStr = new SimpleDateFormat("yyyyMMdd")
					.format(designatedDate);
			if (period > 0)
			{
				if (lastModifiedDateStr.compareTo(designatedDateStr) < 0)
				{
					return;
				}
			} else
			{
				if (lastModifiedDateStr.compareTo(designatedDateStr) > 0)
				{
					return;
				}
			}
		}
		// add to list if matched all conditions
		set.add(file);

	}

	private TreeSet<File> set = new TreeSet<File>();

	public void clear()
	{
		set.clear();
	}
}
