package com.ynhenc.gis.mapsvr;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ynhenc.gis.model.map.GisProject;
import com.ynhenc.gis.model.shape.Mbr;
import com.ynhenc.gis.model.shape.PntShort;
import com.ynhenc.gis.model.shape.Projection;
import com.ynhenc.gis.projection.CoordinateConversion;
import com.ynhenc.gis.web.MapRegistry;
import com.ynhenc.gis.web.MapService;
import com.ynhenc.gis.web.Request;

public abstract class JspService_000_MapObject extends JspService {

	public JspService_000_MapObject( HttpServletRequest httpRequest) {
		super( httpRequest);
	}

	public void initService() {

		GisProject gisProject = this.getGisProject();

		Request req = this.getRequest();

		if (gisProject != null) {

			MapService mapService = new MapService();

			Dimension mapImageSize = mapService.getPixelSize(req, gisProject);

			int zoomLevelCurr = mapService.getZoomLevelCurr(req, gisProject);

			Projection projection = mapService.getProjection(req, mapImageSize, gisProject, zoomLevelCurr);

			if (true) { // topLevel 설정.

				Mbr mbrPhys = gisProject.getMbrPhys();

				Projection projectionPhys = Projection.getProject(mbrPhys, mapImageSize);

				Mbr mbrPhysScr = projectionPhys.getMbrScreen();

				double scalePhysScr = projectionPhys.getMapScale();

				req.setAttribute("mbrPhysScr", mbrPhysScr);

				req.setAttribute("scalePhysScr", 1.0 / scalePhysScr);

				req.setAttribute("topLevel", gisProject.getZoomLevelMax());

				this.debug("topLevel = " + gisProject.getZoomLevelMax());

			} // 끝. topLevel 설정.

			if (true) { // 현재 지도 값 설정.

				 Mbr mbr = projection.getMbr();

				CoordinateConversion coordConv = gisProject.getCoordinateConversion();

				PntShort min = mbr.getMin();
				min = coordConv.getConvertValue(min);
				PntShort max = mbr.getMax();
				max = coordConv.getConvertValue(max);
				PntShort cen = mbr.getCentroid();
				cen = coordConv.getConvertValue(cen);

				req.setAttribute("width", mapImageSize.width);
				req.setAttribute("height", mapImageSize.height);

				req.setAttribute("min", min);
				req.setAttribute("max", max);
				req.setAttribute("cen", cen);

			} // 긑. 현재 지도 값 설정.

		} else {
			this.debug("GIS PROJECT is null.");
		}

	}

	public final MapRegistry getMapRegistry() {

		MapRegistry mapRegistry = MapRegistry.getMapRegistry();

		ServletContext application = this.getApplication();

		if ( application.getAttribute("mapRegistry") == null) {

			if (false) {
				String gisProjectFilePathDefault = this.getGisProjectFilePathDefault();

				this.debug("GisProjectFilePathDefault A = " + gisProjectFilePathDefault);

				mapRegistry.setGisProjectFilePath(gisProjectFilePathDefault);
			}

			application.setAttribute("mapRegistry", mapRegistry);
			application.setAttribute("gisProject", mapRegistry.getGisProject());
		}

		return mapRegistry;

	}

	public String getGisProjectFilePathDefault() {
		return this.getRealPath("ygis_map/ccc.xml");
	}

	public synchronized GisProject getGisProject() {
		return this.getMapRegistry().getGisProject();
	}

	public CoordinateConversion getCoordinateConversion() {
		return this.getGisProject().getCoordinateConversion();
	}

}
