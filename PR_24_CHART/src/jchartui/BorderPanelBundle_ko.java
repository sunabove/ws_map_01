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

public class BorderPanelBundle_ko extends ListResourceBundle {

    public BorderPanelBundle_ko() {
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
		{"automatic", "자동"},
		{"weight", "두께(W)"},
		{"shadow", "그림자(D)"}
	};

}