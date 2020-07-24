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

public class PatternTabForAxisBundle_ko extends ListResourceBundle {

    public PatternTabForAxisBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"border", "테두리"},
		{"borderAuto", "자동(A)"},
		{"borderNone", "없음(N)"},
		{"borderCustom", "사용자 정의"},
		{"style", "스타일(S)"},
		{"borderColor", "색(C)"},
		{"weight", "두께(W)"},
		{"shadow", "그림자(D)"},
		{"area", "영역"},
		{"automatic", "자동"},
		{"areaAuto", "자동(U)"},
		{"areaNone", "없음(E)"},
		{"areaColor", "색"},
		{"fillEffect", "채우기 효과(I)..."},
		{"invert", "음수이면 반전(V)"},
		{"sample", "보기"}
	};
}