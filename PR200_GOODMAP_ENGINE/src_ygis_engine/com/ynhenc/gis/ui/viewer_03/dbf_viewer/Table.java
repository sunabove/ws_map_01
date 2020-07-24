package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

public class Table {

	public CellSource getCellSource() {
		return cellSource;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Record[] getRecords() {
		return records;
	}

	private Cell getCellAt( int rowIdx, int colIdx ) {
		return this.getRecord(rowIdx).getCell(colIdx);
	}

	public void setValueAt(Object obj, int rowIdx, int colIdx) {
		this.getCellAt( rowIdx, colIdx).setValue( obj );
	}

	public Object getValueAt(int rowIdx, int colIdx) {
		return this.getCellAt( rowIdx, colIdx).getValue();
	}

	public Record getRecord(int rowIdx) {
		Record[] records = this.getRecords();

		if (records[rowIdx] == null) {
			records[rowIdx] = new Record(this.getColumnCount());
		}

		return records[rowIdx];
	}

	public Table(int rowCount, int columnCount, CellSource cellSource) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.cellSource = cellSource;
		this.records = new Record[rowCount];
	}

	private transient Record[] records;
	private CellSource cellSource;
	private int rowCount;
	private int columnCount;

}
