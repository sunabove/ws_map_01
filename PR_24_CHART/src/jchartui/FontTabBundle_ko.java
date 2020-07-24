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
	    "����",
		"�Ǽ�",
		"���߽Ǽ�"
	};

	private String[] backgrounds = {
		"�ڵ�",
		"����",
		"������"
	};

	private String[] fontStyleList = {
		"�Ϲ�",
		"�����",
		"����",
		"���� �����"
	};

	private Object[][] contents = {
	    {"font", "�۲�(F)"},
		{"fontStyle", "�۲� ��Ÿ��(O)"},
		{"size", "ũ��(S)"},
		{"underline", "����(U)"},
		{"color", "��(C)"},
		{"background", "���(A)"},
		{"effect", "ȿ��"},
		{"strikethrough", "��Ҽ�(K)"},
		{"superscript", "��÷��(E)"},
		{"subscript", "�Ʒ�÷��(B)"},
		{"preview", "�̸�����"},
		{"autoScale", "�ڵ�ũ�� ����(T)"},
		{"underlines", underlines},
		{"backgrounds", backgrounds},
		{"styles", fontStyleList},
		{"sampleText", "������AaBbCc"},
		{"automatic", "�ڵ�"}
	};
}