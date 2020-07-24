package com.ynhenc.comm.db;

import java.sql.*;
import java.util.*;
import java.lang.reflect.*;

import com.ynhenc.comm.*;
import com.ynhenc.comm.util.SqlUtil;

public abstract class Database extends GisComLib {

	public boolean isMySql() {
		return false;
	}

	public QueryResult getQueryResult(String sqlText, boolean debug) throws Exception {
		if (true) {
			this.debug("SQL[" + sqlNo + "]: " + sqlText + ";");
		}
		long start = System.currentTimeMillis();
		QueryResult qr = new QueryResult(this.selectFrom(sqlText), this.getEncoding(), debug);
		long end = System.currentTimeMillis();
		double sec = (end - start) / 1000.0;
		if (debug) {
			this.debug("QueryTime: " + sec + " (sec)");
		}
		return qr;
	}

	private void preprocessSql(String sqlText) {
		this.sqlNo += 1;
		if (false) {
			this.debug("SQL[" + sqlNo + "]: " + sqlText + ";");
		}
	}

	public ResultSet selectFrom(String sqlTxt) throws Exception {
		return this.executeQuery(sqlTxt, this.getStatement());
	}

	public ResultSet selectFromUsingNewStmt(String sqlTxt) throws Exception {
		return this.executeQuery(sqlTxt, this.createStatement());
	}

	private ResultSet executeQuery(String sqlTxt, Statement stmt) throws SQLException {
		try {
			this.preprocessSql(sqlTxt);
			sqlTxt = this.encode(sqlTxt);
			return stmt.executeQuery(sqlTxt);
		} catch (SQLException e) {
			this.closeStatement();
			String msg = "ERROR WHILE SELECTING : " + sqlTxt;
			this.showErrorMsg(msg, e);
			throw e;
		}
	}

	private void closeStatement() {
		Statement stmt = this.stmt;
		if (stmt != null) {
			try {
				stmt.close();
				this.stmt = null;
			} catch (SQLException ex) {
				debug.println(this, ex);
			}
		}
	}

	public int clearInstance(String instance) throws Exception {

		instance = instance.toUpperCase();

		String sql = "DROP DATABASE " + instance;

		int row = this.executeUpdate(sql);

		if (this.isInstanceCreated(instance)) {
			row = this.dropAllTable();
		}

		return row;

	}

	public int createInstance(String instance) throws Exception {

		String sql = "CREATE DATABASE " + instance.toUpperCase();

		return this.executeUpdate(sql);

	}

	public int dropAllTable() throws Exception {

		Hashtable tableList = this.getTableList();

		int size = tableList.size();

		String[] tableSqlNameList = new String[size];

		Collection coll = tableList.values();

		coll.toArray(tableSqlNameList);

		return this.dropTableListIfExists(tableSqlNameList);

	}

	public int dropTableListIfExists(String[] tableList) throws Exception {

		if (tableList == null || tableList.length < 1) {
			return 0;
		}

		StringBuffer argList = SqlUtil.createArgCommaList(tableList);

		if (argList.length() < 1) {
			return 0;
		}

		String sql = "DROP TABLE IF EXISTS " + argList.toString();

		int row = this.executeUpdate(sql);

		// remove the table name from the table name registry

		// end of removal the table name from the table name registry

		return row;

	}

	public int dropTableIfExists(String table) throws Exception {

		String sql = "DROP TABLE IF EXISTS " + table;

		int row = this.executeUpdate(sql);

		// remove the table sql name from table name list

		// end of removal the table sql name

		return row;

	}

	private String quote(String text) {
		return '\'' + text + '\'';
	}

	public boolean isTableCreated(String tableSqlName) {

		// String sql = "SHOW TABLES LIKE " + tableSqlName ;
		String sql = "SHOW TABLES LIKE " + this.quote(tableSqlName);

		try {
			ResultSet rs = this.selectFrom(sql);
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			return false;
		}

		return false;

	}

	public boolean isInstanceCreated(String instance) throws Exception {

		instance = instance.toUpperCase();

		String sql = "SHOW DATABASES";

		ResultSet rs = this.selectFrom(sql);

		String aInstance;

		try {
			while (rs.next()) {
				aInstance = rs.getString(1).trim();
				// kernel.println( this, "A INSTANCE = " + aInstance );
				if (instance.equalsIgnoreCase(aInstance)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw e;
		}

		return false;

	}

	public Hashtable getTableList() {

		ResultSet rs = null;

		try {
			rs = this.getTableListResultSet();
		} catch (Exception e) {
			Kernel.getKernel().processException(e);
			return null;
		}

		Hashtable tableList = new Hashtable();

		String tableSqlName;
		Object key;

		try {
			while (rs.next()) {
				tableSqlName = rs.getString(1).toUpperCase();
				key = tableSqlName;
				tableList.put(key, tableSqlName);
			}
		} catch (SQLException e) {
			Kernel.getKernel().processException(e);
		}

		return tableList;

	}

	private ResultSet getTableListResultSet() throws Exception {

		String sql = "SHOW TABLES";

		return this.executeQuery(sql, this.getStatement());

	}

	public int createTable(String tableSqlName, String idFieldSql, StringBuffer fieldSqlBfr, String tableOptionSql) throws Exception {

		int fieldSqlTxtLen = fieldSqlBfr.length();

		String sql =

		"CREATE TABLE " + tableSqlName + "("

		+ (idFieldSql == null ? "" : idFieldSql)

		+ (idFieldSql != null && idFieldSql.length() > 0 && fieldSqlBfr.length() > 0 ? "," : "")

		+ fieldSqlBfr.toString()

		+ ")"

		+ tableOptionSql;

		int row = this.executeUpdate(sql);



		this.getTableList().put(tableSqlName, tableSqlName);

		return row;

	}

	public int insertInto(String tableSqlName, String fieldClause, String valueClause) throws Exception {

		String sql = "INSERT INTO " + tableSqlName + "(" + fieldClause + ") " + "VALUES(" + valueClause + ")";

		return this.executeUpdate(sql);

	}

	public int insertIntoUsingFieldValueOnly(String tableSqlName, String idValue, String valueClause) throws Exception {

		String sql =

		"INSERT INTO " + tableSqlName + " VALUES(" + idValue + "," + valueClause + ")";

		return this.executeUpdate(sql);

	}

	public int updateTable(String tableSqlName, String setClause, String whereClause) throws Exception {

		StringBuffer strBfr = new StringBuffer();

		strBfr.append("UPDATE ");
		strBfr.append(tableSqlName);
		strBfr.append(" SET ");
		strBfr.append(setClause);

		if (whereClause != null) {
			strBfr.append(" WHERE ");
			strBfr.append(whereClause);
		}

		return this.executeUpdate(strBfr.toString());

	}

	private String encode(String text) {
		// return getEncoding().toDb( text );
		return text;
	}

	public int executeUpdate(String sqlTxt) throws Exception {

		Statement stmt = this.getStatement();

		try {

			this.preprocessSql(sqlTxt);

			sqlTxt = this.encode(sqlTxt);

			return stmt.executeUpdate(sqlTxt);

		} catch (SQLException e) {

			String msg = "ERROR WHILE UPDATING : " + sqlTxt;

			this.showErrorMsg(msg, e);

			this.closeStatement();

			throw e;

		}

	}

	private void showErrorMsg(String s, Exception e) {
		this.debug(s);
		this.debug(e);
	}

	private Statement getStatement() throws Exception {
		Statement stmt = this.stmt;

		if (stmt != null) {
			return stmt;
		}
		return (this.stmt = this.createStatement());
	}

	private Statement createStatement() throws Exception {
		Connection conn = this.getConnection();
		return conn.createStatement();
	}

	protected Connection getConnection() throws Exception {

		Connection conn = this.conn;

		if (conn != null && (!conn.isClosed())) {
			return conn;
		}

		if (this.driver == null) {
			this.loadDriver();
		}

		if (conn != null && conn.isClosed()) {
			this.debug("Connection is closed. Connecting DB again....");
		}

		String dbUrl = this.getDbUrl();

		debug.println(this, "DBURL=" + dbUrl);

		try {
			this.conn = DriverManager.getConnection(dbUrl);
			this.debug("DB connection has established.");
		} catch (SQLException e) {
			this.conn = null;
			throw e;
		}

		return this.conn;

	}

	public final String getDbUrl() {
		return this.dbUrl;
	}

	public final Encoding getEncoding() {
		return this.encoding;
	}

	// abstract methods

	abstract public void loadDriver() throws Exception;

	// end of abstract methods

	protected Database(String dbUrl, Encoding encoding) {
		this.dbUrl = dbUrl;

		this.encoding = encoding;
	}

	// member

	private String dbUrl;

	protected java.sql.Driver driver;

	protected Connection conn;
	protected Statement stmt;

	private int sqlNo;

	private Encoding encoding;

	// static member

	// end of declaration static member

	// static method

	// end of declaration static method

}
