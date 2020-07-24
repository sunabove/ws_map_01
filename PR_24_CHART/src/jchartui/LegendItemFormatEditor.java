//package com.techdigm.chart.editor;
package jchartui;

/**
 * Title:       ���ʼ��� ���̾� �α� �ڽ� Ŭ����<p>
 * Description: JAVA CHART v1.0<p>
 * Copyright:   Copyright (c) juney<p>
 * Company:     JCosmos<p>
 * @author      withhim
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import com.techdigm.chart.*;
import jchart.*;

//import com.techdigm.chart.ShapeStyle;
import jchart.ShapeStyle;

import java.util.ResourceBundle;
import java.util.Locale;


public class LegendItemFormatEditor extends Editor implements BorderPool {

	JPanel contentPane;
    JTabbedPane tabPaneMain = new JTabbedPane();

    FontTab tabFont = new FontTab();

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	private ShapeStyle shapeStyle = new ShapeStyle();
	private Font font;
	private Color fontColor;

	LegendItem legendItem;

	public Locale currentLocale;


  //Construct the frame

  public LegendItemFormatEditor() {

	legendItem = ( LegendItem ) SpreadSheet.getCurrentSpreadSheet().getSelectedChartElement();

	//��ó�� �����Ǹ� ���� locale������ ������ ���´�.
//	this.currentLocale = jcalc.JCalcResource.getLocale();
	this.currentLocale = JCalcResource.getLocale();

    try {

      jbInit();

    }
    catch(Exception e) {
      e.printStackTrace();
    }

	this.setResizable(false);
//	this.setLocationRelativeTo(jcalc.JCalc.frame);
  }

  //Component initialization
  private void jbInit() throws Exception  {
//	ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/EditorDialogBundle", jcalc.JCalcResource.getLocale());
	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendItemFormatEditorBundle", JCalcResource.getLocale());

    contentPane = (JPanel) this.getContentPane();
	contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
        this.setTitle(bundle.getString("legendItemFormat"));    // "���� ����"
        tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));

		// OK Button �ʱ�ȭ
		buttonOk.setText(bundle.getString("okButton"));
        buttonOk.setBounds(new Rectangle(247, 360, 90, 20));
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		// Cancel Button �ʱ�ȭ
        buttonCancel.setText(bundle.getString("cancelButton"));
        buttonCancel.setBounds(new Rectangle(349, 360, 90, 20));
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonCancel_actionPerformed(e);
            }
        });

        contentPane.add(tabPaneMain, null);
		/*
		*/

		// tabbedPanel ����
        tabPaneMain.add(tabFont, bundle.getString("fontTab"));

		contentPane.add(buttonCancel, null);
        contentPane.add(buttonOk, null);

		tabPaneMain.setEnabledAt(0, true);

		initalizeValue();

  }
	public void initalizeValue() {

	}



	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendItemFormatEditorBundle", JCalcResource.getLocale());

        this.setTitle(bundle.getString("legendItemFormat"));    // "���� ����"
        tabPaneMain.add(tabFont, bundle.getString("fontTab"));

		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

    void buttonOk_actionPerformed(ActionEvent e) {

    }

    void buttonCancel_actionPerformed(ActionEvent e) {

    }

}