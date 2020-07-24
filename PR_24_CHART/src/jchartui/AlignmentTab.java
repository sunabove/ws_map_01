//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import jchart.*;
import jchartui.SICSpindle;

import java.util.ResourceBundle;
//import com.borland.jbcl.layout.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class AlignmentTab extends TabPanel implements TransformDegreeChangeListener, SICComponentValueListener {


	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/AlignmentTabBundle", JCalcResource.getLocale());

	String [] verticalAlignmentString = (String[])bundle.getObject("verticalAlignment");
	String [] horizontalAlignmentString = (String[])bundle.getObject("horizontalAlignment");


	Border border1;
    TitledBorder titledBorder1, titledBorder2;
    JPanel panelAlignment = new JPanel();
    JComboBox comboBoxHorizontalAlignment = new JComboBox(verticalAlignmentString);
    JComboBox comboBoxVerticalAlignment = new JComboBox(verticalAlignmentString);
    JTextField textFieldDownwardToggle = new JTextField();
    JPanel panelOrientation = new JPanel();
    JLabel labelHorizontalAlignment = new JLabel();
    JLabel labelVerticalAlignment = new JLabel();
    JLabel labelDegrees = new JLabel();
    TransformPanel panelTransform = new TransformPanel();

	// 단위가 없는 SICSpindle component 추가
	SICSpindle componentSpindle = new SICSpindle(-90, 90, 1, true);



    public AlignmentTab() {

		try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {


		this.setLayout(null);


        titledBorder1 = new TitledBorder(border1, bundle.getString("alignmentText"));
		titledBorder2 = new TitledBorder(border1, bundle.getString("orientation"));

        // 텍스트 맞춤 패널을 만든다.
        panelAlignment.setBorder(titledBorder1);
        panelAlignment.setBounds(new Rectangle(11, 18, 198, 78));
        panelAlignment.setLayout(null);

        comboBoxVerticalAlignment.setBounds(new Rectangle(84, 45, 103, 18));
		comboBoxHorizontalAlignment.setBounds(new Rectangle(84, 20, 103, 18));
		labelHorizontalAlignment.setBounds(new Rectangle(11, 19, 68, 22));
        labelVerticalAlignment.setBounds(new Rectangle(11, 42, 68, 22));

        panelTransform.setBorder(BorderFactory.createLoweredBevelBorder());
        panelAlignment.add(labelHorizontalAlignment, null);
        panelAlignment.add(comboBoxHorizontalAlignment, null);
        panelAlignment.add(labelVerticalAlignment, null);
        panelAlignment.add(comboBoxVerticalAlignment, null);

		this.add(panelAlignment, null);

        // 방향 관련 패널을 만든다.

		panelOrientation.setLayout(null);
        panelOrientation.setBorder(titledBorder1);
        panelOrientation.setBounds(new Rectangle(228, 19, 140, 192));

		textFieldDownwardToggle.setBounds(new Rectangle(12, 20, 25, 134));
		panelTransform.setBounds(new Rectangle(46, 20, 85, 134));
        componentSpindle.setBounds(new Rectangle(12, 162, 55, 23));
        labelDegrees.setBounds(new Rectangle(70, 163, 63, 22));


        panelOrientation.add(textFieldDownwardToggle, null);
		panelOrientation.add(panelTransform, null);
        panelOrientation.add(componentSpindle, null);
		panelOrientation.add(labelDegrees, null);


		this.add(panelOrientation, null);

		// Event 리스너를 등록시킨다.
		panelTransform.addTransformDegreeChangeListener(this);
		componentSpindle.addSICComponentValueListener(this);

		// 화면에 나타날 text를 bundle로 부터 가져온다.
		labelHorizontalAlignment.setText(bundle.getString("horizontal"));
		labelVerticalAlignment.setText(bundle.getString("vertical"));
		labelDegrees.setText(bundle.getString("degrees"));


    }

	public TextualChartElement getTextualChartElement() {

		return ( TextualChartElement ) chartElement;

	}

	/**
	 * PlacementTab의 초기값을 설정하는 함수이다.
	 */
	public void initializeDialogTab() {

		int degrees = getTextualChartElement().getOrientation();
		componentSpindle.setSpinValue( (float) degrees );
		panelTransform.setDegreeValue( (float) degrees );

	}


	public void changeMenuText() {

	    bundle = ResourceBundle.getBundle("jchartui/AlignmentTabBundle", JCalcResource.getLocale());

		// border에 새로운 이름 입히기
        titledBorder1.setTitle(bundle.getString("alignmentText"));
		titledBorder2.setTitle(bundle.getString("orientation"));

		// label에 새로운 Text 입히기
		labelHorizontalAlignment.setText(bundle.getString("horizontal"));
		labelVerticalAlignment.setText(bundle.getString("vertical"));
		labelDegrees.setText(bundle.getString("degrees"));

		// comboBox에 새로운 Text 입히기
		JComboBox temp = new JComboBox((String[])bundle.getObject("horizontalAlignment"));
		this.comboBoxHorizontalAlignment.setModel(temp.getModel());
		temp = new JComboBox((String[])bundle.getObject("verticalAlignment"));
		this.comboBoxVerticalAlignment.setModel(temp.getModel());

	}

	/**
	 * 다이얼로그 박스에서 적용된 내용들을 해당 TextualChartElement에 적용시킨다.
	 */
	public void applyToChartElement () {

		// 다이얼로그 박스에 적용된 orientation 값을 선택된 TextualChartElement에 적용시킨다.
		getTextualChartElement().setOrientation( (int) componentSpindle.getSpinValue() );

	}



	//=============================================================================
	//	이벤트 핸들링.
	//=============================================================================

	/**
	 * TransformPanel에 의해 변경된 degree값을 SpinValue에 넣어준다.
	 */
	public void transformDegreeChanged(TransformDegreeChangeEvent event){

		float degree = (float) event.getDegree();

	    componentSpindle.setSpinValue( degree );

	}

	/**
	 * SicSplindle에 의해 변경된 degree값을 TransformPanel에 전달해 준다.
	 */
	public void sicComponentValueChanged(SICComponentValueEvent event) {

		double degree = componentSpindle.getSpinValue();

	    panelTransform.setDegreeValue( degree );




	}




}