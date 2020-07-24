package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

public class CellText extends Cell {

	public String getString() {
		return (String) this.getValue();
	}

	public CellText( String value ) {
		super( value );
	}

}
