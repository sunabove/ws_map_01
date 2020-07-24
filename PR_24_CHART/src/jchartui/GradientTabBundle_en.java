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

public class GradientTabBundle_en extends ListResourceBundle {

    public GradientTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[]  presetString = {
		"Early Sunset", "Late Sunset", "Nightfall", "Daybreak",
		"Ocean", "Fire" , "Moss", "Parchment", "Mahogany",
		"Gold", "Silver", "Sapphire"
	};

	private Object[][] contents = {
		{"color", "Color"},
		{"oneColor", "One Color"},
		{"twoColor", "Two Color"},
		{"preset", "Preset"},
		{"shadingStyle", "Shading Style"},
		{"horizontal", "Horizontal"},
		{"vertical", "Vertical"},
		{"diagonalUp", "Diagonal up"},
		{"diagonalDown", "Diagonal down"},
		{"fromCorner", "From Corner"},
		{"fromCenter", "From Center"},
		{"variants", "Variants"},
		{"dark", "Dark"},
		{"light", "Light"},
		{"presetString", presetString}
	};
}