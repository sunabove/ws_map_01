package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

public class CellNumber extends Cell {

	public Number getNumber() {
		return (Number) this.getValue();
	}

	public CellNumber( Number value ) {
		super( value );
	}

}
