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

	// ������ ���� SICSpindle component �߰�
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

        // �ؽ�Ʈ ���� �г��� �����.
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

        // ���� ���� �г��� �����.

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

		// Event �����ʸ� ��Ͻ�Ų��.
		panelTransform.addTransformDegreeChangeListener(this);
		componentSpindle.addSICComponentValueListener(this);

		// ȭ�鿡 ��Ÿ�� text�� bundle�� ���� �����´�.
		labelHorizontalAlignment.setText(bundle.getString("horizontal"));
		labelVerticalAlignment.setText(bundle.getString("vertical"));
		labelDegrees.setText(bundle.getString("degrees"));


    }

	public TextualChartElement getTextualChartElement() {

		return ( TextualChartElement ) chartElement;

	}

	/**
	 * PlacementTab�� �ʱⰪ�� �����ϴ� �Լ��̴�.
	 */
	public void initializeDialogTab() {

		int degrees = getTextualChartElement().getOrientation();
		componentSpindle.setSpinValue( (float) degrees );
		panelTransform.setDegreeValue( (float) degrees );

	}


	public void changeMenuText() {

	    bundle = ResourceBundle.getBundle("jchartui/AlignmentTabBundle", JCalcResource.getLocale());

		// border�� ���ο� �̸� ������
        titledBorder1.setTitle(bundle.getString("alignmentText"));
		titledBorder2.setTitle(bundle.getString("orientation"));

		// label�� ���ο� Text ������
		labelHorizontalAlignment.setText(bundle.getString("horizontal"));
		labelVerticalAlignment.setText(bundle.getString("vertical"));
		labelDegrees.setText(bundle.getString("degrees"));

		// comboBox�� ���ο� Text ������
		JComboBox temp = new JComboBox((String[])bundle.getObject("horizontalAlignment"));
		this.comboBoxHorizontalAlignment.setModel(temp.getModel());
		temp = new JComboBox((String[])bundle.getObject("verticalAlignment"));
		this.comboBoxVerticalAlignment.setModel(temp.getModel());

	}

	/**
	 * ���̾�α� �ڽ����� ����� ������� �ش� TextualChartElement�� �����Ų��.
	 */
	public void applyToChartElement () {

		// ���̾�α� �ڽ��� ����� orientation ���� ���õ� TextualChartElement�� �����Ų��.
		getTextualChartElement().setOrientation( (int) componentSpindle.getSpinValue() );

	}



	//=============================================================================
	//	�̺�Ʈ �ڵ鸵.
	//=============================================================================

	/**
	 * TransformPanel�� ���� ����� degree���� SpinValue�� �־��ش�.
	 */
	public void transformDegreeChanged(TransformDegreeChangeEvent event){

		float degree = (float) event.getDegree();

	    componentSpindle.setSpinValue( degree );

	}

	/**
	 * SicSplindle�� ���� ����� degree���� TransformPanel�� ������ �ش�.
	 */
	public void sicComponentValueChanged(SICComponentValueEvent event) {

		double degree = componentSpindle.getSpinValue();

	    panelTransform.setDegreeValue( degree );




	}




}