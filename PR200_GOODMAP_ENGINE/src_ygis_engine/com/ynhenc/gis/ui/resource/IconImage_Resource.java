package com.ynhenc.gis.ui.resource;

import java.net.MalformedURLException;
import java.net.URL;

public class IconImage_Resource extends IconImage {

	public IconImage_Resource(String folder, String imageName) {
		super(folder, imageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public URL getURL() {

		return this.getClass().getResource(this.getFolder() + "/" + this.getImageName());
	}

}
