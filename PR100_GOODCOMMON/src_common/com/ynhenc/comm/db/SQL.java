package com.ynhenc.comm.db;

public class SQL { 
	
	public SQL(String sqlText) {
		super();
		this.sqlText = sqlText;
	}

	public String getSqlText() {
		return sqlText;
	} 

	private String sqlText;
	
}
