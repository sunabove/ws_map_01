package com.ynhenc.gis.model.map;

import java.awt.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.*;

public class AddObjectPoi extends GisComLib {

	public Color getPoiBgColor() {
		return poiBgColor;
	}

	public void setPoiBgColor(Color poiBgColor) {
		this.poiBgColor = poiBgColor;
	}

	public Color getPoiLineColor() {
		return poiLineColor;
	}

	public void setPoiLineColor(Color poiLineColor) {
		this.poiLineColor = poiLineColor;
	}

	public float getPoiLineWidth() {
		return poiLineWidth;
	}

	public void setPoiLineWidth( double poiLineWidth) {
		this.poiLineWidth = ( float ) poiLineWidth;
	}

	public ToolTip getToolTip() {
		return toolTip;
	}

	public void setToolTip(ToolTip toolTip) {
		this.toolTip = toolTip;
	}

	public String getPoiLayerName() {
		return poiLayerName;
	}

	public void setPoiLayerName(String poiLayerName) {
		this.poiLayerName = poiLayerName;
	}

	public int getPoiId() {
		return poiId;
	}

	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}

	public PntShort getPoiPoint() {
		return poiPoint;
	}

	public void setPoiPoint(PntShort poiPoint) {
		this.poiPoint = poiPoint;
	}

	public String getPoiLabel() {
		return poiLabel;
	}

	public void setPoiLabel(String poiLabel) {
		this.poiLabel = poiLabel;
	}

	public Color getPoiTextColor() {
		return poiTextColor;
	}

	public void setPoiTextColor(Color poiTextColor) {
		this.poiTextColor = poiTextColor;
	}

	public String getPoiIcon() {
		return poiIcon;
	}

	public void setPoiIcon(String poiIcon) {
		this.poiIcon = poiIcon;
	}

	public AddObjectPoi(String poiLayerName, int poiId, PntShort poiPoint, String poiLabel, Color poiTextColor, String poiIcon, ToolTip toolTip) {
		super();
		this.poiLayerName = poiLayerName;
		this.poiId = poiId;
		this.poiPoint = poiPoint;
		this.poiLabel = poiLabel;
		this.poiTextColor = poiTextColor;
		this.poiIcon = poiIcon;
		this.toolTip = toolTip;
	}

	private String poiLayerName;
	private int poiId;
	private PntShort poiPoint;
	private String poiLabel;
	private Color poiTextColor;
	private Color poiBgColor;
	private Color poiLineColor;
	private float poiLineWidth;
	private String poiIcon;
	private ToolTip toolTip;

}
