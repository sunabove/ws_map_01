package com.ynhenc.comm.db;

import java.sql.*;


import com.sqlmagic.tinysql.*;

public class Database_01_Dbf extends Database {

    public Database_01_Dbf( String userDir ) {
        super( getDbUrl_Dbf( userDir ), DbfEncoding.dbfEncoding );
        this.userDir = userDir;
    }

	public static String getDbUrl_Dbf( String userDir ) {

         String url = "jdbc:dbfFile:" + userDir ;

        return url;

    }

    @Override
	public void loadDriver() throws SQLException {
        dbfFileDriver driver = new dbfFileDriver();
        this.driver = driver;
    }

    // member
    private String userDir;
    // end of member

    public static void main( String [] args ) throws Exception {

    	String dbfDir = "/home/gis/ygis_map/";

    	Database db = new Database_01_Dbf( dbfDir );

    	String sqlText = "select GIJUM from jejuBus where JRJ_ID ='405000622'" ;

    	QueryResult qr = db.getQueryResult(sqlText, true);

    	qr.showAllRow( System.out );

    }
}
