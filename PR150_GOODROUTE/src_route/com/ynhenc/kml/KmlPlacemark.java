package com.ynhenc.kml;

import java.io.Writer;

public class KmlPlacemark extends KmlContainer<KmlObject> {

	public KmlPlacemark(String name, KmlStyle kmlStyle, String desc) {
		super(name, desc);
		if (kmlStyle != null) {
			this.kmlStyle = kmlStyle;
			this.addComponent(this.kmlStyle.getStyleUrl());
		}
	}

	@Override
	public String getTag() {
		return "Placemark";
	}

	private KmlStyle kmlStyle;

}
