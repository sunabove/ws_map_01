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

public class GradientTabBundle_ko extends ListResourceBundle {

    public GradientTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[]  presetString = {
		"이른 해질녁", "늦은 해질녁", "밤의 어둠", "새벽",
		"해양", "불" , "이끼", "양피지", "마호가니",
		"금색", "은색", "사파이어"
	};

	private Object[][] contents = {
		{"color", "색"},
		{"oneColor", "한 색(O)"},
		{"twoColor", "두 색(T)"},
		{"preset", "미리 설정된색(R)"},
		{"shadingStyle", "유형"},
		{"horizontal", "수평(Z)"},
		{"vertical", "수직(V)"},
		{"diagonalUp", "오른쪽 대각선(U)"},
		{"diagonalDown", "왼쪽 대각선(D)"},
		{"fromCorner", "모서리에서(F)"},
		{"fromCenter", "가운데에서(M)"},
		{"variants", "적용(S)"},
		{"dark", "어둡게"},
		{"light", "밝게"},
		{"presetString", presetString}
	};
}