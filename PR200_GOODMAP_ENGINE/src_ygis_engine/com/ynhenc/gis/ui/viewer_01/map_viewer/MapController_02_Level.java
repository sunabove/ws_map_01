package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.UnitUtil;
import com.ynhenc.gis.GisRegistry;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.GeoPoint;
import com.ynhenc.gis.model.shape.Mbr;
import com.ynhenc.gis.model.shape.PntShort;
import com.ynhenc.gis.model.shape.Projection;

public class MapController_02_Level extends MapController {

	@Override
	public boolean setZoomIn() {
		this.setZoomLevel(this.getZoomLevelCurr() - 1);
		return true;
	}

	@Override
	public boolean setZoomOut() {
		this.setZoomLevel(this.getZoomLevelCurr() + 1);
		return true;
	}

	@Override
	public void setViewFullExtent() {
		this.debug("Setting View All Box .....");
		if (true) {
			GisProject gisProject = this.getGisProject();
			final Projection projection = gisProject.getProjection();
			final ZoomLevelList zoomLevelList = gisProject.getZoomLevelList();
			Dimension pixelSize = projection.getPixelSize();
			final Mbr mbrTopLevel = this.getTopLevelMbr();
			Mbr mbrNew = mbrTopLevel;
			int zoomLevelMax = zoomLevelList.getZoomLevelMax();
			Projection projectionNew = Projection.getProject(mbrNew, pixelSize);
			gisProject.getZoomLevelList().setZoomLevelCurr(zoomLevelMax);
			gisProject.setProjection(projectionNew);
		} else {
			this.setZoomLevel(this.getZoomLevelMax());
		}
		this.debug("Done Setting View All Box.");

	}

	private int getZoomLevelCurr() {
		GisProject gisProject = this.getGisProject();
		return gisProject.getZoomLevelList().getZoomLevelCurr();
	}

	private int getZoomLevelMax() {
		GisProject gisProject = this.getGisProject();
		return gisProject.getZoomLevelList().getZoomLevelMax();
	}

	@Override
	public boolean setZoomLevel(int zoomLevelCurr) {

		GisProject gisProject = this.getGisProject();

		final Projection projectionCurr = gisProject.getProjection();

		PntShort centroid = projectionCurr.getMbr().getCentroid();

		Dimension pixelSize = projectionCurr.getPixelSize();

		Projection projectionNew = this.getProjection(gisProject, zoomLevelCurr, centroid, pixelSize);

		gisProject.getZoomLevelList().setZoomLevelCurr(zoomLevelCurr);

		gisProject.setProjection(projectionNew);

		return true;

	}

	@Override
	public Projection getProjection(GisProject gisProject, int zoomLevelCurr, PntShort centroid, Dimension pixelSize) {

		Mbr mbrLevel = this.getMbrLevel(gisProject, zoomLevelCurr, centroid, pixelSize);

		if (mbrLevel != null) {

			return Projection.getProject(mbrLevel, pixelSize);

		} else {

			return null;

		}

	}

	public Mbr getMbrLevel( GisProject gisProject, int zoomLevelCurr, PntShort centroid, Dimension pixelSize ) {

		boolean localDebug = false;

		if (true) { // 확대 레벨 검증.
			int zoomLevelMax = this.getZoomLevelMax();
			zoomLevelCurr = zoomLevelCurr < 0 ? 0 : zoomLevelCurr;
			zoomLevelCurr = zoomLevelCurr > zoomLevelMax ? zoomLevelMax : zoomLevelCurr;
		}

		final Mbr mbrTopLevel = gisProject.getMbrTopLevel();
		Mbr mbrNew = null;

		if (mbrTopLevel != null) {
			final ZoomLevelList zoomLevelList = gisProject.getZoomLevelList();
			LevelScale levelScale = zoomLevelList.getLevelScale( zoomLevelCurr );

			if( localDebug ) {
				this.debug("LEVEL = " + levelScale.getLevel() + ", DIST = " + levelScale.getDist() + ", SCALE = " + levelScale.getScale());
			}

			MbrUnit mbrUnit = MbrUnit.getMbrUnit(levelScale, pixelSize);

			double sw = mbrUnit.getWidth();
			double sh = mbrUnit.getHeight();

			double scx = centroid.x;
			double scy = centroid.y;

			double minx = scx - sw / 2.0F;
			double miny = scy - sh / 2.0F;
			double maxx = scx + sw / 2.0F;
			double maxy = scy + sh / 2.0F;

			mbrNew = new Mbr(minx, miny, maxx, maxy);
		}

		if (localDebug) {
			this.debug("MBR TOP = " + mbrTopLevel);
			this.debug("MBR NEW = " + mbrNew);
		}

		return mbrNew;
	}

	public MapController_02_Level(GisProject gisProject) {
		super(gisProject);
	}

}
