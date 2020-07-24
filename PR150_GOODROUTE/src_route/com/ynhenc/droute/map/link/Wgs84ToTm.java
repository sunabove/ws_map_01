package com.ynhenc.droute.map.link;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

import com.jhlabs.map.proj.*;
import com.ynhenc.comm.GisComLib;

public class Wgs84ToTm extends GisComLib {

	public static Point2D getTmPointFromWgs84( double gx, double gy ) {
		return getTmPointFromWgs84( new Point2D.Double( gx, gy ));
	}

	public static Point2D getTmPointFromWgs84( Point2D.Double wgs84 ) {

		Point2D.Double tm = new Point2D.Double(0, 0);

		getProjection().transform(wgs84, tm);

		if ( false ) {
			double dx = 185.4170934;
			double dy = -310.3102959;

			tm.setLocation( tm.getX() + dx, tm.getY() + dy );;
		}

		return tm;
	}

	private static Projection getProjection() {
		if (PROJECTION == null) {
			String tm = "cs2cs +proj=latlong +datum=WGS84 +ellps=WGS84 +to +proj=tmerc +lat_0=38N +lon_0=127.0028902777777777776E +ellps=bessel +x_0=200000 +y_0=500000 +k=1.0 +towgs84=-146.43,507.89,681.46";
			String katec = "cs2cs +proj=latlong +datum=WGS84 +ellps=WGS84 +to +proj=tmerc +lat_0=38N +lon_0=128.0E +ellps=bessel +x_0=400000 +y_0=600000 +k=0.9998 +towgs84=-146.43,507.89,681.46";
			String prj = tm;
			PROJECTION = ProjectionFactory.fromPROJ4Specification(prj.split(" "));
		}
		return PROJECTION;
	}

	private static Projection PROJECTION;

	public static void main( String [] args ) throws Exception {

		double gx = 127.40;
		double gy = 38.11;
		Point2D.Double wgs = new Point2D.Double( gx, gy );
		Point2D tm = getTmPointFromWgs84( wgs );
		if ( true ) {
			GisComLib lib = new GisComLib();
			lib.debug("wgs:" + wgs );
			lib.debug("tm:" + tm );
		}

	}
}