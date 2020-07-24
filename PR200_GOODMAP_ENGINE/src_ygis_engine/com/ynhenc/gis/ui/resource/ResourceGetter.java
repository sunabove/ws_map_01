package com.ynhenc.gis.ui.resource;

import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;

public class ResourceGetter extends GisComLib {

	public BufferedImage getBfrImage(String folder, String imageName)
			throws Exception {

		URL url = this.getUrl(folder, imageName);

		return ImageIO.read( url );

	}

	public BufferedImage getBfrImage( URL url )
			throws Exception {

		return ImageIO.read( url );

	}

	public URL getUrl(String folder, String imageName) {

		return this.getClass().getResource(folder + "/" + imageName);

	}

	public InputStream getStream(String folder, String src) {
		return this.getClass().getResourceAsStream(folder + "/" + src);
	}

	public StringBuffer getStreamAsStringBuffer(String folder, String src) {
		InputStream in = this.getStream(folder, src);

		InputStreamReader reader = new InputStreamReader(in);

		StringBuffer text = new StringBuffer();

		try {

			CharArrayWriter caw = FileUtil.readCharArrayWriter(reader);

			text.append(caw.toCharArray());

			return text;

		} catch (Exception e) {
			this.debug(e);

			return text;

		}
	}

}
