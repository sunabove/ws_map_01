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

public class ChartOptionEditorBundle_ko extends ListResourceBundle {

    public ChartOptionEditorBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }


	private Object[][] contents = {
		{"title1", "차트 옵션"},
		{"title2", "차트 마법사 - 4 단계 중 3 단계"},
		{"chartTitle", "차트 제목(T):"},
		{"xAxisTitle", "X(항목) 축(C):"},
		{"yAxisTitle", "Y(값) 축(V):"},
		{"secondXTitle", "보조 X(항목) 축(X):"},
		{"secondYTitle", "보조 Y(항목) 축(Y):"},
		{"primaryAxis", "기본 축"},
		{"checkXAxis", "X(항목) 축(G)"},
		{"auto", "자동"},
		{"category", "항목"},
		{"timeScale", "시간 단위"},
		{"checkYAxis", "Y(계열) 축(S)"},
		{"checkZAxis", "Z(값) 축(V)"},
		{"gridXAxis", "X(항목) 축"},
		{"gridYAxis", "Y(값) 축"},
		{"xMajorGrid", "주 눈금선(M)"},
		{"xMinorGrid", "보조 눈금선(I)"},
		{"yMajorGrid", "주 눈금선(O)"},
		{"yMinorGrid", "보조 눈금선(G)"},
		{"showLegend", "범례 표시(S)"},
		{"hasDataTable", "데이터 테이블 표시(D)"},
		{"labelLegendKey", "범례 표식 표시(L)"},
		{"ok", "확인"},
		{"cancel", "최소"}
	};
}