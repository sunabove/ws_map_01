package com.ynhenc.droute;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

import com.ynhenc.comm.*;
import com.ynhenc.comm.db.*;
import com.ynhenc.comm.util.BundleUtil;
import com.ynhenc.comm.util.FileUtil;
import com.ynhenc.droute.map.link.sql.SqlGetterRoute;

public class ComLibRoute extends GisComLib implements Mode {

	public static String getAppName() {
		return "Good Route V1.2.00";
	}

	public QueryResult getQueryResult(String sql, boolean sqlDebug) throws Exception {
		Database db = this.getOracleDb();
		return db.getQueryResult(sql, sqlDebug);
	}

	public SQL getSQL(String sqlPathName) throws Exception {
		return new SqlGetterRoute().getSql(sqlPathName);
	}

	private Database getOracleDb() {
		return DbFactory.getDbFactory().getOracleDb();
	}

}
