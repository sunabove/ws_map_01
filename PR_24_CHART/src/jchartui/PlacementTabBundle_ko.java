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

public class PlacementTabBundle_ko extends ListResourceBundle {

    public PlacementTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"placement", "배치"},
		{"legendPositionBottom", "아래쪽(B)"},
		{"legendPositionCorner", "모서리(C)"},
		{"legendPositionTop", "위쪽(T)"},
		{"legendPositionRight", "오른쪽(R)"},
		{"legendPositionLeft", "왼쪽(L)"}

	};
}