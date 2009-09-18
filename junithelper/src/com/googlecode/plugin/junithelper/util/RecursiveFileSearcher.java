package com.googlecode.plugin.junithelper.util;

import java.io.File;

public interface RecursiveFileSearcher
{

	public File[] listFiles(String directoryPath, String fileName);

	public File[] listFiles(String directoryPath, String fileNamePattern,
			int type, boolean isRecursive, int period);

	public void clear();

}
