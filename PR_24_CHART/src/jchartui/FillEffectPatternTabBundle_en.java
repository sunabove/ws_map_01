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

public class FillEffectPatternTabBundle_en extends ListResourceBundle {

    public FillEffectPatternTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[] patternString = {
		 "5%", "50%", "Light downward diagonal", "Light vertical", "Dashed downward diagonal", "Zig zag", "Divot", "Samll grid",
		"10%", "60%", "Light upward diagonal", "Light horizontal", "Dashed upward diagonal", "Wave", "Dotted grid", "Large grid",
		"20%", "70%", "Dark downward diagonal", "Narrow vertical", "Dashed horizontal", "Diagonal brick", "Dotted diamond", "Small checker board",
		"25%", "75%", "Dark upward diagonal", "Narrow horizontal", "Dashed vertical", "Horizontal brick", "Shingle", "Large checker board",
		"30%", "80%", "Wide downward diagonal", "Dark vertical", "Small confetti", "Weave", "Trellis", "Outlined diamond",
		"40%", "90%", "Wide upward diagonal", "Dark horizontal", "Large confetti", "Plaid", "Sphere", "Solid diamond"
	};

	private Object[][] contents = {
		{"pattern", "Pattern:"},
		{"foreground", "Foreground"},
		{"background", "Background"},
		{"patternString", patternString}
	};
}