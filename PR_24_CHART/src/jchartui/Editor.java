package jchartui;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import jchart.*;

import javax.swing.*;

public abstract class Editor extends JDialog{

    protected int mode = APPLY_MODE;

    public static final int STEP_MODE = 0, APPLY_MODE = 1;

    public Editor() {

	super.setModal( true );

    }

   public static ChartElement getSelectedChartElementOnTheCurrentSpreadSheet() {

       SpreadSheet sheet = SpreadSheet.getCurrentSpreadSheet();

       if( sheet == null ) {

	   return null;

       }

       return sheet.getSelectedChartElement();

   }

}