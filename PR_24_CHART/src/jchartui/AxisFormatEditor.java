//package com.techdigm.chart.editor;
package jchartui;

/**
 * Title:        chartwizard<p>
 * Description:  JAVA CHART v1.0<p>
 * Copyright:    Copyright (c) juney<p>
 * Company:      JCosmos<p>
 * @author juney
 * @version 1.0
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import com.techdigm.chart.*;
import jchart.*;

//import com.techdigm.chart.ShapeStyle;
//import jchart.ShapeStyle;

import java.util.ResourceBundle;
import java.util.Locale;

public class AxisFormatEditor extends Editor {

    JPanel contentPane;
	JTabbedPane tabPaneMain = new JTabbedPane();

    PatternTab tabPattern;
    GridTabForValueAxis tabGrid;
    FontTab tabFont;
    JPanel tabDisplayFormat;
    AlignmentTabForAxis tabAlign;

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	public Locale currentLocale;

    public AxisFormatEditor() {

		tabPattern = new PatternTab(PatternTab.AXIS_MODE);
		tabGrid = new GridTabForValueAxis();
		tabFont = new FontTab();
		tabDisplayFormat = new JPanel();
		tabAlign = new AlignmentTabForAxis();

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

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/EditorDialogBundle", JCalcResource.getLocale());

		contentPane = (JPanel) this.getContentPane();

		// Layout을 정한다.
		contentPane.setLayout(null);

		// Component들의 Bound를 정한다.
		this.setSize(new Dimension(447, 413));
		tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
		buttonCancel.setBounds(new Rectangle(349, 360, 90, 20));
		buttonOk.setBounds(new Rectangle(247, 360, 90, 20));


	// Component들을 첨가시킨다.
	contentPane.add(tabPaneMain, null);

		tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabGrid, bundle.getString("scaleTab"));
	tabPaneMain.add(tabFont, bundle.getString("fontTab"));
	tabPaneMain.add(tabDisplayFormat, bundle.getString("numberTab"));
	tabPaneMain.add(tabAlign, bundle.getString("alignmentTab"));

		contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);


		// Component들의 Text를 설정한다.
	    this.setTitle(bundle.getString("axis"));// "축 서식"
		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));


		// 리스너를 등록시킨다.
		buttonOk.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonOk_actionPerformed(e);
	    }
	});
		buttonCancel.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonCancel_actionPerformed(e);
	    }
	});


  }

    void buttonOk_actionPerformed(ActionEvent e) {

		tabPattern.applyToChartElement();
		tabAlign.applyToChartElement();
		tabGrid.applyToChartElement();

		this.dispose();

		SpreadSheet.getCurrentSpreadSheet().repaint();

    }

    void buttonCancel_actionPerformed(ActionEvent e) {

		initializeDialog();

		this.dispose();

    }

	public void changeMenuText() {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/EditorDialogBundle", JCalcResource.getLocale());

	    this.setTitle(bundle.getString("axis"));// "축 서식"

	    tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabGrid, bundle.getString("scaleTab"));
	tabPaneMain.add(tabFont, bundle.getString("fontTab"));
	tabPaneMain.add(tabDisplayFormat, bundle.getString("numberTab"));
	tabPaneMain.add(tabAlign, bundle.getString("alignmentTab"));

		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));

		this.tabPattern.changeMenuText();
		this.tabFont.changeMenuText();
		this.tabGrid.changeMenuText();

	}

	public void initializeDialog() {

	    tabPattern.initializeDialogTab();
		tabGrid.initializeDialogTab();
		tabAlign.initializeDialogTab();

	}

	public void show() {

	     ChartElement cse = getSelectedChartElementOnTheCurrentSpreadSheet();

		tabPattern.setChartElement( cse );
	    tabGrid.setChartElement( cse );
		tabAlign.setChartElement( cse );

		this.initializeDialog();

		super.show();

	}

}