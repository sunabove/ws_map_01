package com.ynhenc.gis.ui.viewer_03.dbf_viewer;

import java.io.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;

import org.xBaseJ.*;
import org.xBaseJ.fields.*;

import com.ynhenc.comm.DebugInterface;

public class TableModelDbf extends AbstractTableModel implements DebugInterface {

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	private static final long serialVersionUID = 7986205696674749402L;

	public String[] getColNameList() {
		return this.colNameList;
	}

	public final synchronized Field getDbfField(int rowIdx, int colIdx) throws Exception {
		if ((rowIdx + 1) != this.dbf.getCurrentRecordNumber()) {
			// 현재 행과 다르면 행번호로 이동한다.
			// dBF 인덱싱은 1부터 시작함으로 유념하여야 한다.
			this.dbf.gotoRecord(rowIdx + 1);
		}

		return this.dbf.getField(colIdx + 1);
	}

	public Integer getColumnIndex(String colName) {
		String colNameList[] = this.getColNameList();

		for (int i = 0, iLen = colNameList.length; i < iLen; i++) {
			if (colName.equalsIgnoreCase(colNameList[i])) {
				return i;
			}
		}

		return null;
	}

	@Override
	public String getColumnName(int colIdx) {
		DBF dbf = this.getDbf();

		if (dbf != null) {
			String colName = null;
			try {
				colName = dbf.getField(colIdx + 1).getName();
			} catch (Exception e) {
				this.debug.println(this, e);
			}
			return colName;
		} else {
			return null;
		}
	}

	@Override
	public void setValueAt(Object obj, int rowIdx, int colIdx) {
		String s = ("" + obj).trim();
		try {
			if (s.length() > 0) {
				Field fld = this.getDbfField(rowIdx, colIdx);
				char fldType = fld.getType();
				if (fldType == 'D') {
					s = "" + this.getDateFormat().parse(s);
				} else if (fldType == 'F') {
					s = "" + Float.valueOf(s);
				} else if (fldType == 'N') {
					s = "" + Float.valueOf(s);
				}

				fld.put(s);

				this.getDbf().update();

				this.fireTableCellUpdated(rowIdx, colIdx);
			}
		} catch (Exception e) {
			debug.println(this, e);
		}
	}

	public String getStringValueAt(int rowIdx, int colIdx) {
		try {
			return this.getDbfField(rowIdx, colIdx).get().trim();
		} catch (Exception e) {
			this.debug.println(this, e);
			return null;
		}
	}

	public Object getValueAt(int rowIdx, int colIdx) {
		try {
			Field field = this.getDbfField(rowIdx, colIdx);
			char fieldType = field.getType();
			String fieldValue = field.get().trim();
			if (fieldType == 'C') {
				return fieldValue;
			} else if (fieldType == 'N' || fieldType == 'F') {
				return Float.valueOf(fieldValue);
			} else if (fieldType == 'D') {
				return fieldValue;
			} else {
				return fieldValue;
			}
		} catch (Exception e) {
			this.debug.println(this, e);
			return "";
		}
	}

	public void save() throws Exception {
		DBF dbf = this.getDbf();

		if (dbf != null) {
			dbf.update();
		}
	}

	@Override
	public Class getColumnClass(int colIdx) {
		Object obj = this.getValueAt(0, colIdx);
		if( obj != null) {
			return obj.getClass();
		} else {
			return null;
		}
	}

	public int getRowCount() {
		DBF dbf = this.getDbf();

		if (dbf != null) {
			return dbf.getRecordCount();
		} else {
			return 0;
		}
	}

	public int getColumnCount() {
		DBF dbf = this.getDbf();

		if (dbf != null) {
			return dbf.getFieldCount();
		} else {
			return 0;
		}
	}

	@Override
	public boolean isCellEditable(int rowIdx, int colIdx) {
		return true;
	}

	public void setDbf(File dbfFile) throws Exception {
		if (dbfFile != null && dbfFile.exists()) {
			DBF dbf = new DBF(dbfFile.getCanonicalPath());
			this.dbf = dbf;
			int columnCount = dbf.getFieldCount();
			this.colNameList = new String[columnCount];
			for (int col = 0, colLen = columnCount; col < colLen; col++) {
				this.colNameList[col] = dbf.getField(col + 1).getName();
			}
		}
	}

	private DBF getDbf() {
		return this.dbf;
	}

	public TableModelDbf() throws Exception {
		this(null);
	}

	public TableModelDbf(File fileDbf) throws Exception {
		this.setDbf(fileDbf);
	}

	private DBF dbf;
	private String[] colNameList;
	private DateFormat dateFormat = DateFormat.getDateInstance(3);

}
