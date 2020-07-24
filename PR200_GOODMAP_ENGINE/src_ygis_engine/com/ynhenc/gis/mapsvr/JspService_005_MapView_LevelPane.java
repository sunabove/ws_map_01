package com.ynhenc.gis.mapsvr;

import java.awt.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.web.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;

import com.ynhenc.gis.web.*;

public class JspService_005_MapView_LevelPane extends JspService_000_MapObject {

	public JspService_005_MapView_LevelPane(HttpServletRequest request) {
		super(request);
	}

	@Override
	public void getService() {

		com.ynhenc.gis.web.Request req = this.getRequest();

		GisProject gisProject = this.getGisProject();

		if (gisProject != null) {

			int zoomLevelCurr = req.getInt("zoomLevelCurr", gisProject.getZoomLevelMax());

			MapTileGroup mapTileGroup = gisProject.getMapTileGroup(zoomLevelCurr);

			req.setAttribute("mapTileGroup", mapTileGroup);

		} else {
			this.debug("GIS PROJECT is null.");
		}

	}

}
