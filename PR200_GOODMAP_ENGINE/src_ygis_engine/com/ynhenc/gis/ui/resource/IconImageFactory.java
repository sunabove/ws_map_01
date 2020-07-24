package com.ynhenc.gis.ui.resource;

import java.awt.image.BufferedImage;

import com.ynhenc.comm.GisComLib;

public class IconImageFactory extends GisComLib {

	public static final IconImage getIconImage( String folder, String imageName ) {
		return new IconImage( folder, imageName );
	}

	public static final IconImage_Resource getIconImage_Resource( String folder, String imageName ) {
		return new IconImage_Resource( folder, imageName );
	}

	public static void main( String [] args ) {
		IconImage i = getIconImage( "icon_01_map", "bank.gif" );
		BufferedImage bi = i.getBfrImage();

		System.out.println( "" + bi.getWidth() + ", " + bi.getHeight() );
	}

}
