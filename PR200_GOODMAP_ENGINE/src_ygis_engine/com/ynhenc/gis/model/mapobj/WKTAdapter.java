package com.ynhenc.gis.model.mapobj;

import java.util.*;
import java.io.*;

import com.ynhenc.comm.util.Parser;
import com.ynhenc.gis.model.map.Code;
import com.ynhenc.gis.model.shape.*;

public class WKTAdapter extends com.ynhenc.comm.GisComLib {

	public WKTAdapter() {
		super();
	}

	public Layer readLineLyr(File folder, String name) {
		if (folder != null && folder.exists()) {
			File[] files = folder.listFiles();
			File file;
			Layer lyr = new Layer(LayerType.LINE, name);

			ShapeLine line;
			int recId;
			for (int i = 0, len = files.length; i < len; i++) {
				recId = i;
				file = files[i];
				if (file == null || file.isDirectory()) {
					// skip
				} else if (file != null && file.exists()) {
					try {
						line = this.readLine(new BufferedReader(new FileReader(file)), recId);
						lyr.addSpatialShape(line);
					} catch (IOException ex) {
						this.debug(ex);
					}
				}
			}

			return lyr;

		} else {
			return null;
		}
	}

	public ShapeLine readLine(BufferedReader reader, int recId) throws IOException {
		String wkt = reader.readLine();
		return this.readLine(wkt, recId);
	}

	public ShapeLine readLine(String wkt, int id) {
		wkt = wkt.trim();
		String head = "LINESTRING(";
		String body = wkt.substring(head.length(), wkt.length() - 1);
		body = body.trim();
		String delim = ",";
		Parser parser = new Parser(body, delim);
		String data;
		double x;
		double y;
		int space;
		Vector pointList = new Vector();
		while (this.isGood(data = parser.readNext())) {
			data = data.trim();
			// debug("DATA = " + data);
			if (data.length() > 0) {
				space = data.indexOf(' ');
				x = Double.valueOf(data.substring(0, space).trim()).doubleValue();
				y = Double.valueOf(data.substring(space + 1).trim()).doubleValue();
				pointList.add(new GeoPoint(x, y));
			} else {
				break;
			}
		}
		int size = pointList.size();
		GeoPoint[] points = new GeoPoint[size];
		pointList.toArray(points);

		ShapeLine line = new ShapeLine(id, -1, new GeoPolyLine(points));

		return line;

	}

	public static void main2(String[] args) {

		String wkt = "LINESTRING(15955.233356368792 12780.3379712488,15955.233356368792 12780.3379712488,15955.233356368792 12673.864046721978,15949.629465604223 12612.221248311713,15944.025574839654 12542.172613754592,15901.996394105381 12416.085071551775,15848.759431841969 12295.601420113528,15775.908851902564 12124.682751794153,15775.908851902564 12051.832171854749,15694.652435816304 11931.3485204165,15635.811582788323 11777.241524390836,15560.159057466633 11662.361763717157,15293.974246149575 11547.48200304348,15061.412779419934 11457.819750810366,14901.7018926297 11505.452822309207,14671.942371282345 11407.384733929239,14585.082064431515 11309.31664554927,14515.033429874395 11233.66412022758,14475.806194522407 11281.297191726422,14425.37117764128 11340.138044754403,14327.303089261311 11323.326372460695,14271.264181615616 11312.118590931555,14271.264181615616 11396.1769524001,14240.442782410482 11460.621696192651,14167.592202471076 11544.680057661195,14041.50466026826 11690.381217540005,13982.663807240278 11757.627906714843)";
		WKTAdapter adapter = new WKTAdapter();
		ShapeLine line = adapter.readLine(wkt, 0);
		String readWkt = line.toGeomText();
		if (wkt.equals(readWkt)) {
			System.out.println("GOOD");
		} else {
			System.out.println("WRONG");
		}
	}

}
