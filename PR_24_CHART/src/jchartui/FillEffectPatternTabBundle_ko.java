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
		 "5%", "50%", "���� ������ �缱", "���� ������", "���� ������ �缱", "���� ���", "����� �缱", "���� ����",
		"10%", "60%", "���� ������ �缱", "���� ����", "���� ������ �缱", "����", "���� ����", "���� ����",
		"20%", "70%", "��ο� ������ �缱", "���� ������", "���� ����", "���� �缱 ����", "���� ���̾Ƹ��", "���� üũ ����",
		"25%", "75%", "��ο� ������ �缱", "���� ����", "���� ������", "���� ���� ����", "���� ��", "���� üũ ����",
		"30%", "80%", "���� ������ �缱", "��ο� ������", "���� ������ ����", "����", "���� ��Ÿ��", "üũ ����",
		"40%", "90%", "���� ������ �缱", "��ο� ����", "������ ����", "���� ����", "Ÿ�� ����", "���̾� ���"
	};

	private Object[][] contents = {
		{"pattern", "����:"},
		{"foreground", "���� ��(F)"},
		{"background", "��� ��(B)"},
		{"patternString", patternString}
	};
}