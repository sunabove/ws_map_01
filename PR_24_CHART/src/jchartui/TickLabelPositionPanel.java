//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import jchart.*;

import java.util.ResourceBundle;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TickLabelPositionPanel extends JPanel {

	Axis axis;

    JRadioButton radioButtonTickLabelPositionLow = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionHigh = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionNextToAxis = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionNone = new JRadioButton();

    public TickLabelPositionPanel() {

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

		this.setLayout(null);


		this.add(radioButtonTickLabelPositionNone, null);
        this.add(radioButtonTickLabelPositionHigh, null);
        this.add(radioButtonTickLabelPositionLow, null);
        this.add(radioButtonTickLabelPositionNextToAxis, null);

		// Component들의 Bounds를 정한다.
        radioButtonTickLabelPositionNone.setBounds(new Rectangle(8, 16, 90, 22));
		radioButtonTickLabelPositionHigh.setBounds(new Rectangle(106, 16, 90, 22));
		radioButtonTickLabelPositionLow.setBounds(new Rectangle(8, 42, 90, 22));
		radioButtonTickLabelPositionNextToAxis.setBounds(new Rectangle(106, 42, 90, 22));

		// radio버튼들을 그룹화 한다.
		ButtonGroup positionGroup = new ButtonGroup();
		positionGroup.add(radioButtonTickLabelPositionNone);
		positionGroup.add(radioButtonTickLabelPositionHigh);
		positionGroup.add(radioButtonTickLabelPositionLow);
		positionGroup.add(radioButtonTickLabelPositionNextToAxis);

		changeMenuText();


    }

public void setAxisChartElement(Axis axis) {

		this.axis = axis;

	}

	/**
	 * PlacementTab의 초기값을 설정하는 함수이다.
	 */
	public void initializeDialogTab() {

		byte tickLabelPosition = axis.getTickLabelPosition();

		switch (tickLabelPosition) {

		case 0 :    radioButtonTickLabelPositionNone.setSelected(true);
			break;
		case 1 :    radioButtonTickLabelPositionHigh.setSelected(true);
			break;
		case 2 :    radioButtonTickLabelPositionLow.setSelected(true);
			break;
		case 3 :    radioButtonTickLabelPositionNextToAxis.setSelected(true);
			break;

		}
	}



	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/TickLabelPositionPanelBundle", JCalcResource.getLocale());

        radioButtonTickLabelPositionNone.setText(bundle.getString("none"));
        radioButtonTickLabelPositionHigh.setText(bundle.getString("high"));
        radioButtonTickLabelPositionLow.setText(bundle.getString("low"));
		radioButtonTickLabelPositionNextToAxis.setText(bundle.getString("nextToAxis"));

	}

	public void applyToAxisChartElement () {

		if (radioButtonTickLabelPositionNone.isSelected() ) {

			axis.setTickLabelPosition( AppRegistry.TICK_LABEL_POSITION_NONE );

		} else if (radioButtonTickLabelPositionHigh.isSelected() ) {

			axis.setTickLabelPosition( AppRegistry.TICK_LABEL_POSITION_HIGH );

		} else if (radioButtonTickLabelPositionLow.isSelected() ) {

			axis.setTickLabelPosition( AppRegistry.TICK_LABEL_POSITION_LOW );

		} else {

			axis.setTickLabelPosition( AppRegistry.TICK_LABEL_POSITION_NEXT_TO_AXIS );

	    }

	}


}