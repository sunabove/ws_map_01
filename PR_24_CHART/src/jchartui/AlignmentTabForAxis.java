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

	// ������ ���� SICSpindle component �߰�
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


        // ���� ���� �г��� �����.

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



		// Event �����ʸ� ��Ͻ�Ų��.
		panelTransform.addTransformDegreeChangeListener(this);
		componentSpindle.addSICComponentValueListener(this);
		radioButtonAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonAuto_actionPerformed(e);
            }
        });

		// ȭ�鿡 ��Ÿ�� text�� bundle�� ���� �����´�.
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
	 * PlacementTab�� �ʱⰪ�� �����ϴ� �Լ��̴�.
	 */
	public void initializeDialogTab() {

		int degrees = getTextualChartElement().getOrientation();
		componentSpindle.setSpinValue( (float) degrees );
		panelTransform.setDegreeValue( (float) degrees );

	}


	public void changeMenuText() {

	    bundle = ResourceBundle.getBundle("jchartui/AlignmentTabForAxisBundle", JCalcResource.getLocale());

		// border�� ���ο� �̸� ������
        titledBorder1.setTitle(bundle.getString("orientation"));

		// label�� ���ο� Text ������
		labelDegrees.setText(bundle.getString("degrees"));

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

		radioButtonAuto.setSelected( false );

	}

	/**
	 * SicSplindle�� ���� ����� degree���� TransformPanel�� ������ �ش�.
	 */
	public void sicComponentValueChanged(SICComponentValueEvent event) {

		double degree = componentSpindle.getSpinValue();

	    panelTransform.setDegreeValue( degree );

		radioButtonAuto.setSelected( false );


	}

    void radioButtonAuto_actionPerformed(ActionEvent e) {

		// ���� �ڵ��� ���õǾ� �ִ� ���� radioButton�� ���� selected�� false�� ��
		// �� ����.
		if ( ! radioButtonAuto.isSelected() ) {

			radioButtonAuto.setSelected( true );

			return;
		}

		// �ڵ��� �����ϸ� degree ���� O���� �Ѵ�.
		panelTransform.setDegreeValue( 0 );
		componentSpindle.setSpinValue( 0 );



    }




}