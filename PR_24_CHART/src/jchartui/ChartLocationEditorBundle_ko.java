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

public class ChartLocationEditorBundle_ko extends ListResourceBundle {

    public ChartLocationEditorBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
		{"title1", "차트 위치"},
		{"title2", "차트 마법사 - 4 단계 중 4 단계 - 차트 위치"},
		{"placeChart", "차트 위치:"},
		{"asObject", "워크시트에 삽입(O):"},
		{"asNewSheet", "새로운 시트로(S):"},
		{"ok", "확인"},
		{"cancel", "취소"}
	};
}