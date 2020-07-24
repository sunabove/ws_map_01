package com.ynhenc.gis.model.shape;

import java.awt.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.ui.resource.IconImage;

public class Poi extends GisComLib {

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public float getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public ToolTip getToolTip() {
		return toolTip;
	}

	public void setToolTip(ToolTip toolTip) {
		this.toolTip = toolTip;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public IconImage getIconImage() {
		return this.iconImage;
	}

	public void setIconImage(IconImage iconImage) {
		this.iconImage = iconImage;
	}

	public String getTextLabel() {
		return this.textLabel;
	}

	public void setTextLabel(String textLabel) {
		this.textLabel = textLabel;
	}

	public Poi( ) {
		this.bgColor = Color.green;
		this.lineWidth = 2;
		this.lineColor = Color.black;
		//this.lineColor = new Color( ~ this.bgColor.getRGB() );
	}

	private String textLabel;
	private IconImage iconImage;
	private Color textColor;
	private Color bgColor;
	private Color lineColor;
	private float lineWidth;

	private ToolTip toolTip;

}
