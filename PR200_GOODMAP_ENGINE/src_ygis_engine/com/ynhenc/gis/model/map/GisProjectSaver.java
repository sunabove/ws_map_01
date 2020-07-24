package com.ynhenc.gis.model.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.FileFilter_01_Simple;
import com.ynhenc.comm.file.FileSystem;
import com.ynhenc.comm.util.XMLUtil;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.Projection;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class GisProjectSaver extends GisComLib {

	public GisProjectSaver(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	public void gisProject_02_Save() {
		FileSystem fs = new FileSystem();

		MapViewer mapViewer = this.mapViewer;

		final GisProject gisProject = mapViewer.getGisProject();

		JFileChooser fileChooser = mapViewer.getFileChooser_02_GisProject();

		fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		fileChooser.setDialogTitle( "프로젝트 저장");
		fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
		fileChooser.setCurrentDirectory( gisProject.getFolderProject() );

		final File file = fs.selectFile_Single( mapViewer, fileChooser );

		if (file != null && gisProject != null ) {
			// 파일 다이얼로그 선택후 까맣게 화면이 유지되는 것을 방지하기 위한 조치임.
			// this.updateUI();

			this.invokeLater(new Runnable() {
				public void run() {
					try {
						GisProjectSaver.this.gisProject_02_Save_Runnable(file, gisProject);
					} catch (Exception e) {
						GisProjectSaver.this.debug(e);
					}
				}
			});

		}

	}

	private void gisProject_02_Save_Runnable(File file, GisProject gisProject) throws Exception {

		if( ! file.getName().toUpperCase().endsWith( ".XML") ) {
			file = new File( file.getCanonicalPath() + ".xml" );
		}

		this.message("GisProject File = " + file.getCanonicalPath());

		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");

		GisProjectFileFormat gisProjectFileFormat = new GisProjectFileFormat();

		gisProjectFileFormat.setGisProject(gisProject);

		XMLUtil.toXml(gisProjectFileFormat, writer);

	}

	private MapViewer mapViewer;

}
