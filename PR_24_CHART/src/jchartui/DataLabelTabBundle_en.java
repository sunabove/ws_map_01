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

public class DataLabelTabBundle_en extends ListResourceBundle {

    public DataLabelTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
    	return contents;
    }

	private Object[][] contents = {
	    {"dataLabel", "Data Labels"},
		{"none", "None"},
		{"showValue", "Show value"},
		{"showPercent", "Show percent"},
		{"showLabel", "Show label"},
		{"showLabelAndPercent", "Show label and percent"},
		{"showBubbleSizes", "Show bubble sizes"},
		{"legendKeyNextToLabel","Legend key next to label"}
	};

}