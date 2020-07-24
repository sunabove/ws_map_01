package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

import java.util.Date;

public class CellDate extends Cell {

	public Date getDate() {
		return (Date) this.getValue();
	}

	public CellDate( Date value ) {
		super( value );
	}

}
