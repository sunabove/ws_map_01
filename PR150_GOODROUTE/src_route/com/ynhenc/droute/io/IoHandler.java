package com.ynhenc.droute.io;

import java.io.File;

import javax.swing.JFileChooser;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.*;

public abstract class IoHandler extends GisComLib {

	public JFileChooser getFileChooserSave( String title ) {
		return this.getFileChooser( JFileChooser.SAVE_DIALOG , title, JFileChooser.FILES_ONLY );
	}

	public JFileChooser getFileChooserOpen( String title ) {
		return this.getFileChooser( JFileChooser.OPEN_DIALOG , title, JFileChooser.FILES_ONLY );
	}

	private JFileChooser getFileChooser( int dialogType, String title, int fileSelectionMode ) {

		JFileChooser fileChooser = this.getFileChooser();

		fileChooser.setDialogType( dialogType );
		fileChooser.setDialogTitle( title );
		fileChooser.setFileSelectionMode( fileSelectionMode );
		if( this.CURR_DIR != null ) {
			fileChooser.setCurrentDirectory( CURR_DIR );
		}

		return fileChooser;

	}

	private JFileChooser getFileChooser() {
		FileChooserFactory factory = FileChooserFactory.getFileChooserFactory();
		return factory.getJFileChooser(this.getFileFilter());
	}

	public abstract FileFilter_01_Simple getFileFilter();

	private static File CURR_DIR = null;

}
