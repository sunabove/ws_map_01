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

public class JspService_002_MapModel extends JspService_000_MapObject {

	public JspService_002_MapModel(HttpServletRequest request) {
		super(request);
	}

	@Override
	public void getService() {

		com.ynhenc.gis.web.Request req = this.getRequest();

		Session session = req.getSession();

		final GisProject gisProject = this.getGisProject();

		if( true ) {
			// remove all poi list
			String removeAllPoiList = req.getString("removeAllPoiList");

			this.debug("removeAllPoiList = " + removeAllPoiList);

			if (removeAllPoiList != null && removeAllPoiList.trim().length() > 0) {
				// POI 모두 삭제.
				gisProject.getRemoveAllPoiList( req );
			}
		} // enf of remove all poi list

		if( true ) {
			// remove session layer
			String removeLayerName = req.getString( "removeLayerName" );
			this.debug("removeLayerName = " + removeLayerName );
			if( this.isGood( removeLayerName) ) {
				int removeShapeId = req.getInt( "removeShapeId", -1);
				this.debug("removeShapeId = " + removeShapeId );
				gisProject.getRemoveSessionLayer( req, removeLayerName, removeShapeId);
			}
		} // end of remove session layer

		if (true) { // POI 추가는 무조건 한다.
			this.getAddPoi();
		}

		if( true ) {
			// 검색도 무조건 한다
			Layer srchLayer =  this.getSrchLayer();

			if (srchLayer != null) {
				req.setAttribute("srchLayer", srchLayer);
			}
		}

		if (this.isGood(req.getString("clearSearch"))) {
			// 검색 삭제도 무조건 한다.
			gisProject.getClearLayerSearch( req );
		}

	}

	private Layer getSrchLayer() {

		Layer srchLayer;

		com.ynhenc.gis.web.Request req = this.getRequest();

		Session session = req.getSession();

		GisProject gisProject = this.getGisProject();

		final String srchLayerName = req.getString("srchLayerName");

		if (this.isGood(srchLayerName)) {
			srchLayer = gisProject.getLayer(srchLayerName);
		} else {
			srchLayer = gisProject.getLayer(0);
		}

		if (true) {
			// 검색 명령어 처리.
			String srchColName = req.getString("srchColName").trim();
			String srchColValue = req.getString("srchColValue").trim();
			Color lineColor = req.getColor("srchLayerLineColor");
			Color fillColor = req.getColor("srchLayerFillColor");
			Color textColor = req.getColor("srchLayerTextColor");
			int srchLayerLineWidth = req.getInt("srchLayerLineWidth", -1);

			this.debug("srchLayerName = " + srchLayerName);
			this.debug("srchColName = " + srchColName);
			this.debug("srchColValue = " + srchColValue);
			this.debug("lineColor = " + lineColor);
			this.debug("fillColor = " + fillColor);
			this.debug("textColor = " + textColor);
			this.debug("srchLayerLineWidth = " + srchLayerLineWidth);

			gisProject.getLayerSearchByAttribute( req , srchLayerName, srchColName, srchColValue, lineColor, fillColor,
					textColor, srchLayerLineWidth);
		}

		return srchLayer;

	}

	private void getAddPoi() {

		com.ynhenc.gis.web.Request req = this.getRequest();

		Session session = req.getSession();

		GisProject gisProject = this.getGisProject();

		String layerName = req.getString( "layerName", "");
		int layerNo = req.getInt( "layerNo", -1 );
		Integer type = req.getInteger("type");
		int id = req.getInt("id", -1);
		String label = req.getString("label");

		PntShort mapPointFr = req.getPointConverted("mapX", "mapY", gisProject);

		PntShort mapPointTo = req.getPointConverted("mapX1", "mapY1", gisProject);

		String icon = req.getString("icon");

		String pointTextColorText = req.getString("textColor");

		Color textColor = req.getColor("textColor");
		Color lineColor = req.getColor("lineColor");

		Double lineWidth = req.getDouble("lineWidth", new Double( 1 ));

		int layerLevelSt = req.getInteger( "layerLevelSt" , -1) ;
		int layerLevelEd = req.getInteger( "layerLevelEd" , -1) ;

		int toolTipType = req.getInt( "toolTipType", 0 );
		String toolTipContent = req.getString("toolTipContent", "");

		boolean localDebug = true;

		if (localDebug) {
			this.debug("layerName = " + layerName);
			this.debug("layerNo = " + layerNo);
			this.debug("type = " + type);
			this.debug("id = " + id);
			this.debug("label = " + label);
			this.debug("textColor = " + pointTextColorText);
			this.debug("mapPointFr = " + mapPointFr);
			this.debug("mapPointTo = " + mapPointTo);
			this.debug("icon = " + icon);
			this.debug("lineColor = " + lineColor);
			this.debug("lineWidth = " + lineWidth);
			this.debug("toolTipType = " + toolTipType);
			this.debug("toolTipContent = " + toolTipContent);
			this.debug("layerLevelSt = " + layerLevelSt);
			this.debug("layerLevelEd = " + layerLevelEd);
		}

		if (id > -1 && mapPointFr != null) {

			ToolTip toolTip = null;

			if( this.isGood(toolTipContent )) {
				toolTip = new ToolTip( toolTipType, toolTipContent );
			}

			MinMaxInt dispLevel = new MinMaxInt( -1, -1 );

			Layer layer = null;

			if (type == null || type == 0) {
				AddObjectPoi poi = new AddObjectPoi(layerName, id, mapPointFr, label, textColor, icon, toolTip);
				int measureDist = req.getInt("measureDist", layerNo );
				if (measureDist > 0) { // 거리 측정 인 경우....
				}
				layer = gisProject.getAdd_01_Poi( req , poi, -1, dispLevel );
			} else if (type == 1 && mapPointFr != null && mapPointTo != null) {
				VertexList vertexList = new VertexList();
				vertexList.add( new Vertex( mapPointFr.getX(), mapPointFr.getY() ) );
				vertexList.add( new Vertex( mapPointTo.getX(), mapPointTo.getY() ) );
				AddObjectLoi loi = new AddObjectLoi( layerName, id, vertexList, lineColor,  lineWidth.intValue(), toolTip );
				layer = gisProject.getAdd_02_Loi( req , loi, layerNo, dispLevel );
			}

			if( layer != null ) {
				layer.setDispLevel( new MinMaxInt( layerLevelSt, layerLevelEd));
				if( layerNo < 0 ) {
					MapDataSession mapData = gisProject.getMapData_Session(req);
					layerNo = mapData.getLayerList().getMaxLayerNo();
				}
				layer.setLayerNo(layerNo);
			}

		}

	}

	private void getAddPoiOld() {

		com.ynhenc.gis.web.Request req = this.getRequest();

		Session session = req.getSession();

		GisProject gisProject = this.getGisProject();

		String poiLayerName = req.getString("poiLayerName", "");
		int poiLayerNo = req.getInt( "poiLayerNo", -1 );
		Integer poiType = req.getInteger("poiType");
		int poiId = req.getInt("poiId", -1);
		String poiLabel = req.getString("poiLabel");

		PntShort poiMapPointFr = req.getPointConverted("poiMapX", "poiMapY", gisProject);

		PntShort poiMapPointTo = req.getPointConverted("poiMapX1", "poiMapY1", gisProject);

		String poiIcon = req.getString("poiIcon");

		String poiTextColorText = req.getString("poiTextColor");

		Color poiTextColor = req.getColor("poiTextColor");
		Color poiLineColor = req.getColor("poiLineColor");

		Double poiLineWidth = req.getDouble("poiLineWidth", null);

		int poiToolTipType = req.getInt( "poiToolTipType", 0 );
		String poiToolTipContent = req.getString("poiToolTipContent", "");

		boolean localDebug = true;

		if (localDebug) {
			this.debug("poiLayerName = " + poiLayerName);
			this.debug("poiLayerNo = " + poiLayerNo);
			this.debug("poiType = " + poiType);
			this.debug("poiId = " + poiId);
			this.debug("poiLabel = " + poiLabel);
			this.debug("poiTextColor = " + poiTextColorText);
			this.debug("poiMapPointFr = " + poiMapPointFr);
			this.debug("poiMapPointTo = " + poiMapPointTo);
			this.debug("poiIcon = " + poiIcon);
			this.debug("poiLineColor = " + poiLineColor);
			this.debug("poiLineWidth = " + poiLineWidth);
			this.debug("poiToolTipType = " + poiToolTipType);
			this.debug("poiToolTipContent = " + poiToolTipContent);
		}

		if (poiId > -1 && poiMapPointFr != null) {

			ToolTip toolTip = null;

			if( this.isGood(poiToolTipContent )) {
				toolTip = new ToolTip( poiToolTipType, poiToolTipContent );
			}

			MinMaxInt dispLevel = new MinMaxInt( -1, -1 );

			if (poiType == null || poiType == 0) {
				AddObjectPoi poi = new AddObjectPoi(poiLayerName, poiId, poiMapPointFr, poiLabel, poiTextColor, poiIcon, toolTip);
				int measureDist = req.getInt("measureDist", poiLayerNo );
				if (measureDist > 0) { // 거리 측정 인 경우....
				}
				Layer layer = gisProject.getAdd_01_Poi( req , poi, -1, dispLevel );
				gisProject.getMapData_Session( req ).moveLayerTop(layer) ;
			} else if (poiType == 1 && poiMapPointFr != null && poiMapPointTo != null) {
				VertexList vertexList = new VertexList();
				vertexList.add( new Vertex( poiMapPointFr.getX(), poiMapPointFr.getY() ) );
				vertexList.add( new Vertex( poiMapPointTo.getX(), poiMapPointTo.getY() ) );
				int lineWidth = poiLineWidth != null ? poiLineWidth.intValue() : 1 ;
				AddObjectLoi loi = new AddObjectLoi( poiLayerName, poiId, vertexList, poiLineColor,  lineWidth, toolTip );
				Layer layer = gisProject.getAdd_02_Loi( req , loi, poiLayerNo, dispLevel );
			}

		}

	}

}
