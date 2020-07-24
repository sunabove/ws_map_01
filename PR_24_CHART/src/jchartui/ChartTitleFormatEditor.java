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


public class ChartTitleFormatEditor extends Editor implements BorderPool {

	JPanel contentPane;
    JTabbedPane tabPaneMain = new JTabbedPane();

	PatternTab tabPattern;
    FontTab tabFont;
    AlignmentTab tabAlignment;

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	private ShapeStyle shapeStyle = new ShapeStyle();
	private Font font;
	private Color fontColor;



	public Locale currentLocale;


  //Construct the frame
  public ChartTitleFormatEditor() {

	tabPattern = new PatternTab( PatternTab.AREA_MODE );
    tabFont = new FontTab();
    tabAlignment = new AlignmentTab();


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

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartTitleFormatEditorBundle", JCalcResource.getLocale());

	contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
	this.setTitle(bundle.getString("chartTitleFormat"));    // "범례 서식"
	tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
	tabAlignment.setEnabled(false);

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
	tabPaneMain.add(tabAlignment, bundle.getString("alignmentTab"));

		contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);




  }




	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartTitleFormatEditorBundle", JCalcResource.getLocale());

		tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabFont, bundle.getString("fontTab"));
	tabPaneMain.add(tabAlignment, bundle.getString("alignmentTab"));

		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

	/**
	 * 차트 제목 서식 다이얼로그 박스에서 OK 버튼을 눌렀을 때
	 * 모든 Tab에서 적용된 값들을 선택된 ChartTitle에 적용 시키고
	 * 리페인트 시킨다.
	 */
	void buttonOk_actionPerformed(ActionEvent e) {
		tabPattern.applyToChartElement();
		tabAlignment.applyToChartElement();
		this.hide();
		SpreadSheet.getCurrentSpreadSheet().repaint();

    }

	/**
	 * 차트 제목 서식 다이얼로그 박스에서 Cancle 버튼을 눌렀을 때
	 * 아무일도 하지 않고 다이얼로그 박스를 닫는다.
	 */
    void buttonCancel_actionPerformed(ActionEvent e) {

		initializeDialog();
		this.hide();

    }

	protected void initializeDialog() {

		tabPattern.initializeDialogTab();
		tabAlignment.initializeDialogTab();

	}

	public void show() {

	    tabPattern.setChartElement( getSelectedChartElementOnTheCurrentSpreadSheet() );
	    tabAlignment.setChartElement( getSelectedChartElementOnTheCurrentSpreadSheet() );

		initializeDialog();


		super.show();

	}



}