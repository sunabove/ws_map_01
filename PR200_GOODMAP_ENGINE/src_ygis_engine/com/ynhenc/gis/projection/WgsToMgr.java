package com.ynhenc.gis.projection;

public class WgsToMgr extends WgsToUtm {

	public String convertLatLonToMGRUTM(Wgs wgs) {

		this.validate(wgs);

		String mgrUTM = "";

		this.setVariables(wgs);

		String longZone = this.getLongZone(wgs);
		LatZoneList latZoneList = new LatZoneList();
		String latZone = latZoneList.getLatZone(wgs);

		double _easting = this.getEasting();
		double _northing = this.getNorthing(wgs);
		Digraphs digraphs = new Digraphs();
		String digraph1 = digraphs.getDigraph1(Integer.parseInt(longZone), _easting);
		String digraph2 = digraphs.getDigraph2(Integer.parseInt(longZone), _northing);

		String easting = String.valueOf((int) _easting);
		if (easting.length() < 5) {
			easting = "00000" + easting;
		}
		easting = easting.substring(easting.length() - 5);

		String northing;
		northing = String.valueOf((int) _northing);
		if (northing.length() < 5) {
			northing = "0000" + northing;
		}
		northing = northing.substring(northing.length() - 5);

		mgrUTM = longZone + latZone + digraph1 + digraph2 + easting + northing;
		return mgrUTM;
	}
}