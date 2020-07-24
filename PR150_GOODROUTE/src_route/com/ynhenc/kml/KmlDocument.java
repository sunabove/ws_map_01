package com.ynhenc.kml;

import com.ynhenc.droute.render.*;

public class KmlDocument extends KmlContainer< KmlFolder > {

	public KmlStyleContainer getStyleList() {
		return styleList;
	}

	public KmlDocument(String name, String desc, KmlStyleContainer styleList ) {
		super(name, desc);
		this.styleList = styleList ;
	}

	@Override
	public String getTag() {
		return "Document";
	}

	private KmlStyleContainer styleList ;

}
