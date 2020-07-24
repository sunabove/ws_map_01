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
import com.ynhenc.droute.render.*;
import com.ynhenc.kml.*;

public class WebRoute extends GisComLib {

	public void toKml(JspWriter jspWriter, RouteRequest routeRequest, Path path) {

		if (path != null) {
			KmlHandlerPath kmlHandler = new KmlHandlerPath();
			try {
				kmlHandler.toKmlDocument(jspWriter, path);
			} catch (Exception e) {
				this.debug(e);
			}
		}

	}

	public Path getPath(RouteRequest routeRequest) {

		Path pathFirst = null;
		Path pathSecond = null;

		SrchOption srchOption = routeRequest.getSrchOption();

		Map map = this.getMap();
		if (map != null) {
			Double sx = routeRequest.getSx();
			Double sy = routeRequest.getSy();
			Double mx = routeRequest.getMx();
			Double my = routeRequest.getMy();
			Double ex = routeRequest.getEx();
			Double ey = routeRequest.getEy();

			if (sx != null && sy != null && ex != null && ey != null) {
				Vert s = Vert.getVert(sx.doubleValue(), sy.doubleValue());
				Vert e = Vert.getVert(ex.doubleValue(), ey.doubleValue());
				Node startNode = map.getNodeNearest(s, Find.Mode.START );
				Node endNode = map.getNodeNearest(e, Find.Mode.END );

				PathFinder pathFinder = this.getPathFinder( srchOption );
				if (startNode != null && endNode != null) {
					if ( mx == null || my == null) {
						pathFirst = pathFinder.getPath(map, startNode, endNode, srchOption);
					} else {
						Vert mVert = Vert.getVert(mx.doubleValue(), my.doubleValue());
						Node middleNodeEnd = map.getNodeNearest(mVert, Find.Mode.END );
						Node middleNodeStart = map.getNodeNearest(mVert, Find.Mode.START );
						pathFirst = pathFinder.getPath(map, startNode, middleNodeEnd, srchOption);
						pathSecond = pathFinder.getPath(map, middleNodeStart, endNode, srchOption);
						if (pathFirst != null && pathSecond != null) {
							// join two path into one path
							pathFirst.addList(pathSecond);
						}
					}
				}
			}
		}

		if (pathFirst == null) {
			pathFirst = new Path( srchOption );
		}

		if( true ) {
			GisComLib comLib = new GisComLib();

			String colorText = routeRequest.getLineColor();
			Color lineColor = Color.black;
			if( this.isGood( colorText ) ) 	{
				lineColor = comLib.getColor( colorText , Color.black );
			}
			float lineWidth = routeRequest.getLineWidth();

			StyleLink style = new StyleLink( lineColor, lineColor, lineWidth, 255, true );

			pathFirst.setStyle( style );
		}

		return pathFirst;
	}

	public Map getMap() {
		if (map == null) {
			try {
				map = MapLink.getNewMapLink();
			} catch (Exception e) {
				map = null;
				this.debug(e);
			}
		}
		return map;
	}

	public PathFinder getPathFinder(SrchOption srchOption) {
		return PathFinder.getNewPathFinder(srchOption);
	}

	private static Map map;
}
