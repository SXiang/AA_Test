package com.acl.qa.taf.tool;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {
	String ext;
	public FileFilter(String ext) {
		this.ext = "." + ext;
	}
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(ext);
	}
}