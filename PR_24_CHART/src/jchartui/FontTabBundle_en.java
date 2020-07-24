//package com.techdigm.chart.editor;
package jchartui;

import java.util.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class FontTabBundle_en extends ListResourceBundle {

    public FontTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
    return contents;
    }

	private String[] underlines = {
	    "None",
		"Single",
		"Double"
	};

	private String[] backgrounds = {
		"Automatic",
		"Transparent",
		"Opaque"
	};

	private String[] fontStyleList = {
		"Regular",
		"Italic",
		"Bold",
		"Bold Italic"
	};

	private Object[][] contents = {
	    {"font", "Font"},
		{"fontStyle", "Font Style"},
		{"size", "Size"},
		{"underline", "Underline"},
		{"color", "Color"},
		{"background", "Background"},
		{"effect", "Effect"},
		{"strikethrough", "Strikethrough"},
		{"superscript", "SuperScript"},
		{"subscript", "SubScript"},
		{"preview", "Preview"},
		{"autoScale", "Automatic Scale"},
		{"underlines", underlines},
		{"backgrounds", backgrounds},
		{"styles", fontStyleList},
		{"sampleText", "AaBbCcYyZz"},
		{"automatic", "Automatic"}
	};
}