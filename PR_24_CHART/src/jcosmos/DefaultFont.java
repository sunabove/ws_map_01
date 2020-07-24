//package com.techdigm.editor;
package jcosmos;


import java.awt.Font;
import java.awt.Color;

public class DefaultFont {

	// Script 종류
	public static final byte	SCRIPT_NONE					=  0;
	public static final byte	SCRIPT_SUPER				=  1;
	public static final byte	SCRIPT_SUB					=  2;
	// Underline 종류
	public static final byte	UNDERLINE_NONE				=  0;
	public static final byte	UNDERLINE_SINGLE			=  1;
	public static final byte	UNDERLINE_DOUBLE			=  2;
	public static final byte	UNDERLINE_ACCSINGLE			=  3;
	public static final byte	UNDERLINE_ACCDOUBLE			=  4;

	// Default Font  속성.
//	public static       String	FONT_NAME		= "Dialog";
	public static       String	FONT_NAME		= "굴림";
	public static       short	FONT_SIZE		= 12;
	public static final int		FONT_STYLE		= Font.PLAIN;
	public static final Color	FONT_COLOR		= Color.black;
	public static       Font	FONT			= new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);

	public static void setFontName(String fontName) {
	    FONT_NAME = fontName;
		FONT = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
	}

	public static void setFontSize(short fontSize) {
		FONT_SIZE = fontSize;
		FONT = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
	}
}

