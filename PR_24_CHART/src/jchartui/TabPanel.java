package jchartui;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import jchart.*;

import java.util.ResourceBundle;

public abstract class TabPanel extends JPanel{

	ChartElement chartElement;

    public TabPanel() {
    }

	public abstract void initializeDialogTab();

	public abstract void applyToChartElement();

	public abstract void changeMenuText();

	public void setChartElement(ChartElement chartElement) {

		this.chartElement = chartElement;

	}


}