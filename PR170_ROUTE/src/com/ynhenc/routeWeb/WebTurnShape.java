package com.ynhenc.routeWeb;

import java.awt.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.web.Request;
import com.ynhenc.droute.*;
import com.ynhenc.droute.io.*;
import com.ynhenc.droute.map.*;
import com.ynhenc.droute.map.link.*;
import com.ynhenc.droute.render.Style;
import com.ynhenc.kml.*;

public class WebTurnShape extends GisComLib {

	public void toKml(JspWriter jspWriter, TurnShapeRequest turnShapeRequest, LinkShapeList linkShapeList) {

		if (linkShapeList != null) {
			KmlHandlerLinkShape kmlHandler = new KmlHandlerLinkShape();
			KmlStyle kmlStyle = null;
			if( true ) {
				GisComLib comLib = new GisComLib();

				String colorText = turnShapeRequest.getLineColor();
				Color lineColor = Color.black;
				if( this.isGood( colorText ) ) 	{
					lineColor = comLib.getColor( colorText , Color.black );
				}
				float lineWidth = turnShapeRequest.getLineWidth();

				kmlStyle = new KmlLineStyle( 0, lineColor, lineWidth );
			}

			try {
				kmlHandler.toKmlDocument(jspWriter, linkShapeList, kmlStyle );
			} catch (Exception e) {
				this.debug(e);
			}
		}

	}

	public LinkShapeList getLinkShapeList(TurnShapeRequest req ) {

		LinkShapeList linkShapeList = new LinkShapeList();

		if (true) {
			Long linkId = req.getS_link_id();
			if (linkId != null) {
				LinkShape linkShape = LinkShape.getLinkShape(linkId.longValue());
				linkShapeList.add(linkShape);
			}
		}

		if (true) {
			Long linkId = req.getE_link_id();
			if (linkId != null) {
				LinkShape linkShape = LinkShape.getLinkShape(linkId.longValue());
				linkShapeList.add(linkShape);
			}
		}

		return linkShapeList;
	}
}
