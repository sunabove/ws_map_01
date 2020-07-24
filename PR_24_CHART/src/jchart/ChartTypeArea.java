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

public class ChartTypeArea extends ChartType2D {

  public ChartTypeArea() {
  }

   /**
   * returns main grid points for vertical chart type
   */
  public PointedObject [] getItemAxisMainGridPoints(ItemAxis itemAxis) {

     Chart chart = itemAxis.getChart();

     ChartData cd = chart.getChartData();  // cahrt data

     String [] refNames = cd.getReferenceNames(); // reference names

     int len = refNames.length;

     PointedObject [] points = new PointedObject[ len ];

     Rectangle area = itemAxis.getArea();

     int dw = (len > 1 ) ? area.width/(len -1) : area.width/len;  // points width

     int cenX, cenY = area.y + area.height/2;

     for(int i = 0; i < len; i ++ ) {
          String value = refNames[ i ];

          cenX = area.x + (int)( dw*i );

          points[i] = new PointedObject( cenX, cenY, value );

     }

     return points;

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

      Color fillColor = getChartColor( oci ); // DataElementView filling color

      ShapeStyle style = new ShapeStyle( fillColor, Color.black ); // DataElementView Shape Style

      dataSeriesView.setShapeStyle( style );

      Point pal = pa.getLocation(); // paint area location
      int pah = pa.getHeight(); // paint area height

      int baseY = pal.y + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      int width = (int) (pa.getWidth());

      if( len > 1 ) {
          width = (int)(width/( len - 1.0 ));
      }

      Polygon dsvPoly = new Polygon();

      for(int i = 0; i < len - 1; i ++ ) {

          DataElementView currDev = devs[ i ] ; // current data element view
          DataElementView nextDev = devs[ i + 1 ]; // next data element view

          currDev.setShapeStyle( null );

          int currX = (int)(pal.x + width*i);

          int currY = baseY - (int)(pah*(currDev.getDataElement().getValue() - min)/(max - min));

          int nextX = currX + width;

          int nextY = baseY - (int)(pah*(nextDev.getDataElement().getValue() - min)/(max - min));

          // setting data element view shape
          Polygon devPoly = new Polygon();

          devPoly.addPoint( currX, baseY );
          devPoly.addPoint( currX, currY );
          devPoly.addPoint( nextX, nextY );
          devPoly.addPoint( nextX, baseY );

//          currDev.setShape( devPoly );
          // end of setting data element view shape

          // settting data series view shape

          if( i == 0 ) {
              dsvPoly.addPoint( currX, baseY );
          }

          dsvPoly.addPoint( currX, currY );

          if( i == len - 2 ) {
             dsvPoly.addPoint( nextX, nextY );
             dsvPoly.addPoint( nextX, baseY );
          }

          // end of setting data series view shape
      }

      dataSeriesView.setShape( dsvPoly );

   }

   public Rectangle [] getSelectedMarks(DataElementView dataElementView) {
      return dataElementView.getSelectedMarksLineType();
   }

}