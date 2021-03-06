//package com.techdigm.chart.editor;
package jchartui;

/**
 * Title:       범례서식 다이얼 로그 박스 클래스<p>
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


	//맨처음 생성되면 현재 locale정보를 저장해 놓는다.
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
        this.setTitle(bundle.getString("legendFormat"));    // "범례 서식"
        tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
        tabPlacement.setEnabled(false);

		// OK Button 초기화
		buttonOk.setText(bundle.getString("okButton"));
        buttonOk.setBounds(new Rectangle(247, 360, 90, 20));
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		// Cancel Button 초기화
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

		// tabbedPanel 생성
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


		// 화면을 리페인트한다.
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