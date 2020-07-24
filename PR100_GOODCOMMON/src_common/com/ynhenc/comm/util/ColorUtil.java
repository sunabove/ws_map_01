package com.ynhenc.comm.util;

import java.awt.Color;

public class ColorUtil {

	public static String toHtmlColor(Color color) {

		final String[] rgb = { Integer.toHexString(color.getRed()).toUpperCase(),
				Integer.toHexString(color.getGreen()).toUpperCase(), Integer.toHexString(color.getBlue()).toUpperCase() };

		for (int i = 0; i < 3; i++) {
			if (rgb[i].length() == 1) {
				rgb[i] = "0" + rgb[i];
			}
		}

		return "#" + rgb[0] + rgb[1] + rgb[2];

	}

	public static String toKmlColor(Color color) {

		// KML color is OBGR

		final String[] rgb = { Integer.toHexString(color.getAlpha()).toUpperCase(),
				Integer.toHexString(color.getBlue()).toUpperCase(), Integer.toHexString(color.getGreen()).toUpperCase(),
				Integer.toHexString(color.getRed()).toUpperCase(), };

		for (int i = 0, iLen = rgb.length; i < iLen; i++) {
			if (rgb[i].length() == 1) {
				rgb[i] = "0" + rgb[i];
			}
		}

		return rgb[0] + rgb[1] + rgb[2] + rgb[3];

	}

	public static Color fromHtmlColor(String text) {

		if (text == null) {
			return null;
		} else if (text.equalsIgnoreCase("NULL") || text.length() < 6) {
			return null;
		}

		if (text.startsWith("#")) {
			text = text.substring(1);
		}

		int r = parseHexaString(text.substring(0, 2));
		int g = parseHexaString(text.substring(2, 4));
		int b = parseHexaString(text.substring(4, 6));
		int a = 0xFF ;
		if( text.length() > 7 ) {
			a = parseHexaString(text.substring(6, 8));
		}

		try {
			return new Color( r, g, b, a );
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int parseHexaString(String hexa) {

		int value = 0;
		char c;
		int digit;
		for (int i = 0, len = hexa.length(); i < len; i++) {
			c = hexa.charAt(i);
			digit = 0;
			switch (c) {
			case '1':
				digit = 1;
				break;
			case '2':
				digit = 2;
				break;
			case '3':
				digit = 3;
				break;
			case '4':
				digit = 4;
				break;
			case '5':
				digit = 5;
				break;
			case '6':
				digit = 6;
				break;
			case '7':
				digit = 7;
				break;
			case '8':
				digit = 8;
				break;
			case '9':
				digit = 9;
				break;
			case 'a':
				digit = 10;
				break;
			case 'b':
				digit = 11;
				break;
			case 'c':
				digit = 12;
				break;
			case 'd':
				digit = 13;
				break;
			case 'e':
				digit = 14;
				break;
			case 'f':
				digit = 15;
				break;
			case 'A':
				digit = 10;
				break;
			case 'B':
				digit = 11;
				break;
			case 'C':
				digit = 12;
				break;
			case 'D':
				digit = 13;
				break;
			case 'E':
				digit = 14;
				break;
			case 'F':
				digit = 15;
				break;
			}
			value = value * 16 + digit;
		}

		return value;

	}

}
