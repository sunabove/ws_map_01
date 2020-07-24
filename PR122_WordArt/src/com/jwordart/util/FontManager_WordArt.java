/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.util;

import java.awt.*;
import javax.swing.*;

import com.jwordart.ui.resource.Resource_WordArt;

public class FontManager_WordArt {

	public static final int defaultFontIndex = 0;
	public static final int defaultSizeIndex = 11;
	public static String[] fontFamilyNames;
	public static String[] preferredFontSizes = { "8", "9", "10", "12", "14", "16", "18", "20", "24", "28", "32", "36", "40",
			"44", "48", "54", "60", "66", "72", "80", "96" };

	public static ImageIcon iconFontNames[];
	public static ImageIcon iconFontSizes[];

	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontFamilyNames = ge.getAvailableFontFamilyNames(java.util.Locale.KOREA);

		// Set iconifiedFontNames;
		final int fontNum = fontFamilyNames.length;
		iconFontNames = new ImageIcon[fontNum];

		for (int i = 0; i < fontNum; i++) {
			try {
				iconFontNames[i] = Resource_WordArt.getIcon("wordart", "font_icon.gif");
				iconFontNames[i].setDescription(fontFamilyNames[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Set iconFontSizes;
		final int preferredFontSizedNum = preferredFontSizes.length;
		iconFontSizes = new ImageIcon[preferredFontSizedNum];

		for (int i = 0; i < preferredFontSizedNum; i++) {
			try {
				iconFontSizes[i] = Resource_WordArt.getIcon("wordart", "null.gif");
				iconFontSizes[i].setDescription(preferredFontSizes[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FontManager_WordArt() {
	}

	public static void main(String args[]) {
		new FontManager_WordArt();
	}

	public static Font getDefaultFont() {
		int size = Integer.valueOf(preferredFontSizes[defaultSizeIndex]).intValue();
		String fontName = "";
		for( int i = 0, iLen = fontFamilyNames.length; i < iLen ; i ++ ) {
			fontName = fontFamilyNames[i];
			if( fontName.contains( "돋움") ) {
				break;
			}
		}
		return new Font(fontFamilyNames[defaultFontIndex], Font.PLAIN, size);
	}

	public static void setFontFamilyMenuItem(JComboBox menu) {
		menu.removeAllItems();
		int count = fontFamilyNames.length;
		for (int i = 0; i < count; i++) {
			menu.add(new JLabel(fontFamilyNames[i]));
		}
		menu.validate();
	}
}