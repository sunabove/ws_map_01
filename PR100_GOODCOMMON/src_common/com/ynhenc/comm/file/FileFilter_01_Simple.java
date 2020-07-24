package com.ynhenc.comm.file;

import java.io.File;

public class FileFilter_01_Simple extends javax.swing.filechooser.FileFilter
		implements java.io.FileFilter {

	String[] extensions;
	String description;

	public FileFilter_01_Simple(String ext) {
		this(new String[] { ext }, null);
	}

	public FileFilter_01_Simple(String[] exts, String descr) {
		// clone and lowercase the extensions
		extensions = new String[exts.length];
		for (int i = exts.length - 1; i >= 0; i--) {
			extensions[i] = exts[i].toLowerCase();
		}
		// make suer we have a valid (if simplistic) description
		description = (descr == null) ? exts[0] + " files" : descr;
	}

	public boolean accept(File f) {
		// we always allow directories, regardless of their extension
		if (f.isDirectory()) {
			return true;
		}

		// ok, it's a regular file so check the extension
		String name = f.getName().toLowerCase();
		for (int i = extensions.length - 1; i >= 0; i--) {
			if (name.endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return description;
	}
}
