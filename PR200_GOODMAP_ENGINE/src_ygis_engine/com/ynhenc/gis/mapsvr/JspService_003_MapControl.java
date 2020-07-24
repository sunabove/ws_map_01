package com.ynhenc.gis.mapsvr;

import java.awt.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.web.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.projection.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;

import com.ynhenc.gis.web.*;

public class JspService_003_MapControl extends JspService_000_MapObject {

	public JspService_003_MapControl(HttpServletRequest request) {
		super(request);

		this.initService();
	}

	@Override
	public void getService() {

		com.ynhenc.gis.web.Request req = this.getRequest();

		GisProject gisProject = this.getGisProject();

		if (gisProject != null) {

			// do nothing !!!

		} else {
			this.debug("GIS PROJECT is null.");
		}

	}

}
