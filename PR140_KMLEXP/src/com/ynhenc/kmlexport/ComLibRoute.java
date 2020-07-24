package com.ynhenc.kmlexport;

import java.awt.geom.Point2D;
import java.io.*;

import com.ynhenc.comm.*;
import com.ynhenc.comm.db.*;
import com.ynhenc.comm.util.FileUtil;
import com.ynhenc.kmlexport.sql.SqlGetterRoute;

public class ComLibRoute extends GisComLib {

	public QueryResult getQueryResult(String sql) throws Exception {
		Database db = this.getOracleDb();
		boolean sqlDebug = true;
		return db.getQueryResult(sql, sqlDebug );
	}

	public SQL getSQL( String sqlPathName ) throws Exception {
		return new SqlGetterRoute().getSql( sqlPathName );
	}

	private Database getOracleDb() {

		if (ORACLE_DB == null) {

			//String host = "220.90.136.132" ;
			//int port = 1521;
			//String sid = "yhenc";
			//String user = "ynhenc" ;
			//String passwd = "yhec0070" ;
			
			String host = "192.168.2.41" ;
			int port = 1521;
			String sid = "topis";
			String user = "topis2route" ;
			String passwd = "admin12" ;

			String dbUrl = Database_02_Oracle.getDbUrl_Oracle(host, sid, port, user, passwd);

			Database db = new Database_02_Oracle(dbUrl);

			ORACLE_DB = db;

		}

		return ORACLE_DB;
	}

	private static Database ORACLE_DB;

}
