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

public class DataLabelTabBundle_ko extends ListResourceBundle {

    public DataLabelTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
	    {"dataLabel", "������ ���̺�"},
		{"none", "����"},
		{"showValue", "�� ǥ��(V)"},
		{"showPercent", "����� ǥ��(P)"},
		{"showLabel", "���̺� ǥ��(L)"},
		{"showLabelAndPercent", "���̺�� ����� ǥ��(A)"},
		{"showBubbleSizes", "��ǰ ũ�� ǥ��(B)"},
		{"legendKeyNextToLabel", "���̺� ���� ���� ǥ�� ǥ��(K)"}
	};

}