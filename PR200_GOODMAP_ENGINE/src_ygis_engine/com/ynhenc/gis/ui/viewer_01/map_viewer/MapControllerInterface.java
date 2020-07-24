package com.ynhenc.gis.ui.viewer_01.map_viewer;

import com.ynhenc.gis.model.map.GisProject;

public interface MapControllerInterface {

	public abstract GisProject getGisProject();

	public abstract void setGisProject(GisProject gisProject);

	public abstract boolean setZoomIn();

	public abstract boolean setZoomOut();
	
	public abstract boolean setZoomLevel( int zoomLevel );

	public abstract void setViewFullExtent();

	public abstract void setPan(int dx, int dy);

	public abstract void setPan(String dir);

}