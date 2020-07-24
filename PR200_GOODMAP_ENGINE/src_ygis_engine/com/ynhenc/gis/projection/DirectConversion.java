package com.ynhenc.gis.projection;

import com.ynhenc.gis.model.shape.PntShort;

public class DirectConversion extends CoordinateConversion {

	@Override
	public PntShort getConvertValue(PntShort wgs) {
		return wgs ;
	}

	@Override
	public String getCoordinateSystemName() {
		return "DIRECT";
	}

}
