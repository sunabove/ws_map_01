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

public class PatternTabBundle_ko extends ListResourceBundle {

    public PatternTabBundle_ko() {
    }

    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"border", "테두리"},
		{"area", "영역"},
		{"sample", "보기"},
		{"majorTickMark", "주 눈금(M)"},
		{"minorTickMark", "보조 눈금(R)"},
		{"tickLabel", "눈금 레이블(T)"}
	};

}