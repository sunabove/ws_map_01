package com.ynhenc.comm.util;

import java.sql.*;
import java.io.*;

import com.thoughtworks.xstream.*;
import com.ynhenc.comm.db.Encoding;

public class XMLUtil {

	public XMLUtil() {
		super();
	}

	public static StringBuffer toXML(ResultSet rs, Encoding encoding) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();

		int colNo = rsmd.getColumnCount();

		String[] colNames = new String[colNo];
		for (int i = 0, len = colNo; i < len; i++) {
			colNames[i] = rsmd.getColumnName(i + 1);
		}

		StringBuffer xml = new StringBuffer();
		String colName;
		Object value;
		String NL = System.getProperty("line.separator");

		xml.append("<RowList>");
		xml.append(NL);

		while (rs.next()) {
			xml.append("<Row>");
			xml.append(NL);

			for (int i = 0, len = colNo; i < len; i++) {
				colName = colNames[i];
				colName = colName.replaceAll( "#", "_" );
				colName = colName.replaceAll( "$", "_" );
				value = rs.getObject(i + 1);
				xml.append("<" + colName + ">");
				if (value != null) {
					xml.append(HtmlUtil.toHtmlText(encoding.fromDb(value.toString())));
				}
				xml.append("</" + colName + ">");
				xml.append(NL);
			}
			xml.append("</Row>");
			xml.append(NL);
		}

		xml.append("</RowList>");

		return xml;
	}

	public static boolean toXml(Object obj, Writer writer) throws Exception {

		XStream xstream = new XStream();

		// Note: This is an optional step.
		// Without it XStream would work fine,
		// but the XML element names would contain the fully qualified name of
		// each class (including package)
		// which would bulk up the XML a bit. See the Alias Tutorial a more
		// complete introduction.
		if (false) {
			xstream.alias("person", obj.getClass());
		}

		// The resulting XML looks like this:

		xstream.toXML(obj, writer);

		writer.flush();

		writer.close();

		return true;

	}

	public static Object fromXml(Reader reader) throws Exception {

		XStream xstream = new XStream();

		Object obj = xstream.fromXML(reader);

		reader.close();

		return obj;
	}

	private static Object fromXml(String xml) {

		XStream xstream = new XStream();

		Object obj = xstream.fromXML(xml);

		return obj;
	}
}
