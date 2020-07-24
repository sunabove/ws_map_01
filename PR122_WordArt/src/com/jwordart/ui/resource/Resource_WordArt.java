/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.swing.*;

import com.jwordart.GisComLib;

public class Resource_WordArt extends GisComLib {

	private static Class<Resource_WordArt> klass = Resource_WordArt.class;

	public static URL getUrl(String src) {

		return klass.getResource(RESOURCE_FOLDER + src);

	}

	public static InputStream getStream(String src) {
		return klass.getResourceAsStream(RESOURCE_FOLDER + src);
	}

	public static Image getImage(String src) {

		return getImage(getUrl(src));

	}

	public static Image getImage(String dir, String src) {

		return getIcon(dir, src).getImage();

	}

	public static BufferedImage getBufferedImage(String dir, String src) {

		Image image = getImage(dir, src);

		if (image == null) {
			return null;
		} else {
			return convertToBufferedImage( image );
		}
	}

	public static ImageIcon getIcon(String dir, String src) {

		String urlText = dir + "/" + src;
		URL url = getUrl(urlText);

		if (url == null) {
			debug.println( klass , "NULL URL PATH = " + urlText);
			return null;
		} else {
			return getIcon(getUrl(dir + "/" + src));
		}

	}

	private static ImageIcon getIcon(java.net.URL imageURL) {

		try {
			return new javax.swing.ImageIcon(imageURL);
		} catch (RuntimeException e) {
			e.printStackTrace();
			debug.println( klass, "ImageUrl =" + imageURL);
			return null;
		}

	}

	public static Image getImage(URL url) {

		try {
			Image image = Toolkit.getDefaultToolkit().getImage(url);
			image = loadImage(image);
			return image;
		} catch (Exception e) {
			return getImageNotFound();
		}

	}

	public static Image getImageNotFound() {

		return new ImageIcon(getUrl(IMAGE_NOT_FOUND_RERSOURCE)).getImage();

	}

	public static Dimension getImageSize(String rscName) {
		Image img = getImage(rscName);
		Component com = new Component() {
		};
		return new Dimension(img.getWidth(com), img.getHeight(com));
	}

	private static Image loadImage(Image image) {

		Component comp = new Component() {
		};
		MediaTracker tracker = new MediaTracker(comp);

		int id = 0;
		tracker.addImage(image, id);

		try {
			tracker.waitForID(id, 0);
		} catch (InterruptedException e) {
			e.printStackTrace(System.out);
			// return image;
		}
		tracker.removeImage(image, id);

		return image;

	}

	private static final String RESOURCE_FOLDER = "";
	public static final String IMAGE_NOT_FOUND_RERSOURCE = "image_not_found.jpg";

}