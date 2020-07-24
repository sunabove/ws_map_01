package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.*;
import java.io.*;

import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.ui.comp.*;

public class MapMetaDataPane extends JDbfGridPane {
	public GisProject getCurrGisProject() {
		return currGisProject;
	}

	public void setCurrGisProject(GisProject currGisProject) {
		this.currGisProject = currGisProject;
	}

	public MapViewer getMapViewer() {
		return mapViewer;
	}

	public void setMapViewer(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	private void checkGisProjectHasChanged() throws Exception {
		MapViewer mapViewer = this.getMapViewer();
		if( mapViewer != null ) {
			GisProject currGisProject = this.getCurrGisProject();
			GisProject newGisProject = mapViewer.getGisProject();
			if( currGisProject != newGisProject ) {
				this.setCurrGisProject( newGisProject );
				if( newGisProject != null) {
					File mapMetaDataFile = newGisProject.getMapMetaDataFile();
					this.setDbfFile(mapMetaDataFile);
				}
			}
		}
	}

	@Override
	public void paint( Graphics g ) {

		try {
			this.checkGisProjectHasChanged();
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		super.paint( g );
	}

	public MapMetaDataPane() {
		super();
	}

	private MapViewer mapViewer;
	private GisProject currGisProject;

}
