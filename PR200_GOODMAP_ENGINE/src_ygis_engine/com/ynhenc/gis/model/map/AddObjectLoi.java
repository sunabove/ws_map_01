package com.ynhenc.gis.model.map;

import java.awt.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.*;

public class AddObjectLoi extends GisComLib {

	public ToolTip getToolTip() {
		return toolTip;
	}

	public void setToolTip(ToolTip toolTip) {
		this.toolTip = toolTip;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GeoPolyLine getPolyLine() {

		VertexList vertexList = this.getVertexList();

		if( vertexList == null || vertexList.size() < 2 ) {
			return null;
		}

		GeoPoint [] pointList = new GeoPoint[ vertexList.size() ];

		int i = 0 ;
		for( Vertex vertex : vertexList ) {
			pointList[ i ] = new GeoPoint( vertex.getX(), vertex.getY() );
			i ++ ;
		}

		return new GeoPolyLine(pointList);

	}

	public VertexList getVertexList() {
		return vertexList;
	}

	public NodeList getNodeList() {
		if( this.getVertexList() != null ) {
			return this.getVertexList().getNodeList();
		}
		return null;
	}

	public void setVertexList(VertexList vertexList) {
		this.vertexList = vertexList;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public AddObjectLoi(String layerName, int id, VertexList vertexList, Color lineColor, int lineWidth, ToolTip toolTip ) {
		super();
		this.layerName = layerName;
		this.id = id;
		this.vertexList = vertexList;
		this.lineColor = lineColor;
		this.lineWidth = lineWidth;
		this.toolTip = toolTip;
	}

	private String layerName;
	private int id;
	private VertexList vertexList;
	private Color lineColor;
	private int lineWidth;
	private ToolTip toolTip;

}
