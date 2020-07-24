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

public class DataLabelTabBundle_ko extends ListResourceBundle {

    public DataLabelTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"dataLabel", "데이터 레이블"},
		{"none", "없음"},
		{"showValue", "값 표시(V)"},
		{"showPercent", "백분율 표시(P)"},
		{"showLabel", "레이블 표시(L)"},
		{"showLabelAndPercent", "레이블과 백분율 표시(A)"},
		{"showBubbleSizes", "거품 크기 표시(B)"},
		{"legendKeyNextToLabel", "레이블 옆에 범례 표식 표시(K)"}
	};

}