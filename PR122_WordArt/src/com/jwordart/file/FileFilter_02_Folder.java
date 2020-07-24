package com.jwordart.file;

import java.io.*;

public class FileFilter_02_Folder extends javax.swing.filechooser.FileFilter implements
		java.io.FileFilter {

	public FileFilter_02_Folder() {
		super();
	}

	@Override
	public boolean accept(File file) {
		return file.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folders Only";
	}

}
