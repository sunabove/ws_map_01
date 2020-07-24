package com.ynhenc.gis.web;

import java.io.File;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.FileManager;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class MapRegistry extends GisComLib {

	public String getGisProjectFilePath() {
		return this.gisProjectFilePath;
	}

	public boolean setGisProjectFilePath(String gisProjectFilePath) {
		this.gisProjectFilePath = gisProjectFilePath;

		this.debug("GisProjectFilePath = " + gisProjectFilePath);

		this.gisProject = null;

		GisProject gisProject = null ;

		try {
			gisProject = this.createGisProject();
		} catch (Exception e) {
			this.debug( e );
		}

		if (gisProject != null) {
			this.gisProject = gisProject;
		}

		return gisProject != null;
	}

	public GisProject getGisProject() {

		return this.gisProject;

	}

	private synchronized GisProject createGisProject() throws Exception {

		String filePath = this.getGisProjectFilePath();

		// test code
		File file = new File(filePath);

		MapViewer mapViewer = null;

		GisProjectOpener opener = new GisProjectOpener( mapViewer );

		GisProjectFileFormat gisProjectFileFormat = opener.openGisProjectFile(file);

		opener.init_GisProjectOpenFile(gisProjectFileFormat );

		GisProject gisProject = gisProjectFileFormat.getGisProject();

		GisProjectOption option = gisProject.getGisProjectOption();

		if (option != null) {
			// 중앙선을 무조건 웹에서는 안 보이게 함.
			option.setCrossLine_Show(false);
			option.setLogo_Show(false);
		}

		return gisProject;

	}

	public synchronized boolean getLoadGisProject() {

		try {
			this.gisProject = this.createGisProject();
		} catch (Exception e) {
			this.debug(e);
			this.gisProject = null;

			return false;
		}

		return true;
	}

	private MapRegistry() {
		this.getLoadGisProject();
	}

	private String gisProjectFilePath = "/home/gis/ygis_map/ccc.xml";

	private GisProject gisProject;

	public static MapRegistry getMapRegistry() {
		return mapRegistry;
	}

	private static final MapRegistry mapRegistry = new MapRegistry();

}
