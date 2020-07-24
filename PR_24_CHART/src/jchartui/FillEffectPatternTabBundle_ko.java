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

public class FillEffectPatternTabBundle_ko extends ListResourceBundle {

    public FillEffectPatternTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[] patternString = {
		 "5%", "50%", "밝은 역방향 사선", "밝은 수직선", "수평 역방향 사선", "지그 재그", "양방향 사선", "좁은 괘선",
		"10%", "60%", "밝은 정방향 사선", "밝은 수평선", "수평 정방향 사선", "물결", "점선 괘선", "넓은 괘선",
		"20%", "70%", "어두운 역방향 사선", "좁은 수직선", "점선 수평선", "벽돌 사선 무늬", "점선 다이아몬드", "좁은 체크 보드",
		"25%", "75%", "어두운 정방향 사선", "좁은 수평선", "점선 수직선", "수평 벽돌 무늬", "지붕 널", "넓은 체크 보드",
		"30%", "80%", "넓은 역방향 사선", "어두운 수직선", "작은 색종이 조각", "평직", "격자 울타리", "체크 무늬",
		"40%", "90%", "넓은 정방향 사선", "어두운 수평선", "색종이 조각", "격자 무늬", "타원 무늬", "다이아 몬드"
	};

	private Object[][] contents = {
		{"pattern", "패턴:"},
		{"foreground", "무늬 색(F)"},
		{"background", "배경 색(B)"},
		{"patternString", patternString}
	};
}