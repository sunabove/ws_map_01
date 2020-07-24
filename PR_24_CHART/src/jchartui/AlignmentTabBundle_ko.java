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

public class AlignmentTabBundle_ko extends ListResourceBundle {

    public AlignmentTabBundle_ko() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	private String[] horizontalAlignment = {
	    "����",
		"���",
		"������",
		"���� ����",
		"�յ� ����"
	};

	private String[] verticalAlignment = {
	    "����",
		"���",
		"�Ʒ���",
		"���� ����",
		"�յ� ����"
	};

	private Object[][] contents = {
	    {"alignmentText", "�ؽ�Ʈ ����"},
		{"horizontal", "����(H) :"},
		{"horizontalAlignment", horizontalAlignment},
		{"vertical", "����(V) :"},
		{"verticalAlignment", verticalAlignment},
		{"orientation", "����"},
		{"degrees", "��(D)"}
	};
}