//package com.techdigm.editor;
package jcosmos;


import java.awt.Font;
import java.awt.Color;

public class DefaultFont {

	// Script ����
	public static final byte	SCRIPT_NONE					=  0;
	public static final byte	SCRIPT_SUPER				=  1;
	public static final byte	SCRIPT_SUB					=  2;
	// Underline ����
	public static final byte	UNDERLINE_NONE				=  0;
	public static final byte	UNDERLINE_SINGLE			=  1;
	public static final byte	UNDERLINE_DOUBLE			=  2;
	public static final byte	UNDERLINE_ACCSINGLE			=  3;
	public static final byte	UNDERLINE_ACCDOUBLE			=  4;

	// Default Font  �Ӽ�.
//	public static       String	FONT_NAME		= "Dialog";
	public static       String	FONT_NAME		= "����";
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

