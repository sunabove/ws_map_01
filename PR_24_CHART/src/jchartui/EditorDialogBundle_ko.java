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

public class EditorDialogBundle_ko extends ListResourceBundle {

    public EditorDialogBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private Object[][] contents = {
//Tab�� Title
		{"titleTab",        "   ����   "},
		{"axisTab",         "   ��    "},
		{"gridTab",         "  ���ݼ� "},
		{"legendTab",       "  ����   "},
		{"dataLabelTab",    " ������ ���̺� "},
		{"dataTableTab",    " ������ ���̺� "},
		{"fontTab" ,        "   �۲�  "},
		{"patternTab",      "   ����  "},
		{"yErrorBarTab",    "Y ���� ����"},
		{"seriesOrderTab", "�迭 ����"},
		{"optionTab",       "   �ɼ�  "},
		{"placementTab",    "   ��ġ  "},
		{"alignmentTab",    "   ����  "},
		{"propertyTab",     "   �Ӽ�  "},
		{"scaleTab",        "   ����  "},
		{"numberTab",       " ǥ�� ���� "},

//�� Dialog�� Title
		{"chartOption", "��Ʈ �ɼ�"},
		{"dataSeries", "������ �迭 ����"},
		{"dataElement", "������ ��� ����"},
		{"legend", "���� ����"},
		{"legendEntry", "���� �׸� ����"},
		{"legendKey", "���� ǥ�� ����"},
		{"chartTitle", "��Ʈ ���� ����"},
		{"chartArea", "��Ʈ ���� ����"},
		{"graphExtend", "�׸� ���� ����"},
		{"gridLine", "���ݼ� ����"},
		{"axisTitle", "�� ���� ����"},
		{"axis", "�� ����"},

		{"ok", "Ȯ��"},
		{"cancel", "���"}
	};
}