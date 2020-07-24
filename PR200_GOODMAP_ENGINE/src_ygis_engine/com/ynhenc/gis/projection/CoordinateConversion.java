package com.ynhenc.gis.projection;

import java.util.Hashtable;
import java.util.Map;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.model.shape.PntShort;

public abstract class CoordinateConversion extends GisComLib {

	public CoordinateConversion() {
	}

	public boolean validate(PntShort wgs) {
		if (wgs.getY() < -90.0 || wgs.getY() > 90.0 || wgs.getX() < -180.0 || wgs.getX() >= 180.0) {
			// throw new IllegalArgumentException("Legal ranges: latitude [-90,90], longitude [-180,180).");
			this.debug( "Legal ranges: latitude [-90,90], longitude [-180,180)." );
			return false;
		}
		{
			return true;
		}
	}

	public double degreeToRadian(double degree) {
		return degree * Math.PI / 180;
	}

	public double radianToDegree(double radian) {
		return radian * 180 / Math.PI;
	}

	public double POW(double a, double b) {
		return Math.pow(a, b);
	}

	public double SIN(double value) {
		return Math.sin(value);
	}

	public double COS(double value) {
		return Math.cos(value);
	}

	public double TAN(double value) {
		return Math.tan(value);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof CoordinateConversion) {
			CoordinateConversion cv = (CoordinateConversion) object;
			return this.equalsStringTrimIgnoreCase(this.getCoordinateSystemName(), cv.getCoordinateSystemName());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getCoordinateSystemName();
	}

	public abstract String getCoordinateSystemName();

	public abstract PntShort getConvertValue(PntShort pntShort);

}
