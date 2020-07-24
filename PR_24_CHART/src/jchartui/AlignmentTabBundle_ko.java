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

public class AlignmentTabBundle_ko extends ListResourceBundle {

    public AlignmentTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private String[] horizontalAlignment = {
	    "왼쪽",
		"가운데",
		"오른쪽",
		"양쪽 맞춤",
		"균등 분할"
	};

	private String[] verticalAlignment = {
	    "위쪽",
		"가운데",
		"아래쪽",
		"양쪽 맞춤",
		"균등 분할"
	};

	private Object[][] contents = {
	    {"alignmentText", "텍스트 맞춤"},
		{"horizontal", "가로(H) :"},
		{"horizontalAlignment", horizontalAlignment},
		{"vertical", "세로(V) :"},
		{"verticalAlignment", verticalAlignment},
		{"orientation", "방향"},
		{"degrees", "도(D)"}
	};
}