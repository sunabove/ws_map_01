package com.ynhenc.kmlexport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.comm.db.SQL;

public abstract class KmlExporter extends ComLibRoute {

	public void toKml( File file ) throws Exception {

		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

		// write kml document head
		this.toKmlHead( writer );

		// write kml body
		this.toKmlBody( writer );
		// writer kml tail
		this.toKmlTail(writer);

		// flush stream to physical file.
		writer.flush();
		fos.flush();
		fos.close();

	}

	public void toKmlHead(Writer buff) throws Exception {

		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buff.append(NL);
		buff.append("<kml xmlns=\"http://earth.google.com/kml/2.2\">");
		buff.append(NL);
		buff.append("<Document>");
		buff.append(NL);
		buff.append("<name>");
		buff.append( this.getDocumentName() );
		buff.append("</name>");
		buff.append(NL);
		buff.append("<description>");
		buff.append( this.getDocumentDesc() );
		buff.append("</description>");
		buff.append(NL);

	}

	public void toKmlTail(Writer buff) throws Exception {
		buff.append("</Document>");
		buff.append(NL);
		buff.append("</kml>");
	}



	public QueryResult getQueryResult() throws Exception {
		// returns oracle sql query result

		String sqlText = this.getSqlText();
		QueryResult qr = this.getQueryResult(sqlText);

		return qr;
	}

	public abstract String getDocumentName() ;
	public abstract String getDocumentDesc();
	public abstract String getSqlText() throws Exception ; // oracle sql text

	public abstract void toKmlBody( Writer buff ) throws Exception ;

}
