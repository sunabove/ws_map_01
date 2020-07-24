package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

public class Record {

	public Cell[] getCells() {
		return cells;
	}

	public Cell getCell( int colIdx ) {
		return this.getCells()[ colIdx ];
	}

	public Record( int cellLen ) {
		this.cells = new Cell[ cellLen ];
	}

	private Cell [] cells;
}
