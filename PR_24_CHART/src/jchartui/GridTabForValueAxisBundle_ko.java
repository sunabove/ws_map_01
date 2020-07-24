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

public class GridTabForValueAxisBundle_ko extends ListResourceBundle {

    public GridTabForValueAxisBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private String[] displayUnits = {
		"없음",
	    "백",
		"천",
		"백만",
		"억"
	};

	private Object[][] contents = {
	    {"valueAxisScale", "Y(값)축 눈금"},
		{"auto", "자동"},
		{"minimum", "최소값(N):"},
		{"maximum", "최대값(X):"},
		{"majorUnit", "주 단위(A):"},
		{"minorUnit", "보조 단위(I):"},
//		{"xAxis", "X(항목) 축"},
//		{"crossesAt", "교점(C):"},
		{"xAxisCrossesAt", "X(항목) 축 교점(C):"},
		{"displayUnit", "표시 단위(U):"},
		{"displayUnits", displayUnits},
		{"showDisplayUnitLabelOnChart", "차트에 단위 레이블 표시(D)"},
		{"logarithmicScale", "로그 눈금 간격(L)"},
		{"categoriesInReverseOrder", "축 반전(R)"},
		{"xAxisCrossesAtMaxValue", "최대값에서 X(항목) 축 교차(M)"}
	};
}