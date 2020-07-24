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

public class TickMarkPanel extends JPanel {

	private Axis axis;

	private byte tickMarkType;



    ButtonGroup existenceGroup = new ButtonGroup();
    JRadioButton radioButtonTickMarkInside = new JRadioButton();
    JRadioButton radioButtonTickMarkOutside = new JRadioButton();
    JRadioButton radioButtonTickMarkCross = new JRadioButton();
    JRadioButton radioButtonTickMarkNone = new JRadioButton();

    public TickMarkPanel(byte tickMarkType) {

		this.tickMarkType = tickMarkType;

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

		// Component���� Layout�� ���Ѵ�.
		this.setLayout(null);

		// Component���� addition�Ѵ�.
		this.add(radioButtonTickMarkNone, null);
        this.add(radioButtonTickMarkOutside, null);
        this.add(radioButtonTickMarkInside, null);
        this.add(radioButtonTickMarkCross, null);

		// Component���� Bounds�� ���Ѵ�.
        radioButtonTickMarkNone.setBounds(new Rectangle(8, 16, 90, 22));
		radioButtonTickMarkOutside.setBounds(new Rectangle(106, 16, 90, 22));
		radioButtonTickMarkInside.setBounds(new Rectangle(8, 42, 90, 22));
		radioButtonTickMarkCross.setBounds(new Rectangle(106, 42, 90, 22));

		// radio��ư���� �׷�ȭ �Ѵ�.
		existenceGroup.add(radioButtonTickMarkNone);
		existenceGroup.add(radioButtonTickMarkOutside);
		existenceGroup.add(radioButtonTickMarkInside);
		existenceGroup.add(radioButtonTickMarkCross);

		changeMenuText();


    }

	public void setAxisChartElement(Axis axis) {

		this.axis = axis;

	}

	/**
	 * PlacementTab�� �ʱⰪ�� �����ϴ� �Լ��̴�.
	 */
	public void initializeDialogTab() {

		int tickMark = axis.getTickMark( this.tickMarkType );

		switch (tickMark) {

		case 0 :    radioButtonTickMarkNone.setSelected(true);
			break;
		case 1 :    radioButtonTickMarkOutside.setSelected(true);
			break;
		case 2 :    radioButtonTickMarkInside.setSelected(true);
			break;
		case 3 :    radioButtonTickMarkCross.setSelected(true);
			break;

		}
	}


	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/TickMarkPanelBundle", JCalcResource.getLocale());

        radioButtonTickMarkNone.setText(bundle.getString("none"));
        radioButtonTickMarkOutside.setText(bundle.getString("outside"));
        radioButtonTickMarkInside.setText(bundle.getString("inside"));
		radioButtonTickMarkCross.setText(bundle.getString("cross"));

	}

	public void applyToAxisChartElement () {

		if (radioButtonTickMarkNone.isSelected() ) {

			axis.setTickMark(this.tickMarkType, AppRegistry.TICK_MARK_NONE);

		} else if (radioButtonTickMarkOutside.isSelected() ) {

			axis.setTickMark(this.tickMarkType, AppRegistry.TICK_MARK_OUTSIDE);

		} else if (radioButtonTickMarkCross.isSelected() ) {

			axis.setTickMark(this.tickMarkType, AppRegistry.TICK_MARK_INSIDE);

		} else {

			axis.setTickMark(this.tickMarkType, AppRegistry.TICK_MARK_CROSS);

	    }

	}

}