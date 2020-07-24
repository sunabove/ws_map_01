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

public class ChartOptionEditorBundle_en extends ListResourceBundle {

    public ChartOptionEditorBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }


	private Object[][] contents = {
		{"title1", "Chart Options"},
		{"title2", "Chart Wizard - Step 4 of 3"},
		{"chartTitle", "Chart title:"},
		{"xAxisTitle", "Category (X) axis:"},
		{"yAxisTitle", "Value (Y) acis:"},
		{"secondXTitle", "Second category (X) axis:"},
		{"secondYTitle", "Second vlaue (Y) axis:"},
		{"primaryAxis", "Primary axis"},
		{"checkXAxis", "Category (X) axis"},
		{"auto", "Automatic"},
		{"category", "Category"},
		{"timeScale", "Time-Scale"},
		{"checkYAxis", "Value (Y) axis"},
		{"checkZAxis", "Value (Z) axis"},
		{"gridXAxis", "Category (X) axis"},
		{"gridYAxis", "Value (Y) axis"},
		{"xMajorGrid", "Major gridlines"},
		{"xMinorGrid", "Minor gridlines"},
		{"yMajorGrid", "Major gridlines"},
		{"yMinorGrid", "Minor gridlines"},
		{"showLegend", "Show legend"},
		{"hasDataTable", "Show data table"},
		{"showLegendKey", "Show legend keys"},
		{"ok", "OK"},
		{"cancel", "Cancel"}
	};
}