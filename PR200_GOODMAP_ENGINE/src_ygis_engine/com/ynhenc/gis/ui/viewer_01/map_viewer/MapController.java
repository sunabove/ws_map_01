package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.Dimension;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.GisRegistry;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.GeoPoint;
import com.ynhenc.gis.model.shape.Mbr;
import com.ynhenc.gis.model.shape.PntShort;
import com.ynhenc.gis.model.shape.Projection;

public abstract class MapController extends GisComLib {

	public abstract boolean setZoomIn();

	public abstract boolean setZoomOut();

	public abstract boolean setZoomLevel( int zoomLevel );

	public abstract Projection getProjection(GisProject gisProject, int zoomLevelCurr, PntShort centroid, Dimension pixelSize) ;

	public abstract void setViewFullExtent();

	public final GisProject getGisProject() {
		return gisProject;
	}

	public final void setGisProject(GisProject gisProject) {
		this.gisProject = gisProject;
	}

	public final void setPan(int dx, int dy) {

		GisProject gisProject = this.getGisProject();

		Projection projection = gisProject.getProjection();

		double scale = projection.getScale();

		double sx = dx / scale; // spatial x
		double sy = -dy / scale; // spatial y

		Mbr mbr = projection.getMbr();

		double minx = mbr.getMinX() + sx ;
		double miny = mbr.getMinY() + sy ;
		double maxx = mbr.getMaxX() + sx ;
		double maxy = mbr.getMaxY() + sy ;

		Mbr newMBR = new Mbr( minx , miny, maxx, maxy );

		Dimension pixelSize = projection.getPixelSize();

		projection = Projection.getProject( newMBR, pixelSize);

		gisProject.setProjection(projection);

	}

	public final void setPan(String dir) {

		GisProject gisProject = this.getGisProject();

		Projection projection = gisProject.getProjection();

		Dimension pixelSize = projection.getPixelSize();

		if (pixelSize == null) {
			return;
		}

		int dx = pixelSize.width / 4, dy = pixelSize.height / 4;

		if (dir.equalsIgnoreCase("NW")) {
			dx = -dx;
			dy = -dy;
		} else if (dir.equalsIgnoreCase("N")) {
			dx = 0;
			dy = -dy;
		} else if (dir.equalsIgnoreCase("NE")) {
			dy = -dy;
		} else if (dir.equalsIgnoreCase("W")) {
			dx = -dx;
			dy = 0;
		} else if (dir.equalsIgnoreCase("E")) {
			dy = 0;
		} else if (dir.equalsIgnoreCase("SW")) {
			dx = -dx;
		} else if (dir.equalsIgnoreCase("S")) {
			dx = 0;
		} else if (dir.equalsIgnoreCase("SE")) {
			dy = dy + 0 ;
		}

		this.setPan(dx, dy);

	}

	public final Mbr getTopLevelMbr() {
		return this.getGisProject().getMapData_Base().getMbrTopLevelWithMargin( 0.1F );
	}

	protected MapController(GisProject gisProject) {
		super();
		this.gisProject = gisProject;
	}

	private GisProject gisProject;

}
