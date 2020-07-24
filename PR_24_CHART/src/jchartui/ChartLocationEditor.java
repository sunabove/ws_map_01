
/**
 * Title:        chartwizard<p>
 * Description:  JAVA CHART v1.0<p>
 * Copyright:    Copyright (c) juney<p>
 * Company:      JCosmos<p>
 * @author juney
 * @version 1.0
 */
//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
//import com.techdigm.chart.*;
import jchart.*;
import jcosmos.Utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class ChartLocationEditor extends JDialog {
	JPanel contentPane;

	//private DataSheet dataSheet;
	public Locale currentLocale;

	//Wizard Mode/ 일반 mode를 설정하는 변수..
	private boolean isWizardMode = false;
	private final byte stepIndex = 4;
	//private ChartWizardButtonPanel wizardPanel ;// = new ChartWizardButtonPanel();
	String[] comboBoxList ;
	private int comboIndex =0;

	JComboBox comboBoxSheetList ;//= new JComboBox();
	JTextField textFieldNewSheetName = new JTextField();
	JRadioButton radioButtonNewSheet = new JRadioButton();
	JRadioButton radioButtonAsObject = new JRadioButton();
	JButton buttonHelp = new JButton();
	JLabel labelLocation = new JLabel();
	JPanel panelGeneralButton = new JPanel();
	JButton buttonOk = new JButton();
	JButton buttonCancel = new JButton();
    JToggleButton buttonImage1 = new JToggleButton();
    JToggleButton buttonImage2 = new JToggleButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();

    //Construct the frame
	//public ChartLocationEditor(DataSheet dataSheet) {
	public ChartLocationEditor() {
		//super(jcalc.JCalc.frame, true);
		//this.dataSheet = dataSheet;
		//wizardPanel = new ChartWizardButtonPanel(this.dataSheet);
		//맨처음 생성되면 현재 locale정보를 저장해 놓는다.
		//this.currentLocale = jcalc.JCalcResource.getLocale();
		this.currentLocale = jchartui.JCalcResource.getLocale();


		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//this.setWizardMode(this.isWizardMode);
		//this.wizardPanel.setParent(this);
		//this.wizardPanel.setStep(this.stepIndex);
		this.setResizable(false);
		//this.setLocationRelativeTo(jcalc.JCalc.frame);
	}

	//Component initialization
	private void jbInit() throws Exception  {
//		ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/ChartLocationEditorBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartLocationEditorBundle", jchartui.JCalcResource.getLocale());
		// 콤보 박스에 sheet이름 넣기 (현재 존재하는 sheet에서 가져오기) -ㅡ수정 필요 (sheet이름까지도 가져와야 하는 건지 알아두기)
//		comboBoxList = new String[this.dataSheet.getSheet().workbook.workSheets.getSize()];
//		for(int a = 1; a< this.dataSheet.getSheet().workbook.workSheets.getSize()+1; a++){
//			comboBoxList[a-1] = "sheet"+ a ;
//		}

		labelLocation.setPreferredSize(new Dimension(60, 15));
        labelLocation.setText(bundle.getString("placeChart"));
        labelLocation.setBounds(new Rectangle(8, 3, 74, 23));

        radioButtonAsObject.setText(bundle.getString("asObject"));
        radioButtonAsObject.setMnemonic('O');
        radioButtonAsObject.setBounds(new Rectangle(70, 76, 137, 24));
        radioButtonNewSheet.setMnemonic('S');
        radioButtonNewSheet.setBounds(new Rectangle(70, 32, 135, 24));
        radioButtonNewSheet.setText(bundle.getString("asNewSheet"));
        radioButtonNewSheet.setRequestFocusEnabled(false);
		comboBoxSheetList = new JComboBox(comboBoxList);
//		comboBoxSheetList.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                comboBoxSheetList_actionPerformed(e);
//            }
//        });
//        buttonOk.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                buttonOk_actionPerformed(e);
//            }
//        });
//        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                buttonCancel_actionPerformed(e);
//            }
//        });
        this.buttonGroup1.add(radioButtonAsObject);
		this.buttonGroup1.add(radioButtonNewSheet);

		comboBoxSheetList.setBounds(new Rectangle(215, 76, 217, 24));
        textFieldNewSheetName.setText("Chart1");
        textFieldNewSheetName.setBounds(new Rectangle(215, 32, 217, 24));

		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(null);
		this.setSize(new Dimension(445, 190));

//		this.setTitle("차트 마법사 - 4 단계 중 4 단계 - 차트 위치");
		buttonHelp.setIcon(new ImageIcon(Utility.getResource("01.gif")));
        buttonHelp.setBounds(new Rectangle(12, 141, 27, 21));
        buttonHelp.setRequestFocusEnabled(false);
        buttonHelp.setPreferredSize(new Dimension(27, 21));

		panelGeneralButton.setBounds(new Rectangle(103, 129, 330, 38));
        panelGeneralButton.setLayout(null);
//		this.wizardPanel.setBounds(new Rectangle(103, 129, 330, 38));

        buttonOk.setText(bundle.getString("ok"));
        buttonOk.setBounds(new Rectangle(156, 8, 78, 22));
        buttonCancel.setText(bundle.getString("cancel"));
        buttonCancel.setBounds(new Rectangle(247, 8, 78, 22));

		buttonImage1.setIcon(new ImageIcon(Utility.getResource("step4-01.gif")));
        buttonImage1.setFocusPainted(false);
        buttonImage1.setRequestFocusEnabled(false);
        buttonImage1.setBorder(null);
        buttonImage1.setBounds(new Rectangle(13, 26, 44, 37));

		buttonImage2.setIcon(new ImageIcon(Utility.getResource("step4-02.gif")));
        buttonImage2.setFocusPainted(false);
        buttonImage2.setRequestFocusEnabled(false);
        buttonImage2.setBorder(null);
        buttonImage2.setBounds(new Rectangle(13, 70, 44, 37));

		contentPane.add(comboBoxSheetList, null);
        contentPane.add(textFieldNewSheetName, null);
        contentPane.add(labelLocation, null);
        contentPane.add(radioButtonNewSheet, null);
        contentPane.add(radioButtonAsObject, null);
        contentPane.add(buttonHelp, null);
        contentPane.add(panelGeneralButton, null);
        panelGeneralButton.add(buttonCancel, null);
        panelGeneralButton.add(buttonOk, null);
        contentPane.add(buttonImage1, null);
        contentPane.add(buttonImage2, null);

//		contentPane.add(this.wizardPanel);
    }

//	public void setWizardMode(boolean isWizardMode) {
//		ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/ChartLocationEditorBundle",
//															jcalc.JCalcResource.getLocale());
//
//		this.isWizardMode = isWizardMode;
//		this.panelGeneralButton.setVisible(!isWizardMode);
//		this.wizardPanel.setVisible(isWizardMode);
//
//		if (isWizardMode) this.setTitle(bundle.getString("title2"));
//		else this.setTitle(bundle.getString("title1"));
//	}

//
//	public void show() {
//		this.setWizardMode(this.isWizardMode);
//		this.wizardPanel.setParent(this);
//		this.wizardPanel.setStep(this.stepIndex);
//		super.show();
//	}


//	public void changeMenuText() {
//		ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/ChartLocationEditorBundle",
//															jcalc.JCalcResource.getLocale());
//
//		labelLocation.setText(bundle.getString("placeChart"));
//        radioButtonAsObject.setText(bundle.getString("asObject"));
//        radioButtonNewSheet.setText(bundle.getString("asNewSheet"));
//
//		buttonOk.setText(bundle.getString("ok"));
//        buttonCancel.setText(bundle.getString("cancel"));
//
//		if (isWizardMode) this.setTitle(bundle.getString("title2"));
//		else this.setTitle(bundle.getString("title1"));
//
//
//		this.wizardPanel.changeMenuText();
//	}
//
//	// 콤보박스 리스트를 변경시킬때 일어나는 일...
//    void comboBoxSheetList_actionPerformed(ActionEvent e) {
//
//		JComboBox cb = (JComboBox)e.getSource();
//		comboIndex = cb.getSelectedIndex();
//		System.out.println("index -- >" + index);
//
//
//    }
//
//    void buttonOk_actionPerformed(ActionEvent e) {
//		String sheetName = this.comboBoxList[comboIndex];
//		//this.dataSheet.getSheet().workbook.setCurrentSheet(string);
//		this.dataSheet.getSheet().addWizardChart(dataSheet.chartTypeGroupName,dataSheet.chartTypeSubIndex, dataSheet.isHorizontal, dataSheet.chartOption, sheetName);
//		this.comboBoxSheetList.setSelectedIndex(0);
//		this.dispose();
//    }
//
//    void buttonCancel_actionPerformed(ActionEvent e) {
//		this.dispose();
//    }
//

}








