//package com.techdigm.chart.editor;
package jchartui;

/**
 * Title:       �� ���� ���� ���̾� �α� �ڽ� Ŭ����<p>
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


public class AxisTitleFormatEditor extends Editor {

	Title title;

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



  //Construct the frame
  public AxisTitleFormatEditor() {

	tabPattern = new PatternTab( PatternTab.AREA_MODE );
    tabFont = new FontTab();
    tabAlignment = new AlignmentTab();


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

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/AxisTitleFormatEditorBundle", JCalcResource.getLocale());

	contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
	this.setTitle(bundle.getString("axisTitleFormat"));    // "�� ���� ����"
	tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
	tabAlignment.setEnabled(false);

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
	tabPaneMain.add(tabAlignment, bundle.getString("alignmentTab"));

		contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);



  }




	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/AxisTitleFormatEditorBundle", JCalcResource.getLocale());

		this.setTitle(bundle.getString("axisTitleFormat"));    // "�� ���� ����"

		tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabFont, bundle.getString("fontTab"));
	tabPaneMain.add(tabAlignment, bundle.getString("alignmentTab"));

		buttonOk.setText(bundle.getString("okButton"));
		buttonCancel.setText(bundle.getString("cancelButton"));
	}

	/**
	 * �� ���� ���� ���̾�α� �ڽ����� OK ��ư�� ������ ��
	 * ��� Tab���� ����� ������ ���õ� ChartTitle�� ���� ��Ű��
	 * ������Ʈ ��Ų��.
	 */
	void buttonOk_actionPerformed(ActionEvent e) {
		tabPattern.applyToChartElement();
		tabAlignment.applyToChartElement();

		this.hide();

		SpreadSheet.getCurrentSpreadSheet().repaint();

    }

	/**
	 * �� ���� ���� ���̾�α� �ڽ����� Cancle ��ư�� ������ ��
	 * �ƹ��ϵ� ���� �ʰ� ���̾�α� �ڽ��� �ݴ´�.
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

		ChartElement cse = getSelectedChartElementOnTheCurrentSpreadSheet();

		tabPattern.setChartElement( cse );
		tabAlignment.setChartElement( cse );

		initializeDialog();


		super.show();

	}



}