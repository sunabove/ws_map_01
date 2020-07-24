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

public class ChartTypeBendLinePercentedAccumulated extends ChartTypeVerticalBarPercentedAccumulated implements LinearChartType, FeedbackChartType {

  public ChartTypeBendLinePercentedAccumulated() {
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

     int pw = area.width/len;  // points width
     int cenX, cenY = area.y + area.height/2;

     for(int i = 0; i < len; i ++ ) {
          String value = "" + ( i + 1 );

          cenX = area.x + (int)( pw*( i + 0.5) );

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

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, color ); // DataElementView Shape Style

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = ( paw + 0.0 )/( len );

      DataSeries [] referenceSeries = cd.getReferenceSeries();

      for(int i = 0; i < len - 1 ; i ++ ) {

          // sets current value

          DataElementView currDev = devs[ i ] ; // data element view

          currDev.setShapeStyle( style );

          double currVal = currDev.getDataElement().getValue();

          DataSeries currRefSeries = referenceSeries[ i ];

          DataElement [] currRefDes = currRefSeries.getDataElements(); // reference data elements

          double currSum = currRefSeries.getAbsoluteSum();

          for( int k = 0; k < oci; k ++ ) {

               currVal += currRefDes[k].getValue();

          }

          double currY = baseY - pah*currVal/currSum;

          double currX = pax + dw*(i + 0.5);

          // end of setting current value

          // sets next value

          DataElementView nextDev = devs[ i + 1 ] ; // data element view

          double nextVal = nextDev.getDataElement().getValue();

          DataSeries nextRefSeries = referenceSeries[ i + 1 ];

          DataElement [] nextRefDes = nextRefSeries.getDataElements(); // reference data elements

          double nextSum = nextRefSeries.getAbsoluteSum();

          for( int k = 0; k < oci; k ++ ) {

               nextVal += nextRefDes[k].getValue();

          }

          double nextY = baseY - pah*nextVal/nextSum;

          double nextX = pax + dw*(i + 1.5);

          Shape shape = new Line2D.Double( currX, currY, nextX, nextY );

          currDev.setShape( shape );
      }

   }

}