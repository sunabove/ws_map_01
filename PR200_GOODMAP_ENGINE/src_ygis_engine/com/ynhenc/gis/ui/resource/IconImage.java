package com.ynhenc.gis.ui.resource;

import java.awt.Frame;
import java.awt.Image;
import java.awt.image.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.ynhenc.comm.GisComLib;

public class IconImage extends ResourceGetter implements GetTextInterface {

	public String getImageName() {
		return this.imageName;
	}

	public String getText() {
		String imageName = this.getImageName();

		if (imageName != null) {
			int dotIdx = imageName.lastIndexOf(".");
			return imageName.substring(0, dotIdx);
		}

		return null;
	}

	public int getWidth() {
		BufferedImage bfrImage = this.getBfrImage();
		if (bfrImage != null) {
			return bfrImage.getWidth();
		} else {
			return 0;
		}
	}

	public int getHeight() {
		BufferedImage bfrImage = this.getBfrImage();
		if (bfrImage != null) {
			return bfrImage.getHeight();
		} else {
			return 0;
		}
	}

	public BufferedImage getBfrImage() {
		if (this.bfrImage == null) {
			try {
				if (true) {
					URL url = this.getURL();
					if (url != null) {
						this.bfrImage = this.getBfrImage(url);
					}
				} else {
					this.bfrImage = this.getBfrImage(this.folder, this.imageName);
				}
			} catch (Exception e) {
				this.debug(e);
			}
		}
		return this.bfrImage;
	}

	public URL getURL() {

		String urlText = "file:///" + this.getFolder() + "/" + this.getImageName();

		URL url = null;

		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			this.debug(e);
		}

		return url;
	}

	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	protected IconImage(String folder, String imageName) {
		this.folder = folder;
		this.imageName = imageName;
	}

	private String folder;
	private String imageName;

	private transient BufferedImage bfrImage;

}
