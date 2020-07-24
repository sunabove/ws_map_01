package com.ynhenc.gis.projection;

import com.ynhenc.gis.model.shape.PntShort;

public class UtmToWgs extends CoordinateConversion {

	double easting;

	double northing;

	int zone;

	String southernHemisphere = "ACDEFGHJKLM";

	protected String getHemisphere(String latZone) {
		String hemisphere = "N";
		if (this.southernHemisphere.indexOf(latZone) > -1) {
			hemisphere = "S";
		}
		return hemisphere;
	}

	@Override
	public PntShort getConvertValue( PntShort utm ) {

		//this.validate( utm );

		String utmText = "52 S " + utm.getX() + " " + utm.getY() ;

		return this.convertUtmToWgs( utmText ) ;
	}

	public PntShort convertUtmToWgs(String utmText) {

		String[] utm = utmText.split(" ");
		this.zone = Integer.parseInt(utm[0]);
		String latZone = utm[1];
		this.easting = Double.parseDouble(utm[2]);
		this.northing = Double.parseDouble(utm[3]);
		String hemisphere = this.getHemisphere(latZone);
		double laty = 0.0;
		double lonx = 0.0;

		if (hemisphere.equals("S")) {
			this.northing = 10000000 - this.northing;
		}
		this.setVariables();
		laty = 180 * (this.phi1 - this.fact1 * (this.fact2 + this.fact3 + this.fact4)) / Math.PI;

		if (this.zone > 0) {
			this.zoneCM = 6 * this.zone - 183.0;
		} else {
			this.zoneCM = 3.0;

		}

		lonx = this.zoneCM - this._a3;
		if (hemisphere.equals("S")) {
			laty = -laty;
		}

		Wgs wgs = Wgs.wgs( lonx, laty );

		return wgs;

	}

	protected void setVariables() {
		this.arc = this.northing / this.k0;
		this.mu = this.arc
				/ (this.a * (1 - this.POW(this.e, 2) / 4.0 - 3 * this.POW(this.e, 4) / 64.0 - 5 * this.POW(this.e, 6) / 256.0));

		this.ei = (1 - this.POW((1 - this.e * this.e), (1 / 2.0))) / (1 + this.POW((1 - this.e * this.e), (1 / 2.0)));

		this.ca = 3 * this.ei / 2 - 27 * this.POW(this.ei, 3) / 32.0;

		this.cb = 21 * this.POW(this.ei, 2) / 16 - 55 * this.POW(this.ei, 4) / 32;
		this.cc = 151 * this.POW(this.ei, 3) / 96;
		this.cd = 1097 * this.POW(this.ei, 4) / 512;
		this.phi1 = this.mu + this.ca * this.SIN(2 * this.mu) + this.cb * this.SIN(4 * this.mu) + this.cc * this.SIN(6 * this.mu)
				+ this.cd * this.SIN(8 * this.mu);

		this.n0 = this.a / this.POW((1 - this.POW((this.e * this.SIN(this.phi1)), 2)), (1 / 2.0));

		this.r0 = this.a * (1 - this.e * this.e) / this.POW((1 - this.POW((this.e * this.SIN(this.phi1)), 2)), (3 / 2.0));
		this.fact1 = this.n0 * this.TAN(this.phi1) / this.r0;

		this._a1 = 500000 - this.easting;
		this.dd0 = this._a1 / (this.n0 * this.k0);
		this.fact2 = this.dd0 * this.dd0 / 2;

		this.t0 = this.POW(this.TAN(this.phi1), 2);
		this.Q0 = this.e1sq * this.POW(this.COS(this.phi1), 2);
		this.fact3 = (5 + 3 * this.t0 + 10 * this.Q0 - 4 * this.Q0 * this.Q0 - 9 * this.e1sq) * this.POW(this.dd0, 4) / 24;

		this.fact4 = (61 + 90 * this.t0 + 298 * this.Q0 + 45 * this.t0 * this.t0 - 252 * this.e1sq - 3 * this.Q0 * this.Q0)
				* this.POW(this.dd0, 6) / 720;

		//
		this.lof1 = this._a1 / (this.n0 * this.k0);
		this.lof2 = (1 + 2 * this.t0 + this.Q0) * this.POW(this.dd0, 3) / 6.0;
		this.lof3 = (5 - 2 * this.Q0 + 28 * this.t0 - 3 * this.POW(this.Q0, 2) + 8 * this.e1sq + 24 * this.POW(this.t0, 2))
				* this.POW(this.dd0, 5) / 120;
		this._a2 = (this.lof1 - this.lof2 + this.lof3) / this.COS(this.phi1);
		this._a3 = this._a2 * 180 / Math.PI;

	}

	@Override
	public String getCoordinateSystemName() {
		return "UTM->Wgs";
	}

	double arc;

	double mu;

	double ei;

	double ca;

	double cb;

	double cc;

	double cd;

	double n0;

	double r0;

	double _a1;

	double dd0;

	double t0;

	double Q0;

	double lof1;

	double lof2;

	double lof3;

	double _a2;

	double phi1;

	double fact1;

	double fact2;

	double fact3;

	double fact4;

	double zoneCM;

	double _a3;

	double b = 6356752.314;

	double a = 6378137;

	double e = 0.081819191;

	double e1sq = 0.006739497;

	double k0 = 0.9996;

}