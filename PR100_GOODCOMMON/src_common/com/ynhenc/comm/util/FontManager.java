package com.ynhenc.comm.util;

import java.awt.*;
import java.awt.font.*;
import java.util.*;
import javax.swing.*;

import java.awt.geom.AffineTransform;

public class FontManager {

	private static Hashtable fonts = new Hashtable();

	public static Font getFont(String family, int type, int size) {

		String key = family + type + size;

		if (fonts.contains(key)) {

			return (Font) fonts.get(key);

		} else {

			Font font = new Font(family, type, size);

			fonts.put(key, font);

			return font;

		}

	}

	public static Font getDefaultFont() {

		return getFont("Serif", Font.PLAIN, 14);

	}

	public static Font getDrawingFont(Font font, Rectangle currentBounds,
			Rectangle originalBounds) {

		double sx = currentBounds.width / (originalBounds.width + 0.0);
		double sy = currentBounds.height / (originalBounds.height + 0.0);

		AffineTransform at = AffineTransform.getScaleInstance(sx, sy);

		return font.deriveFont(at);

	}

	public static String[] getAvailableFontFamilyNames() {

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		return ge.getAvailableFontFamilyNames(java.util.Locale.KOREA);

	}

	public static Integer [] getPreferredFontSizeList() {
		return preferredFontSizeList;
	}

	private static Integer [] preferredFontSizeList = {
		 new Integer(8),  new Integer(9),
		 new Integer(10 ),  new Integer(12), new Integer(14), new Integer(16), new Integer(18),
		 new Integer(20), new Integer(24), new Integer(28), new Integer(32), new Integer(36),
		 new Integer(40), new Integer(44), new Integer(48), new Integer(54), new Integer(60),
		 new Integer(66), new Integer(72), new Integer(80), new Integer(96),
		};


}
