package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javachart.chart.*;
import jcosmos.*;

public class ChartUtilities {

  private static int [][] chartColors = {
		{152, 156, 255}, {152,  52, 104}, {255, 255, 200}, {100, 255, 200}, { 95,   0,  95},
		{255, 128, 128}, {  0, 100, 200}, {208, 204, 255}, {  0,   0, 128}, {255,   0, 255},
		{255, 255,   0}, {  0, 255, 255}, {128,   0, 128}, {128,   0,   0}, {  0, 128, 128},
		{  0,   0, 255}, {  0, 204, 255}, {200, 255, 255}, {200, 255, 200}, {255, 255, 152}
  };

  private static Hashtable colorTable = new Hashtable();  // color hash table


  public static Color getChartColor(int index) {

		String id = "" + index;

		Color color = (Color) colorTable.get( id );

		if( color == null ) {

			int [] rgb = chartColors[ index ];

			color = new Color( rgb[0], rgb[1], rgb[2] );

			colorTable.put( id, color );

		}

		return color;

   }

  public static void paint(Graphics g, ChartData tm ) {

     Graphics2D g2 = (Graphics2D) g;

//     RenderingHints hints = new RenderingHints(
//	  RenderingHints.KEY_ANTIALIASING,
//	  RenderingHints.VALUE_ANTIALIAS_ON );
//     hints.add( new RenderingHints( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY ) );
//
//     g2.setRenderingHints( hints );

     Dimension size = tm.getSize();

     int type = tm.getType();

     String mainTitle = tm.getTitle();
     String xTitle = tm.getXTitle();
     String yTitle = tm.getYTtitle();

      Chart chart;

      if( type == ChartData.PIE_TYPE ) {

	chart = new PieChart("Apples and Oranges");

      } else if( type == ChartData.LINE_TYPE ) {

	chart = new LineChart("Apples and Oranges");

      } else { // if type == ChartData.BAR_TYPE ,,, 디폴트 옵션 값이다.

	chart = new BarChart("Apples and Oranges");

      }

      int WIDTH = size.width, HEIGHT = size.height;

      // Give it a title
      chart.getBackground().setTitleFont( FontManager.getFont( "Serif", Font.PLAIN, 16));
      chart.getBackground().setTitleString( mainTitle );

      // Show, place, and customize its legend
      chart.setLegendVisible(true);

      if( type == ChartData.PIE_TYPE ) {

	chart.getLegend().setLlX(0.8);  // normalized from lower left
	chart.getLegend().setLlY(0.5); // normalized from lower left
	chart.getLegend().setIconHeight(0.04);
	chart.getLegend().setIconWidth(0.02);
	chart.getLegend().setIconGap(0.01);

      } else {

	chart.getLegend().setLlX(0.4);  // normalized from lower left
	chart.getLegend().setLlY(0.75); // normalized from lower left
	chart.getLegend().setIconHeight(0.04);
	chart.getLegend().setIconWidth(0.02);
	chart.getLegend().setIconGap(0.01);
	chart.getLegend().setVerticalLayout(false);

      }

      int dataNum = (type == 1) ? 1 : tm.getDataNum();

      for( int i = 0; i < dataNum ;i ++ ) {

	chart.addDataSet( tm.getLabel( i ), tm.getData( i ) );

      }

      if( chart.getXAxis() != null ) {

	  chart.getXAxis().addLabels( tm.getLabels() );
	  chart.getXAxis().setTitleString( xTitle );

      }

      // Color apples red and oranges orange

      for( int i = 0; i < dataNum; i ++ ) {

	  try {

	    chart.getDatasets()[i].getGc().setFillColor( getChartColor( i ) );

	  } catch (Exception e) {


	    Utility.debug( e );

	  }

      }


      if( chart.getYAxis() != null ) {

	  chart.getYAxis().setTitleString( yTitle );

      }

      // Size it appropriately
      chart.resize(WIDTH, HEIGHT);

      // Ask the chart to draw itself to the off screen graphics context

      try {

	chart.drawGraph(g);

      } catch (Exception e) {

	Utility.debug( e );

      }

  }

}