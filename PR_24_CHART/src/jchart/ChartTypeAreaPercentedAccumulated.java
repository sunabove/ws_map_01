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

public class ChartTypeAreaPercentedAccumulated extends ChartTypeVerticalBarPercentedAccumulated {

  public ChartTypeAreaPercentedAccumulated() {
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

      DataSeries [] ds = cd.getDataSeries(); // data series

      int dsLen = ds.length;

      double accuMin = cd.getAxisMinValue();  // accumulated minimum value
//      double accuMax = accuMin;  // accumulated maximum value

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      DataSeries [] referenceSeries = cd.getReferenceSeries();

//      for( int i = 0, refLen = referenceSeries.length; i < refLen ; i ++ ) {
//
//          DataElement [] refDes = referenceSeries[ i ].getDataElements(); // reference data elements
//
//          double lmax = 0;
//
//          for( int k = 0, len = refDes.length; k < len; k ++ ) {
//
//              lmax += refDes[k].getValue();
//
//          }
//
//          accuMax = ( accuMax > lmax ) ? accuMax : lmax ;
//
//      }

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

      dataSeriesView.setShapeStyle( style );

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = paw;

      if( len > 1 ) {
          dw = ( paw + 0.0 )/( len - 1 );
      } else {
          dw = paw;
      }

      int [] dsvXpoints = new int[ len*2 ];
      int [] dsvYpoints = new int[ len*2 ];

      for(int i = 0; i < len - 1 ; i ++ ) {

          // sets current value

          DataElementView currDev = devs[ i ] ; // data element view

          currDev.setShapeStyle( null );

          double currVal = currDev.getDataElement().getValue();

          double currBaseVal = accuMin;

          DataSeries currRefSeries = referenceSeries[ i ];

          double currSum = currRefSeries.getAbsoluteSum();

          DataElement [] currRefDes = currRefSeries.getDataElements(); // reference data elements

          for( int k = 0; k < oci; k ++ ) {

               currBaseVal += currRefDes[k].getValue();

          }

          double currAccuH = pah*currBaseVal/currSum;

          double currH = pah*currVal/currSum;

          double currX = (int)(pax + dw*i);

          double currY = baseY - currAccuH - currH;

          // end of setting current values

          // sets next values

          DataElementView nextDev = devs[ i + 1 ] ; // data element view

          double nextVal = nextDev.getDataElement().getValue();

          double nextBaseVal = accuMin;

          DataSeries nextRefSeries = referenceSeries[ i + 1 ];

          double nextSum = nextRefSeries.getAbsoluteSum();

          DataElement [] nextRefDes = nextRefSeries.getDataElements(); // reference data elements

          for( int k = 0; k < oci; k ++ ) {

               nextBaseVal += nextRefDes[k].getValue();

          }

          double nextAccuH = pah*nextBaseVal/nextSum;

          double nextH = pah*nextVal/nextSum;

          double nextX = currX + dw;

          double nextY = baseY - nextAccuH - nextH;

          // end of setting next values


          // setting data element view shape

          Polygon devPoly = new Polygon();

          devPoly.addPoint( (int) currX, (int)( baseY - currAccuH ) );
          devPoly.addPoint( (int) currX, (int) currY );
          devPoly.addPoint( (int) nextX, (int) nextY );
          devPoly.addPoint( (int) nextX, (int)( baseY - nextAccuH ) );

          currDev.setShape( devPoly );

          // end of setting data element view shape

          // settting data series view shape

          dsvXpoints[ i ] = (int) currX;
          dsvYpoints[ i ] = (int) currY;

          dsvXpoints[ 2*len - i - 1 ] = (int) currX;
          dsvYpoints[ 2*len - i - 1 ] = (int) ( baseY - currAccuH );

          if( i == len - 2 ) {

             dsvXpoints[ i + 1 ] = (int) nextX;
             dsvYpoints[ i + 1 ] = (int) nextY;

             dsvXpoints[ 2*len - i - 2 ] = (int) nextX;
             dsvYpoints[ 2*len - i - 2 ] = (int) ( baseY - nextAccuH );

          }

          // end of setting data series view shape

          currDev.setShape( devPoly );

      }

      Polygon dsvPoly = new Polygon( dsvXpoints, dsvYpoints, len * 2 );

      dataSeriesView.setShape( dsvPoly );

   }

   public Rectangle [] getSelectedMarks(DataElementView dataElementView) {
      return dataElementView.getSelectedMarksLineType();
   }

}