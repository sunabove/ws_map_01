package com.ynhenc.kml;

import java.awt.Color;
import java.io.Writer;

import com.ynhenc.comm.util.*;

public class KmlLineStyle extends KmlStyle {

	public float getLineWidth() {
		return lineWidth;
	}

	public Color getLineColor() {
		return lineColor;
	}

	@Override
	public void writeTag(Writer buff) throws Exception {

		String styleId = "" + this.getId() ;
		String lineWidthTxt = "" + this.getLineWidth();
		String lineColorTxt = ColorUtil.toKmlColor( this.getLineColor() ) ;

		String tag = "<Style id=\"styleIdVal\"><LineStyle><width>lineWidthVal</width><color>lineColorVal</color></LineStyle></Style>";

		tag = tag.replaceAll( "styleIdVal", styleId );
		tag = tag.replaceAll( "lineWidthVal", lineWidthTxt );
		tag = tag.replaceAll( "lineColorVal", lineColorTxt );

		buff.write( tag );
	}

	@Override
	public String getTag() {
		return "Style";
	}

	public KmlLineStyle( int id, Color lineColor, float lineWidth ) {
		super( id );
		this.lineColor = lineColor;
		this.lineWidth = lineWidth;
	}

	private Color lineColor;
	private float lineWidth;

}
