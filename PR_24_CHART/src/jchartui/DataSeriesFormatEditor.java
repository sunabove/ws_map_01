
package jchartui;

/**
 * Title:       데이터 계열 서식 다이얼 로그 박스 클래스<p>
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
import jchart.*;

import jchart.ShapeStyle;

import java.util.ResourceBundle;
import java.util.Locale;


public class DataSeriesFormatEditor extends Editor {

	ChartElement chartElement;

	JPanel contentPane;
    JTabbedPane tabPaneMain = new JTabbedPane();

	PatternTab tabPattern;
	DataLabelTab tabDataLabel;
//    OptionTab tabOption;

	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	private ShapeStyle shapeStyle;



  //Construct the frame
  public DataSeriesFormatEditor() {

	tabPattern = new PatternTab( PatternTab.AREA_MODE );
    tabDataLabel = new DataLabelTab();
//    tabOption = new OptionTab();


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

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/DataSeriesFormatEditorBundle", JCalcResource.getLocale());

        contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(447, 413));
        this.setTitle(bundle.getString("formatDataSeries"));    // "데이터 계열 서식"
        tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));


		// OK Button 초기화
		buttonOk.setText(bundle.getString("ok"));
        buttonOk.setBounds(new Rectangle(247, 360, 90, 20));
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		// Cancel Button 초기화
        buttonCancel.setText(bundle.getString("cancel"));
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
        tabPaneMain.add(tabPattern, bundle.getString("pattern"));
        tabPaneMain.add(tabDataLabel, bundle.getString("dataLabel"));
//        tabPaneMain.add(tabOption, bundle.getString("option"));

		contentPane.add(buttonCancel, null);
        contentPane.add(buttonOk, null);



  }


	public void changeMenuText() {
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/DataSeriesFormatEditorBundle", JCalcResource.getLocale());

		this.setTitle(bundle.getString("formatDataSeries"));

		tabPaneMain.add(tabPattern, bundle.getString("pattern"));
        tabPaneMain.add(tabDataLabel, bundle.getString("dataLabel"));
//        tabPaneMain.add(tabOption, bundle.getString("option"));

		buttonOk.setText(bundle.getString("ok"));
		buttonCancel.setText(bundle.getString("cancel"));
	}

	/**
	 * 축 제목 서식 다이얼로그 박스에서 OK 버튼을 눌렀을 때
	 * 모든 Tab에서 적용된 값들을 선택된 ChartTitle에 적용 시키고
	 * 리페인트 시킨다.
	 */
	void buttonOk_actionPerformed(ActionEvent e) {
		tabPattern.applyToChartElement();
		tabDataLabel.applyToChartElement();
//		tabOption.applyToTextualChartElement();
		this.hide();
		SpreadSheet.getCurrentSpreadSheet().repaint();

    }

	/**
	 * 축 제목 서식 다이얼로그 박스에서 Cancle 버튼을 눌렀을 때
	 * 아무일도 하지 않고 다이얼로그 박스를 닫는다.
	 */
    void buttonCancel_actionPerformed(ActionEvent e) {

//		initalizeDialog();
		this.hide();

    }

	public void initalizeDialog() {

		tabPattern.initializeDialogTab();
		tabDataLabel.initializeDialogTab();
//		tabOption.initalizeDialogTab();

	}

	public void show() {

	    SpreadSheet sheet = SpreadSheet.getCurrentSpreadSheet();

		this.chartElement = sheet.getSelectedChartElement();


		tabPattern.setChartElement( this.chartElement );
		tabDataLabel.setChartElement( this.chartElement );
//		tabOption.setTextualChartElement(this.title);

		initalizeDialog();


		super.show();

	}



}