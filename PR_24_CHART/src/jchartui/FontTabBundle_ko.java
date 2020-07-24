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

public class FontTabBundle_ko extends ListResourceBundle {

    public FontTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private String[] underlines = {
	    "없음",
		"실선",
		"이중실선"
	};

	private String[] backgrounds = {
		"자동",
		"투명",
		"불투명"
	};

	private String[] fontStyleList = {
		"일반",
		"기울임",
		"굵게",
		"굵게 기울임"
	};

	private Object[][] contents = {
	    {"font", "글꼴(F)"},
		{"fontStyle", "글꼴 스타일(O)"},
		{"size", "크기(S)"},
		{"underline", "밑줄(U)"},
		{"color", "색(C)"},
		{"background", "배경(A)"},
		{"effect", "효과"},
		{"strikethrough", "취소선(K)"},
		{"superscript", "위첨자(E)"},
		{"subscript", "아래첨자(B)"},
		{"preview", "미리보기"},
		{"autoScale", "자동크기 조정(T)"},
		{"underlines", underlines},
		{"backgrounds", backgrounds},
		{"styles", fontStyleList},
		{"sampleText", "가나다AaBbCc"},
		{"automatic", "자동"}
	};
}