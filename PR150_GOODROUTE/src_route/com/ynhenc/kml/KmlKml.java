package com.ynhenc.kml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.comm.db.SQL;

public class KmlKml extends KmlContainer<KmlDocument> {

	@Override
	public void openTag(Writer buff) throws Exception {

		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buff.append(NL);
		buff.append("<kml xmlns=\"http://earth.google.com/kml/2.2\">");
		buff.append(NL);

	}

	public KmlKml() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return "kml";
	}

}
