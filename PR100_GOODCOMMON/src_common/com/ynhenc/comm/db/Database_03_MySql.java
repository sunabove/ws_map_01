package com.ynhenc.comm.db;

import java.sql.*;

public class Database_03_MySql extends Database {

	public Database_03_MySql( String dbUrl ) {
		super( dbUrl , new DirectEncoding( "" ) );

		this.connCharSet = "" ;
	}

	@Override
	public void loadDriver() throws Exception {

		try {
			this.driver = new com.mysql.jdbc.Driver();
		} catch (Exception e) {
			this.driver = null;
			throw e;
		}

	}

	public String getDbUrl_MySql () {

		String host = "" ;
		String instance = "" ;
		String user =  "" ;
		String password = "" ;
		String connCharSet = this.connCharSet;

		int timeOut = 5000;

		String dbUrl = "jdbc:mysql://" + host + "/" + instance + "?user=" + user + "&password=" + password + "&connectTimeout="
				+ timeOut + "&autoReconnect=true&autoReconnectForPools=true&initialTimeout=1" + "&characterEncoding="
				+ connCharSet;

		return dbUrl;
	}

	// member
	private String connCharSet;
	// end of member

}
