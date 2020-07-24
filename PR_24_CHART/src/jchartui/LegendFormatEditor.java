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


public class LegendFormatEditor extends Editor {
	Legend legend;
	JPanel contentPane;
    JTabbedPane tabPaneMain = new JTabbedPane();

	PatternTab tabPattern;
    FontTab tabFont;
    PlacementTab tabPlacement;

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	private ShapeStyle shapeStyle = new ShapeStyle();
	private Font font;
	private Color fontColor;



	public Locale currentLocale;


  //Construct the frame
//  public AxisStyleEditor(DataSheet dataSheet) {
  public LegendFormatEditor() {

	tabPattern = new PatternTab( PatternTab.AREA_MODE );
    tabFont = new FontTab();
    tabPlacement = new PlacementTab();


	//��ó�� �����Ǹ� ���� locale������ ������ ���´�.
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
//	    ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/EditorDialogBundle", jcalc.JCalcResource.getLocale());
	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendFormatEditorBundle", JCalcResource.getLocale());

        contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
        this.setTitle(bundle.getString("legendFormat"));    // "���� ����"
        tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
        tabPlacement.setEnabled(false);

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
        tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
        tabPaneMain.add(tabFont, bundle.getString("fontTab"));
        tabPaneMain.add(tabPlacement, bundle.getString("placementTab"));

		contentPane.add(buttonCancel, null);
        contentPane.add(buttonOk, null);

		tabPaneMain.setEnabledAt(0, true);
		tabPaneMain.setEnabledAt(1, true);
		tabPaneMain.setEnabledAt(2, true);


  }

	public void initalizeDialog() {

		tabPattern.initializeDialogTab();
		tabPlacement.initializeDialogTab();

	}



	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PlacementTabBundle", JCalcResource.getLocale());

		tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
        tabPaneMain.add(tabFont, bundle.getString("fontTab"));
        tabPaneMain.add(tabPlacement, bundle.getString("placementTab"));

		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

    void buttonOk_actionPerformed(ActionEvent e) {

		tabPattern.applyToChartElement();

		tabPlacement.applyToChartElement();


		// ȭ���� ������Ʈ�Ѵ�.
		legend.getSheet().repaint();

		this.hide();

    }

    void buttonCancel_actionPerformed(ActionEvent e) {

		this.hide();

    }

	public void show() {

	    SpreadSheet sheet = SpreadSheet.getCurrentSpreadSheet();

		this.legend = ( Legend ) sheet.getSelectedChartElement();

		tabPattern.setChartElement(this.legend);

		tabPlacement.setChartElement(this.legend);

		initalizeDialog();

		super.show();

	}



}