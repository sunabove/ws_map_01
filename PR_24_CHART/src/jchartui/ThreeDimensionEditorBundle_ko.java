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
		 "�μ����", "�����", "������", "������", "�ʷϻ� �븮��", "��� �븮��", "���� �븮��",
		 "ȭ����", "�Ķ��� �ڿ���", "��ȫ�� �ڿ���", "���ֻ� ��", "�ɴٹ�", "���Ƿ罺", "ĵ����",
		 "����", "¥�� ���� ���ڸ�", "���� �����", "���� ����", "ȭ����", "��", "�ڸ�ũ", "����",
		 "��ũ", "�Ϲ� ����", ""
	};

	private Object[][] contents = {
		{"textureStirng", textureString},
		{"selectTexture", "���� ����"},
		{"otherTexture", "�ٸ� ����.."},
		{"texture", "����:"},
		{"textureString", textureString},
	};

}