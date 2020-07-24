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

public class GridTabForValueAxisBundle_ko extends ListResourceBundle {

    public GridTabForValueAxisBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private String[] displayUnits = {
		"����",
	    "��",
		"õ",
		"�鸸",
		"��"
	};

	private Object[][] contents = {
	    {"valueAxisScale", "Y(��)�� ����"},
		{"auto", "�ڵ�"},
		{"minimum", "�ּҰ�(N):"},
		{"maximum", "�ִ밪(X):"},
		{"majorUnit", "�� ����(A):"},
		{"minorUnit", "���� ����(I):"},
//		{"xAxis", "X(�׸�) ��"},
//		{"crossesAt", "����(C):"},
		{"xAxisCrossesAt", "X(�׸�) �� ����(C):"},
		{"displayUnit", "ǥ�� ����(U):"},
		{"displayUnits", displayUnits},
		{"showDisplayUnitLabelOnChart", "��Ʈ�� ���� ���̺� ǥ��(D)"},
		{"logarithmicScale", "�α� ���� ����(L)"},
		{"categoriesInReverseOrder", "�� ����(R)"},
		{"xAxisCrossesAtMaxValue", "�ִ밪���� X(�׸�) �� ����(M)"}
	};
}