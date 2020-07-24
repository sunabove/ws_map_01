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

public class GridTabForValueAxisBundle_en extends ListResourceBundle {

    public GridTabForValueAxisBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
    	return contents;
    }

	private String[] displayUnits = {
		"None",
	    "Hundreds",
		"Thousands",
		"Millions",
		"Trillions"
	};

	private Object[][] contents = {
	    {"valueAxisScale", "Value (Y) axis scale"},
		{"auto", "Auto"},
		{"minimum", "Minimum:"},
		{"maximum", "Maximum:"},
		{"majorUnit", "Major unit:"},
		{"minorUnit", "Minor unit:"},
		{"xAxisCrossesAt", "Category(X) axis Crosses at:"},
		{"displayUnit", "Display units:"},
		{"displayUnits", displayUnits},
		{"showDisplayUnitLabelOnChart", "Show display units label on chart"},
		{"logarithmicScale", "Logarithmic scale"},
		{"categoriesInReverseOrder", "Categories in reverse order"},
		{"xAxisCrossesAtMaxValue", "Category(X) axis crosses at maximum value"}
	};
}