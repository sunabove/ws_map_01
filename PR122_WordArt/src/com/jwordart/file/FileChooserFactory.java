package com.jwordart.file;

import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.jwordart.GisComLib;

public class FileChooserFactory extends GisComLib {

	private FileChooserFactory() {
	}

	public JFileChooser getJFileChooser(FileFilter fileFilter) {
		JFileChooser fileChooser = this.chooserList.get(fileFilter);
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(fileFilter);
			fileChooser.setMultiSelectionEnabled(false);
			this.chooserList.put(fileFilter, fileChooser);
		}
		return fileChooser;
	}

	public static FileChooserFactory getFileChooserFactory() {
		return fileChooserFactory;
	}

	private Hashtable<FileFilter, JFileChooser> chooserList = new Hashtable<FileFilter, JFileChooser>();

	private static final FileChooserFactory fileChooserFactory = new FileChooserFactory();

}
