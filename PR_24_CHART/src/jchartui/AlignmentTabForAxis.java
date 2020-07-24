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

public class AlignmentTabForAxis extends TabPanel implements TransformDegreeChangeListener, SICComponentValueListener {


	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/AlignmentTabForAxisBundle", JCalcResource.getLocale());

	Border border1;
    TitledBorder titledBorder1;

	JRadioButton radioButtonAuto = new JRadioButton();
    JTextField textFieldDownwardToggle = new JTextField();
    JPanel panelOrientation = new JPanel();
    JLabel labelDegrees = new JLabel();
    TransformPanel panelTransform = new TransformPanel();

	// 단위가 없는 SICSpindle component 추가
	SICSpindle componentSpindle = new SICSpindle(-90, 90, 1, true);




    public AlignmentTabForAxis() {

		try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {


		this.setLayout(null);


        titledBorder1 = new TitledBorder(border1, bundle.getString("orientation"));

        panelTransform.setBorder(BorderFactory.createLoweredBevelBorder());


        // 방향 관련 패널을 만든다.

		panelOrientation.setLayout(null);
        panelOrientation.setBorder(titledBorder1);
        panelOrientation.setBounds(new Rectangle(22, 18, 143, 221));

		textFieldDownwardToggle.setBounds(new Rectangle(12, 44, 25, 134));
		panelTransform.setBounds(new Rectangle(46, 44, 85, 134));
        componentSpindle.setBounds(new Rectangle(12, 186, 55, 23));
        labelDegrees.setBounds(new Rectangle(70, 186, 63, 23));
		radioButtonAuto.setBounds(new Rectangle(18, 19, 63, 23));

		radioButtonAuto.setMnemonic('A');

        panelOrientation.add(panelTransform, null);
        panelOrientation.add(radioButtonAuto, null);
        panelOrientation.add(textFieldDownwardToggle, null);
        panelOrientation.add(componentSpindle, null);
        panelOrientation.add(labelDegrees, null);
        this.add(panelOrientation, null);



		// Event 리스너를 등록시킨다.
		panelTransform.addTransformDegreeChangeListener(this);
		componentSpindle.addSICComponentValueListener(this);
		radioButtonAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonAuto_actionPerformed(e);
            }
        });

		// 화면에 나타날 text를 bundle로 부터 가져온다.
		radioButtonAuto.setText(bundle.getString("auto"));
		labelDegrees.setText(bundle.getString("degrees"));


    }

	public TextualChartElement getTextualChartElement() {

		if( chartElement instanceof Axis ) {

		    return ( TextualChartElement ) chartElement.getChartElement(TickLabelGroup.class);

		}

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

	    bundle = ResourceBundle.getBundle("jchartui/AlignmentTabForAxisBundle", JCalcResource.getLocale());

		// border에 새로운 이름 입히기
        titledBorder1.setTitle(bundle.getString("orientation"));

		// label에 새로운 Text 입히기
		labelDegrees.setText(bundle.getString("degrees"));

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

		radioButtonAuto.setSelected( false );

	}

	/**
	 * SicSplindle에 의해 변경된 degree값을 TransformPanel에 전달해 준다.
	 */
	public void sicComponentValueChanged(SICComponentValueEvent event) {

		double degree = componentSpindle.getSpinValue();

	    panelTransform.setDegreeValue( degree );

		radioButtonAuto.setSelected( false );


	}

    void radioButtonAuto_actionPerformed(ActionEvent e) {

		// 현재 자동이 선택되어 있는 경우는 radioButton에 의해 selected를 false로 할
		// 수 없다.
		if ( ! radioButtonAuto.isSelected() ) {

			radioButtonAuto.setSelected( true );

			return;
		}

		// 자동을 선택하면 degree 값을 O으로 한다.
		panelTransform.setDegreeValue( 0 );
		componentSpindle.setSpinValue( 0 );



    }




}