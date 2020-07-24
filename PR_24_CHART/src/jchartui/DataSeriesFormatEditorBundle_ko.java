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

public class DataSeriesFormatEditorBundle_ko extends ListResourceBundle {

    public DataSeriesFormatEditorBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"formatDataSeries", "데이터 계열 서식"},
		{"pattern", "무늬"},
		{"axes", "축"},
		{"yErrorBars", "Y 오차 막대"},
		{"dataLabel", "데이터 레이블"},
		{"seriesOrder", "계열 순서"},
		{"option", "옵션"},
		{"cancel", "취소"},
		{"ok", "확인"}
	};
}