//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import jchart.*;
import jcosmos.Utility;

import java.util.ResourceBundle;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class PlacementTab extends TabPanel {

    JRadioButton radioButtonLegendPositionRight = new JRadioButton();
    JRadioButton radioButtonLegendPositionLeft = new JRadioButton();
    JRadioButton radioButtonLegendPositionTop = new JRadioButton();
    JRadioButton radioButtonLegendPositionCorner = new JRadioButton();
    JRadioButton radioButtonLegendPositionBottom = new JRadioButton();
    GridLayout gridLayout5 = new GridLayout();
    JPanel panelPosition = new JPanel();
	Border border1;
    TitledBorder titledBorder1;
    ButtonGroup positionGroup = new ButtonGroup();

    public PlacementTab() {

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PlacementTabBundle", JCalcResource.getLocale());

        titledBorder1 = new TitledBorder(border1, bundle.getString("placement"));

        this.setLayout(null);

        radioButtonLegendPositionRight.setMnemonic('R');
        radioButtonLegendPositionLeft.setMnemonic('L');
        radioButtonLegendPositionTop.setMnemonic('T');
        radioButtonLegendPositionCorner.setMnemonic('C');
        radioButtonLegendPositionBottom.setMnemonic('B');


		radioButtonLegendPositionBottom.setText(bundle.getString("legendPositionBottom"));
        radioButtonLegendPositionCorner.setText(bundle.getString("legendPositionCorner"));
        radioButtonLegendPositionLeft.setText(bundle.getString("legendPositionLeft"));
        radioButtonLegendPositionRight.setText(bundle.getString("legendPositionRight"));
        radioButtonLegendPositionTop.setText(bundle.getString("legendPositionTop"));


        gridLayout5.setRows(5);

        panelPosition.setBorder(titledBorder1);
        panelPosition.setPreferredSize(new Dimension(106, 10));
        panelPosition.setBounds(new Rectangle(8, 7, 106, 123));
        panelPosition.setLayout(gridLayout5);

        panelPosition.add(radioButtonLegendPositionBottom, null);
        panelPosition.add(radioButtonLegendPositionCorner, null);
        panelPosition.add(radioButtonLegendPositionTop, null);
        panelPosition.add(radioButtonLegendPositionRight, null);
        panelPosition.add(radioButtonLegendPositionLeft, null);

        this.add(panelPosition, null);

        positionGroup.add(radioButtonLegendPositionBottom);
        positionGroup.add(radioButtonLegendPositionCorner);
        positionGroup.add(radioButtonLegendPositionTop);
        positionGroup.add(radioButtonLegendPositionRight);
        positionGroup.add(radioButtonLegendPositionLeft);

    }

	public Legend getLegend() {

		Utility.debug(chartElement, "chartElement = " );

	    if (chartElement instanceof Legend) {

			return (Legend) chartElement;

		}
		// 선택된 chartElement가 Chart인 경우
		return (Legend) ( chartElement.getChart().getChartElement(Legend.class) );

	}

	/**
	 * PlacementTab의 초기값을 설정하는 함수이다.
	 */
	public void initializeDialogTab() {

		Legend legend = getLegend();

		int legendType = legend.getLocationType();

		switch (legendType) {

		case 0 :    radioButtonLegendPositionBottom.setSelected(true);
			break;
		case 1 :    radioButtonLegendPositionCorner.setSelected(true);
			break;
		case 3 :    radioButtonLegendPositionLeft.setSelected(true);
			break;
		case 4 :    radioButtonLegendPositionRight.setSelected(true);
			break;
		case 2 :    radioButtonLegendPositionTop.setSelected(true);
			break;
		default :
			    radioButtonLegendPositionBottom.setSelected(false);
				radioButtonLegendPositionCorner.setSelected(false);
				radioButtonLegendPositionLeft.setSelected(false);
				radioButtonLegendPositionRight.setSelected(false);
				radioButtonLegendPositionTop.setSelected(false);
			break;

		}
	}


	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PlacementTabBundle", JCalcResource.getLocale());

        titledBorder1.setTitle(bundle.getString("placement"));

		radioButtonLegendPositionBottom.setText(bundle.getString("legendPositionBottom"));
        radioButtonLegendPositionCorner.setText(bundle.getString("legendPositionCorner"));
        radioButtonLegendPositionLeft.setText(bundle.getString("legendPositionLeft"));
        radioButtonLegendPositionRight.setText(bundle.getString("legendPositionRight"));
        radioButtonLegendPositionTop.setText(bundle.getString("legendPositionTop"));


	}

	public void applyToChartElement () {

		Legend legend = getLegend();

		if (radioButtonLegendPositionBottom.isSelected() ) {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_BOTTOM);

		} else if (radioButtonLegendPositionCorner.isSelected() ) {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_CORNER);

		} else if (radioButtonLegendPositionLeft.isSelected() ) {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_LEFT);

		} else if (radioButtonLegendPositionRight.isSelected() ) {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_RIGHT);

		} else if (radioButtonLegendPositionTop.isSelected() ) {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_TOP);

		} else {

			legend.setLocationType(AppRegistry.LEGEND_POSITION_NONE);

		}

	}



}