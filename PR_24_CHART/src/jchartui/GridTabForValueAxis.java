//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;


import jchart.*;

import java.util.ResourceBundle;


/**
 * Title:           GridTabForValueAxis 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author          withhim
 * @version 1.0
 * Description:     값을 나타내는 축의 다이얼로그박스에 들어가는 눈금 Tab을 나타내는
 *                  클래스이다. 이 곳에서 ValueAxisOption 클래스의 값을 조절 할 수 있다.
 */

public class GridTabForValueAxis extends TabPanel {

	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/GridTabForValueAxisBundle", jchartui.JCalcResource.getLocale());

	JLabel labelTitle = new JLabel();
    JLabel labelAuto = new JLabel();
    JCheckBox checkBoxMinScaleIsAuto = new JCheckBox();
    JCheckBox checkBoxMaxScaleIsAuto = new JCheckBox();
    JCheckBox checkBoxMajorUnitIsAuto = new JCheckBox();
    JCheckBox checkBoxMinorUnitIsAuto = new JCheckBox();
    JCheckBox checkBoxCrosses = new JCheckBox();
	LinePanel bottomLine = new LinePanel();
    JTextField textFieldMinScale = new JTextField();
    JTextField textFieldMaxScale = new JTextField();
    JTextField textFieldMajorUnit = new JTextField();
    JTextField textFieldMinorUnit = new JTextField();
    JTextField textFieldCrossesAt = new JTextField();
    LinePanel topLine = new LinePanel();
    JLabel labelDisplayUnit = new JLabel();
    JComboBox comboBoxDisplayUnit = new JComboBox((String[])bundle.getObject("displayUnits"));
    JCheckBox checkBoxHasDisplayUnitLabel = new JCheckBox();
    JCheckBox checkBoxReversePlotOrder = new JCheckBox();
    JCheckBox checkBoxLogarithmicScaleType = new JCheckBox();
    JCheckBox checkBoxCrossesMax = new JCheckBox();

    public GridTabForValueAxis() {

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

		// Component들의 Layout을 정한다.
		this.setLayout(null);

		// Component들을 추가시킨다.
        textFieldMinScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldMinScale_actionPerformed(e);
            }
        });

        this.add(checkBoxMajorUnitIsAuto, null);
        this.add(checkBoxMinScaleIsAuto, null);
        this.add(checkBoxMaxScaleIsAuto, null);
        this.add(checkBoxMinorUnitIsAuto, null);
        this.add(checkBoxCrosses, null);
        this.add(textFieldMinScale, null);
        this.add(textFieldMaxScale, null);
        this.add(textFieldMajorUnit, null);
        this.add(textFieldMinorUnit, null);
        this.add(labelTitle, null);
        this.add(textFieldCrossesAt, null);
        this.add(topLine, null);
        this.add(labelDisplayUnit, null);
        this.add(labelAuto, null);
        this.add(bottomLine, null);
        this.add(checkBoxHasDisplayUnitLabel, null);
        this.add(checkBoxCrossesMax, null);
        this.add(checkBoxLogarithmicScaleType, null);
        this.add(checkBoxReversePlotOrder, null);
        this.add(comboBoxDisplayUnit, null);


		// 추가된 Component들의 크기와 위치를 정한다.
		labelTitle.setBounds(new Rectangle(5, 5, 170, 22));
        labelAuto.setBounds(new Rectangle(5, 30, 54, 22));
        checkBoxMinScaleIsAuto.setBounds(new Rectangle(10, 55, 121, 22));
        checkBoxMaxScaleIsAuto.setBounds(new Rectangle(10, 80, 112, 22));
        checkBoxMajorUnitIsAuto.setBounds(new Rectangle(10, 105, 112, 22));
        checkBoxMinorUnitIsAuto.setBounds(new Rectangle(10, 130, 112, 22));
		checkBoxCrosses.setBounds(new Rectangle(10, 155, 112, 22));
        textFieldMinScale.setBounds(new Rectangle(142, 55, 106, 22));
        textFieldMaxScale.setBounds(new Rectangle(142, 80, 106, 22));
        textFieldMajorUnit.setBounds(new Rectangle(142, 105, 106, 22));
        textFieldMinorUnit.setBounds(new Rectangle(142, 130, 106, 22));
        textFieldCrossesAt.setBounds(new Rectangle(142, 155, 106, 22));


        topLine.setBounds(new Rectangle(5, 178, 410, 10));

		labelDisplayUnit.setBounds(new Rectangle(5, 190, 89, 22));
        comboBoxDisplayUnit.setBounds(new Rectangle(98, 190, 101, 22));
        checkBoxHasDisplayUnitLabel.setBounds(new Rectangle(221, 190, 184, 22));

		bottomLine.setBounds(new Rectangle(5, 214, 410, 10));

		checkBoxLogarithmicScaleType.setBounds(new Rectangle(10, 225, 190, 22));
        checkBoxReversePlotOrder.setBounds(new Rectangle(10, 247, 190, 22));
        checkBoxCrossesMax.setBounds(new Rectangle(10, 269, 190, 22));



		// Component들의 Text를 설정한다.
		changeMenuText();
        comboBoxDisplayUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxDisplayUnit_actionPerformed(e);
            }
        });


    }


	/**
	 * GridTabForValueAxis의 초기값을 설정하는 함수이다.
	 */
	public void initializeDialogTab() {

		ValueAxisOption vao = getValueAxisOption();

		checkBoxMinScaleIsAuto.setSelected( vao.getMinScaleIsAuto() );
		checkBoxMaxScaleIsAuto.setSelected( vao.getMaxScaleIsAuto() );
		checkBoxMajorUnitIsAuto.setSelected( vao.getMajorUnitIsAuto() );
		checkBoxMinorUnitIsAuto.setSelected( vao.getMinorUnitIsAuto() );
	    checkBoxCrosses.setSelected( ( vao.getCrosses() == AppRegistry.AXIS_CROSSES_AUTOMATIC ) ? true : false );
		checkBoxHasDisplayUnitLabel.setSelected( vao.getHasDisplayUnitLabel() );
		checkBoxLogarithmicScaleType.setSelected( ( vao.getScaleType() == AppRegistry.SCALE_LOGARITHMIC ) ? true : false );
		checkBoxReversePlotOrder.setSelected( vao.getReversePlotOrder() );

		textFieldMinScale.setText("" + vao.getMinScale() );
		textFieldMaxScale.setText("" + vao.getMaxScale() );
		textFieldMajorUnit.setText("" + vao.getMajorUnit() );
		textFieldMinorUnit.setText("" + vao.getMinorUnit() );
		textFieldCrossesAt.setText("" + vao.getCroessesAt() );

		comboBoxDisplayUnit.setSelectedIndex( (int) vao.getDisplayUnit() );

		// 표시 단위가 없을 때에는 hasDisplayUnitLabel을 설정 할 수 없다.
		if ( comboBoxDisplayUnit.getSelectedIndex() == 0 ) {

			checkBoxHasDisplayUnitLabel.setEnabled( false );

		} else {

			checkBoxHasDisplayUnitLabel.setEnabled( true );
		}

	}


	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/GridTabForValueAxisBundle", JCalcResource.getLocale());

		labelTitle.setText( bundle.getString("valueAxisScale") );
		labelAuto.setText( bundle.getString("auto") );
		checkBoxMinScaleIsAuto.setText( bundle.getString("minimum") );
		checkBoxMaxScaleIsAuto.setText( bundle.getString("maximum") );
		checkBoxMajorUnitIsAuto.setText( bundle.getString("majorUnit") );
		checkBoxMinorUnitIsAuto.setText( bundle.getString("minorUnit") );
		checkBoxCrosses.setText( bundle.getString("xAxisCrossesAt") );
		labelDisplayUnit.setText( bundle.getString("displayUnit") );

		JComboBox temp = new JComboBox((String[])bundle.getObject("displayUnits"));
		this.comboBoxDisplayUnit.setModel(temp.getModel());

		checkBoxHasDisplayUnitLabel.setText( bundle.getString("showDisplayUnitLabelOnChart") );
		checkBoxLogarithmicScaleType.setText( bundle.getString("logarithmicScale") );
		checkBoxReversePlotOrder.setText( bundle.getString("categoriesInReverseOrder") );
		checkBoxCrossesMax.setText( bundle.getString("xAxisCrossesAtMaxValue") );
	}

	public void applyToChartElement () {

		ValueAxisOption vao = getValueAxisOption();

		vao.setMinScaleIsAuto( this.checkBoxMinScaleIsAuto.isSelected() );
		vao.setMaxScaleIsAuto( this.checkBoxMaxScaleIsAuto.isSelected() );
		vao.setMajorUnitIsAuto( this.checkBoxMajorUnitIsAuto.isSelected() );
		vao.setMinorUnitIsAuto( this.checkBoxMajorUnitIsAuto.isSelected() );

		vao.setCrosses( ( this.checkBoxCrosses.isSelected() ) ? AppRegistry.AXIS_CROSSES_AUTOMATIC : AppRegistry.AXIS_CROSSES_CUSTOM );

		vao.setHasDisplayUnitLabel( this.checkBoxHasDisplayUnitLabel.isSelected() );
		vao.setDisplayUnit( ( byte ) this.comboBoxDisplayUnit.getSelectedIndex() );
		vao.setScaleType( ( this.checkBoxLogarithmicScaleType.isSelected() ) ? AppRegistry.SCALE_LOGARITHMIC : AppRegistry.SCALE_LINEAR );
		vao.setReversePlotOrder( this.checkBoxReversePlotOrder.isSelected() );

		if ( this.checkBoxCrossesMax.isSelected() ) {
			vao.setCrosses( AppRegistry.MAXIMUM );
		}
	}

	public ValueAxisOption getValueAxisOption() {

		ValueAxis va;

		if ( chartElement instanceof ValueAxis ) {

	        va = (ValueAxis) super.chartElement;


		} else {

   		    va = (ValueAxis) chartElement.getChart().getChartElement( ValueAxis.class );

		}

		return ( ValueAxisOption ) va.getAxisOption();


	}

	/**
	 * Event Handling
	 */

    void textFieldMinScale_actionPerformed(ActionEvent e) {

		checkBoxMinScaleIsAuto.setSelected( false );

    }

    void comboBoxDisplayUnit_actionPerformed(ActionEvent e) {

		// 표시 단위가 없을 때에는 hasDisplayUnitLabel을 설정 할 수 없다.
		if ( comboBoxDisplayUnit.getSelectedIndex() == 0 ) {

			checkBoxHasDisplayUnitLabel.setEnabled( false );

		} else {

			checkBoxHasDisplayUnitLabel.setEnabled( true );
		}

    }




}