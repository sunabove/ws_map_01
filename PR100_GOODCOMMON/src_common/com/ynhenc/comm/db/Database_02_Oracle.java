package com.ynhenc.comm.db;

import java.sql.*;
import java.io.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;
import javax.naming.*;

import oracle.jdbc.pool.*;

import com.ynhenc.comm.util.*;

public class Database_02_Oracle extends Database {

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	@Override
	protected Connection getConnection() throws Exception {

		try {

			Connection jndiConn = this.getJndiConnection( this.getJndiName() );

			this.debug("Oracle JNDI connection has been connected!");

			return jndiConn;

		} catch (Exception ex) {

			return this.getOracleConnCacheConnection();

		}

	}

	@Override
	public void loadDriver() {

	}

	public OracleDataSource getOracleDataSource() throws Exception {

		String dbUrl = this.getDbUrl();

		this.debug("Connecting to " + dbUrl );

		OracleDataSource ods = new OracleDataSource();

		ods.setURL( dbUrl );

		return ods;

	}

	protected Connection getOracleConnCacheConnection() throws Exception {

		OracleDataSource ods = this.getOracleDataSource();

		Connection conn = ods.getConnection();

		conn.setAutoCommit(false);

		return conn;

	}

	protected Connection getJndiConnection(String jndiName) throws Exception {

		try {

			Context ctx = new InitialContext();

			DataSource ds = (javax.sql.DataSource) ctx.lookup(jndiName);

			Connection conn = ds.getConnection();

			conn.setAutoCommit(false);

			this.debug("JNDI Oracle connection has been connected!");

			return conn;

		} catch (Exception ex) {
			throw ex;
		}

	}

	private Database_02_Oracle(String dbUrl) {

		super(dbUrl, getEncodingOracle() );

	}

	public static Encoding getEncodingOracle() {
		return ORACLE_ENCODING;
	}

	private static String getDbUrl_Oracle( String host, String sid, int port, String user, String passwd ) {
		String connect_string = "jdbc:oracle:thin:" + user + "/" + passwd + "@//" + host + ":" + port + "/" + sid;
		return connect_string;
	}

	public static Database_02_Oracle getDatabaseOracleFromBundle( String bundleFileName, boolean useCache ) {

		ResourceBundle bundle = BundleUtil.getBundle( bundleFileName , useCache );

		String host = bundle.getString( "HOSTNAME" );
		String sid = bundle.getString( "SID" );
		int port = Integer.parseInt( bundle.getString( "PORT" ) );
		String user = bundle.getString( "USERNAME" );
		String passwd = bundle.getString( "PASSWORD" );

		String dbUrl = Database_02_Oracle.getDbUrl_Oracle( host, sid, port, user, passwd );

		return getDatabaseOracle( dbUrl );
	}

	private static Database_02_Oracle getDatabaseOracle( String dbUrl ) {

		Database_02_Oracle oracle = oracleList.get( dbUrl );
		if( oracle == null ) {
			oracle = new Database_02_Oracle( dbUrl );
			oracleList.put(dbUrl, oracle);
		}
		return oracle ;
	}

	private static Hashtable< String, Database_02_Oracle > oracleList = new Hashtable< String, Database_02_Oracle >();

	public static void main_Direnct(String[] args) throws Exception {

		String host = "220.90.136.132";
		String sid = "yhenc";
		int port = 1521;
		String user = "topis";
		String passwd = "topis";

		String dbUrl = Database_02_Oracle.getDbUrl_Oracle( host, sid, port, user, passwd );

		String sql = "SELECT * FROM tab ";
		//String sql = "SELECT * FROM TAB";
		Database db = new Database_02_Oracle(dbUrl);
		QueryResult qr = db.getQueryResult(sql, true );
		qr.showAllRow( System.out );

		qr.close();
	}

	private static Properties loadParams(String file) throws IOException {
		// Loads a ResourceBundle and creates Properties from it
		Properties prop = new Properties();
		ResourceBundle bundle = ResourceBundle.getBundle(file);
		Enumeration<String> it = bundle.getKeys();
		String key = null;
		Object val;
		while (it.hasMoreElements()) {
			key = it.nextElement();
			val = bundle.getObject(key);

			if( true ) {
				System.out.println( "key:" + key + ", val:" + val );
			}

			prop.put(key,  val );
		}
		return prop;
	}



	public static void main(String[] args) throws Exception {

		String sql = "SELECT * FROM tab order by tname";
		Database db = Database_02_Oracle.getDatabaseOracleFromBundle( "bundle/BDL001_OracleConn_YNH" , true  );
		QueryResult qr = db.getQueryResult(sql, true );
		qr.showAllRow( System.out );

		qr.close();
	}

	private static final Encoding ORACLE_ENCODING = new DirectEncoding( "KO16KSC5601" ) ;
	//private static final Encoding ORACLE_ENCODING = new DirectEncoding( "EUC-KR" ) ;

	// member
	private String jndiName = "jdbc/ynhenc";
	// end of member

}
