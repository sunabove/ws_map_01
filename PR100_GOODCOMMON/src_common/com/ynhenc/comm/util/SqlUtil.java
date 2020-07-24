package com.ynhenc.comm.util;

import java.sql.*;
import java.lang.reflect.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.db.SqlKeyWordChecker;

public class SqlUtil extends GisComLib {

	public static StringBuffer createArgCommaList(Object[] argList) {

		StringBuffer bfr = new StringBuffer();

		Object arg;

		for (int i = 0, len = argList.length; i < len; i++) {

			arg = argList[i];

			bfr.append(arg.toString());

			if (i < len - 1) {
				bfr.append(',');
			}

		}

		return bfr;

	}

}
