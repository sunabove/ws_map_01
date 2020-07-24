package com.ynhenc.gis.model.map;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.FileFilter_02_Folder;
import com.ynhenc.comm.file.FileSystem;
import com.ynhenc.comm.util.WebCopy;
import com.ynhenc.comm.util.XMLUtil;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.Projection;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class GisProjectCreator extends GisComLib {

	private MapViewer mapViewer;

	public GisProjectCreator(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	public GisProject getProjectNew() throws Exception {
		File projectFolder = this.getProjectNew_FolderCreatedIfNotExists();

		if (projectFolder != null && projectFolder.exists()) {
			GisProject gisProject = new GisProject();

			gisProject.setProjectFolderPath(projectFolder);

			this.getCreateDefaultFolder(gisProject);

			try {
				if (false) {
					this.createMapMetaDataFile(gisProject);
				}
			} catch (Exception e) {
				this.debug(e);
			}
			return gisProject;
		}

		return null;
	}

	private void createMapMetaDataFile(GisProject gisProject) throws Exception {
		File file = gisProject.getMapMetaDataFile();
		if (file == null) {
			String path = gisProject.getProjectFolderPath();
			file = new File(path, "MAP.DBF");
			FileOutputStream out = new FileOutputStream(file);
			InputStream in = this.getClass().getResourceAsStream("resources/MAP_SAMPLE.DBF");
			WebCopy webCopy = new WebCopy();
			webCopy.copy(in, out);
			out.close();
		}
	}

	private void getCreateDefaultFolder(GisProject gisProject) throws Exception {
		if (true) {
			// 맵레이어 폴더 생성.
			File folderMapLayer = gisProject.getFolderMapLayer();

			if (folderMapLayer != null && !folderMapLayer.exists()) {
				folderMapLayer.mkdir();
			}
		}

		if (true) {
			// 맵 아이콘 폴더 생성.
			File folderMapIcon = gisProject.getFolderMapIcon();

			if (folderMapIcon != null && !folderMapIcon.exists()) {
				folderMapIcon.mkdir();
			}

		}
	}

	private File getProjectNew_FolderCreatedIfNotExists() {
		MapViewer mapViewer = this.mapViewer;
		FileSystem fs = new FileSystem();
		JFileChooser folderChooser = this.getFolderChooser_GisProjectPath();

		File projectFolder = null;

		if (true) {
			projectFolder = fs.selectFile_Single(mapViewer, folderChooser);
		} else {
			projectFolder = fs.selectFolder_Single(mapViewer, folderChooser);
		}

		if (projectFolder != null && !projectFolder.exists()) {
			projectFolder.mkdir();
		}

		return projectFolder;
	}

	private JFileChooser getFolderChooser_GisProjectPath() {

		if (GisProjectCreator.fileChooser_folderChooser != null) {
			return GisProjectCreator.fileChooser_folderChooser;
		} else {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new FileFilter_02_Folder());
			chooser.setDialogTitle("프로젝트 폴더 선택");
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			GisProjectCreator.fileChooser_folderChooser = chooser;
			return chooser;
		}

	}

	private static JFileChooser fileChooser_folderChooser;

}
