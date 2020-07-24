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



	//맨처음 생성되면 현재 locale정보를 저장해 놓는다.
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

		// OK Button 초기화

        buttonOk.setBounds(new Rectangle(247, 360, 90, 20));
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		// Cancel Button 초기화
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

		// tabbedPanel 생성
        // 다이얼로그 박스 이름 Text 바꾸기
		this.setTitle(bundle.getString("legendItemSymbolFormat"));    // "범례 표식 서식"

		// 탭의 이름 Text 바꾸기
        tabPaneMain.add(tabPattern, bundle.getString("patternTab"));

		// 각각의 탭 내용의 Text 바꾸기
		tabPattern.changeMenuText();

		// OK, Cancel, 버튼 이름 Text 바꾸기
		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));


  }


	public void initalizeDialog() {

		tabPattern.initializeDialogTab();

	}



	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/LegendItemSymbolFormatEditorBundle", JCalcResource.getLocale());

		// 다이얼로그 박스 이름 Text 바꾸기
		this.setTitle(bundle.getString("legendItemSymbolFormat"));    // "범례 표식 서식"

		// 탭의 이름 Text 바꾸기
        tabPaneMain.add(tabPattern, bundle.getString("patternTab"));

		// 각각의 탭 내용의 Text 바꾸기
		tabPattern.changeMenuText();

		// OK, Cancel, 버튼 이름 Text 바꾸기
		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

    void buttonOk_actionPerformed(ActionEvent e) {

		tabPattern.applyToChartElement();


		// 화면을 리페인트한다.
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