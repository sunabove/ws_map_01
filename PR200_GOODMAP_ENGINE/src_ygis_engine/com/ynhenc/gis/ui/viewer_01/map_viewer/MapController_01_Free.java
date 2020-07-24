package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.Dimension;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.GisRegistry;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.GeoPoint;
import com.ynhenc.gis.model.shape.Mbr;
import com.ynhenc.gis.model.shape.PntShort;
import com.ynhenc.gis.model.shape.Projection;

public class MapController_01_Free extends MapController {

	@Override
	public boolean setZoomIn() {

		GisProject gisProject = this.getGisProject();

		Projection projection = gisProject.getProjection();

		Mbr mbr = projection.getMbr();

		double pixelWidth = projection.getPixelWidth();
		double pixelHeight = projection.getPixelHeight();

		double percent = 0.25 ;

		PntShort cenS = mbr.getCentroid();

		double cenSx = cenS.x;
		double cenSy = cenS.y;

		double sw = mbr.getWidth(); // spatial width
		double sh = mbr.getHeight(); // spatial height

		double wv = 0, hv = 0; // width variant and height variant

		if (sw < sh) {
			wv = (sw / 2) * percent;
			hv = (wv * pixelHeight) / pixelWidth;
		} else {
			hv = (sh / 2) * percent;
			wv = (hv * pixelWidth) / pixelHeight;
		}

		if (mbr.getMinX() + wv >= cenSx || mbr.getMinY() + hv >= cenSy) {
			return false;
		}

		Mbr newMBR = new Mbr(mbr.getMinX() + wv, mbr.getMinY() + hv, mbr.getMaxX() - wv,
				mbr.getMaxY() - hv);

		projection = Projection.getProject(newMBR, pixelWidth, pixelHeight);

		gisProject.setProjection(projection);

		return true;

	}

	@Override
	public boolean setZoomOut() {

		GisProject gisProject = this.getGisProject();

		Projection projection = gisProject.getProjection();
		Mbr mbr = projection.getMbr();

		double pixelWidth = projection.getPixelWidth();
		double pixelHeight = projection.getPixelHeight();

		double percent = .25 ;

		double sw = mbr.getWidth(); // spatial width
		double sh = mbr.getHeight(); // spatial height

		double wv = 0, hv = 0; // width variant and height variant

		if (sw < sh) {

			wv = (sw / 2) * percent;

			hv = (wv * pixelHeight) / pixelWidth;

		} else {

			hv = (sh / 2) * percent;

			wv = (hv * pixelWidth) / pixelHeight;

		}

		Mbr newMBR = new Mbr(mbr.getMinX() - wv, mbr.getMinY() - hv, mbr.getMaxX() + wv,
				mbr.getMaxY() + hv);

		projection = Projection.getProject(newMBR, pixelWidth, pixelHeight);

		gisProject.setProjection(projection);

		return true;

	}

	@Override
	public boolean setZoomLevel( int zoomLevel ) {
		// do nothing !!!!!!!
		return true;
	}

	@Override
	public Projection getProjection(GisProject gisProject, int zoomLevelCurr, PntShort centroid, Dimension pixelSize)  {
		return null;
	}


	@Override
	public void setViewFullExtent() {

		this.debug("Setting View All Box .....");

		GisProject gisProject = this.getGisProject();

		if (gisProject == null) {
			// do nothing !
		} else if (gisProject != null) {

			Projection projection = gisProject.getProjection();

			Dimension pixelSize = projection.getPixelSize();

			Mbr mbr = null;

			mbr = this.getGisProject().getMapData_Base()
					.getMbrTopLevelWithMargin(0.1F);

			if (mbr == null) {
				mbr = GisRegistry.getGisRegistry().getMbrGlobalDefault();
			}

			this.debug("VIEW ALL MBR = " + mbr);

			projection = Projection.getProject(mbr, pixelSize);

			gisProject.setProjection(projection);
		}

		this.debug("Done Setting View All Box.");

	}

	public MapController_01_Free(GisProject gisProject) {
		super( gisProject );
	}

}
