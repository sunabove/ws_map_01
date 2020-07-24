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

public class AlignmentTabBundle_en extends ListResourceBundle {

    public AlignmentTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
    	return contents;
    }

	private String[] horizontalAlignment = {
	    "Left",
		"Center",
		"Right",
		"Justify",
		"Distributed"
	};

	private String[] verticalAlignment = {
	    "Top",
		"Center",
		"Bottom",
		"Justify",
		"Distributed"
	};


	private Object[][] contents = {
	  	{"alignmentText", "Text alignment"},
		{"horizontal", "Horizontal :"},
		{"horizontalAlignment", horizontalAlignment},
		{"vertical",   "Vertical   :"},
		{"verticalAlignment", verticalAlignment},
		{"orientation", "Orientation"},
		{"degrees", "Degrees"}
	};
}