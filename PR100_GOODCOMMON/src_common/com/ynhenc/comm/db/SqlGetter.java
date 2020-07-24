package com.ynhenc.comm.db;

import java.io.*;
import java.util.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.db.*;
import com.ynhenc.comm.util.*;

public abstract class SqlGetter extends GisComLib {

	protected SqlGetter() {
	}

	public SQL getSql( String src ) throws Exception {

		this.debug( "Sql Path Name: " + src );

		SqlList sqlList = this.sqlList;
		SQL sql = this.sqlList.get( src );
		if( sql == null) {
			String sqlText = this.getResourceAsString( src );
			if( sqlText == null || sqlText.trim().length() < 1 ) {
				return null;
			} else {
				sql = new SQL( sqlText );
				sqlList.put( src, sql );
				return sql;
			}
		} else {
			return sql;
		}
	}

	private String getResourceAsString(String name) throws Exception {
		InputStream in = this.getClass().getResourceAsStream(name);
		String encoding = "UTF-8";
		InputStreamReader reader = new InputStreamReader(in, encoding);
		return FileUtil.readCharArrayWriter(reader).toString();
	}

	private static final SqlList sqlList = new SqlList();

}
