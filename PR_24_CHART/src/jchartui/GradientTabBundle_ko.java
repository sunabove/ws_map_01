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
		"�̸� ������", "���� ������", "���� ���", "����",
		"�ؾ�", "��" , "�̳�", "������", "��ȣ����",
		"�ݻ�", "����", "�����̾�"
	};

	private Object[][] contents = {
		{"color", "��"},
		{"oneColor", "�� ��(O)"},
		{"twoColor", "�� ��(T)"},
		{"preset", "�̸� �����Ȼ�(R)"},
		{"shadingStyle", "����"},
		{"horizontal", "����(Z)"},
		{"vertical", "����(V)"},
		{"diagonalUp", "������ �밢��(U)"},
		{"diagonalDown", "���� �밢��(D)"},
		{"fromCorner", "�𼭸�����(F)"},
		{"fromCenter", "�������(M)"},
		{"variants", "����(S)"},
		{"dark", "��Ӱ�"},
		{"light", "���"},
		{"presetString", presetString}
	};
}