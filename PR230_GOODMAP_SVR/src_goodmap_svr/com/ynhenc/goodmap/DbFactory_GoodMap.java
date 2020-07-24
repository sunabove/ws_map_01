package com.ynhenc.goodmap;

import oracle.jdbc.pool.*;

import com.ynhenc.comm.*;
import com.ynhenc.comm.db.*;

public class DbFactory_GoodMap extends GisComLib {

	private DbFactory_GoodMap() {

	}

	public Database_02_Oracle getDatabase_Impl() {
		String bundleFileName = "bundle/BDL003_OracleConn";
		if( false ) {
			bundleFileName = "bundle/BDL001_OracleConn_YNH";
		}
		if( true ) {
			this.debug( "BundleFileName:" + bundleFileName );
		}
		Database_02_Oracle db = Database_02_Oracle.getDatabaseOracleFromBundle(bundleFileName, true);

		return db;
	}

	private static DbFactory_GoodMap getDbFactory() {
		return dbFactory;
	}

	private final static DbFactory_GoodMap dbFactory = new DbFactory_GoodMap();

	public static Database_02_Oracle getDatabase() {
		return getDbFactory().getDatabase_Impl();
	}

	public static OracleDataSource getOracleDataSource() throws Exception {
		return getDatabase().getOracleDataSource();
	}

}
