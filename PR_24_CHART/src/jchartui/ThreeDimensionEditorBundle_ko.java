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

public class ThreeDimensionEditorBundle_ko extends ListResourceBundle {

    public ThreeDimensionEditorBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[] textureString = {
		 "인쇄용지", "재생지", "양피지", "편지지", "초록색 대리석", "흰색 대리석", "갈색 대리석",
		 "화강암", "파랑색 박엽지", "분홍색 박엽지", "자주색 편물", "꽃다발", "파피루스", "캔버스",
		 "데님", "짜서 만든 돗자리", "작은 물방울", "종이 가방", "화석어", "모래", "코르크", "월넛",
		 "오크", "일반 목재", ""
	};

	private Object[][] contents = {
		{"textureStirng", textureString},
		{"selectTexture", "질감 선택"},
		{"otherTexture", "다른 질감.."},
		{"texture", "질감:"},
		{"textureString", textureString},
	};

}