package com.ynhenc.comm.db;

public class SqlKeyWordChecker {

	private SqlKeyWordChecker() {
	}

	public static final boolean isSqlKeyWord(String name) {

		String[] sqlKeyWordList = SQL_KEY_WORD_LIST;

		for (int i = 0, len = sqlKeyWordList.length; i < len; i++) {
			if (name.equals(sqlKeyWordList[i])) {
				return true;
			}
		}

		return false;
	}

	private static final String[] SQL_KEY_WORD_LIST = { "OBJECT", "TABLEMETA", "FIELDMETA", "ARRAYCOLLECTION", "BY", "TABLE",
			"CREATE", "DROP", "INSERT", "INTO", "SELECT", "FROM", "ORDER", "DATABASE", "DATE", "INT", "TEXT", "BLOB", "LONG",
			"INDEX", "KEY", "KEYS", };

}
