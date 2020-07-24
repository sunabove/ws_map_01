package com.ynhenc.comm.db;

import java.util.ResourceBundle;

import com.ynhenc.comm.*;
import com.ynhenc.comm.util.BundleUtil;

public class DbFactory extends GisComLib {

	public final ResourceBundle getRouteSystemBundle() {
		String bundleFileName = "bundle/BDL000_ROUTE_SYS" ;
		boolean useCache = false;

		ResourceBundle bundle = BundleUtil.getBundle( bundleFileName , useCache );

		return bundle;
	}

	public synchronized Database_02_Oracle getOracleDb() {
		if (ORACLE_DB == null) {
			ResourceBundle routeSysBundle = this.getRouteSystemBundle();
			String bundleName = routeSysBundle.getString( "DB_BUNDLE_NAME" );
			if( true ) {
				this.debug( "DB_BUNDLE_NAME: " + bundleName );
			}
			if ( false ) {
				//bundleName = "bundle/BDL001_OracleConn_YNH";
			}
			Database_02_Oracle db = Database_02_Oracle.getDatabaseOracleFromBundle(bundleName, true );
			ORACLE_DB = db;
		}

		return ORACLE_DB;
	}

	private DbFactory() {
	}

	public static DbFactory getDbFactory() {
		return dbFactory;
	}

	private Database_02_Oracle ORACLE_DB;

	private static final DbFactory dbFactory = new DbFactory();

}
