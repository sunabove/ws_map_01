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

public class DataLabelTab extends TabPanel {

	DataLabelPanel dataLabelPanel;

    public DataLabelTab() {

		dataLabelPanel = new DataLabelPanel( dataLabelPanel.DATA_MODE );

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

		dataLabelPanel.setBounds(new Rectangle(0, 0, 400, 300));
        this.add(dataLabelPanel, null);

	}

	public void changeMenuText() {

	    dataLabelPanel.changeMenuText();

	}

	public void applyToChartElement() {

		dataLabelPanel.applyToChartElement();

	}

	public void initializeDialogTab() {

		dataLabelPanel.initializeDialogTab();

	}

	public void setChartElement( ChartElement chartElement ) {

	    dataLabelPanel.setChartElement( chartElement );
	}

}