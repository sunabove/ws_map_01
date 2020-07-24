package com.ynhenc.gis.model.map;

import java.awt.Dimension;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.UnitUtil;

public class MbrUnit extends GisComLib {


	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}

	private MbrUnit(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	private double width;
	private double height;

	public static MbrUnit getMbrUnit( LevelScale levelScale, Dimension pixelSize ) {

		double scale = levelScale.getScale();

		double sw = UnitUtil.getConvertPixelToMeter( pixelSize.width ) / scale ;

		double sh = UnitUtil.getConvertPixelToMeter( pixelSize.height ) / scale ;

		return new MbrUnit( sw, sh );

	}

}
