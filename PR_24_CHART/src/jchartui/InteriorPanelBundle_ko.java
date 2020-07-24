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

public class InteriorPanelBundle_ko extends ListResourceBundle {

    public InteriorPanelBundle_ko() {
    }

    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"areaAuto", "자동(U)"},
		{"areaNone", "없음(E)"},
		{"areaColor", "색(O)"},
		{"fillEffect", "채우기 효과(I)..."},
		{"invert", "음수이면 반전(V)"}
	};

}