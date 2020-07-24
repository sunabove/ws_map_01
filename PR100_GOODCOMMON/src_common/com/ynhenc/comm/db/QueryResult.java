package com.ynhenc.comm.db;

import java.io.*;
import java.sql.*;

import com.ynhenc.comm.GisComLib;

public class QueryResult extends GisComLib {

	public QueryResult(ResultSet rs, Encoding encoding, boolean debugFlag) {
		this.rs = rs;
		this.encoding = encoding;
		this.debugFlag = debugFlag;
	}

	public int getColumnCount() throws Exception {
		return this.rs.getMetaData().getColumnCount();
	}

	public void showAllRow(PrintStream writer) throws Exception {

		writer.append(this.getAllRowResult() + "\n ");

		writer.flush();

	}

	public StringBuffer getAllRowResult() throws Exception {
		// returns all row result
		StringBuffer sb = new StringBuffer();

		while (this.next()) {
			sb.append(this.getRowResult() + NL);
		}

		return sb;
	}

	public StringBuffer getRowResult() throws Exception {
		// return one row result

		StringBuffer sb = new StringBuffer();

		sb.append("ROW[" + this.rs.getRow() + "]: ");

		for (int i = 0, iLen = this.getColumnCount(); i < iLen; i++) {
			sb.append(this.getObject(i + 1));
			if (i < iLen - 1) {
				sb.append("\t");
			}
		}

		return sb;
	}

	public boolean hasNext() throws Exception {
		return this.next();
	}

	public boolean next() throws Exception {
		return rs.next();
	}

	public Object getObject(int col) {
		try {
			return rs.getObject(col);
		} catch (SQLException e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public Object getObject(String col) {
		try {
			return rs.getObject(col);
		} catch (SQLException ex) {
			try {
				// debug.debug( this, ex );
				return rs.getObject(col.toUpperCase());
			} catch (Exception ex2) {
				this.debug(ex2, debugFlag);
				return null;
			}
		}
	}

	private String getObjString(String col) throws Exception {
		try {
			String obj = rs.getString(col);
			return obj;
		} catch ( Exception e ) {
			this.debug( "colName = " + col, debugFlag );
			throw e;
		}
	}

	private String getObjString(int col) throws Exception {
		try {
			String obj = rs.getString(col);
			return obj;
		} catch ( Exception e ) {
			this.debug( "colIdx = " + col, debugFlag );
			throw e;
		}
	}

	public Time getObjTime(String col) throws Exception {
		try {
			return rs.getTime(col);
		} catch (Exception ex) {
			return rs.getTime(col.toUpperCase());
		}
	}

	public Integer getInteger(String colName) {
		try {
			return this.getNumber(colName).intValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public Integer getInteger(int colIdx) {
		try {
			return this.getNumber(colIdx).intValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public Long getLong(String colName) {
		try {
			return this.getNumber(colName).longValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public Long getLong(int colIdx) {
		try {
			return this.getNumber(colIdx).longValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public Number getNumber(String colName) throws Exception {
		return this.getNumberObject(this.getObject(colName));
	}

	public Number getNumber(int colIdx) throws Exception {
		return this.getNumberObject(this.getObject(colIdx));
	}

	private Number getNumberObject(Object obj) throws Exception {
		try {
			if (obj instanceof Number) {
				return (Number) obj;
			} else if (obj != null) {
				return Double.parseDouble(obj.toString().trim());
			} else {
				return null;
			}
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}



	public Double getDouble(String colName) {
		return this.getDouble( colName, null );
	}

	public Double getDouble(String colName, Double def) {
		try {
			return this.getNumber(colName).doubleValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return def;
		}
	}

	public Double getDouble(int colIdx) {
		try {
			return this.getNumber(colIdx).doubleValue();
		} catch (Exception e) {
			this.debug(e, debugFlag);
			return null;
		}
	}

	public String getString(String col) {
		String txt;
		try {
			txt = this.getObjString(col);
			return encoding.fromDb(txt);
		} catch (Exception e) {
			this.debug(e, debugFlag);
		}
		return "";
	}

	public String getString(int col) {
		String txt;
		try {
			txt = this.getObjString(col);
			return encoding.fromDb(txt);
		} catch (Exception e) {
			this.debug(e, debugFlag);
		}
		return "";
	}

	public Time getTime(String col) throws Exception {
		return this.getObjTime(col);
	}

	public boolean close() {
		try {
			this.rs.close();
			return true;
		} catch (Exception ex) {
			this.debug(ex);
			return false;
		}
	}

	private ResultSet rs;
	private Encoding encoding;
	private boolean debugFlag = false;
}
