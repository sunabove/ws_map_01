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

public class EditorDialogBundle_ko extends ListResourceBundle {

    public EditorDialogBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
//Tab의 Title
		{"titleTab",        "   제목   "},
		{"axisTab",         "   축    "},
		{"gridTab",         "  눈금선 "},
		{"legendTab",       "  범례   "},
		{"dataLabelTab",    " 데이터 레이블 "},
		{"dataTableTab",    " 데이터 테이블 "},
		{"fontTab" ,        "   글꼴  "},
		{"patternTab",      "   무늬  "},
		{"yErrorBarTab",    "Y 오차 막대"},
		{"seriesOrderTab", "계열 순서"},
		{"optionTab",       "   옵션  "},
		{"placementTab",    "   배치  "},
		{"alignmentTab",    "   맞춤  "},
		{"propertyTab",     "   속성  "},
		{"scaleTab",        "   눈금  "},
		{"numberTab",       " 표시 형식 "},

//각 Dialog의 Title
		{"chartOption", "차트 옵션"},
		{"dataSeries", "데이터 계열 서식"},
		{"dataElement", "데이터 요소 서식"},
		{"legend", "범례 서식"},
		{"legendEntry", "범례 항목 서식"},
		{"legendKey", "범례 표식 서식"},
		{"chartTitle", "차트 제목 서식"},
		{"chartArea", "차트 영역 서식"},
		{"graphExtend", "그림 영역 서식"},
		{"gridLine", "눈금선 서식"},
		{"axisTitle", "축 제목 서식"},
		{"axis", "축 서식"},

		{"ok", "확인"},
		{"cancel", "취소"}
	};
}