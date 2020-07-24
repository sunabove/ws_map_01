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


public class LegendItemSymbolFormatEditor extends Editor implements BorderPool {

	LegendItemSymbol legendItemSymbol;

	JPanel contentPane;
    JTabbedPane tabPaneMain = new JTabbedPane();

    PatternTab tabPattern = new PatternTab( PatternTab.AREA_MODE );

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	private ShapeStyle shapeStyle = new ShapeStyle();



	public Locale currentLocale;


  //Construct the frame

  public LegendItemSymbolFormatEditor() {



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
	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendItemSymbolFormatEditorBundle", JCalcResource.getLocale());

    contentPane = (JPanel) this.getContentPane();
	contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
        tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));

		// OK Button �ʱ�ȭ

        buttonOk.setBounds(new Rectangle(247, 360, 90, 20));
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		// Cancel Button �ʱ�ȭ
        buttonCancel.setBounds(new Rectangle(349, 360, 90, 20));
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonCancel_actionPerformed(e);
            }
        });

        contentPane.add(tabPaneMain, null);
		contentPane.add(buttonCancel, null);
        contentPane.add(buttonOk, null);


		/*
		*/

		// tabbedPanel ����
        // ���̾�α� �ڽ� �̸� Text �ٲٱ�
		this.setTitle(bundle.getString("legendItemSymbolFormat"));    // "���� ǥ�� ����"

		// ���� �̸� Text �ٲٱ�
        tabPaneMain.add(tabPattern, bundle.getString("patternTab"));

		// ������ �� ������ Text �ٲٱ�
		tabPattern.changeMenuText();

		// OK, Cancel, ��ư �̸� Text �ٲٱ�
		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));


  }


	public void initalizeDialog() {

		tabPattern.initializeDialogTab();

	}



	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendItemSymbolFormatEditorBundle", JCalcResource.getLocale());

		// ���̾�α� �ڽ� �̸� Text �ٲٱ�
		this.setTitle(bundle.getString("legendItemSymbolFormat"));    // "���� ǥ�� ����"

		// ���� �̸� Text �ٲٱ�
        tabPaneMain.add(tabPattern, bundle.getString("patternTab"));

		// ������ �� ������ Text �ٲٱ�
		tabPattern.changeMenuText();

		// OK, Cancel, ��ư �̸� Text �ٲٱ�
		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

    void buttonOk_actionPerformed(ActionEvent e) {

		tabPattern.applyToChartElement();


		// ȭ���� ������Ʈ�Ѵ�.
		legendItemSymbol.getSheet().repaint();

		this.hide();


    }

    void buttonCancel_actionPerformed(ActionEvent e) {

		this.hide();

    }

	public void show() {

	    SpreadSheet sheet = SpreadSheet.getCurrentSpreadSheet();

		this.legendItemSymbol  = (LegendItemSymbol) sheet.getSelectedChartElement();

		tabPattern.setChartElement(this.legendItemSymbol);

		initalizeDialog();


		super.show();

	}

}