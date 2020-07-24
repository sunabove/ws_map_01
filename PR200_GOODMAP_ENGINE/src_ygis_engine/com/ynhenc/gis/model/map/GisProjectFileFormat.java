package com.ynhenc.gis.model.map;

import java.io.File;

public class GisProjectFileFormat {

	public File getFileOpened() {
		return this.fileOpened;
	}

	public void setFileOpened(File fileOpened) {
		this.fileOpened = fileOpened;
	}

	public GisProject getGisProject() {
		return this.gisProject;
	}

	public void setGisProject(GisProject gisProject) {
		this.gisProject = gisProject;
	}

	public String getVersion() {
		return this.version;
	}

	private transient File fileOpened;

	private String version = "0.1.002";

	private GisProject gisProject;

}
