package com.ynhenc.gis.web;

import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import java.io.File;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.ImageUtil;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.renderer.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;

public class MapService extends GisComLib {

	public MapService() {
		super();
	}

	public int getOptimalZoomLevel( Request req , Mbr mbr ) {

		double width = mbr.getWidth();
		double height = mbr.getHeight();

		MapRegistry mapRegistry = MapRegistry.getMapRegistry();
		GisProject gisProject = mapRegistry.getGisProject();
		Dimension pixelSize = this.getPixelSize(req, gisProject);

		final int zoomLevelMax = gisProject.getZoomLevelMax();
		Projection projection;
		Mbr mbrScr;
		double w, h;

		for( int zoomLevelCurr = 0 ; zoomLevelCurr <= zoomLevelMax ; zoomLevelCurr ++ ) {
			projection = this.getProjection(req, pixelSize, gisProject, zoomLevelCurr );
			mbrScr = projection.getMbrScreen();
			w = mbrScr.getWidth();
			h = mbrScr.getHeight();
			if( w >= width && h >= height ) {
				return zoomLevelCurr;
			}
		}

		return zoomLevelMax ;
	}

	public BufferedImage getImage(Request req) throws Exception {
		// 데이터 설정.
		MapRegistry mapRegistry = MapRegistry.getMapRegistry();
		GisProject gisProject = mapRegistry.getGisProject();
		int zoomLevelCurr = this.getZoomLevelCurr(req, gisProject);
		Dimension pixelSize = this.getPixelSize(req, gisProject);
		Projection projection = this.getProjection(req, pixelSize, gisProject, zoomLevelCurr);
		Session session = req.getSession();
		Integer debug = req.getInt("debug", -1);
		// 끝. 데이터 설정.

		// 지도 페인팅.
		LayerPainter layerPainter = LayerPainter.newLayerPainter();
		BufferedImage img = ImageUtil.createImage(pixelSize.width, pixelSize.height);
		Graphics2D g2 = img.createGraphics();

		long then = System.currentTimeMillis();

		if ( true ) { // 배경 그리기.
			Color lyrBg = Color.white;
			Rectangle2D pixelRect = projection.getPixelRect();

			g2.setColor(lyrBg);
			g2.fill(pixelRect);
			g2.setClip(pixelRect);
		} // 끝. 배경 그리기.

		layerPainter.paint_01_GisProject(g2, gisProject, zoomLevelCurr, projection, req );

		if(debug > 0) {
			layerPainter.paint_07_RenderingTime(g2, projection, zoomLevelCurr, then);
		}

		if( false ) {
			// drawing center cross line
			int w = (int) projection.getPixelWidth();
			int h = (int) projection.getPixelHeight();
			int x = w / 2;
			int y = h / 2;
			int gap = 4 ;

			g2.setColor(Color.red);

			g2.drawLine( x - gap, y, x + gap , y );
			g2.drawLine( x , y - gap, x , y + gap );
			// end of drawing center cross line
		}

		if (false) { // 이미지 경계선 표시.
			g2.setColor(Color.black);
			g2.drawRect(0, 0, pixelSize.width - 1, pixelSize.height - 1);

		} // 이미지 경계선 표시.

		// 끝. 지도 페인팅.

		return img;

	}

	public Dimension getPixelSize(Request req, GisProject gisProject) {
		int width = req.getInt("width", 0);
		int height = req.getInt("height", 0);
		if (width * height > 1) {
			return new Dimension(width, height);
		} else {
			return gisProject.getMapPixelSizeDefault();
		}
	}

	public int getZoomLevelCurr(Request req, GisProject gisProject) {
		int zoomLevelCurr = req.getInt( "zoomLevelCurr", gisProject.getTopLevel() );

		if( zoomLevelCurr > gisProject.getTopLevel() ) {
			zoomLevelCurr = gisProject.getTopLevel();
		}

		if( zoomLevelCurr < gisProject.getMinLevel() ) {
			zoomLevelCurr = gisProject.getMinLevel() ;
		}

		return zoomLevelCurr;
	}

	public Projection getProjection(Request req, Dimension pixelSize, GisProject gisProject, int zoomLevelCurr) {
		PntShort centroid = null;

		if (centroid == null) {
			centroid = req.getPointConverted("mapX", "mapY", gisProject);
		}

		MapController_02_Level mapController = new MapController_02_Level(gisProject);

		if (centroid == null) {
			centroid = gisProject.getMbrTopLevel().getCentroid();
		}

		boolean localDebug = false ;

		if (localDebug) {
			this.debug("width=" + pixelSize.width);
			this.debug("height=" + pixelSize.height);
			this.debug("zoomLevelCurr=" + zoomLevelCurr);
			this.debug("cen=" + centroid);
		}

		return mapController.getProjection(gisProject, zoomLevelCurr, centroid, pixelSize);
	}

}
