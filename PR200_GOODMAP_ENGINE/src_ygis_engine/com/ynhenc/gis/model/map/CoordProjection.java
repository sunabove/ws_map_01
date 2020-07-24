package com.ynhenc.gis.model.map;

import com.ynhenc.gis.model.shape.*;

public class CoordProjection {

	public CoordProjection() {
		super();
	}

	private static class BesselPnt {

		public BesselPnt(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Bessel: x = " + x + ", y = " + y;
		}

		int x;
		int y;

	}

	private class LonLat {

		public LonLat(DoBunCho lon, DoBunCho lat) {
			this.lon = lon;
			this.lat = lat;
		}

		@Override
		public String toString() {
			return "경도 = " + lon + ", 위도 = " + lat;
		}

		public DoBunCho lon;
		public DoBunCho lat;

	}

	private class DoBunCho {
		public DoBunCho(int dho, int bun, float cho) {
			this.dho = dho;
			this.bun = bun;
			this.cho = cho;
		}

		public int dho;
		public int bun;
		public float cho;

		@Override
		public String toString() {
			return dho + " 도 " + bun + " 분 " + cho + " 초";
		}
	}

	private String toLonLatInfos2(BesselPnt bessel) {

		LonLat xy = null;

		int xDo = bessel.x / 360000;
		int xBun = (bessel.x % 360000) / 6000;
		float xCho = ((bessel.x % 360000) % 6000) / 100.0F;
		int yDo = bessel.y / 360000;
		int yBun = (bessel.y % 360000) / 6000;
		float yCho = ((bessel.y % 360000) % 6000) / 100.0F;

		DoBunCho lon = new DoBunCho(xDo, xBun, xCho);
		DoBunCho lat = new DoBunCho(yDo, yBun, yCho);
		xy = new LonLat(lon, lat);

		int nY = (int) (bessel.y / 240000.);
		int nX = (int) (bessel.x / 360000.) - 100;
		int nYY = (int) ((bessel.y / 100. - nY * 2400) / 300.);
		int nXX = (int) ((bessel.x / 100. - (nX + 100) * 3600.) / 450.);

		int nBottomBun = nY * 40 + nYY * 5;
		int nTopBun = nBottomBun + 5;
		int nBottomDo = (int) (nBottomBun / 60.0);
		int nTopDo = (int) (nTopBun / 60.0);
		float fRealY = (float) ((bessel.y / 100. - nBottomBun * 60) * 30);

		nBottomBun -= nBottomDo * 60;
		nTopBun -= nTopDo * 60;

		int nLeftCho = (nX + 100) * 3600 + nXX * 450;
		int nRightCho = nLeftCho + 450;
		int nLeftDo = (int) (nLeftCho / 3600.);
		int nRightDo = (int) (nRightCho / 3600.);
		int nLeftBun = (int) ((nLeftCho - nLeftDo * 3600) / 60.0);
		int nRightBun = (int) ((nRightCho - nRightDo * 3600) / 60.0);
		float fRealX = (float) ((bessel.x / 100. - nLeftCho) * 220 / 9);

		nLeftCho -= nLeftDo * 3600 + nLeftBun * 60;
		nRightCho -= nRightDo * 3600 + nRightBun * 60;

		int doyup = nY * 10000 + nX * 100 + nYY * 10 + nXX;

		return "reg: x = " + fRealX + ", y = " + fRealY + ", doyup = " + doyup + ", " + xy;

	}

	private LonLat toLonLat(BesselPnt bessel) {

		int xDo = bessel.x / 360000;
		int xBun = (bessel.x % 360000) / 6000;
		float xCho = (float) ((bessel.x % 6000) / 100.0);
		int yDo = bessel.y / 360000;
		int yBun = (bessel.y % 360000) / 6000;
		float yCho = (float) ((bessel.y % 6000) / 100.0);

		DoBunCho lon = new DoBunCho(xDo, xBun, xCho);
		DoBunCho lat = new DoBunCho(yDo, yBun, yCho);

		return new LonLat(lon, lat);
	}

	private GeoPoint toPnt(BesselPnt bessel) {

		final int nX = (int) (bessel.x / 360000.) - 100;
		final int nXX = (int) (bessel.x / 45000. - (nX + 100) * 8.0);

		final int nY = (int) (bessel.y / 240000.);
		final int nYY = (int) ((bessel.y / 30000. - nY * 8.0));

		int nLeftCho = (nX + 100) * 3600 + nXX * 450;
		double realX = (bessel.x / 100. - nLeftCho) * (220. / 9.);

		realX = realX + nXX * 11000;

		int nBottomBun = nY * 40 + nYY * 5;
		double realY = (bessel.y * (3. / 10.) - nBottomBun * 1800);
		realY = realY + nYY * 9000;

		return new GeoPoint((float) realX, (float) realY);
		// this.doYup = nY * 10000 + nX * 100 + nYY * 10 + nXX;

	}

	public GeoPoint toPnt(double bessel_x, double bessel_y) {

		final int nX = (int) (bessel_x / 360000.) - 100;
		final int nXX = (int) (bessel_x / 45000. - (nX + 100) * 8.0);

		final int nY = (int) (bessel_y / 240000.);
		final int nYY = (int) ((bessel_y / 30000. - nY * 8.0));

		int nLeftCho = (nX + 100) * 3600 + nXX * 450;
		double realX = (bessel_x / 100. - nLeftCho) * (220. / 9.) + nXX * 11000;

		int nBottomBun = nY * 40 + nYY * 5;
		double realY = (bessel_y * (3. / 10.) - nBottomBun * 1800) + nYY * 9000;

		return new GeoPoint((float) realX, (float) realY);
		// this.doYup = nY * 10000 + nX * 100 + nYY * 10 + nXX;

	}

	private GeoPoint toPnt2(BesselPnt bessel) {

		final int nX = (int) (bessel.x / 360000.);
		final int nXX = (int) ((bessel.x / 360000. - nX) * 8.0);
		double realX = bessel.x * 2.2 / 9. - (nX * 8 + nXX) * 11000.;

		final int nY = (int) (bessel.y / 240000.);
		final int nYY = (int) ((bessel.y / 240000. - nY) * 8.0);

		double realY = bessel.y * 0.3 - (nY * 8 + nYY) * 9000;

		return new GeoPoint((float) realX, (float) realY);

	}

	public static void main(String[] args) {

		CoordProjection proj = new CoordProjection();

		// Pnt p = projection.toBesselPnt( 12000, 8000 );
		// System.out.println( "P = " + p );
	}

	public static void main2(String[] args) {

		CoordProjection proj = new CoordProjection();

		int[] coords = { 45720180, 13493603, 45722237, 13491693, 45771725, 13478126, 45774724, 13480920, 45786608, 13485813 };

		for (int i = 0, len = coords.length; i < len;) {
			int x = coords[i];
			i++;
			int y = coords[i];
			i++;

			java.io.PrintStream out = System.out;

			BesselPnt bessel = new BesselPnt(x, y);
			// out.println("AFTER2 = " + projection.toLonLatInfos2(bessel) );
			LonLat gw = proj.toLonLat(bessel);
			GeoPoint p3 = proj.toPnt(bessel);
			// Pnt p4 = projection.toPnt2(bessel);
			// out.println("DoYup = " + projection.doYup);
			out.println("ORG = " + bessel);

			// MapUnitInfo info = MapUnitInfo.getMapUnitInfo(projection.doYup);
			// BesselPnt b2 = projection.toBesselPnt(p3, info);
			// out.println("AFT = " + b2);
			// out.println("AFTER3 = reg: " + p3 + " " + gw );

		}
	}
}
