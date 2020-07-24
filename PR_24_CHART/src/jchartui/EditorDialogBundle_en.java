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

public class EditorDialogBundle_en extends ListResourceBundle {

    public EditorDialogBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
    return contents;
    }

	private Object[][] contents = {
// Tab Title                "   제목   "},
		{"titleTab",        "  Title  "},
		{"axisTab",         "   Axis   "},
		{"gridTab",         "   Grid   "},
		{"legendTab",       "  Legend  "},
		{"dataLabelTab",    " Data Labels "},
		{"dataTableTab",    " Data Table "},
		{"fontTab" ,        "   Font   "},
		{"patternTab",      " Patterns "},
		{"yErrorBarTab",    " Y Error Bars "},
		{"seriesOrderTab",  " Series Order "},
		{"optionTab",       "  Options  "},
		{"placementTab",    " Placement "},
		{"alignmentTab",    " Alignment "},
		{"propertyTab",     " Properties "},
		{"scaleTab",        "   Scale   "},
		{"numberTab",       "  Number  "},

//각 Dialog의 Title
		{"chartOption", "Chart Options"},
		{"dataSeries", "Format Data Series"},
		{"dataElement", "Format Data Point"},
		{"legend", "Format Legend"},
		{"legendEntry", "Format Legend Entry"},
		{"legendKey", "Format Legend Key"},
		{"chartTitle", "Format Chart Title"},
		{"chartArea", "Format Chart Area"},
		{"graphExtend", "Format Plot Area"},
		{"gridLine", "Format Gridlines"},
		{"axisTitle", "Format Axis Title"},
		{"axis", "Format Axis"},

		{"ok", "OK"},
		{"cancel", "Cancel"}
	};
}