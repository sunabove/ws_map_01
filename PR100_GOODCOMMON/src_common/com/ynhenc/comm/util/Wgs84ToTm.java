package com.ynhenc.comm.util;

import java.io.*;
import java.util.*;

import com.ynhenc.comm.GisComLib;

public class Wgs84ToTm extends GisComLib {

	static final double lon_kor = 0.000050444862;
	static final double a = 6377397.155; // tokyo datum 의 장반경 : SemiMajor
	// Axis
	static final double a84 = 6378137.0; // wgs84 datum 의 장반경 : SemiMajor
	// Axis
	static final double f84 = 0.0033528106647478; // 1/298.257223563 = 1/f 편평율
	// Flattening
	static final double f_tokyo = 0.00334277318; // 1/299.152813 = 1/f 편평율
	// Flattening
	static final double e2 = 0.006674372231; // Bessel 의 이심률의 제곱 :
	// Eccentricity
	static final double e12 = 0.006719218798; // Wgs84 의 이심률의 제곱 :
	// Eccentricity
	static final double m0 = 0.9999; // ???
	static final double q = 10000855.7658;
	static final double omega_z = -0.5540;
	static final double s = 0.2263;
	static final double delta_z = 4.50;
	static final double A_coef[] = { 6366742.52024116306, -15988.6385238568588,
			16.7299538817284, -0.0217848007897, 0.0000307730631,
			-0.0000000453374, 0.0000000000685, -0.0000000000001 };

	// zone origin for latitude : 우리나라는 3개의 zone 으로 구분된다.
	static final double B_origin[] = { 38, 38, 38 };

	// zone origin for longitude: 우리나라는 3개의 zone 으로 구분된다.
	static final double L_origin[] = { 125, 127, 129 };

	private int timeZone;

	// Additional attribute to process pointer varialbe
	private double laty, lonx, height, x, y, XX, YY, ZZ;

	public Wgs84ToTm() {
	}

	private double[] getTmData(int pLat, int pLatMin, double pLatSec, int pLon,
			int pLonMin, double pLonSec) {

		int mLat, mLatMin, mLon, mLonMin;
		double mLatSec, mLonSec;

		mLat = pLat;
		mLatMin = pLatMin;
		mLatSec = pLatSec;

		mLon = pLon;
		mLonMin = pLonMin;
		mLonSec = pLonSec;

		double laty = makeRadian(pLat, pLatMin, pLatSec);
		double lonx = makeRadian(pLon, pLonMin, pLonSec);

		return getTmData(laty, lonx);

	}

	public double[] getTmData(double lonx, double laty) {

		int datum = 1;

		height = x = y = 0;

		XX = YY = ZZ = 0;

		this.lonx = lonx;
		this.laty = laty;

		int timeZone = this.calcTimeZone(lonx);

		this.setTimeZone(timeZone);

		dmsToXyz(this.laty, this.lonx, this.height, this.XX, this.YY, this.ZZ,
				datum);

		wgs84ToTokyo(this.XX, this.YY, this.ZZ);

		datum = 2;

		xyzToDms(this.XX, this.YY, this.ZZ, this.laty, this.lonx, this.height,
				datum);

		lonx = lonx - lon_kor;

		bl2xy(this.laty, this.lonx, this.timeZone, this.x, this.y);

		// TongIn
		x = x + 500000.0;
		y = y + 200000.0;

		return new double[] { x, y };

	}

	private int calcTimeZone(double lonx) {

		if (lonx <= 126) {
			return 0;
		} else if (lonx <= 128) {
			return 1;
		} else {
			return 2;
		}
	}

	private double makeRadian(int pDeg, int pMin, double pSec) {
		return (((pSec / 60 + pMin) / 60 + pDeg) * Math.PI / 180);
	}

	private void dmsToXyz(double pLat, double pLon, double pHeight, double pX,
			double pY, double pZ, int datum) {

		double f = 0, adms = 0;

		switch (datum) {
		case 1: /* wgs - 84 */
			adms = a84;
			f = f84;
			break;
		case 2: /* tokyo datum */
			adms = a;
			f = f_tokyo;
			break;
		}

		double eSquare = f * (2 - f);
		double N = adms
				/ Math.sqrt(1 - eSquare * Math.sin(pLat) * Math.sin(pLat));
		this.XX = (N + pHeight) * Math.cos(pLat) * Math.cos(pLon);
		this.YY = (N + pHeight) * Math.cos(pLat) * Math.sin(pLon);
		this.ZZ = (N * (1 - eSquare) + pHeight) * Math.sin(pLat);
	}

	private void wgs84ToTokyo(double tX, double tY, double tZ) {
		this.XX = tX + 146.00;
		this.YY = tY - 507.00;
		this.ZZ = tZ - 687.00;
	}

	private void xyzToDms(double pX, double pY, double pZ, double pLat,
			double pLon, double pHeight, int datum) {
		
		double b_dash, P, theta, E_square, E_square_dash, neu, f = 0, adms = 0;
		switch (datum) {
		case 1: /* wgs-84 */
			adms = a84;
			f = f84;
			break;
		case 2: /* tokyo datum */
			adms = a;
			f = f_tokyo;
			break;
		}

		b_dash = adms * (1 - f);
		P = Math.sqrt(pX * pX + pY * pY);
		theta = Math.atan((pZ * adms) / (P * b_dash));
		E_square = (adms * adms - b_dash * b_dash) / (adms * adms);
		E_square_dash = (adms * adms - b_dash * b_dash) / (b_dash * b_dash);
		this.laty = Math.atan((pZ + E_square_dash * b_dash * Math.sin(theta)
				* Math.sin(theta) * Math.sin(theta))
				/ (P - E_square * adms * Math.cos(theta) * Math.cos(theta)
						* Math.cos(theta)));
		this.lonx = Math.atan(pY / pX) + Math.PI;
		neu = adms
				/ Math.sqrt(1 - E_square * Math.sin(this.laty)
						* Math.sin(this.laty));
		this.height = P / Math.cos(this.laty) - neu;
	}

	private void bl2xy(double pLat, double pLon, int pZone, double pX, double pY) {
		double f, d1, dx, mx0;
		double et2, w, m, n, tn2, x2, x4, x6, x8, y1, y3, y5, y7;
		double B0, L0;
		double sinb, cosb, tanb;

		B0 = B_origin[pZone] * Math.PI / 180;
		L0 = L_origin[pZone] * Math.PI / 180;
		d1 = pLon - L0;
		cosb = Math.cos(pLat);
		sinb = Math.sin(pLat);
		tanb = Math.tan(pLat);
		f = B0;
		mx0 = mx(f);
		f = pLat;
		dx = mx(f) - mx0;
		et2 = e12 * Math.pow(cosb, 2);
		w = 1 - e2 * Math.pow(sinb, 2);
		n = a / Math.pow(w, 0.5);
		m = n * (1 - e2) / w;
		tn2 = Math.pow(tanb, 2);
		x2 = n * Math.pow(cosb, 2) * tanb * Math.pow(d1, 2) / 2;
		x4 = n * tanb * Math.pow(cosb, 4)
				* (5 - tn2 + 9 * et2 + 4 * Math.pow(et2, 2)) * Math.pow(d1, 4)
				/ 24;
		x6 = n
				* tanb
				* Math.pow(cosb, 6)
				* (61 - 58 * tn2 + Math.pow(tn2, 2) + 270 * et2 - 330 * tn2
						* et2) * Math.pow(d1, 6) / 720;
		x8 = n
				* tanb
				* Math.pow(cosb, 8)
				* (1385 - 3111 * tn2 + 543 * Math.pow(tn2, 2) - Math
						.pow(tn2, 3)) * Math.pow(d1, 8) / 40320;

		this.x = m0 * (x8 + x6 + x4 + x2 + dx);

		y1 = n * cosb * d1;
		y3 = n * Math.pow(cosb, 3) * (1 - tn2 + et2) * Math.pow(d1, 3) / 6;
		y5 = n * Math.pow(cosb, 5)
				* (5 - 18 * tn2 + Math.pow(tn2, 2) - 14 * et2 + 58 * tn2 * et2)
				* Math.pow(d1, 5) / 120;
		y7 = n * Math.pow(cosb, 7)
				* (61 - 479 * tn2 + 179 * Math.pow(tn2, 2) - Math.pow(tn2, 3))
				* Math.pow(d1, 7) / 5040;

		this.y = m0 * (y7 + y5 + y3 + y1);
	}

	private double mx(double f) {
		double m;
		int i = 0;

		m = A_coef[0] * f;
		for (i = 1; i < 8; i++) {
			m = m + A_coef[i] * Math.sin(f * 2 * i);
		}
		return m;
	}

	private void setTimeZone(int timeZone) {

		if (timeZone < 0 || timeZone > 2) {
			// System.out.println("Warn: Invalid time zone!");
			this.timeZone = 0;
		} else {
			// System.out.println(" Set time zone as " + timeZone);
			this.timeZone = timeZone;
		}

	}

	public static void main(String[] args) throws IOException {

		Wgs84ToTm wgs84ToTm = new Wgs84ToTm();

		String tInput = "";

		double tTimeZone;

		do {
			System.out.println("\n     **** WGS84 ==> TM ****");

			double[] latitude, longitude;

			try {

				tTimeZone = readDouble("\n     Time Zone(0/1/2): ");

				wgs84ToTm.setTimeZone((int) tTimeZone);

				System.out.print("     Enter WGS84 Latitude(deg, min, sec) : ");

				latitude = readLineAndParseAsDoubles();

				System.out
						.print("     Enter WGS84 Longitude(deg, min, sec) : ");

				longitude = readLineAndParseAsDoubles();
			} catch (IOException pE) {
				continue;
			}

			double[] tmData = wgs84ToTm.getTmData((int) latitude[0],
					(int) latitude[1], latitude[2], (int) longitude[0],
					(int) longitude[1], longitude[2]);

			String msg = "\n     TM : x = " + tmData[0] + ", y = " + tmData[1];

			System.out.println(msg);

		} while (true);

	}

	public static double[] readLineAndParseAsDoubles() throws IOException {
		String tInput = console.readLine();

		StringTokenizer tokenizer = new StringTokenizer(tInput, ",");
		String tDoubleStr;
		double[] value = new double[3];
		try {
			for (int i = 0; i < value.length; i++) {
				tDoubleStr = tokenizer.nextToken();
				if (tDoubleStr != null) {
					value[i] = Double.valueOf(tDoubleStr.trim()).doubleValue();
				} else {
					value = null;
				}
			}
		} catch (NoSuchElementException pE) {
			throw new IOException();
		}
		return value;
	}

	public static double readDouble(String pMessage) throws IOException {
		System.out.println(pMessage);

		String tInput = console.readLine();

		if (tInput.equals("quit")) {
			System.exit(0);
		}
		double value = 0;

		try {
			value = Double.valueOf(tInput).doubleValue();
		} catch (NumberFormatException pE) {
			value = readDouble(pMessage);
		}

		return value;
	}

	final static BufferedReader console = new BufferedReader(
			new InputStreamReader(System.in));

}