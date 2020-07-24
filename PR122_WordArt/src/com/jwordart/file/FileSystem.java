package com.jwordart.file;

import javax.swing.JFileChooser;

import com.jwordart.GisComLib;

import java.awt.Component;
import java.io.File;

public class FileSystem  extends GisComLib {

	public File selectFolder_Single(Component comp) {

		return this.selectFolder_Single( comp, this.getFolderChooser() );
	}

	public File selectFolder_Single(Component comp, JFileChooser folderChooser ) {

		int option = folderChooser.showOpenDialog(comp);

		if (option == JFileChooser.CANCEL_OPTION) {
			return null;
		} else {
			File file = folderChooser.getSelectedFile();
			if (file != null && file.isDirectory() ) {
				return file;
			} else {
				return null;
			}
		}

	}

	public File selectFile_Single(Component comp, JFileChooser fileChooser   ) {

		int option = -1 ;

		if( fileChooser.getDialogType() == JFileChooser.OPEN_DIALOG ) {
			option = fileChooser.showOpenDialog(comp);
		} else {
			option = fileChooser.showSaveDialog(comp);
		}

		if (option == JFileChooser.CANCEL_OPTION) {
			return null;
		} else {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				return file;
			} else {
				return null;
			}
		}

	}

	public File selectFile_Single(Component comp) {

		JFileChooser fileChooser = this.getFileChooser();

		return this.selectFile_Single(comp, fileChooser);

	}

	public File[] selectFile_Multiple(Component comp, JFileChooser fileChooser) {

		int option = fileChooser.showOpenDialog(comp);

		if (option == JFileChooser.CANCEL_OPTION) {
			return null;
		} else {
			File[] fileList = fileChooser.getSelectedFiles();
			return fileList;
		}

	}

	private JFileChooser getFolderChooser() {

		if ( FileSystem.chooser_02_Folder != null) {
			return FileSystem.chooser_02_Folder;
		} else {
			JFileChooser folderChooser = new JFileChooser();
			folderChooser
					.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			folderChooser.setFileFilter(new FileFilter_02_Folder());
			FileSystem.chooser_02_Folder = folderChooser;
			return folderChooser;
		}

	}

	private JFileChooser getFileChooser() {
		if (FileSystem.chooser_01_File != null) {
			return FileSystem.chooser_01_File;
		} else {
			JFileChooser fileChooser = new JFileChooser();
			FileSystem.chooser_01_File = fileChooser;
			return fileChooser;
		}
	}

	public FileSystem() {
		super();
	}

	// static member
	private static JFileChooser chooser_02_Folder;
	private static JFileChooser chooser_01_File;
	// end of static member

}
