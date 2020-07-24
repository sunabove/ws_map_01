package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class ChartTypeBendLineSymbolic extends ChartTypeBendLine implements FeedbackChartType {

  public ChartTypeBendLineSymbolic() {
  }

  /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      ChartData cd = chart.getChartData();  // cahrt data

      double min = cd.getAxisMinValue();  // minimum value
      double max = cd.getAxisMaxValue();  // maximum value

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, color ); // DataElementView Shape Style

      Point pal = pa.getLocation(); // paint area location
      int pah = pa.getHeight(); // paint area height

      int baseY = pal.y + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      int width = (int) ( pa.getWidth() /(bvTypeNum + 1.0)/(len + 0.0) ); // DataElementView width

      for(int i = 0; i < len - 1; i ++ ) {

          DataElementView currDev = devs[ i ] ; // current data element view
          DataElementView nextDev = devs[ i + 1 ]; // next data element view

          currDev.setShapeStyle( style );

          int currX = (int)(pal.x + width/2.0 + width + width*(bvTypeNum + 1.0)*i);

          int currY = baseY - (int)(pah*(currDev.getDataElement().getValue() - min)/(max - min));

          int nextX = (int)(pal.x + width/2.0 + width + width*(bvTypeNum + 1.0)*(i + 1));

          int nextY = baseY - (int)(pah*(nextDev.getDataElement().getValue() - min)/(max - min));

          Line2D line = new Line2D.Double( currX, currY, nextX, nextY );

          currDev.setShape( line );

          if( i == 0 ) {

             Shape currSymbolShape = getDataElementIcon( oci, currX, currY );

             SymbolicChartElement currSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, currSymbolShape, style );

             currDev.addChild( currSymbol );


          } else {

             Shape currSymbolShape = getDataElementIcon( oci, currX, currY );

             SymbolicChartElement currSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, currSymbolShape, style );

             Shape nextSymbolShape = getDataElementIcon( oci, nextX, nextY );

             SymbolicChartElement nextSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, nextSymbolShape, style );

             currDev.addChild( currSymbol );

             currDev.addChild( nextSymbol );

          }

      }

   }

}